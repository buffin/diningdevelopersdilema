package org.diningdevelopers.rest.resources;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import org.diningdevelopers.business.boundary.LocationBoundary;
import org.diningdevelopers.rest.dto.LocationDTO;
import org.diningdevelopers.util.MappingService;

@Path("/locations")
public class LocationResource {
	
	@Inject
	private LocationBoundary locationBoundary;
	
	@Inject
	private MappingService mappingService;

	@GET
	@Produces("application/json")
	public List<LocationDTO> getLocations() {
		List<LocationDTO> locations = mappingService.mapCollection(locationBoundary.getActiveLocations(), LocationDTO.class);
		return locations;
	}
}
