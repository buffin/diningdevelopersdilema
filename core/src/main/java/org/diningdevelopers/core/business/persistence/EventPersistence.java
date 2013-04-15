package org.diningdevelopers.core.business.persistence;

import java.util.Date;

import org.diningdevelopers.core.database.entities.Event;

public interface EventPersistence {

	Event findVotingForDate(Date today);

	Event findLatestVoting();

	void save(Event voting);

}
