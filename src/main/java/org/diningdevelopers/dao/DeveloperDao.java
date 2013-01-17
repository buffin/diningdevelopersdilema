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

	public Developer findByUsername(String username) {
		CriteriaHelper<Developer> helper = new CriteriaHelper<>(entityManager, Developer.class);

		helper.addEqual(Developer_.username, username);

		return helper.getSingleResultOrNull();
	}

	public List<Developer> findParticipating() {
		CriteriaHelper<Developer> helper = new CriteriaHelper<>(entityManager, Developer.class);

		helper.addEqual(Developer_.participating, Boolean.TRUE);

		return helper.getResultList();
	}
}
