package org.diningdevelopers.core.business.persistence;

import java.util.List;

import org.diningdevelopers.core.database.entities.UserEntity;

public interface UserPersistence {

	List<UserEntity> findAll();

	UserEntity findByUsername(String username);

	UserEntity findById(Long id);

	void persist(UserEntity user);

}
