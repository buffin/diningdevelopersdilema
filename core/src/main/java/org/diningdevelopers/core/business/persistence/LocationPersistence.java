package org.diningdevelopers.core.business.persistence;

import java.util.List;

import org.diningdevelopers.core.business.model.Location;

public interface LocationPersistence {

	List<Location> findActive();

	Location findById(Long locationId);

}
