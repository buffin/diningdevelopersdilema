package org.diningdevelopers.dao;

import java.util.List;

import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.diningdevelopers.entity.User;
import org.diningdevelopers.entity.Developer_;

@Named
public class DeveloperDao {

	@PersistenceContext
	private EntityManager entityManager;

	public List<User> findAll() {
		return JpaUtils.findAll(entityManager, User.class);
	}

	public User findById(Long id) {
		return entityManager.find(User.class, id);
	}

	public User findByUsername(String username) {
		CriteriaHelper<User> helper = new CriteriaHelper<>(entityManager, User.class);

		helper.addEqual(Developer_.username, username);

		return helper.getSingleResultOrNull();
	}	

	public void persist(User developer) {
		entityManager.persist(developer);
	}

}
