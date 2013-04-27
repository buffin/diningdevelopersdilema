package org.diningdevelopers.database.gateways;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.diningdevelopers.business.model.Event;
import org.diningdevelopers.business.model.User;
import org.diningdevelopers.business.persistence.UserPersistence;
import org.diningdevelopers.database.dao.UserDao;
import org.diningdevelopers.database.entities.EventEntity;
import org.diningdevelopers.database.entities.UserEntity;
import org.diningdevelopers.util.MappingService;

@Stateless
public class UserGateway implements UserPersistence {

	@Inject
	private UserDao userDao;
	
	@Inject
	private MappingService mappingService;
	
	@Override
	public List<User> findAll() {
		return mappingService.mapCollection(userDao.findAll(), User.class);
	}

	@Override
	public User findByUsername(String username) {
		return mappingService.map(userDao.findByUsername(username), User.class);
	}

	@Override
	public User findById(Long id) {
		return mappingService.map(userDao.findById(id), User.class);
	}

	@Override
	public void persist(User user) {
		userDao.persist(mappingService.map(user, UserEntity.class));
	}

	@Override
	public List<User> findParticipatingUsersOfEvent(Event event) {
		List<UserEntity> users = userDao.findUsersOfEvent(mappingService.map(event, EventEntity.class));
		return mappingService.mapCollection(users, User.class);
	}

}
