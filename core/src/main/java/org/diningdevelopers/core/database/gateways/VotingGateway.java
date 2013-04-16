package org.diningdevelopers.core.database.gateways;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.diningdevelopers.core.business.MappingService;
import org.diningdevelopers.core.business.model.Location;
import org.diningdevelopers.core.business.model.User;
import org.diningdevelopers.core.business.model.Vote;
import org.diningdevelopers.core.business.persistence.VotingPersistence;
import org.diningdevelopers.core.database.dao.VotingDao;
import org.diningdevelopers.core.database.entities.LocationEntity;
import org.diningdevelopers.core.database.entities.UserEntity;
import org.diningdevelopers.core.database.entities.VoteEntity;

@Stateless
public class VotingGateway implements VotingPersistence {
	
	@Inject
	private VotingDao votingDao;
	
	@Inject
	private MappingService mappingService;

	@Override
	public void removeAllVotes() {
		votingDao.removeAllVotes();

	}

	@Override
	public Vote findLatestVote(User developer, Location l) {
		VoteEntity vote = votingDao.findLatestVote(mappingService.map(developer, UserEntity.class), 
												   mappingService.map(l, LocationEntity.class));
		return mappingService.map(vote, Vote.class);
	}

	@Override
	public void removeVotes(User developer) {
		votingDao.removeVotes(mappingService.map(developer, UserEntity.class));
	}

	@Override
	public void save(Vote vote) {
		votingDao.save(mappingService.map(vote, VoteEntity.class));
	}

	@Override
	public List<Vote> findLatestVotes(User d) {
		List<VoteEntity> votes = votingDao.findLatestVotes(mappingService.map(d, UserEntity.class));
		return mappingService.mapCollection(votes, Vote.class);
	}

}
