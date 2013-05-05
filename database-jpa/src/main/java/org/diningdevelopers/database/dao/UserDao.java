package org.diningdevelopers.database.dao;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.diningdevelopers.database.dao.helper.CriteriaHelper;
import org.diningdevelopers.database.dao.helper.JpaUtils;
import org.diningdevelopers.database.entities.EventEntity;
import org.diningdevelopers.database.entities.UserEntity;
import org.diningdevelopers.database.entities.UserEntity_;
import org.diningdevelopers.database.entities.VoteEntity;
import org.diningdevelopers.database.entities.VoteEntity_;

@Stateless
public class UserDao {

	@PersistenceContext
	private EntityManager entityManager;

	public List<UserEntity> findAll() {
		return JpaUtils.findAll(entityManager, UserEntity.class);
	}

	public UserEntity findById(Long id) {
		return entityManager.find(UserEntity.class, id);
	}

	public UserEntity findByUsername(String username) {
		CriteriaHelper<UserEntity> helper = new CriteriaHelper<>(entityManager,
				UserEntity.class);

		helper.addEqual(UserEntity_.username, username);

		return helper.getSingleResultOrNull();
	}

	public void persist(UserEntity developer) {
		entityManager.merge(developer);
	}

	public List<UserEntity> findUsersOfEvent(EventEntity event) {
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery<UserEntity> query = builder.createQuery(UserEntity.class);
		Root<UserEntity> users = query.from(UserEntity.class);
		Root<VoteEntity> votes = query.from(VoteEntity.class);
		query.select(users)
				.distinct(true)
				.where(builder.and(
						builder.equal(users, votes.get(VoteEntity_.developer)),
						builder.equal(votes.get(VoteEntity_.event), event)));

		return entityManager.createQuery(query).getResultList();
	}

}
