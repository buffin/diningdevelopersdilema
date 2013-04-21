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
import org.diningdevelopers.core.database.entities.VoteEntity_;

@Named
public class VotingDao {

	@PersistenceContext
	private EntityManager entityManager;

	public List<VoteEntity> findLatestByCriteria(VotingCriteria criteria) {
		CriteriaHelper<VoteEntity> helper = new CriteriaHelper<>(entityManager, VoteEntity.class);

		if (criteria.getDeveloper() != null) {
			helper.addEqual(VoteEntity_.developer, criteria.getDeveloper());
		}

		if (criteria.getLocation() != null) {
			helper.addEqual(VoteEntity_.location, criteria.getLocation());
		}

		helper.addOrder(VoteEntity_.date, false);

		return helper.getResultList();
	}

	public VoteEntity findLatestVote(UserEntity d, LocationEntity l) {
		CriteriaHelper<VoteEntity> helper = new CriteriaHelper<>(entityManager, VoteEntity.class);
		helper.addEqual(VoteEntity_.developer, d);
		helper.addEqual(VoteEntity_.location, l);
		helper.setCacheable(true);
		helper.addOrder(VoteEntity_.date, false);
		helper.setMaxResults(1);

		helper.setCacheable(true);

		return helper.getSingleResultOrNull();
	}

	public List<VoteEntity> findLatestVotes(UserEntity developer) {
		VotingCriteria votingCriteria = new VotingCriteria();
		votingCriteria.setDeveloper(developer);

		return findLatestByCriteria(votingCriteria);
	}
	
	public List<VoteEntity> findLatestVotes() {
		return findLatestByCriteria(new VotingCriteria());
	}

	public void removeAllVotes() {
		String queryString = "delete from VoteEntity";
		entityManager.createQuery(queryString).executeUpdate();
	}

	public void removeVotes(UserEntity developer) {
		String queryString = "delete from VoteEntity v where v.developer = :d";

		Query query = entityManager.createQuery(queryString);
		query.setParameter("d", developer);

		query.executeUpdate();
	}

	public void save(VoteEntity vote) {
		entityManager.persist(vote);
	}

}
