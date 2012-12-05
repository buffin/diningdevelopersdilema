package org.diningdevelopers.service;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.diningdevelopers.dao.DeveloperDao;
import org.diningdevelopers.entity.Developer;

@Stateless
public class DeveloperService {

	@Inject
	private DeveloperDao developerDao;

	public Developer findByUsername(String username) {
		return developerDao.findByUsername(username);
	}
}
