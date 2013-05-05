package org.diningdevelopers.business.persistence;

import java.util.List;

import org.diningdevelopers.business.model.Location;

public interface LocationPersistence {

	List<Location> findActive();

	Location findById(Long locationId);

}
