package org.diningdevelopers.core.database.dao;

import java.util.Date;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.diningdevelopers.core.database.dao.helper.CriteriaHelper;
import org.diningdevelopers.core.database.entities.EventEntity;
import org.diningdevelopers.core.database.entities.VotingStateEntity;
import org.diningdevelopers.entity.Event_;

public class EventDao {

	@PersistenceContext
	private EntityManager entityManager;

	public void save(EventEntity voting) {
		entityManager.persist(voting);
	}

	public EventEntity findVotingForDate(Date date) {
		CriteriaHelper<EventEntity> helper = new CriteriaHelper<>(entityManager, EventEntity.class);
		helper.addGreaterThanOrEqualTo(Event_.date, date);
		helper.addOrder(Event_.date, false);
		helper.setMaxResults(1);
		return helper.getSingleResultOrNull();
	}

	public EventEntity findLatestVoting() {
		CriteriaHelper<EventEntity> helper = new CriteriaHelper<>(entityManager, EventEntity.class);
		helper.addOrder(Event_.date, false);
		helper.setMaxResults(1);
		return helper.getSingleResultOrNull();
	}

	public Date findLatestActiveVoting() {
		CriteriaHelper<EventEntity> helper = new CriteriaHelper<>(entityManager, EventEntity.class);
		helper.addOrder(Event_.date, false);
		helper.addEqual(Event_.state, VotingStateEntity.Open);
		helper.setMaxResults(1);
		EventEntity voting = helper.getSingleResultOrNull();
		return voting == null ? new Date() : voting.getDate();

	}

}
