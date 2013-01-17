package org.diningdevelopers.model;

import java.util.Map;

public class DecisionModel {

	private Long locationId;
	private String locationName;

	private Map<Long, Float> votings;

	private float probabilityPercent;

	public Long getLocationId() {
		return locationId;
	}

	public String getLocationName() {
		return locationName;
	}

	public float getProbabilityPercent() {
		return probabilityPercent;
	}

	public Map<Long, Float> getVotings() {
		return votings;
	}

	public void setLocationId(Long locationId) {
		this.locationId = locationId;
	}

	public void setLocationName(String locationName) {
		this.locationName = locationName;
	}

	public void setProbabilityPercent(float probabilityPercent) {
		this.probabilityPercent = probabilityPercent;
	}

	public void setVotings(Map<Long, Float> votings) {
		this.votings = votings;
	}

}
