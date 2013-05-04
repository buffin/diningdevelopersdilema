package org.diningdevelopers.business.interactor;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.diningdevelopers.business.boundary.VoteBoundary;
import org.diningdevelopers.business.model.Event;
import org.diningdevelopers.business.model.Location;
import org.diningdevelopers.business.model.User;
import org.diningdevelopers.business.model.Vote;
import org.diningdevelopers.business.persistence.AuditPersistence;
import org.diningdevelopers.business.persistence.EventPersistence;
import org.diningdevelopers.business.persistence.LocationPersistence;
import org.diningdevelopers.business.persistence.UserPersistence;
import org.diningdevelopers.business.persistence.VotingPersistence;
import org.diningdevelopers.business.responsemodels.VotesOfUserResponseModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Stateless
public class VoteInteractor implements VoteBoundary, Serializable {

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
			if (newVoteValue == null) {
				newVoteValue = 0;
			}
			
			Vote voteToSave = null;
			
			if (needToCreateNewVote(loadedVote)) {
				voteToSave = new Vote();
				voteToSave.setLocation(location);
				voteToSave.setDeveloper(developer);
				voteToSave.setDate(new Date());
				voteToSave.setVote(newVoteValue);

				String auditMessage = "%s hat sein Voting für %s auf %d gesetzt";
				if (voteToSave.getVote() > 0) {
					auditPersistence.createAudit(username, String.format(auditMessage, username, location.getName(), vote.getVote()));
				}
				voteToSave.setEvent(event);
			} else {
				voteToSave = loadedVote;
				Integer oldVote = voteToSave.getVote();
				if (oldVote.equals(newVoteValue) == false) {
					String auditMessage = "%s hat sein Voting für %s geändert. Alt: %d, Neu: %d";
					auditPersistence.createAudit(username, String.format(auditMessage, username, location.getName(), oldVote, newVoteValue));
					voteToSave.setVote(newVoteValue);
				}
			}
			votingPersistence.save(voteToSave);
		}
	}
}
