package org.diningdevelopers.business.boundary;

import java.util.List;

import org.diningdevelopers.business.model.Location;

public interface LocationBoundary {

	List<Location> getActiveLocations();

}
