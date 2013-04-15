package org.diningdevelopers.dao;

import java.util.List;

import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.diningdevelopers.entity.Location;
import org.diningdevelopers.entity.Location_;

@Named
public class LocationDao {

	@PersistenceContext
	private EntityManager entityManager;

	public List<Location> findActive() {
		CriteriaHelper<Location> helper = new CriteriaHelper<>(entityManager, Location.class);

		helper.addOrder(Location_.name, true);
		helper.setCacheable(true);

		return helper.getResultList();
	}

	public Location findById(Long id) {
		return entityManager.find(Location.class, id);
	}
}
