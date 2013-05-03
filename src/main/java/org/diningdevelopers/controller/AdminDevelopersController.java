package org.diningdevelopers.controller;

import java.io.Serializable;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.diningdevelopers.model.UserModel;
import org.diningdevelopers.service.UserService;

@Named
@SessionScoped
public class AdminDevelopersController implements Serializable {

	private List<UserModel> users;

	private UserModel current;

	@Inject
	private UserService userService;

	public UserModel getCurrent() {
		return current;
	}

	public List<UserModel> getUsers() {
		return users;
	}

	public String open() {

		return null;
	}

	public String refresh() {
		users = userService.findAll();

		return null;
	}

	public String save() {

		return null;
	}

	public void setCurrent(UserModel current) {
		this.current = current;
	}

	public void setUsers(List<UserModel> developers) {
		this.users = developers;
	}
}
