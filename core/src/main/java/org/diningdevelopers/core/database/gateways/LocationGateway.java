package org.diningdevelopers.core.database.gateways;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.diningdevelopers.core.business.persistence.LocationPersistence;
import org.diningdevelopers.core.database.dao.LocationDao;
import org.diningdevelopers.core.database.entities.LocationEntity;

@Stateless
public class LocationGateway implements LocationPersistence {

	@Inject
	private LocationDao locationDao;
	
	@Override
	public List<LocationEntity> findActive() {
		return locationDao.findActive();
	}

	@Override
	public LocationEntity findById(Long locationId) {
		return locationDao.findById(locationId);
	}

}
