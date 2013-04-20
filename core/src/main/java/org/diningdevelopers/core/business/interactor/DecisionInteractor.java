package org.diningdevelopers.core.business.interactor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.diningdevelopers.core.business.EventResultEvaluator;
import org.diningdevelopers.core.business.NotificationService;
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
import org.diningdevelopers.core.business.responsemodels.LatestVotesResponseModel;
import org.diningdevelopers.core.business.responsemodels.ResultReponseModel;

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
	public LatestVotesResponseModel findLatestVotesOfUsers() {
		Map<User, List<Vote>> userToVotes = new HashMap<User, List<Vote>>();
		List<User> developers = userPersistence.findAll();
		for (User d : developers) {
			userToVotes.put(d, votingPersistence.findLatestVotesForUser(d));
		}
		
		return new LatestVotesResponseModel(userToVotes);
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
		LatestVotesResponseModel model = findLatestVotesOfUsers();
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
	public ResultReponseModel getResultModelForLatestVote() {
		ResultReponseModel result = null;
		Event voting = eventPersistence.findLatestVoting();
		if (voting != null) {
			result = new ResultReponseModel(voting.getResult(), voting.getWinningLocation().getName());
		}
		return result;
	}

}
