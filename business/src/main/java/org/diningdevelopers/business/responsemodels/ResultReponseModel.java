package org.diningdevelopers.business.responsemodels;

public class ResultReponseModel {

	private Integer random;
	private String locationName;

	public ResultReponseModel(Integer random, String locationName) {
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
