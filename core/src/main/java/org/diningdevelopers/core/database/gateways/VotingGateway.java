package org.diningdevelopers.core.database.gateways;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.diningdevelopers.core.business.persistence.VotingPersistence;
import org.diningdevelopers.core.database.dao.VotingDao;
import org.diningdevelopers.entity.Location;
import org.diningdevelopers.entity.User;
import org.diningdevelopers.entity.Vote;

@Stateless
public class VotingGateway implements VotingPersistence {
	
	@Inject
	private VotingDao votingDao;

	@Override
	public void removeAllVotes() {
		votingDao.removeAllVotes();

	}

	@Override
	public Vote findLatestVote(User developer, Location l) {
		return votingDao.findLatestVote(developer, l);
	}

	@Override
	public void removeVotes(User developer) {
		votingDao.removeVotes(developer);
	}

	@Override
	public void save(Vote vote) {
		votingDao.save(vote);
	}

	@Override
	public List<Vote> findLatestVotes(User d) {
		return votingDao.findLatestVotes(d);
	}

}
