package org.diningdevelopers.business.interactor;

import java.io.Serializable;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.apache.commons.codec.digest.DigestUtils;
import org.diningdevelopers.business.boundary.UserBoundary;
import org.diningdevelopers.business.model.User;
import org.diningdevelopers.business.persistence.UserPersistence;

@Stateless
public class UserInteractor implements UserBoundary, Serializable {

	@Inject
	private UserPersistence userPersistence;

	@Override
	public List<User> findAll() {
		return userPersistence.findAll();
	}

	@Override
	public User findByUsername(String username) {
		return userPersistence.findByUsername(username);
	}

	@Override
	public void changePassword(String username, String password) {
		User user = userPersistence.findByUsername(username);

		if (user != null) {
			String passwordSha = DigestUtils.shaHex(password);
			user.setPassword(passwordSha);
		}
	}

	@Override
	public String getMailAddress(String username) {
		User user = userPersistence.findByUsername(username);

		if (user != null) {
			return user.getEmail();
		}

		return null;
	}

	@Override
	public void updateMailAddress(String username, String email) {
		User user = userPersistence.findByUsername(username);

		if (user != null) {
			user.setEmail(email);
		}
	}

}
