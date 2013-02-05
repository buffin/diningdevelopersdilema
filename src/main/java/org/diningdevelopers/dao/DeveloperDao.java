package org.diningdevelopers.dao;

import java.util.List;

import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.diningdevelopers.entity.Developer;
import org.diningdevelopers.entity.Developer_;

@Named
public class DeveloperDao {

	@PersistenceContext
	private EntityManager entityManager;

	public List<Developer> findAll() {
		return JpaUtils.findAll(entityManager, Developer.class);
	}

	public Developer findById(Long id) {
		return entityManager.find(Developer.class, id);
	}

	public Developer findByUsername(String username) {
		CriteriaHelper<Developer> helper = new CriteriaHelper<>(entityManager, Developer.class);

		helper.addEqual(Developer_.username, username);

		return helper.getSingleResultOrNull();
	}	

	public void persist(Developer developer) {
		entityManager.persist(developer);
	}

}
