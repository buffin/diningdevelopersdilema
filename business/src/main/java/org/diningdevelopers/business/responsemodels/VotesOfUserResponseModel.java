package org.diningdevelopers.business.responsemodels;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.diningdevelopers.business.model.Vote;

public class VotesOfUserResponseModel {
	
	private List<Vote> votes;

	public VotesOfUserResponseModel(List<Vote> votes) {
		this.votes = votes;
	}
	
	public Vote getVoteForLocation(String locationName) {
		for (Vote vote : votes) {
			if (StringUtils.equals(vote.getLocation().getName(), locationName)) {
				return vote;
			}
		}
		return null;
	}

	public List<Vote> getVotes() {
		return votes;
	}
}
