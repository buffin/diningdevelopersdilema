package org.diningdevelopers.core.business.boundary;

import java.util.List;

import org.diningdevelopers.core.business.model.Location;

public interface LocationBoundary {

	List<Location> getActiveLocations();

}
