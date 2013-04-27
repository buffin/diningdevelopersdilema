package org.diningdevelopers.business;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.diningdevelopers.business.model.Location;
import org.diningdevelopers.business.model.Vote;
import org.junit.Before;
import org.junit.Test;

public class EventResultEvaluatorTest {
	
	EventResultEvaluator evaluator;
	List<Vote> defaultVoteList;
	
	@Before
	public void setUp() {
		evaluator = new EventResultEvaluator();
		defaultVoteList = buildDefaultVoteList();
	}
	
	@Test
	public void evaluateResultForNumberBetweenRanges() throws Exception {
		Integer randomNumber = 120;
		Location location = evaluator.evaluateWinningLocation(randomNumber, defaultVoteList);
		assertEquals("location3", location.getName());
	}
	
	@Test
	public void evaluateResultForNumberRightAtIntervalBoundary() throws Exception {
		Integer randomNumber = 100;
		Location location = evaluator.evaluateWinningLocation(randomNumber, defaultVoteList);
		assertEquals("location2", location.getName());
	}
	
	@Test
	public void evaluateResultForNumberOneLessThanBoundary() throws Exception {
		Integer randomNumber = 19;
		Location location = evaluator.evaluateWinningLocation(randomNumber, defaultVoteList);
		assertEquals("location1", location.getName());
	}
	
	@Test
	public void evaluateResultForNumberOneMoreThanBoundary() throws Exception {
		Integer randomNumber = 21;
		Location location = evaluator.evaluateWinningLocation(randomNumber, defaultVoteList);
		assertEquals("location2", location.getName());
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
	
	private List<Vote> buildDefaultVoteList() {
		Location location1 = new Location();
		location1.setName("location1");
		
		Location location2 = new Location();
		location2.setName("location2");
		
		Location location3 = new Location();
		location3.setName("location3");
		
		List<Vote> votes = new ArrayList<Vote>();
		Vote vote1 = new Vote();
		vote1.setLocation(location1);
		vote1.setVote(20);
		
		Vote vote2 = new Vote();
		vote2.setLocation(location2);
		vote2.setVote(80);
		
		Vote vote3 = new Vote();
		vote3.setLocation(location3);
		vote3.setVote(100);
		
		votes.addAll(Arrays.asList(vote1, vote2, vote3));
		
		return votes;
	}

}
