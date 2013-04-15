package org.diningdevelopers.core.database.dao;

import java.util.Date;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.diningdevelopers.core.database.dao.helper.CriteriaHelper;
import org.diningdevelopers.core.database.entities.Event;
import org.diningdevelopers.core.database.entities.VotingState;
import org.diningdevelopers.entity.Event_;

public class EventDao {

	@PersistenceContext
	private EntityManager entityManager;

	public void save(Event voting) {
		entityManager.persist(voting);
	}

	public Event findVotingForDate(Date date) {
		CriteriaHelper<Event> helper = new CriteriaHelper<>(entityManager, Event.class);
		helper.addGreaterThanOrEqualTo(Event_.date, date);
		helper.addOrder(Event_.date, false);
		helper.setMaxResults(1);
		return helper.getSingleResultOrNull();
	}

	public Event findLatestVoting() {
		CriteriaHelper<Event> helper = new CriteriaHelper<>(entityManager, Event.class);
		helper.addOrder(Event_.date, false);
		helper.setMaxResults(1);
		return helper.getSingleResultOrNull();
	}

	public Date findLatestActiveVoting() {
		CriteriaHelper<Event> helper = new CriteriaHelper<>(entityManager, Event.class);
		helper.addOrder(Event_.date, false);
		helper.addEqual(Event_.state, VotingState.Open);
		helper.setMaxResults(1);
		Event voting = helper.getSingleResultOrNull();
		return voting == null ? new Date() : voting.getDate();

	}

}
