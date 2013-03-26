package org.diningdevelopers.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;
import org.diningdevelopers.dao.EventDao;
import org.diningdevelopers.dao.LocationDao;
import org.diningdevelopers.dao.UserDao;
import org.diningdevelopers.dao.VotingDao;
import org.diningdevelopers.entity.Event;
import org.diningdevelopers.entity.Location;
import org.diningdevelopers.entity.User;
import org.diningdevelopers.entity.Vote;
import org.diningdevelopers.model.VoteModel;
import org.diningdevelopers.utils.CoordinatesParser;
import org.primefaces.model.map.DefaultMapModel;
import org.primefaces.model.map.LatLng;
import org.primefaces.model.map.MapModel;
import org.primefaces.model.map.Marker;
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
	private EventDao eventDao;

	@Inject
	private UserDao developerDao;

	@Inject
	private CoordinatesParser coordinatesParser;

	public List<VoteModel> getVoteModel(String username) {
		List<Location> locations = locationDao.findActive();
		List<VoteModel> result = new ArrayList<>();

		User developer = developerDao.findByUsername(username);

		for (Location l : locations) {
			VoteModel model = new VoteModel();
			model.setLocationId(l.getId());
			model.setLocationName(l.getName());
			model.setLocationDescription(l.getDescription());
			model.setLocationUrl(l.getUrl());
			model.setLocationCoordinates(l.getCoordinates());
			if (StringUtils.isNotBlank(l.getCoordinates())) {
				MapModel locationModel = new DefaultMapModel();
				LatLng coordinates = coordinatesParser.parseCoordinates(l.getCoordinates());
				locationModel.addOverlay(new Marker(coordinates));
				model.setLocationModel(locationModel);
			}

			Vote latestVote = votingDao.findLatestVote(developer, l);
			if (latestVote != null) {
				model.setVote(latestVote.getVote());
			}

			result.add(model);
		}

		return result;
	}

	private boolean needToCreateNewVote(Vote vote) {
		return vote == null;
	}

	public void removeAllVotes() {
		logger.info("Call to removeAllVotes()");
		votingDao.removeAllVotes();
	}

	public void removeVotes(String username) {
		User developer = developerDao.findByUsername(username);
		votingDao.removeVotes(developer);
		String auditMessage = "%s hat sein Voting widerrufen";
		auditService.createAudit(username, String.format(auditMessage, username));

	}

	public void save(String username, List<VoteModel> voteModels) {
		Event event = eventDao.findLatestVoting();

		for (VoteModel model : voteModels) {
			User developer = developerDao.findByUsername(username);
			Location location = locationDao.findById(model.getLocationId());
			Vote vote = votingDao.findLatestVote(developer, location);

			Integer newVote = model.getVote();
			if (newVote == null) {
				model.setVote(0);
				newVote = 0;
			}

			if (needToCreateNewVote(vote)) {
				vote = new Vote();
				vote.setLocation(location);
				vote.setDeveloper(developer);
				vote.setDate(new Date());
				vote.setVote(newVote);

				String auditMessage = "%s hat sein Voting für %s auf %d gesetzt";
				if (vote.getVote() > 0) {
					auditService.createAudit(username, String.format(auditMessage, username, location.getName(), vote.getVote()));
				}

				vote.setEvent(event);

				votingDao.save(vote);
			} else {
				Integer oldVote = vote.getVote();
				if (oldVote.equals(newVote) == false) {
					String auditMessage = "%s hat sein Voting für %s geändert. Alt: %d, Neu: %d";
					auditService.createAudit(username, String.format(auditMessage, username, location.getName(), oldVote, newVote));
					vote.setVote(newVote);
				}
			}
		}
	}
}
