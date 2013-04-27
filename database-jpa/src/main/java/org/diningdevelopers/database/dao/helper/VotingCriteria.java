package org.diningdevelopers.database.dao.helper;

import org.diningdevelopers.database.entities.LocationEntity;
import org.diningdevelopers.database.entities.UserEntity;

public class VotingCriteria {

	private UserEntity developer;
	private LocationEntity location;

	public UserEntity getDeveloper() {
		return developer;
	}

	public LocationEntity getLocation() {
		return location;
	}

	public void setDeveloper(UserEntity developer) {
		this.developer = developer;
	}

	public void setLocation(LocationEntity location) {
		this.location = location;
	}
}
