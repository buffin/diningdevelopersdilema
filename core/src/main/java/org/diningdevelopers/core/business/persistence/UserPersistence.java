package org.diningdevelopers.core.business.persistence;

import java.util.List;

import org.diningdevelopers.core.database.entities.User;

public interface UserPersistence {

	List<User> findAll();

	User findByUsername(String username);

	User findById(Long id);

	void persist(User user);

}
