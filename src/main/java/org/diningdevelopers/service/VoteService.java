package org.diningdevelopers.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.diningdevelopers.dao.DeveloperDao;
import org.diningdevelopers.dao.LocationDao;
import org.diningdevelopers.dao.VotingDao;
import org.diningdevelopers.entity.Developer;
import org.diningdevelopers.entity.Location;
import org.diningdevelopers.entity.Vote;
import org.diningdevelopers.model.VoteModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Stateless
public class VoteService {

	private Logger logger = LoggerFactory.getLogger(getClass());

	@Inject
	private AuditService auditService;

	@Inject
	private LocationDao locationDao;

	@Inject
	private VotingDao votingDao;

	@Inject
	private DeveloperDao developerDao;

	public List<VoteModel> getVoteModel(String username) {
		List<Location> locations = locationDao.findActive();
		List<VoteModel> result = new ArrayList<>();

		Developer developer = developerDao.findByUsername(username);

		for (Location l : locations) {
			VoteModel model = new VoteModel();
			model.setLocationId(l.getId());
			model.setLocationName(l.getName());

			Vote latestVote = votingDao.findLatestVote(developer, l);
			if (latestVote != null) {
				model.setVote(latestVote.getVote());
			}

			result.add(model);
		}

		return result;
	}

	private boolean needToCreateNewVote(Vote vote, Integer newVoteing) {
		if (vote == null) {
			return true;
		}

		if (vote.getVote().equals(newVoteing) == false) {
			return true;
		}

		return false;
	}

	public void save(String username, List<VoteModel> voteModels) {
		for (VoteModel model : voteModels) {
			Developer developer = developerDao.findByUsername(username);
			Location location = locationDao.findById(model.getLocationId());
			Vote vote = votingDao.findLatestVote(developer, location);

			if (model.getVote() == null) {
				model.setVote(0);

			}

			if (needToCreateNewVote(vote, model.getVote())) {
				Vote newVote = new Vote();
				newVote .setLocation(location);
				newVote .setDeveloper(developer);
				newVote .setDate(new Date());
				newVote .setVote(model.getVote());

				votingDao.save(newVote );

				if (vote != null) {
					auditService.createAudit(username, String.format("%s hat sein Voting für %s geändert. Alt: %d, Neu: %d", username, location.getName(), vote.getVote(), newVote.getVote()));
				}
			}
		}
	}
}
