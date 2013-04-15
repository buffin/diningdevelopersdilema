package org.diningdevelopers.core.business.persistence;

import java.util.List;

import org.diningdevelopers.core.database.entities.Location;
import org.diningdevelopers.core.database.entities.User;
import org.diningdevelopers.core.database.entities.Vote;

public interface VotingPersistence {

	void removeAllVotes();

	Vote findLatestVote(User developer, Location l);

	void removeVotes(User developer);

	void save(Vote vote);

	List<Vote> findLatestVotes(User d);

}
