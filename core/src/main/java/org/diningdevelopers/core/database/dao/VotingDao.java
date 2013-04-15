package org.diningdevelopers.core.database.dao;

import java.util.List;

import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.diningdevelopers.core.database.dao.helper.CriteriaHelper;
import org.diningdevelopers.core.database.dao.helper.VotingCriteria;
import org.diningdevelopers.core.database.entities.Location;
import org.diningdevelopers.core.database.entities.User;
import org.diningdevelopers.core.database.entities.Vote;
import org.diningdevelopers.entity.Vote_;

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

}
