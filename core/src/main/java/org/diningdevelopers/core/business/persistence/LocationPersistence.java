package org.diningdevelopers.core.business.persistence;

import java.util.List;

import org.diningdevelopers.core.database.entities.LocationEntity;

public interface LocationPersistence {

	List<LocationEntity> findActive();

	LocationEntity findById(Long locationId);

}
