package org.diningdevelopers.core.database.gateways;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.diningdevelopers.core.business.persistence.VotingPersistence;
import org.diningdevelopers.core.database.dao.VotingDao;
import org.diningdevelopers.core.database.entities.LocationEntity;
import org.diningdevelopers.core.database.entities.UserEntity;
import org.diningdevelopers.core.database.entities.VoteEntity;

@Stateless
public class VotingGateway implements VotingPersistence {
	
	@Inject
	private VotingDao votingDao;

	@Override
	public void removeAllVotes() {
		votingDao.removeAllVotes();

	}

	@Override
	public VoteEntity findLatestVote(UserEntity developer, LocationEntity l) {
		return votingDao.findLatestVote(developer, l);
	}

	@Override
	public void removeVotes(UserEntity developer) {
		votingDao.removeVotes(developer);
	}

	@Override
	public void save(VoteEntity vote) {
		votingDao.save(vote);
	}

	@Override
	public List<VoteEntity> findLatestVotes(UserEntity d) {
		return votingDao.findLatestVotes(d);
	}

}
