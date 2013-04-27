package org.diningdevelopers.business.boundary;

import java.util.List;

import org.diningdevelopers.business.model.Vote;
import org.diningdevelopers.business.responsemodels.VotesOfUserResponseModel;

public interface VoteBoundary {

	void removeAllVotes();

	void removeVotes(String username);
	
	VotesOfUserResponseModel getVotesOfUser(String username);

	void save(String username, List<Vote> votes);

}
