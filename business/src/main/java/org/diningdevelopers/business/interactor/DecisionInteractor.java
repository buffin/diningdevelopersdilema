package org.diningdevelopers.business.interactor;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.diningdevelopers.business.EventResultEvaluator;
import org.diningdevelopers.business.NotificationService;
import org.diningdevelopers.business.boundary.DecisionBoundary;
import org.diningdevelopers.business.external.RandomOrgNumberGeneratorService;
import org.diningdevelopers.business.model.Event;
import org.diningdevelopers.business.model.Location;
import org.diningdevelopers.business.model.User;
import org.diningdevelopers.business.model.Vote;
import org.diningdevelopers.business.persistence.EventPersistence;
import org.diningdevelopers.business.persistence.UserPersistence;
import org.diningdevelopers.business.persistence.VotingPersistence;
import org.diningdevelopers.business.responsemodels.LatestVotesResponseModel;
import org.diningdevelopers.business.responsemodels.ResultReponseModel;

@Stateless
public class DecisionInteractor implements DecisionBoundary, Serializable {

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

	@Override
	public void determineResultForVoting(Event event) {
		int totalPoints = getTotalPointsForLastEvent();
		
		try {
			int number = randomService.generateRandomNumberBetween(1, totalPoints);
			event.setResult(number);
			
			List<Vote> votes = votingPersistence.findLatesVotes();
			
			Location winner = resultEvaluator.evaluateWinningLocation(number, votes);
			event.setWinningLocation(winner);

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
