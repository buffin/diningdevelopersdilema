package org.diningdevelopers.core.business.requestmodels;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.diningdevelopers.core.business.model.User;
import org.diningdevelopers.core.business.model.Vote;

public class LatestVotesRequestModel {
	
	private Map<User, List<Vote>> userToVotes;

	public LatestVotesRequestModel(Map<User, List<Vote>> userToVotes) {
		this.userToVotes = userToVotes;
	}

	public Collection<User> getUsers() {
		return userToVotes.keySet();
	}

	public List<Vote> getVotesForUser(User user) {
		return userToVotes.get(user);
	}

}
