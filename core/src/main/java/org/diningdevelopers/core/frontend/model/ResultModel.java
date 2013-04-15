package org.diningdevelopers.core.frontend.model;

public class ResultModel {

	private Integer randomNumber;
	private String locationName;
	private String errorMessage;

	public Integer getRandomNumber() {
		return randomNumber;
	}
	public void setRandomNumber(Integer randomNumber) {
		this.randomNumber = randomNumber;
	}
	public String getLocationName() {
		return locationName;
	}
	public void setLocationName(String locationName) {
		this.locationName = locationName;
	}
	public String getErrorMessage() {
		return errorMessage;
	}
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
}
