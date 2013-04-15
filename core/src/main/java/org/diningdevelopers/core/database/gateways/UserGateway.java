package org.diningdevelopers.core.database.gateways;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.diningdevelopers.core.business.persistence.UserPersistence;
import org.diningdevelopers.core.database.dao.UserDao;
import org.diningdevelopers.core.database.entities.User;

@Stateless
public class UserGateway implements UserPersistence {

	@Inject
	private UserDao userDao;
	
	@Override
	public List<User> findAll() {
		return userDao.findAll();
	}

	@Override
	public User findByUsername(String username) {
		return userDao.findByUsername(username);
	}

	@Override
	public User findById(Long id) {
		return userDao.findById(id);
	}

	@Override
	public void persist(User user) {
		userDao.persist(user);
	}

}
