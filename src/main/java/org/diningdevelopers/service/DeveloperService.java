package org.diningdevelopers.service;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.apache.commons.codec.digest.DigestUtils;
import org.diningdevelopers.dao.UserDao;
import org.diningdevelopers.entity.User;
import org.diningdevelopers.model.UserModel;

@Stateless
public class DeveloperService {

	@Inject
	private UserDao developerDao;

	@Inject
	private UserConverter developerConverter;

	public List<UserModel> findAll() {
		List<User> developers = developerDao.findAll();
		List<UserModel> result = new ArrayList<>();

		for (User d : developers) {
			UserModel model = developerConverter.toModel(d);
			result.add(model);
		}

		return result;
	}

	public User findByUsername(String username) {
		return developerDao.findByUsername(username);
	}

	public void save(UserModel model) {
		if (model.getId() != null) {
			User developer = developerDao.findById(model.getId());
			developerConverter.updateEntity(developer, model);
		} else {
			User developer = new User();
			developerConverter.updateEntity(developer, model);
			developer.setUsername(model.getUsername());
			developerDao.persist(developer);
		}
	}

	public void changePassword(String username, String password) {
		User developer = developerDao.findByUsername(username);

		if (developer != null) {
			String passwordSha = DigestUtils.shaHex(password);
			developer.setPassword(passwordSha);
		}
	}

	public String getMailAddress(String username) {
		User developer = findByUsername(username);

		if (developer != null) {
			return developer.getEmail();
		}

		return null;
	}

	public void updateMailAddress(String username, String email) {
		User developer = findByUsername(username);

		if (developer != null) {
			developer.setEmail(email);
		}
	}

}
