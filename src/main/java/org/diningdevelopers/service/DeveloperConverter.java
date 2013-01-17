package org.diningdevelopers.service;

import org.diningdevelopers.entity.Developer;
import org.diningdevelopers.model.DeveloperModel;

public class DeveloperConverter {

	public DeveloperModel toModel(Developer developer) {
		DeveloperModel model = new DeveloperModel();
		model.setId(developer.getId());
		model.setName(developer.getName());
		return model;
	}

}
