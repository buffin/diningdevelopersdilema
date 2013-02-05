package org.diningdevelopers.service;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.apache.commons.codec.digest.DigestUtils;
import org.diningdevelopers.dao.DeveloperDao;
import org.diningdevelopers.entity.Developer;
import org.diningdevelopers.model.DeveloperModel;

@Stateless
public class DeveloperService {

	@Inject
	private DeveloperDao developerDao;

	@Inject
	private DeveloperConverter developerConverter;

	public List<DeveloperModel> findAll() {
		List<Developer> developers = developerDao.findAll();
		List<DeveloperModel> result = new ArrayList<>();

		for (Developer d : developers) {
			DeveloperModel model = developerConverter.toModel(d);
			result.add(model);
		}

		return result;
	}

	public Developer findByUsername(String username) {
		return developerDao.findByUsername(username);
	}

	public void save(DeveloperModel model) {
		if (model.getId() != null) {
			Developer developer = developerDao.findById(model.getId());
			developerConverter.updateEntity(developer, model);
		} else {
			Developer developer = new Developer();
			developerConverter.updateEntity(developer, model);
			developer.setUsername(model.getUsername());
			developerDao.persist(developer);
		}
	}

	public void changePassword(String username, String password) {
		Developer developer = developerDao.findByUsername(username);

		if (developer != null) {
			String passwordSha = DigestUtils.shaHex(password);
			developer.setPassword(passwordSha);
		}
	}

	public String getMailAddress(String username) {
		Developer developer = findByUsername(username);

		if (developer != null) {
			return developer.getEmail();
		}

		return null;
	}

	public void updateMailAddress(String username, String email) {
		Developer developer = findByUsername(username);

		if (developer != null) {
			developer.setEmail(email);
		}
	}

}
