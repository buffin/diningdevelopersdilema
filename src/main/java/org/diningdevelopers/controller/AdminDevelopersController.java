package org.diningdevelopers.controller;

import java.io.Serializable;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.diningdevelopers.model.DeveloperModel;
import org.diningdevelopers.service.DeveloperService;

@Named
@SessionScoped
public class AdminDevelopersController implements Serializable {

	private List<DeveloperModel> developers;

	private DeveloperModel current;

	@Inject
	private DeveloperService developerService;

	public DeveloperModel getCurrent() {
		return current;
	}

	public List<DeveloperModel> getDevelopers() {
		return developers;
	}

	public String open() {

		return null;
	}

	public String refresh() {
		developers = developerService.findAll();

		return null;
	}

	public String save() {

		return null;
	}

	public void setCurrent(DeveloperModel current) {
		this.current = current;
	}

	public void setDevelopers(List<DeveloperModel> developers) {
		this.developers = developers;
	}
}
