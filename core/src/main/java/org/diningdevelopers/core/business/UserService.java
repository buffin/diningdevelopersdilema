package org.diningdevelopers.core.business;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.apache.commons.codec.digest.DigestUtils;
import org.diningdevelopers.core.business.persistence.UserPersistence;
import org.diningdevelopers.core.database.entities.UserEntity;
import org.diningdevelopers.core.frontend.model.UserModel;

@Stateless
public class UserService {

	@Inject
	private UserPersistence userPersistence;

	@Inject
	private MappingService mappingService;

	public List<UserModel> findAll() {
		List<UserEntity> users = userPersistence.findAll();

		return mappingService.mapCollection(users, UserModel.class);
	}

	public UserModel findByUsername(String username) {
		UserEntity user = userPersistence.findByUsername(username);
		return mappingService.map(user, UserModel.class);
	}

	public void save(UserModel model) {
		if (model.getId() != null) {
			UserEntity user = userPersistence.findById(model.getId());
			mappingService.map(model, user);
		} else {
			UserEntity user = new UserEntity();
			mappingService.map(model, user);
			user.setUsername(model.getUsername());
			userPersistence.persist(user);
		}
	}

	public void changePassword(String username, String password) {
		UserEntity user = userPersistence.findByUsername(username);

		if (user != null) {
			String passwordSha = DigestUtils.shaHex(password);
			user.setPassword(passwordSha);
		}
	}

	public String getMailAddress(String username) {
		UserEntity user = userPersistence.findByUsername(username);

		if (user != null) {
			return user.getEmail();
		}

		return null;
	}

	public void updateMailAddress(String username, String email) {
		UserEntity user = userPersistence.findByUsername(username);

		if (user != null) {
			user.setEmail(email);
		}
	}

}
