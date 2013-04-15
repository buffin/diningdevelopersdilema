package org.diningdevelopers.timer;

import javax.ejb.Schedule;
import javax.ejb.Stateless;
import javax.inject.Inject;

import org.diningdevelopers.core.business.EventService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Stateless
public class VotingTimer {

	private final Logger logger = LoggerFactory.getLogger(getClass());

	@Inject
	private EventService eventService;

	@Schedule(dayOfWeek="1-5", hour="11", minute="15", persistent=false)
	public void closeVoting() {
		logger.info("closeVoting() was triggered");
		eventService.closeVoting();
	}

	@Schedule(dayOfWeek="1-5", hour="6", minute="0", persistent=false)
	public void openVoting() {
		logger.info("openVoting() was triggered");
		eventService.openVoting();
	}

}
