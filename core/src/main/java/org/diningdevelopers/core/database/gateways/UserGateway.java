package org.diningdevelopers.core.database.gateways;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.diningdevelopers.core.business.persistence.UserPersistence;
import org.diningdevelopers.core.database.dao.UserDao;
import org.diningdevelopers.core.database.entities.UserEntity;

@Stateless
public class UserGateway implements UserPersistence {

	@Inject
	private UserDao userDao;
	
	@Override
	public List<UserEntity> findAll() {
		return userDao.findAll();
	}

	@Override
	public UserEntity findByUsername(String username) {
		return userDao.findByUsername(username);
	}

	@Override
	public UserEntity findById(Long id) {
		return userDao.findById(id);
	}

	@Override
	public void persist(UserEntity user) {
		userDao.persist(user);
	}

}
