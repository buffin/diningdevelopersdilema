package org.diningdevelopers.core.business;

import java.util.List;

import org.apache.commons.lang3.Range;
import org.diningdevelopers.core.business.model.Location;
import org.diningdevelopers.core.business.model.Vote;

public class EventResultEvaluator {

	public Location evaluateWinningLocation(Integer randomNumber,
			List<Vote> votes) {
		if (randomNumber < 1) {
			throw new RuntimeException("random number cannot be less than 1");
		}

		int currentRangeStart = 1;

		for (Vote vote : votes) {
			int maximumIncludedInInterval = currentRangeStart + vote.getVote() - 1;
			Range<Integer> range = Range.between(currentRangeStart, maximumIncludedInInterval);
			if (range.contains(randomNumber)) {
				return vote.getLocation();
			}
			currentRangeStart += vote.getVote();
		}

		throw new RuntimeException("random number cannot be greater than total sum of voting points");
	}

}
