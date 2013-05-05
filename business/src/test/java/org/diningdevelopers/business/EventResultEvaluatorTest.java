package org.diningdevelopers.business;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.diningdevelopers.business.model.Location;
import org.diningdevelopers.business.model.Vote;
import org.junit.Before;
import org.junit.Test;

public class EventResultEvaluatorTest {
	
	static final String LOCATION3_NAME = "location3";
	static final String LOCATION2_NAME = "location2";
	static final String LOCATION1_NAME = "location1";
	
	EventResultEvaluator evaluator;
	List<Vote> defaultVoteList;
	
	@Before
	public void setUp() {
		evaluator = new EventResultEvaluator();
		defaultVoteList = buildThreeLocationListWithVotes20_80_100();
	}
	
	@Test
	public void evaluateResultForNumberBetweenRanges() throws Exception {
		Integer randomNumber = 120;
		Location location = evaluator.evaluateWinningLocation(randomNumber, defaultVoteList);
		assertEquals(LOCATION3_NAME, location.getName());
	}
	
	@Test
	public void evaluateResultForNumberRightAtIntervalBoundary() throws Exception {
		Integer randomNumber = 100;
		Location location = evaluator.evaluateWinningLocation(randomNumber, defaultVoteList);
		assertEquals(LOCATION2_NAME, location.getName());
	}
	
	@Test
	public void evaluateResultForNumberOneLessThanBoundary() throws Exception {
		Integer randomNumber = 19;
		Location location = evaluator.evaluateWinningLocation(randomNumber, defaultVoteList);
		assertEquals(LOCATION1_NAME, location.getName());
	}
	
	@Test
	public void evaluateResultForNumberOneMoreThanBoundary() throws Exception {
		Integer randomNumber = 21;
		Location location = evaluator.evaluateWinningLocation(randomNumber, defaultVoteList);
		assertEquals(LOCATION2_NAME, location.getName());
	}
	
	@Test(expected = RuntimeException.class)
	public void evaluateResultExceptionForNumberLessThanFirstInterval() throws Exception {
		Integer randomNumber = 0;
		evaluator.evaluateWinningLocation(randomNumber, defaultVoteList);
	}
	
	@Test(expected = RuntimeException.class)
	public void evaluateResultExceptionForNumberGreaterThanOverallMaximum() throws Exception {
		Integer randomNumber = 201;
		evaluator.evaluateWinningLocation(randomNumber, defaultVoteList);
	}
	
	@Test
	public void filterVotesWithZeroAsVoteValue() throws Exception {
		Location location1 = new Location();
		location1.setName(LOCATION1_NAME);
		Location location2 = new Location();
		location2.setName(LOCATION2_NAME);
		Location location3 = new Location();
		location3.setName(LOCATION3_NAME);
		
		List<Vote> votes = new ArrayList<Vote>();
		Vote vote1 = buildVote(location1, 20);
		Vote vote2 = buildVote(location2, 0);
		Vote vote3 = buildVote(location3, 100);
		votes.addAll(Arrays.asList(vote1, vote2, vote3));
		
		int randomNumber = 21;
		
		Location location = evaluator.evaluateWinningLocation(randomNumber, votes);
		assertEquals(LOCATION3_NAME, location.getName());
	}
	
	@Test
	public void groupLocationVotes() throws Exception {
		Location location1 = new Location();
		location1.setName(LOCATION1_NAME);
		Location location3 = new Location();
		location3.setName(LOCATION3_NAME);
		
		List<Vote> votes = new ArrayList<Vote>();
		Vote vote1 = buildVote(location1, 20);
		Vote vote2 = buildVote(location3, 60);
		Vote vote3 = buildVote(location1, 20);
		votes.addAll(Arrays.asList(vote1, vote2, vote3));
		
		int randomNumber = 25;
		
		Location location = evaluator.evaluateWinningLocation(randomNumber, votes);
		assertEquals(LOCATION1_NAME, location.getName());
	}
	
	private List<Vote> buildThreeLocationListWithVotes20_80_100() {
		Location location1 = new Location();
		location1.setName(LOCATION1_NAME);
		Location location2 = new Location();
		location2.setName(LOCATION2_NAME);
		Location location3 = new Location();
		location3.setName(LOCATION3_NAME);
		
		List<Vote> votes = new ArrayList<Vote>();
		Vote vote1 = buildVote(location1, 20);
		Vote vote2 = buildVote(location2, 80);
		Vote vote3 = buildVote(location3, 100);
		
		votes.addAll(Arrays.asList(vote1, vote2, vote3));
		
		return votes;
	}
	
	private Vote buildVote(Location location, int value) {
		Vote vote = new Vote();
		vote.setLocation(location);
		vote.setVote(value);
		return vote;
	}

}
