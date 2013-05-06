package org.diningdevelopers.business.persistence;

import java.util.List;

import org.diningdevelopers.business.model.Location;
import org.diningdevelopers.business.model.User;
import org.diningdevelopers.business.model.Vote;

public interface VotingPersistence {

	void removeAllVotes();

	Vote findLatestVote(User user, Location l);

	void removeVotes(User user);

	void save(Vote vote);

	List<Vote> findLatestVotesForUser(User d);

	List<Vote> findLatesVotes();

}
