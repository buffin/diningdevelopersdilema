package org.diningdevelopers.core.business.boundary;

import java.util.List;

import org.diningdevelopers.core.business.model.Vote;
import org.diningdevelopers.core.business.responsemodels.VotesOfUserResponseModel;

public interface VoteBoundary {

	void removeAllVotes();

	void removeVotes(String username);
	
	VotesOfUserResponseModel getVotesOfUser(String username);

	void save(String username, List<Vote> votes);

}
