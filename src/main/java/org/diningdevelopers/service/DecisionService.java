package org.diningdevelopers.service;

import java.util.ArrayList;
import java.util.Collections;
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
import org.diningdevelopers.entity.Voting;
import org.diningdevelopers.model.DecisionModel;
import org.diningdevelopers.model.DecisionTable;
import org.diningdevelopers.model.DeveloperModel;
import org.diningdevelopers.model.ResultModel;
import org.diningdevelopers.service.external.RandomOrgNumberGeneratorService;
import org.diningdevelopers.utils.DateHelper;

@Stateless
public class DecisionService {

	@Inject
	private DeveloperDao developerDao;

	@Inject
	private DeveloperConverter developerConverter;

	@Inject
	private VotingDao votingDao;
	
	@Inject
	private RandomOrgNumberGeneratorService randomService;
	
	@Inject
	private DateHelper dateHelper;

	public DecisionTable buildDecisionTable(Date date) {
		DecisionTable decisionTable = new DecisionTable();

		List<Developer> developers = developerDao.findAll();
		Map<Long, DecisionModel> decisions = new HashMap<>();

		for (Developer d : developers) {
			insertDeveloperPreferences(decisionTable.getDevelopers(), decisions, d);
		}

		decisionTable.setDecisions(new ArrayList<>(decisions.values()));

		updateTable(decisionTable);

		return decisionTable;
	}
	
	public void determineResultForVoting() {
		DecisionTable table = buildDecisionTable(null);
		int maxValue = Math.round(table.getTotalPoints());
		
		Date today = dateHelper.getDateForTodayWithNulledHoursMinutesAndMiliseconds();
		
		Voting voting = votingDao.findVotingForDate(today);
		
		try {
			int number = randomService.generateRandomNumberBetween(0, maxValue);
			voting.setResult(number);
		} catch (Exception e) {
			voting.setResult(-1);
		} finally {
			votingDao.save(voting);
		}
	}
	
	public ResultModel getResultModelForLatestVote() {
		ResultModel result = new ResultModel();
		Voting voting = votingDao.findLatestVoting();
		if (voting != null) {
			result.setRandomNumber(voting.getResult());
		}
		return result;
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

		List<Vote> votes = votingDao.findLatestVotes(d);
		Integer sum = getVotedPoints(votes);
		boolean hasVotes = false;

		for (Vote v : votes) {
			if ((v.getVote() != null) && (v.getVote().intValue() != 0)) {
				Float votingPercent = (v.getVote().floatValue() / sum) * 100;

				DecisionModel decisionModel = getDecicionModel(decisions, v.getLocation());

				decisionModel.getVotings().put(developerModel.getId(), votingPercent);

				hasVotes = true;
			}
		}

		if (hasVotes == true) {
			developerModels.add(developerModel);
		}
	}

	private void updateTable(DecisionTable decisionTable) {
		Collections.sort(decisionTable.getDecisions());

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
