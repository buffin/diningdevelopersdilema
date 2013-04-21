package org.diningdevelopers.core.database.dao;

import java.util.List;

import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.diningdevelopers.core.database.dao.helper.CriteriaHelper;
import org.diningdevelopers.core.database.dao.helper.JpaUtils;
import org.diningdevelopers.core.database.entities.EventEntity;
import org.diningdevelopers.core.database.entities.UserEntity;
import org.diningdevelopers.core.database.entities.UserEntity_;

@Named
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
		CriteriaHelper<UserEntity> helper = new CriteriaHelper<>(entityManager, UserEntity.class);

		helper.addEqual(UserEntity_.username, username);

		return helper.getSingleResultOrNull();
	}	

	public void persist(UserEntity developer) {
		entityManager.persist(developer);
	}

	public List<UserEntity> findUsersOfEvent(EventEntity map) {
		// TODO find Participants of Event
		return null;
	}

}
