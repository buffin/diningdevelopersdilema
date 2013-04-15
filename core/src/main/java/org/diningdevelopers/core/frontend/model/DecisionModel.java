package org.diningdevelopers.core.frontend.model;

import java.util.Map;

public class DecisionModel implements Comparable<DecisionModel> {

	private Long locationId;
	private String locationName;
	private Map<Long, Integer> votings;
	private int pointsTotal;
	private int randomRangeStart;
	private int randomRangeEnd;

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

	public Map<Long, Integer> getVotings() {
		return votings;
	}

	public void setLocationId(Long locationId) {
		this.locationId = locationId;
	}

	public void setLocationName(String locationName) {
		this.locationName = locationName;
	}

	public void setPointsTotal(int pointsTotal) {
		this.pointsTotal = pointsTotal;
	}

	public void setRandomRangeEnd(int randomRangeEnd) {
		this.randomRangeEnd = randomRangeEnd;
	}

	public void setRandomRangeStart(int randomRangeStart) {
		this.randomRangeStart = randomRangeStart;
	}

	public void setVotings(Map<Long, Integer> votings) {
		this.votings = votings;
	}

}
