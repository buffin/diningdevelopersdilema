package org.diningdevelopers.business;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.apache.commons.lang3.Range;
import org.diningdevelopers.business.model.Location;
import org.diningdevelopers.business.model.Vote;

public class EventResultEvaluator {
	
	public class VoteComparatorForLocationName implements Comparator<Vote> {
	    public int compare(Vote vote1, Vote vote2){
	    	return vote1.getLocation().getName().compareTo(vote2.getLocation().getName());
	    }
	}

	public Location evaluateWinningLocation(Integer randomNumber,
			List<Vote> votes) {
		if (randomNumber < 1) {
			throw new RuntimeException("random number cannot be less than 1");
		}

		int currentRangeStart = 1;
		
		sortByLocationName(votes);

		for (Vote vote : votes) {
			if (vote.getVote() == 0) {
				continue;
			}
			
			int maximumIncludedInInterval = currentRangeStart + vote.getVote() - 1;
			Range<Integer> range = Range.between(currentRangeStart, maximumIncludedInInterval);
			if (range.contains(randomNumber)) {
				return vote.getLocation();
			}
			currentRangeStart += vote.getVote();
		}

		throw new RuntimeException("random number cannot be greater than total sum of voting points");
	}

	private void sortByLocationName(List<Vote> votes) {
		Collections.sort(votes, new VoteComparatorForLocationName());
	}

}
