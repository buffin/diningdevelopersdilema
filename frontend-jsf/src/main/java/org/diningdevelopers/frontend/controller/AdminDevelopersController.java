package org.diningdevelopers.frontend.controller;

import java.io.Serializable;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.diningdevelopers.business.boundary.UserBoundary;
import org.diningdevelopers.frontend.model.UserModel;
import org.diningdevelopers.util.MappingService;

@Named
@SessionScoped
public class AdminDevelopersController implements Serializable {

	private List<UserModel> developers;

	private UserModel current;
	
	@Inject
	private UserBoundary userBoundary;
	
	@Inject
	private MappingService mappingService;

	public UserModel getCurrent() {
		return current;
	}

	public List<UserModel> getDevelopers() {
		return developers;
	}

	public String open() {

		return null;
	}

	public String refresh() {
		developers = mappingService.mapCollection(userBoundary.findAll(), UserModel.class);

		return null;
	}

	public String save() {

		return null;
	}

	public void setCurrent(UserModel current) {
		this.current = current;
	}

	public void setDevelopers(List<UserModel> developers) {
		this.developers = developers;
	}
}
