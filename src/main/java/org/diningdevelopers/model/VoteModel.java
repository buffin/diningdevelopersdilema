package org.diningdevelopers.model;

import org.apache.commons.lang3.StringUtils;
import org.primefaces.model.map.MapModel;

public class VoteModel {

	public Long locationId;
	public String locationName;
	private String locationUrl;
	private String locationDescription;
	private MapModel locationModel;
	private String locationCoordinates;
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

	public String getLocationUrl() {
		return locationUrl;
	}

	public void setLocationUrl(String locationUrl) {
		this.locationUrl = locationUrl;
	}

	public String getLocationDescription() {
		return locationDescription;
	}

	public void setLocationDescription(String locationDescription) {
		this.locationDescription = locationDescription;
	}

	public MapModel getLocationModel() {
		return locationModel;
	}

	public void setLocationModel(MapModel locationModel) {
		this.locationModel = locationModel;
	}

	public String getLocationCoordinates() {
		return locationCoordinates;
	}

	public void setLocationCoordinates(String locationCoordinates) {
		this.locationCoordinates = locationCoordinates;
	}
	
	public boolean isInformationAvailable() {
		return StringUtils.isNotBlank(locationDescription) ||
				StringUtils.isNotBlank(locationCoordinates) ||
				StringUtils.isNotBlank(locationUrl);
	}
	
}
