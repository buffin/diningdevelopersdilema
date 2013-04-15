package org.diningdevelopers.core.database.gateways;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.diningdevelopers.core.business.persistence.LocationPersistence;
import org.diningdevelopers.core.database.dao.LocationDao;
import org.diningdevelopers.core.database.entities.Location;

@Stateless
public class LocationGateway implements LocationPersistence {

	@Inject
	private LocationDao locationDao;
	
	@Override
	public List<Location> findActive() {
		return locationDao.findActive();
	}

	@Override
	public Location findById(Long locationId) {
		return locationDao.findById(locationId);
	}

}
