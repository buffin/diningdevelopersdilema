package org.diningdevelopers.database.dao.helper;

import org.diningdevelopers.database.entities.LocationEntity;
import org.diningdevelopers.database.entities.UserEntity;

public class VotingCriteria {

	private UserEntity user;
	private LocationEntity location;

	public UserEntity getUser() {
		return user;
	}

	public LocationEntity getLocation() {
		return location;
	}

	public void setUser(UserEntity user) {
		this.user = user;
	}

	public void setLocation(LocationEntity location) {
		this.location = location;
	}
}
