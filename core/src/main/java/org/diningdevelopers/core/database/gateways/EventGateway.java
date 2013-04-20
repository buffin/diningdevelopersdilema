package org.diningdevelopers.core.database.gateways;

import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.diningdevelopers.core.business.model.Event;
import org.diningdevelopers.core.business.model.User;
import org.diningdevelopers.core.business.persistence.EventPersistence;
import org.diningdevelopers.core.database.dao.EventDao;
import org.diningdevelopers.core.database.entities.EventEntity;
import org.diningdevelopers.core.util.MappingService;

@Stateless
public class EventGateway implements EventPersistence {
	
	@Inject
	private EventDao eventDao;
	
	@Inject
	private MappingService mappingService;

	@Override
	public Event findVotingForDate(Date today) {
		EventEntity event = eventDao.findVotingForDate(today);
		return mappingService.map(event, Event.class);
	}

	@Override
	public Event findLatestVoting() {
		EventEntity event = eventDao.findLatestVoting();
		return mappingService.map(event, Event.class);
	}

	@Override
	public void save(Event voting) {
		EventEntity event = mappingService.map(voting, EventEntity.class);
		eventDao.save(event);
	}

}
