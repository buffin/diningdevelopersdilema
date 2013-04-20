package org.diningdevelopers.core.database.gateways;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.diningdevelopers.core.business.model.Location;
import org.diningdevelopers.core.business.persistence.LocationPersistence;
import org.diningdevelopers.core.database.dao.LocationDao;
import org.diningdevelopers.core.database.entities.LocationEntity;
import org.diningdevelopers.core.util.MappingService;

@Stateless
public class LocationGateway implements LocationPersistence {

	@Inject
	private LocationDao locationDao;
	
	@Inject
	private MappingService mappingService;
	
	@Override
	public List<Location> findActive() {
		List<LocationEntity> locations = locationDao.findActive();
		return mappingService.mapCollection(locations, Location.class);
	}

	@Override
	public Location findById(Long locationId) {
		LocationEntity location = locationDao.findById(locationId);
		return mappingService.map(location, Location.class);
	}

}
