package org.diningdevelopers.database.dao;

import java.util.Date;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.diningdevelopers.core.business.model.VotingState;
import org.diningdevelopers.database.dao.helper.CriteriaHelper;
import org.diningdevelopers.database.entities.EventEntity;
import org.diningdevelopers.database.entities.EventEntity_;

public class EventDao {

	@PersistenceContext
	private EntityManager entityManager;

	public void save(EventEntity voting) {
		entityManager.persist(voting);
	}

	public EventEntity findVotingForDate(Date date) {
		CriteriaHelper<EventEntity> helper = new CriteriaHelper<>(entityManager, EventEntity.class);
		helper.addGreaterThanOrEqualTo(EventEntity_.date, date);
		helper.addOrder(EventEntity_.date, false);
		helper.setMaxResults(1);
		return helper.getSingleResultOrNull();
	}

	public EventEntity findLatestVoting() {
		CriteriaHelper<EventEntity> helper = new CriteriaHelper<>(entityManager, EventEntity.class);
		helper.addOrder(EventEntity_.date, false);
		helper.setMaxResults(1);
		return helper.getSingleResultOrNull();
	}

	public Date findLatestActiveVoting() {
		CriteriaHelper<EventEntity> helper = new CriteriaHelper<>(entityManager, EventEntity.class);
		helper.addOrder(EventEntity_.date, false);
		helper.addEqual(EventEntity_.state, VotingState.Open);
		helper.setMaxResults(1);
		EventEntity voting = helper.getSingleResultOrNull();
		return voting == null ? new Date() : voting.getDate();

	}

}
