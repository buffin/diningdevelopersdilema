package org.diningdevelopers.business.responsemodels;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.diningdevelopers.business.model.User;
import org.diningdevelopers.business.model.Vote;

public class LatestVotesResponseModel {
	
	private Map<User, List<Vote>> userToVotes;

	public LatestVotesResponseModel(Map<User, List<Vote>> userToVotes) {
		this.userToVotes = userToVotes;
	}

	public Collection<User> getUsers() {
		return userToVotes.keySet();
	}

	public List<Vote> getVotesForUser(User user) {
		return userToVotes.get(user);
	}

}
