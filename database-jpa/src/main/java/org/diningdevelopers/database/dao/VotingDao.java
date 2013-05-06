package org.diningdevelopers.database.dao;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.diningdevelopers.database.dao.helper.CriteriaHelper;
import org.diningdevelopers.database.dao.helper.VotingCriteria;
import org.diningdevelopers.database.entities.LocationEntity;
import org.diningdevelopers.database.entities.UserEntity;
import org.diningdevelopers.database.entities.VoteEntity;
import org.diningdevelopers.database.entities.VoteEntity_;

@Stateless
public class VotingDao {

	@PersistenceContext
	private EntityManager entityManager;

	public List<VoteEntity> findLatestByCriteria(VotingCriteria criteria) {
		CriteriaHelper<VoteEntity> helper = new CriteriaHelper<>(entityManager, VoteEntity.class);

		if (criteria.getUser() != null) {
			helper.addEqual(VoteEntity_.user, criteria.getUser());
		}

		if (criteria.getLocation() != null) {
			helper.addEqual(VoteEntity_.location, criteria.getLocation());
		}

		helper.addOrder(VoteEntity_.date, false);
		helper.addEqual(VoteEntity_.current, true);

		return helper.getResultList();
	}

	public VoteEntity findLatestVote(UserEntity d, LocationEntity l) {
		CriteriaHelper<VoteEntity> helper = new CriteriaHelper<>(entityManager, VoteEntity.class);
		helper.addEqual(VoteEntity_.user, d);
		helper.addEqual(VoteEntity_.location, l);
		helper.setCacheable(true);
		helper.addOrder(VoteEntity_.date, false);
		helper.setMaxResults(1);

		helper.setCacheable(true);

		return helper.getSingleResultOrNull();
	}

	public List<VoteEntity> findLatestVotes(UserEntity user) {
		VotingCriteria votingCriteria = new VotingCriteria();
		votingCriteria.setUser(user);

		return findLatestByCriteria(votingCriteria);
	}
	
	public List<VoteEntity> findLatestVotes() {
		return findLatestByCriteria(new VotingCriteria());
	}

	public void removeAllVotes() {
		String queryString = "delete from VoteEntity";
		entityManager.createQuery(queryString).executeUpdate();
	}

	public void removeVotes(UserEntity user) {
		String queryString = "delete from VoteEntity v where v.user = :u";

		Query query = entityManager.createQuery(queryString);
		query.setParameter("u", user);

		query.executeUpdate();
	}

	public void save(VoteEntity vote) {
		entityManager.merge(vote);
	}

}
