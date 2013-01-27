package org.diningdevelopers.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;
import org.diningdevelopers.dao.DeveloperDao;
import org.diningdevelopers.dao.LocationDao;
import org.diningdevelopers.dao.VotingDao;
import org.diningdevelopers.entity.Developer;
import org.diningdevelopers.entity.Location;
import org.diningdevelopers.entity.Vote;
import org.diningdevelopers.entity.Voting;
import org.diningdevelopers.model.VoteModel;
import org.diningdevelopers.utils.CoordinatesParser;
import org.diningdevelopers.utils.DateHelper;
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
	private DeveloperDao developerDao;
	
	@Inject
	private DecisionService decisionService;
	
	@Inject
	private CoordinatesParser coordinatesParser;
	
	@Inject
	private DateHelper dateHelper;

	public List<VoteModel> getVoteModel(String username) {
		List<Location> locations = locationDao.findActive();
		List<VoteModel> result = new ArrayList<>();

		Developer developer = developerDao.findByUsername(username);

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
		votingDao.removeAllVotes();
	}

	public void removeVotes(String username) {
		Developer developer = developerDao.findByUsername(username);
		votingDao.removeVotes(developer);
	}

	public void save(String username, List<VoteModel> voteModels) {
		for (VoteModel model : voteModels) {
			Developer developer = developerDao.findByUsername(username);
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
	
	public boolean isVotingClosed() {
		Voting voting = votingDao.findLatestVoting();
		if (voting == null) {
			logger.warn("No voting found at all!");
			return true;
		}
		return voting.getClosed();
		
	}
		
	public void openVoting() {
		Voting voting = new Voting(Calendar.getInstance().getTime(), false);
		votingDao.save(voting);
		
		votingDao.removeAllVotes();
	}
	
	public void closeVoting() {
		Date today = dateHelper.getDateForTodayWithNulledHoursMinutesAndMiliseconds();
		Voting voting = votingDao.findVotingForDate(today);
		if (voting == null) {
			Voting closedVoting = new Voting(Calendar.getInstance().getTime(), true);
			votingDao.save(closedVoting);
		} else {
			voting.setClosed(true);
			votingDao.save(voting);
		}
		
		decisionService.determineResultForVoting();
	}
}
