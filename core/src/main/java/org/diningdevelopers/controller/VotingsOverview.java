package org.diningdevelopers.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.enterprise.context.SessionScoped;
import javax.faces.event.ComponentSystemEvent;
import javax.inject.Inject;
import javax.inject.Named;

import org.diningdevelopers.core.business.MappingService;
import org.diningdevelopers.core.business.boundary.DecisionBoundary;
import org.diningdevelopers.core.business.boundary.EventBoundary;
import org.diningdevelopers.core.business.model.Location;
import org.diningdevelopers.core.business.model.User;
import org.diningdevelopers.core.business.model.Vote;
import org.diningdevelopers.core.business.responsemodels.LatestVotesResponseModel;
import org.diningdevelopers.core.business.responsemodels.ResultReponseModel;
import org.diningdevelopers.core.frontend.model.DecisionModel;
import org.diningdevelopers.core.frontend.model.DecisionTable;
import org.diningdevelopers.core.frontend.model.ResultModel;
import org.diningdevelopers.core.frontend.model.UserModel;
import org.diningdevelopers.utils.FacesUtils;
import org.primefaces.model.chart.PieChartModel;

@Named
@SessionScoped
public class VotingsOverview implements Serializable {

	@Inject
	private DecisionBoundary decisionBoundary;

	@Inject
	private EventBoundary eventBoundary;
	
	@Inject
	private MappingService mappingService;

	private DecisionTable decisionTable;

	private PieChartModel pieModel;

	public DecisionTable getDecisionTable() {
		return decisionTable;
	}

	public void init(ComponentSystemEvent event) {
		if (FacesUtils.isNotPostback()) {
			update();
		}
	}

	public void setDecisionTable(DecisionTable decisionTable) {
		this.decisionTable = decisionTable;
	}

	public String update() {
		decisionTable = buildDecisionTable(decisionBoundary.findLatestVotesOfUsers());
		updatePieModel(decisionTable);
		return null;
	}
	
	private DecisionTable buildDecisionTable(LatestVotesResponseModel requestModel) {
		DecisionTable decisionTable = new DecisionTable();

		Collection<User> developers = requestModel.getUsers();
		Map<Long, DecisionModel> decisions = new HashMap<>();

		for (User d : developers) {
			List<Vote> votes = requestModel.getVotesForUser(d);
			insertDeveloperPreferences(decisionTable.getDevelopers(), decisions, d, votes);
		}

		decisionTable.setDecisions(new ArrayList<>(decisions.values()));

		updateTable(decisionTable);

		return decisionTable;
	}
	
	private void updateTable(DecisionTable decisionTable) {
		Collections.sort(decisionTable.getDecisions());

		int totalSum = 0;

		for (DecisionModel m : decisionTable.getDecisions()) {
			int sum = 0;

			m.setRandomRangeStart(totalSum + 1);

			for (Integer i : m.getVotings().values()) {
				sum += i;
			}

			m.setPointsTotal(sum);

			totalSum += sum;
			m.setRandomRangeEnd(totalSum);
		}

		decisionTable.setTotalPoints(totalSum);
	}
	
	private void insertDeveloperPreferences(List<UserModel> developerModels, Map<Long, DecisionModel> decisions, User d, List<Vote> votes) {
		UserModel developerModel = mappingService.map(d, UserModel.class);

		boolean hasVotes = false;

		for (Vote v : votes) {
			if ((v.getVote() != null) && (v.getVote().intValue() != 0)) {
				DecisionModel decisionModel = getDecicionModel(decisions, v.getLocation());

				decisionModel.getVotings().put(developerModel.getId(), v.getVote());

				hasVotes = true;
			}
		}

		if (hasVotes == true) {
			developerModels.add(developerModel);
		}
	}
	
	private DecisionModel getDecicionModel(Map<Long, DecisionModel> decisions, Location location) {
		Long locationId = location.getId();

		DecisionModel decisionModel = decisions.get(locationId);

		if (decisionModel == null) {
			decisionModel = new DecisionModel();
			decisionModel.setVotings(new HashMap<Long, Integer>());
			decisionModel.setLocationId(locationId);
			decisionModel.setLocationName(location.getName());

			decisions.put(locationId, decisionModel);

		}
		return decisionModel;
	}

	public PieChartModel getPieModel() {
		return pieModel;
	}

	private void updatePieModel(DecisionTable decisionTable) {
		pieModel = new PieChartModel();
		for (DecisionModel d : decisionTable.getDecisions()) {
			pieModel.getData().put(d.getLocationName(), d.getPointsTotal());
		}
	} 

	public String getVotingState() {
		return eventBoundary.getLatestEventState();
	}

	public boolean isVotingClosed() {
		return eventBoundary.isLatestEventClosed();
	}

	public ResultModel getResultModel() {
		ResultReponseModel result = decisionBoundary.getResultModelForLatestVote();
		ResultModel resultModel = new ResultModel();
		resultModel.setLocationName(result.getLocationName());
		resultModel.setRandomNumber(result.getRandom());
		return resultModel;
	}

}
