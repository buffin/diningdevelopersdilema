package org.diningdevelopers.dao;

import java.util.Date;
import java.util.List;

import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.diningdevelopers.entity.User;
import org.diningdevelopers.entity.Location;
import org.diningdevelopers.entity.Vote;
import org.diningdevelopers.entity.Vote_;
import org.diningdevelopers.entity.Event;
import org.diningdevelopers.entity.VotingState;
import org.diningdevelopers.entity.Voting_;

@Named
public class VotingDao {

	@PersistenceContext
	private EntityManager entityManager;

	public List<Vote> findLatestByCriteria(VotingCriteria criteria) {
		CriteriaHelper<Vote> helper = new CriteriaHelper<>(entityManager, Vote.class);

		if (criteria.getDeveloper() != null) {
			helper.addEqual(Vote_.developer, criteria.getDeveloper());
		}

		if (criteria.getLocation() != null) {
			helper.addEqual(Vote_.location, criteria.getLocation());
		}

		helper.addOrder(Vote_.date, false);

		return helper.getResultList();
	}

	public Vote findLatestVote(User d, Location l) {
		CriteriaHelper<Vote> helper = new CriteriaHelper<>(entityManager, Vote.class);
		helper.addEqual(Vote_.developer, d);
		helper.addEqual(Vote_.location, l);
		helper.setCacheable(true);
		helper.addOrder(Vote_.date, false);
		helper.setMaxResults(1);

		helper.setCacheable(true);

		return helper.getSingleResultOrNull();
	}

	public List<Vote> findLatestVotes(User developer) {
		VotingCriteria votingCriteria = new VotingCriteria();
		votingCriteria.setDeveloper(developer);

		return findLatestByCriteria(votingCriteria);
	}

	public void removeAllVotes() {
		String queryString = "delete from Vote";
		entityManager.createQuery(queryString).executeUpdate();
	}

	public void removeVotes(User developer) {
		String queryString = "delete from Vote v where v.developer = :d";

		Query query = entityManager.createQuery(queryString);
		query.setParameter("d", developer);

		query.executeUpdate();
	}

	public void save(Vote vote) {
		entityManager.persist(vote);
	}

	public void save(Event voting) {
		entityManager.persist(voting);
	}

	public Event findVotingForDate(Date date) {
		CriteriaHelper<Event> helper = new CriteriaHelper<>(entityManager, Event.class);
		helper.addGreaterThanOrEqualTo(Voting_.date, date);
		helper.addOrder(Voting_.date, false);
		helper.setMaxResults(1);
		return helper.getSingleResultOrNull();
	}

	public Event findLatestVoting() {
		CriteriaHelper<Event> helper = new CriteriaHelper<>(entityManager, Event.class);
		helper.addOrder(Voting_.date, false);
		helper.setMaxResults(1);
		return helper.getSingleResultOrNull();
	}

	public Date findLatestActiveVoting() {
		CriteriaHelper<Event> helper = new CriteriaHelper<>(entityManager, Event.class);
		helper.addOrder(Voting_.date, false);
		helper.addEqual(Voting_.state, VotingState.Open);
		helper.setMaxResults(1);
		Event voting = helper.getSingleResultOrNull();
		return voting == null ? new Date() : voting.getDate();

	}
}
