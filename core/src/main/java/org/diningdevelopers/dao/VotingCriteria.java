package org.diningdevelopers.dao;

import org.diningdevelopers.core.database.entities.Location;
import org.diningdevelopers.core.database.entities.User;

public class VotingCriteria {

	private User developer;
	private Location location;

	public User getDeveloper() {
		return developer;
	}

	public Location getLocation() {
		return location;
	}

	public void setDeveloper(User developer) {
		this.developer = developer;
	}

	public void setLocation(Location location) {
		this.location = location;
	}
}
