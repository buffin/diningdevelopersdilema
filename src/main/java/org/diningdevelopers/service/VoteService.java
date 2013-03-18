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
import org.diningdevelopers.dao.TransactionHelper;
import org.diningdevelopers.dao.VotingDao;
import org.diningdevelopers.entity.User;
import org.diningdevelopers.entity.Location;
import org.diningdevelopers.entity.Vote;
import org.diningdevelopers.entity.Event;
import org.diningdevelopers.entity.VotingState;
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
	private CoordinatesParser coordinatesParser;

	@Inject
	private DateHelper dateHelper;

	@Inject
	private DecisionService decisionService;

	@Inject
	private TransactionHelper transactionHelper;

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

	public VotingState getLatestVotingState() {
		Event voting = votingDao.findLatestVoting();
		if (voting == null) {
			return VotingState.Open;
		}
		return voting.getState();
	}

	public void openVoting() {
		Event voting = new Event(Calendar.getInstance().getTime(), VotingState.Open);
		votingDao.save(voting);

		votingDao.removeAllVotes();
	}

	public void closeVoting() {
		Date today = dateHelper.getDateForTodayWithNulledHoursMinutesAndMiliseconds();
		Event voting = votingDao.findVotingForDate(today);
		if (voting == null) {
			voting = new Event(Calendar.getInstance().getTime(), VotingState.Open);
			votingDao.save(voting);
		}

		transactionHelper.lockWritePessimistic(voting);

		voting.setState(VotingState.InProgress);

		transactionHelper.flush();

		decisionService.determineResultForVoting(voting);

		voting.setState(VotingState.Closed);
	}

	public void reopenVoting() {
		Date today = dateHelper.getDateForTodayWithNulledHoursMinutesAndMiliseconds();
		Event voting = votingDao.findVotingForDate(today);

		if (voting != null) {
			voting.setState(VotingState.Open);
		}
	}

	public boolean isVotingOpen() {
		Event voting = votingDao.findLatestVoting();

		if ((voting != null) && (voting.getState() != null)) {
			return voting.getState() == VotingState.Open;
		} else {
			return false;
		}
	}
}
