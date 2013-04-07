package org.diningdevelopers.service;

import org.diningdevelopers.dao.EventDao;
import org.diningdevelopers.dao.UserDao;
import org.diningdevelopers.dao.VotingDao;
import org.diningdevelopers.entity.Event;
import org.diningdevelopers.entity.Location;
import org.diningdevelopers.entity.User;
import org.diningdevelopers.entity.Vote;
import org.diningdevelopers.model.TimelineLocationModel;
import org.diningdevelopers.model.TimelineUserModel;
import org.diningdevelopers.model.TimelineVoteModel;
import org.diningdevelopers.utils.TimelineBuilder;
import org.primefaces.model.chart.CartesianChartModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Stateless
public class TimelineService {

    @Inject
    private EventDao eventDao;

    @Inject
    private VotingDao voteDao;

    @Inject
    private UserDao userDao;

    @Inject
    private MappingService mappingService;

    Logger logger = LoggerFactory.getLogger(TimelineService.class);

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
        return eventDao.findLatestVoting();
    }


    private List<TimelineLocationModel> getAllCandidateLocations(Event event) {
        List<Location> locationEntities = voteDao.getAllCandidateLocations(event);
        List<TimelineLocationModel> locations = mappingService.mapCollection(locationEntities, TimelineLocationModel.class);
        logger.debug("locations: {}", locations);
        return locations;
    }

    private List<TimelineUserModel> getAllParticipants(Event event) {
        List<User> userEntities = voteDao.getAllParticipants(event);
        List<TimelineUserModel> participants = mappingService.mapCollection(userEntities, TimelineUserModel.class);
        logger.debug("participants: {}", participants);
        return participants;
    }

    private List<TimelineVoteModel> getAllVotesSubmittedTowardsEvent(TimelineUserModel user, Event event) {
        List<Vote> voteEntities = voteDao.getAllVotesOfUserSubmittedTowardsEvent(event, userDao.findById(user.getId()));
        List<TimelineVoteModel> votes = mappingService.mapCollection(voteEntities, TimelineVoteModel.class);
        logger.trace("{}'s votes: {}", user, votes);
        return votes;
    }

}
