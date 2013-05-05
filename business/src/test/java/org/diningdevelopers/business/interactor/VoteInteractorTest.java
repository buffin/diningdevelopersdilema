package org.diningdevelopers.business.interactor;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.diningdevelopers.business.interactor.VoteInteractor;
import org.diningdevelopers.business.model.Location;
import org.diningdevelopers.business.model.User;
import org.diningdevelopers.business.model.Vote;
import org.diningdevelopers.business.persistence.AuditPersistence;
import org.diningdevelopers.business.persistence.EventPersistence;
import org.diningdevelopers.business.persistence.LocationPersistence;
import org.diningdevelopers.business.persistence.UserPersistence;
import org.diningdevelopers.business.persistence.VotingPersistence;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class VoteInteractorTest {
	
	@InjectMocks
	VoteInteractor interactor;
	
	@Mock
	VotingPersistence votingPersistence;
	
	@Mock
	UserPersistence userPersistence;
	
	@Mock
	EventPersistence eventPersistence;
	
	@Mock
	LocationPersistence locationPersistence;
	
	@Mock
	AuditPersistence auditPersistence;
	
	@Mock
	User testUser;
	
	@Captor
	ArgumentCaptor<Vote> arg;
	
	Vote vote;
	
	List<Vote> votes;
	
	@Before
	public void setUp() {
		votes = new ArrayList<Vote>();
		vote = new Vote();
		vote.setVote(10);
		Location location = mock(Location.class);
		vote.setLocation(location);
		votes.add(vote);

		when(locationPersistence.findById(Matchers.anyLong())).thenReturn(location);
		when(userPersistence.findByUsername(Matchers.anyString())).thenReturn(testUser);

	}
	
	@Test
	public void canSaveNewVote() throws Exception {
		when(votingPersistence.findLatestVote(testUser, vote.getLocation())).thenReturn(null);
		interactor.save("test", votes);
		verify(votingPersistence, times(1)).save(arg.capture());
		
		Vote capturedVote = arg.getValue();
		assertEquals(10, (int) capturedVote.getVote());
	}
	
	@Test
	public void existingVoteIsUpdated() throws Exception {
		Vote existingVote = new Vote();
		existingVote.setVote(20);
		
		when(votingPersistence.findLatestVote(testUser, vote.getLocation())).thenReturn(existingVote);
		interactor.save("test", votes);
		assertEquals(10, (int) existingVote.getVote());
	}
	
	@Test
	public void noUpdateForExistingVoteIfVoteValueIsEqual() throws Exception {
		Vote existingVote = mock(Vote.class);
		when(existingVote.getVote()).thenReturn(10);
		
		when(votingPersistence.findLatestVote(testUser, vote.getLocation())).thenReturn(existingVote);
		interactor.save("test", votes);
		
		verify(existingVote, times(0)).setVote(Matchers.anyInt());
	}

}
