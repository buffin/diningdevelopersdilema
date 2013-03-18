package org.diningdevelopers.service;

import org.diningdevelopers.entity.User;
import org.diningdevelopers.model.DeveloperModel;

public class DeveloperConverter {

	public DeveloperModel toModel(User developer) {
		DeveloperModel model = new DeveloperModel();
		model.setId(developer.getId());
		model.setName(developer.getName());
		model.setEmail(developer.getEmail());
		return model;
	}

	public void updateEntity(User developer, DeveloperModel model) {
		developer.setName(model.getName());
		developer.setEmail(model.getEmail());
	}
}
