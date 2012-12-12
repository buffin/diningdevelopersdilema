package org.diningdevelopers.dao;

import org.diningdevelopers.entity.Developer;
import org.diningdevelopers.entity.Location;

public class VotingCriteria {

	private Developer developer;
	private Location location;

	public Developer getDeveloper() {
		return developer;
	}

	public Location getLocation() {
		return location;
	}

	public void setDeveloper(Developer developer) {
		this.developer = developer;
	}

	public void setLocation(Location location) {
		this.location = location;
	}
}
