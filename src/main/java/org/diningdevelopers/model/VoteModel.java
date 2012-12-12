package org.diningdevelopers.model;

public class VoteModel {

	public Long locationId;
	public String locationName;
	public Integer vote;

	public Long getLocationId() {
		return locationId;
	}

	public String getLocationName() {
		return locationName;
	}

	public Integer getVote() {
		return vote;
	}

	public void setLocationId(Long locationId) {
		this.locationId = locationId;
	}

	public void setLocationName(String locationName) {
		this.locationName = locationName;
	}

	public void setVote(Integer vote) {
		this.vote = vote;
	}

}
