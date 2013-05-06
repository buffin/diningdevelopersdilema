package org.diningdevelopers.business.boundary;

import java.util.List;

import org.diningdevelopers.business.model.Event;
import org.diningdevelopers.business.model.User;

public interface UserBoundary {

	List<User> findAll();

	User findByUsername(String username);

	void changePassword(String username, String password);

	String getMailAddress(String username);

	void updateMailAddress(String username, String email);
	
	List<User> findParticipantsOfEvent(Event event);

}
