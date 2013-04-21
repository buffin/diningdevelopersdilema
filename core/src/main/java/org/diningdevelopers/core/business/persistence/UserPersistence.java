package org.diningdevelopers.core.business.persistence;

import java.util.List;

import org.diningdevelopers.core.business.model.Event;
import org.diningdevelopers.core.business.model.User;

public interface UserPersistence {

	List<User> findAll();

	User findByUsername(String username);

	User findById(Long id);

	void persist(User user);

	List<User> findParticipatingUsersOfEvent(Event event);

}
