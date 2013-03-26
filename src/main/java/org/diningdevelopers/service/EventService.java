package org.diningdevelopers.service;

import java.util.Calendar;
import java.util.Date;

import javax.ejb.Stateless;
import javax.inject.Inject;

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
	private TransactionHelper transactionHelper;

	@Inject
	private DecisionService decisionService;

	public void reopenVoting() {
		Date today = dateHelper.getDateForTodayWithNulledHoursMinutesAndMiliseconds();
		Event voting = votingDao.findVotingForDate(today);

		if (voting != null) {
			voting.setState(VotingState.Open);
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

	public boolean isVotingOpen() {
		Event voting = votingDao.findLatestVoting();

		if ((voting != null) && (voting.getState() != null)) {
			return voting.getState() == VotingState.Open;
		} else {
			return false;
		}
	}


}
