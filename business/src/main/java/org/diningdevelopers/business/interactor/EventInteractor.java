package org.diningdevelopers.business.interactor;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.diningdevelopers.business.boundary.DecisionBoundary;
import org.diningdevelopers.business.boundary.EventBoundary;
import org.diningdevelopers.business.model.Event;
import org.diningdevelopers.business.model.VotingState;
import org.diningdevelopers.business.persistence.EventPersistence;
import org.diningdevelopers.business.persistence.VotingPersistence;
import org.diningdevelopers.util.DateHelper;

@Stateless
public class EventInteractor implements EventBoundary, Serializable {

	@Inject
	private DateHelper dateHelper;

	@Inject
	private VotingPersistence votingPersistence;
	
	@Inject
	private EventPersistence eventPersistence;

	@Inject
	private DecisionBoundary decisionService;

	@Override
	public void reopenVoting() {
		Date today = dateHelper.getDateForTodayWithNulledHoursMinutesAndMiliseconds();
		Event event = eventPersistence.findVotingForDate(today);

		if (event != null) {
			event.setState(VotingState.Open);
			eventPersistence.save(event);
		}
	}

	@Override
	public String getLatestEventState() {
		Event event = eventPersistence.findLatestVoting();
		
		if (event == null) {
			return "noch keine events";
		}

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

	@Override
	public void openVoting() {
		Event voting = new Event(Calendar.getInstance().getTime(), VotingState.Open);
		eventPersistence.save(voting);

		votingPersistence.removeAllVotes();
	}

	@Override
	public void closeVoting() {
		Date today = dateHelper.getDateForTodayWithNulledHoursMinutesAndMiliseconds();
		Event event = eventPersistence.findVotingForDate(today);
		if (event == null) {
			event = new Event(Calendar.getInstance().getTime(), VotingState.Open);
			eventPersistence.save(event);
		}

		// TODO: this works only on entities, move to persistence?
		// transactionHelper.lockWritePessimistic(voting);

		event.setState(VotingState.InProgress);

		// transactionHelper.flush();

		decisionService.determineResultForVoting(event);

		event.setState(VotingState.Closed);
		
		eventPersistence.save(event);
	}

	@Override
	public boolean isVotingOpen() {
		Event voting = eventPersistence.findLatestVoting();

		if ((voting != null) && (voting.getState() != null)) {
			return voting.getState() == VotingState.Open;
		} else {
			return false;
		}
	}

	@Override
	public boolean isLatestEventClosed() {
		Event event = eventPersistence.findLatestVoting();

		if ((event != null) && (event.getState() == VotingState.Closed)) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public Event getLatestEvent() {
		return eventPersistence.findLatestVoting();
	}

}
