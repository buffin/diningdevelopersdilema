package org.diningdevelopers.core.business.requestmodels;

public class ResultRequestModel {

	private Integer random;
	private String locationName;

	public ResultRequestModel(Integer random, String locationName) {
		this.random = random;
		this.locationName = locationName;
	}

	public Integer getRandom() {
		return random;
	}

	public String getLocationName() {
		return locationName;
	}

}
