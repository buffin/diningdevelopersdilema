package org.diningdevelopers.dao;

import java.util.List;

import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.diningdevelopers.entity.Event;
import org.diningdevelopers.entity.Location;
import org.diningdevelopers.entity.User;
import org.diningdevelopers.entity.Vote;
import org.diningdevelopers.entity.Vote_;

/* OLD VOTES TABLE EQUALED:
 * SELECT (ID, VOTE, DEVELOPER_ID, LOCATION_ID, VOTE_DATE, EVENT_ID)
 * FROM votes
 * WHERE (developer_id, vote_date) IN
 * (SELECT developer_id, MAX(vote_date) FROM votes GROUP BY developer_id);
 */

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
		helper.addEqual(Vote_.current, true);

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
		helper.addEqual(Vote_.current, true);

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

	public List<User> getAllParticipants(Event event) {
		String queryString = "select distinct v.developer from Vote v where v.event= :e";
		TypedQuery<User> query = entityManager.createQuery(queryString, User.class);
		query.setParameter("e", event);
		return query.getResultList();
	}

	public List<Location> getAllCandidateLocations(Event event) {
		String queryString = "select distinct v.location from Vote v where v.event= :e";
		TypedQuery<Location> query = entityManager.createQuery(queryString, Location.class);
		query.setParameter("e", event);
		return query.getResultList();
	}

	public List<Vote> getAllVotesOfUserSubmittedTowardsEvent(Event event, User user) {
		CriteriaHelper<Vote> helper = new CriteriaHelper<>(entityManager, Vote.class);
		helper.addEqual(Vote_.event, event);
		helper.addEqual(Vote_.developer, user);
		return helper.getResultList();
	}

}
