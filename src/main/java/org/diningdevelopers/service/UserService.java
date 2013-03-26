package org.diningdevelopers.service;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.apache.commons.codec.digest.DigestUtils;
import org.diningdevelopers.dao.UserDao;
import org.diningdevelopers.entity.User;
import org.diningdevelopers.model.UserModel;

@Stateless
public class UserService {

	@Inject
	private UserDao userDao;

	@Inject
	private MappingService mappingService;

	public List<UserModel> findAll() {
		List<User> users = userDao.findAll();

		return mappingService.mapCollection(users, UserModel.class);
	}

	public User findByUsername(String username) {
		return userDao.findByUsername(username);
	}

	public void save(UserModel model) {
		if (model.getId() != null) {
			User user = userDao.findById(model.getId());
			mappingService.map(model, user);
		} else {
			User user = new User();
			mappingService.map(model, user);
			user.setUsername(model.getUsername());
			userDao.persist(user);
		}
	}

	public void changePassword(String username, String password) {
		User user = userDao.findByUsername(username);

		if (user != null) {
			String passwordSha = DigestUtils.shaHex(password);
			user.setPassword(passwordSha);
		}
	}

	public String getMailAddress(String username) {
		User user = findByUsername(username);

		if (user != null) {
			return user.getEmail();
		}

		return null;
	}

	public void updateMailAddress(String username, String email) {
		User user = findByUsername(username);

		if (user != null) {
			user.setEmail(email);
		}
	}

}
