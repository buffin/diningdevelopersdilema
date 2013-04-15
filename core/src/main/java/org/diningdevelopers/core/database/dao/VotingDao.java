package org.diningdevelopers.core.database.dao;

import java.util.List;

import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.diningdevelopers.core.database.dao.helper.CriteriaHelper;
import org.diningdevelopers.core.database.dao.helper.VotingCriteria;
import org.diningdevelopers.core.database.entities.LocationEntity;
import org.diningdevelopers.core.database.entities.UserEntity;
import org.diningdevelopers.core.database.entities.VoteEntity;
import org.diningdevelopers.entity.Vote_;

@Named
public class VotingDao {

	@PersistenceContext
	private EntityManager entityManager;

	public List<VoteEntity> findLatestByCriteria(VotingCriteria criteria) {
		CriteriaHelper<VoteEntity> helper = new CriteriaHelper<>(entityManager, VoteEntity.class);

		if (criteria.getDeveloper() != null) {
			helper.addEqual(Vote_.developer, criteria.getDeveloper());
		}

		if (criteria.getLocation() != null) {
			helper.addEqual(Vote_.location, criteria.getLocation());
		}

		helper.addOrder(Vote_.date, false);

		return helper.getResultList();
	}

	public VoteEntity findLatestVote(UserEntity d, LocationEntity l) {
		CriteriaHelper<VoteEntity> helper = new CriteriaHelper<>(entityManager, VoteEntity.class);
		helper.addEqual(Vote_.developer, d);
		helper.addEqual(Vote_.location, l);
		helper.setCacheable(true);
		helper.addOrder(Vote_.date, false);
		helper.setMaxResults(1);

		helper.setCacheable(true);

		return helper.getSingleResultOrNull();
	}

	public List<VoteEntity> findLatestVotes(UserEntity developer) {
		VotingCriteria votingCriteria = new VotingCriteria();
		votingCriteria.setDeveloper(developer);

		return findLatestByCriteria(votingCriteria);
	}

	public void removeAllVotes() {
		String queryString = "delete from Vote";
		entityManager.createQuery(queryString).executeUpdate();
	}

	public void removeVotes(UserEntity developer) {
		String queryString = "delete from Vote v where v.developer = :d";

		Query query = entityManager.createQuery(queryString);
		query.setParameter("d", developer);

		query.executeUpdate();
	}

	public void save(VoteEntity vote) {
		entityManager.persist(vote);
	}

}
