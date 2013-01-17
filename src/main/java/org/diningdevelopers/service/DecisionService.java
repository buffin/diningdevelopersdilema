package org.diningdevelopers.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.diningdevelopers.dao.DeveloperDao;
import org.diningdevelopers.dao.VotingDao;
import org.diningdevelopers.entity.Developer;
import org.diningdevelopers.entity.Location;
import org.diningdevelopers.entity.Vote;
import org.diningdevelopers.model.DecisionModel;
import org.diningdevelopers.model.DecisionTable;
import org.diningdevelopers.model.DeveloperModel;

@Stateless
public class DecisionService {

	@Inject
	private DeveloperDao developerDao;

	@Inject
	private DeveloperConverter developerConverter;

	@Inject
	private VotingDao votingDao;

	public DecisionTable buildDecisionTable(Date date) {
		DecisionTable decisionTable = new DecisionTable();

		List<Developer> developers = developerDao.findParticipating();
		Map<Long, DecisionModel> decisions = new HashMap<>();

		for (Developer d : developers) {
			insertDeveloperPreferences(decisionTable.getDevelopers(), decisions, d);
		}

		decisionTable.setDecisions(new ArrayList<>(decisions.values()));

		updateTable(decisionTable);

		return decisionTable;
	}

	private DecisionModel getDecicionModel(Map<Long, DecisionModel> decisions, Location location) {
		Long locationId = location.getId();

		DecisionModel decisionModel = decisions.get(locationId);

		if (decisionModel == null) {
			decisionModel = new DecisionModel();
			decisionModel.setVotings(new HashMap<Long, Float>());
			decisionModel.setLocationId(locationId);
			decisionModel.setLocationName(location.getName());

			decisions.put(locationId, decisionModel);

		}
		return decisionModel;
	}

	private Integer getVotedPoints(List<Vote> votes) {
		Integer result = 0;
		for (Vote v : votes) {
			if (v.getVote() != null) {
				result += v.getVote();
			}
		}

		return result;
	}

	private void insertDeveloperPreferences(List<DeveloperModel> developerModels, Map<Long, DecisionModel> decisions, Developer d) {
		DeveloperModel developerModel = developerConverter.toModel(d);

		developerModels.add(developerModel);

		List<Vote> votes = votingDao.findLatestVotes(d);

		Integer sum = getVotedPoints(votes);

		for (Vote v : votes) {
			if (v.getVote() != null) {
				Float votingPercent = (v.getVote().floatValue() / sum)  * 100;

				DecisionModel decisionModel = getDecicionModel(decisions, v.getLocation());

				decisionModel.getVotings().put(developerModel.getId(), votingPercent);
			}
		}
	}

	private void updateTable(DecisionTable decisionTable) {
		float totalSum = 0f;

		for (DecisionModel m : decisionTable.getDecisions()) {
			float sum = 0f;

			m.setRandomRangeStart(totalSum);

			for (Float i : m.getVotings().values()) {
				sum += i;
			}

			m.setPointsTotal(sum);

			totalSum += sum;
			m.setRandomRangeEnd(totalSum);
		}

		decisionTable.setTotalPoints(totalSum);
	}
}
