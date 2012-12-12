package org.diningdevelopers.dao;

import java.util.List;

import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.diningdevelopers.entity.Developer;
import org.diningdevelopers.entity.Location;
import org.diningdevelopers.entity.Vote;
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

		helper.distinct(true);
		helper.addOrder(Vote_.date, false);

		return helper.getResultList();
	}

	public Vote findLatestVote(Developer d, Location l) {
		CriteriaHelper<Vote> helper = new CriteriaHelper<>(entityManager, Vote.class);
		helper.addEqual(Vote_.developer, d);
		helper.addEqual(Vote_.location, l);
		helper.setCacheable(true);
		helper.addOrder(Vote_.date, false);
		helper.setMaxResults(1);

		return helper.getSingleResultOrNull();
	}

	public void save(Vote vote) {
		JpaUtils.save(entityManager, vote);
	}
}
