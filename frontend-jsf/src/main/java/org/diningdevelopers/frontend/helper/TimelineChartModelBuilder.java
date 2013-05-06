package org.diningdevelopers.frontend.helper;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;

import org.diningdevelopers.business.boundary.EventBoundary;
import org.diningdevelopers.business.boundary.LocationBoundary;
import org.diningdevelopers.business.boundary.UserBoundary;
import org.diningdevelopers.business.boundary.VoteBoundary;
import org.diningdevelopers.business.model.Event;
import org.diningdevelopers.business.model.Location;
import org.diningdevelopers.business.model.User;
import org.diningdevelopers.business.responsemodels.VotesOfUserResponseModel;
import org.diningdevelopers.frontend.model.TimelineLocationModel;
import org.diningdevelopers.frontend.model.TimelineUserModel;
import org.diningdevelopers.frontend.model.TimelineVoteModel;
import org.diningdevelopers.util.MappingService;
import org.primefaces.model.chart.CartesianChartModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TimelineChartModelBuilder implements Serializable {
	
	private static final long serialVersionUID = -8849038438009566179L;

	private static final Logger logger = LoggerFactory.getLogger(TimelineChartModelBuilder.class);
	
	@Inject
	private EventBoundary eventBoundary;
	
	@Inject
	private VoteBoundary voteBoundary;
	
	@Inject
	private UserBoundary userBoundary;
	
	@Inject
	private LocationBoundary locationBoundary;
	
	@Inject
	private MappingService mappingService;
	
	public CartesianChartModel buildTimelineModel() {
        Event relevantEvent = getRelevantEvent();
        List<TimelineUserModel> participants = getAllParticipants(relevantEvent);
        TimelineBuilder builder = new TimelineBuilder(getAllCandidateLocations(relevantEvent), participants);
        for ( TimelineUserModel participant : participants ) {
            builder.populateUserVotes(participant, getAllVotesSubmittedTowardsEvent(participant, relevantEvent));
        }
        return builder.build();
    }

    private Event getRelevantEvent() {
        return eventBoundary.getLatestEvent();
    }


    private List<TimelineLocationModel> getAllCandidateLocations(Event event) {
        List<Location> locations = locationBoundary.getActiveLocations();
        List<TimelineLocationModel> timelineLocations = mappingService.mapCollection(locations, TimelineLocationModel.class);
        logger.debug("locations: {}", timelineLocations);
        return timelineLocations;
    }

    private List<TimelineUserModel> getAllParticipants(Event event) {
        List<User> users = userBoundary.findParticipantsOfEvent(event);
        List<TimelineUserModel> timelineUsers = mappingService.mapCollection(users, TimelineUserModel.class);
        logger.debug("participants: {}", timelineUsers);
        return timelineUsers;
    }

    private List<TimelineVoteModel> getAllVotesSubmittedTowardsEvent(TimelineUserModel user, Event event) {
    	VotesOfUserResponseModel votesOfUsers = voteBoundary.getVotesOfUser(user.getUsername());
        List<TimelineVoteModel> timelineVotes = mappingService.mapCollection(votesOfUsers.getVotes(), TimelineVoteModel.class);
        logger.trace("{}'s votes: {}", user, timelineVotes);
        return timelineVotes;
    }

}
