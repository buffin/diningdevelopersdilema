package org.diningdevelopers.business.persistence;

import java.util.Date;

import org.diningdevelopers.business.model.Event;

public interface EventPersistence {

	Event findVotingForDate(Date today);

	Event findLatestVoting();

	void save(Event voting);

}
