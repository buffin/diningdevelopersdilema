package org.diningdevelopers.business.interactor;

import java.io.Serializable;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.diningdevelopers.business.boundary.LocationBoundary;
import org.diningdevelopers.business.model.Location;
import org.diningdevelopers.business.persistence.LocationPersistence;

@Stateless
public class LocationInteractor implements LocationBoundary, Serializable {

	@Inject
	private LocationPersistence locationPersistence;
	
	@Override
	public List<Location> getActiveLocations() {
		return locationPersistence.findActive();
	}
	
}
