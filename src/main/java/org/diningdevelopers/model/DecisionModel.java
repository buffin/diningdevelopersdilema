package org.diningdevelopers.model;

import java.util.Map;

public class DecisionModel implements Comparable<DecisionModel> {

	private Long locationId;
	private String locationName;
	private Map<Long, Float> votings;
	private float pointsTotal;
	private float randomRangeStart;
	private float randomRangeEnd;

	@Override
	public int compareTo(DecisionModel o) {
		return locationName.compareTo(o.getLocationName());
	}

	public Long getLocationId() {
		return locationId;
	}

	public String getLocationName() {
		return locationName;
	}

	public float getPointsTotal() {
		return pointsTotal;
	}

	public float getRandomRangeEnd() {
		return randomRangeEnd;
	}

	public float getRandomRangeStart() {
		return randomRangeStart;
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

	public void setPointsTotal(float pointsTotal) {
		this.pointsTotal = pointsTotal;
	}

	public void setRandomRangeEnd(float randomRangeEnd) {
		this.randomRangeEnd = randomRangeEnd;
	}

	public void setRandomRangeStart(float randomRangeStart) {
		this.randomRangeStart = randomRangeStart;
	}

	public void setVotings(Map<Long, Float> votings) {
		this.votings = votings;
	}

}
