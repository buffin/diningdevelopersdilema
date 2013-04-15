package org.diningdevelopers.core.database.gateways;

import java.util.Date;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.diningdevelopers.core.business.persistence.EventPersistence;
import org.diningdevelopers.core.database.dao.EventDao;
import org.diningdevelopers.core.database.entities.Event;

@Stateless
public class EventGateway implements EventPersistence {
	
	@Inject
	private EventDao eventDao;

	@Override
	public Event findVotingForDate(Date today) {
		return eventDao.findVotingForDate(today);
	}

	@Override
	public Event findLatestVoting() {
		return eventDao.findLatestVoting();
	}

	@Override
	public void save(Event voting) {
		eventDao.save(voting);
	}

}
