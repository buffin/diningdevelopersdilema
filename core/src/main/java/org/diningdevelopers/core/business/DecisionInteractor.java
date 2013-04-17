package org.diningdevelopers.core.business;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.diningdevelopers.core.business.boundary.DecisionBoundary;
import org.diningdevelopers.core.business.external.RandomOrgNumberGeneratorService;
import org.diningdevelopers.core.business.helper.TransactionHelper;
import org.diningdevelopers.core.business.model.Event;
import org.diningdevelopers.core.business.model.Location;
import org.diningdevelopers.core.business.model.User;
import org.diningdevelopers.core.business.model.Vote;
import org.diningdevelopers.core.business.persistence.EventPersistence;
import org.diningdevelopers.core.business.persistence.UserPersistence;
import org.diningdevelopers.core.business.persistence.VotingPersistence;
import org.diningdevelopers.core.business.requestmodels.LatestVotesRequestModel;
import org.diningdevelopers.core.business.requestmodels.ResultRequestModel;

@Stateless
public class DecisionInteractor implements DecisionBoundary {

	@Inject
	private UserPersistence userPersistence;

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
	
	@Inject
	private EventResultEvaluator resultEvaluator;

	@Override
	public LatestVotesRequestModel findLatestVotesOfUsers() {
		Map<User, List<Vote>> userToVotes = new HashMap<User, List<Vote>>();
		List<User> developers = userPersistence.findAll();
		for (User d : developers) {
			userToVotes.put(d, votingPersistence.findLatestVotesForUser(d));
		}
		
		return new LatestVotesRequestModel(userToVotes);
	}

	public void determineResultForVoting(Event event) {
		int totalPoints = getTotalPointsForLastEvent();
		
		try {
			int number = randomService.generateRandomNumberBetween(1, totalPoints);
			event.setResult(number);
			
			List<Vote> votes = votingPersistence.findLatesVotes();
			
			Location winner = resultEvaluator.evaluateWinningLocation(number, votes);
			event.setWinningLocation(winner);

			transactionHelper.flush();

			notificationService.notifiyParticipatingUsers(event);
		} catch (Exception e) {
			event.setResult(-1);
		} finally {
			eventPersistence.save(event);
		}
	}

	private int getTotalPointsForLastEvent() {
		LatestVotesRequestModel model = findLatestVotesOfUsers();
		int total = 0;
		for (User user : model.getUsers()) {
			int sum = 0;
			for (Vote vote : model.getVotesForUser(user)) {
				sum += vote.getVote();
			}
			total += sum;
		}
		return total;
	}

	@Override
	public ResultRequestModel getResultModelForLatestVote() {
		ResultRequestModel result = null;
		Event voting = eventPersistence.findLatestVoting();
		if (voting != null) {
			result = new ResultRequestModel(voting.getResult(), voting.getWinningLocation().getName());
		}
		return result;
	}

}
