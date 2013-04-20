package org.diningdevelopers.core.business;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.diningdevelopers.core.business.boundary.LocationBoundary;
import org.diningdevelopers.core.business.model.Location;
import org.diningdevelopers.core.business.persistence.LocationPersistence;

@Stateless
public class LocationInteractor implements LocationBoundary {

	@Inject
	private LocationPersistence locationPersistence;
	
	@Override
	public List<Location> getActiveLocations() {
		return locationPersistence.findActive();
	}
	
}
