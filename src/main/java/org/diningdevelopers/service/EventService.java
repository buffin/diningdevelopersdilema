package org.diningdevelopers.service;

import java.util.Calendar;
import java.util.Date;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.diningdevelopers.dao.EventDao;
import org.diningdevelopers.dao.TransactionHelper;
import org.diningdevelopers.dao.VotingDao;
import org.diningdevelopers.entity.Event;
import org.diningdevelopers.entity.VotingState;
import org.diningdevelopers.utils.DateHelper;

@Stateless
public class EventService {

	@Inject
	private DateHelper dateHelper;

	@Inject
	private VotingDao votingDao;

	@Inject
	private EventDao eventDao;

	@Inject
	private TransactionHelper transactionHelper;

	@Inject
	private DecisionService decisionService;

	public void reopenVoting() {
		Date today = dateHelper.getDateForTodayWithNulledHoursMinutesAndMiliseconds();
		Event voting = eventDao.findVotingForDate(today);

		if (voting != null) {
			voting.setState(VotingState.Open);
		}
	}

	public String getLatestEventState() {
		Event event = eventDao.findLatestVoting();

		VotingState state = event.getState();

		if (state == VotingState.Open) {
			return "aktiv";
		} else if (state == VotingState.Closed) {
			return "beendet";
		} else if (state == VotingState.InProgress) {
			return "Warte auf Random.org";
		} else {
			return "unbekannt";
		}
	}

	public void openVoting() {
		Event voting = new Event(Calendar.getInstance().getTime(), VotingState.Open);
		eventDao.save(voting);

		votingDao.removeAllVotes();
	}

	public void closeVoting() {
		Date today = dateHelper.getDateForTodayWithNulledHoursMinutesAndMiliseconds();
		Event voting = eventDao.findVotingForDate(today);
		if (voting == null) {
			voting = new Event(Calendar.getInstance().getTime(), VotingState.Open);
			eventDao.save(voting);
		}

		transactionHelper.lockWritePessimistic(voting);

		voting.setState(VotingState.InProgress);

		transactionHelper.flush();

		decisionService.determineResultForVoting(voting);

		voting.setState(VotingState.Closed);
	}

	public boolean isVotingOpen() {
		Event voting = eventDao.findLatestVoting();

		if ((voting != null) && (voting.getState() != null)) {
			return voting.getState() == VotingState.Open;
		} else {
			return false;
		}
	}

	public boolean isLatestEventClosed() {
		Event event = eventDao.findLatestVoting();

		if (event.getState() == VotingState.Closed) {
			return true;
		} else {
			return false;
		}
	}


}
