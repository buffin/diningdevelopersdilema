package org.diningdevelopers.core.database.dao;

import java.util.List;

import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.diningdevelopers.core.database.dao.helper.CriteriaHelper;
import org.diningdevelopers.core.database.entities.LocationEntity;
import org.diningdevelopers.core.database.entities.LocationEntity_;

@Named
public class LocationDao {

	@PersistenceContext
	private EntityManager entityManager;

	public List<LocationEntity> findActive() {
		CriteriaHelper<LocationEntity> helper = new CriteriaHelper<>(entityManager, LocationEntity.class);

		helper.addOrder(LocationEntity_.name, true);
		helper.setCacheable(true);

		return helper.getResultList();
	}

	public LocationEntity findById(Long id) {
		return entityManager.find(LocationEntity.class, id);
	}
}
