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

	public void updateEntity(Developer developer, DeveloperModel model) {
		developer.setName(model.getName());
		developer.setEmail(model.getEmail());
		developer.setParticipating(model.getParticipating());
	}
}
