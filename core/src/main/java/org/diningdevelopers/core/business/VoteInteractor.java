package org.diningdevelopers.core.business;

import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.diningdevelopers.core.business.boundary.VoteBoundary;
import org.diningdevelopers.core.business.model.Event;
import org.diningdevelopers.core.business.model.Location;
import org.diningdevelopers.core.business.model.User;
import org.diningdevelopers.core.business.model.Vote;
import org.diningdevelopers.core.business.persistence.AuditPersistence;
import org.diningdevelopers.core.business.persistence.EventPersistence;
import org.diningdevelopers.core.business.persistence.LocationPersistence;
import org.diningdevelopers.core.business.persistence.UserPersistence;
import org.diningdevelopers.core.business.persistence.VotingPersistence;
import org.diningdevelopers.core.business.responsemodels.VotesOfUserResponseModel;
import org.diningdevelopers.core.frontend.util.CoordinatesParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Stateless
public class VoteInteractor implements VoteBoundary {

	private Logger logger = LoggerFactory.getLogger(getClass());

	@Inject
	private AuditPersistence auditPersistence;

	@Inject
	private LocationPersistence locationPersistence;

	@Inject
	private VotingPersistence votingPersistence;

	@Inject
	private EventPersistence eventPersistence;

	@Inject
	private UserPersistence userPersistence;

	@Inject
	private CoordinatesParser coordinatesParser;

	@Override
	public VotesOfUserResponseModel getVotesOfUser(String username) {
		User developer = userPersistence.findByUsername(username);
		List<Vote> votes = votingPersistence.findLatestVotesForUser(developer);
		
		return new VotesOfUserResponseModel(votes);
	}

	private boolean needToCreateNewVote(Vote vote) {
		return vote == null;
	}

	@Override
	public void removeAllVotes() {
		logger.info("Call to removeAllVotes()");
		votingPersistence.removeAllVotes();
	}

	@Override
	public void removeVotes(String username) {
		User developer = userPersistence.findByUsername(username);
		votingPersistence.removeVotes(developer);
		String auditMessage = "%s hat sein Voting widerrufen";
		auditPersistence.createAudit(username, String.format(auditMessage, username));

	}

	@Override
	public void save(String username, List<Vote> votes) {
		Event event = eventPersistence.findLatestVoting();
		
		for (Vote vote : votes) {
			User developer = userPersistence.findByUsername(username);
			Location location = locationPersistence.findById(vote.getLocation().getId());
			Vote loadedVote = votingPersistence.findLatestVote(developer, location);

			Integer newVoteValue = vote.getVote();
			
			if (needToCreateNewVote(loadedVote)) {
				Vote newVote = new Vote();
				newVote.setLocation(location);
				newVote.setDeveloper(developer);
				newVote.setDate(new Date());
				newVote.setVote(newVoteValue);

				String auditMessage = "%s hat sein Voting für %s auf %d gesetzt";
				if (newVote.getVote() > 0) {
					auditPersistence.createAudit(username, String.format(auditMessage, username, location.getName(), vote.getVote()));
				}

				newVote.setEvent(event);

				votingPersistence.save(newVote);
			} else {
				Integer oldVote = loadedVote.getVote();
				if (oldVote.equals(newVoteValue) == false) {
					String auditMessage = "%s hat sein Voting für %s geändert. Alt: %d, Neu: %d";
					auditPersistence.createAudit(username, String.format(auditMessage, username, location.getName(), oldVote, newVoteValue));
					loadedVote.setVote(newVoteValue);
				}
			}
		}
	}
}
