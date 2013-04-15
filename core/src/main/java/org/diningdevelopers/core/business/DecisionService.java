package org.diningdevelopers.core.business;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.diningdevelopers.core.business.external.RandomOrgNumberGeneratorService;
import org.diningdevelopers.core.business.helper.TransactionHelper;
import org.diningdevelopers.core.business.model.Event;
import org.diningdevelopers.core.business.persistence.EventPersistence;
import org.diningdevelopers.core.business.persistence.UserPersistence;
import org.diningdevelopers.core.business.persistence.VotingPersistence;
import org.diningdevelopers.core.database.entities.EventEntity;
import org.diningdevelopers.core.database.entities.LocationEntity;
import org.diningdevelopers.core.database.entities.UserEntity;
import org.diningdevelopers.core.database.entities.VoteEntity;
import org.diningdevelopers.core.frontend.model.DecisionModel;
import org.diningdevelopers.core.frontend.model.DecisionTable;
import org.diningdevelopers.core.frontend.model.ResultModel;
import org.diningdevelopers.core.frontend.model.UserModel;

@Stateless
public class DecisionService {

	@Inject
	private UserPersistence userPersistence;

	@Inject
	private MappingService mappingService;

	@Inject
	private VotingPersistence votingPersistence;

	@Inject
	private EventPersistence eventPersistence;

	@Inject
	private RandomOrgNumberGeneratorService randomService;

	@Inject
	private NotificationService notificationService;

	@Inject
	private TransactionHelper transactionHelper;

	public DecisionTable buildDecisionTable(Date date) {
		DecisionTable decisionTable = new DecisionTable();

		List<UserEntity> developers = userPersistence.findAll();
		Map<Long, DecisionModel> decisions = new HashMap<>();

		for (UserEntity d : developers) {
			insertDeveloperPreferences(decisionTable.getDevelopers(), decisions, d);
		}

		decisionTable.setDecisions(new ArrayList<>(decisions.values()));

		updateTable(decisionTable);

		return decisionTable;
	}

	public void determineResultForVoting(Event voting) {
		DecisionTable table = buildDecisionTable(null);
		int maxValue = Math.round(table.getTotalPoints());

		try {
			int number = randomService.generateRandomNumberBetween(1, maxValue);
			voting.setResult(number);

			transactionHelper.flush();

			ResultModel resultModel = getResultModelForLatestVote();

			notificationService.notifiyParticipatingUsers(voting, table, resultModel);
		} catch (Exception e) {
			voting.setResult(-1);
		} finally {
			eventPersistence.save(voting);
		}
	}

	private DecisionModel getDecicionModel(Map<Long, DecisionModel> decisions, LocationEntity location) {
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

	public ResultModel getResultModelForLatestVote() {
		ResultModel result = new ResultModel();
		Event voting = eventPersistence.findLatestVoting();
		if (voting != null) {
			Integer random = voting.getResult();
			result.setRandomNumber(random);

			DecisionTable table = buildDecisionTable(null);

			for (DecisionModel d : table.getDecisions()) {
				if ((random >= d.getRandomRangeStart()) && (random <= d.getRandomRangeEnd())) {
					result.setLocationName(d.getLocationName());
				}
			}
		}
		return result;
	}

	private void insertDeveloperPreferences(List<UserModel> developerModels, Map<Long, DecisionModel> decisions, UserEntity d) {
		UserModel developerModel = mappingService.map(d, UserModel.class);

		List<VoteEntity> votes = votingPersistence.findLatestVotes(d);
		boolean hasVotes = false;

		for (VoteEntity v : votes) {
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
}
