package org.diningdevelopers.service;

import org.diningdevelopers.entity.User;
import org.diningdevelopers.model.UserModel;

public class UserConverter {

	public UserModel toModel(User user) {
		UserModel model = new UserModel();
		model.setId(user.getId());
		model.setName(user.getName());
		model.setEmail(user.getEmail());
		return model;
	}

	public void updateEntity(User user, UserModel model) {
		user.setName(model.getName());
		user.setEmail(model.getEmail());
	}
}
