package org.diningdevelopers.core.business.persistence;

import java.util.List;

import org.diningdevelopers.core.database.entities.LocationEntity;
import org.diningdevelopers.core.database.entities.UserEntity;
import org.diningdevelopers.core.database.entities.VoteEntity;

public interface VotingPersistence {

	void removeAllVotes();

	VoteEntity findLatestVote(UserEntity developer, LocationEntity l);

	void removeVotes(UserEntity developer);

	void save(VoteEntity vote);

	List<VoteEntity> findLatestVotes(UserEntity d);

}
