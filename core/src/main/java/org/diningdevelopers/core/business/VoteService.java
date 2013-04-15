package org.diningdevelopers.core.business;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;
import org.diningdevelopers.core.business.model.Event;
import org.diningdevelopers.core.business.persistence.AuditPersistence;
import org.diningdevelopers.core.business.persistence.EventPersistence;
import org.diningdevelopers.core.business.persistence.LocationPersistence;
import org.diningdevelopers.core.business.persistence.UserPersistence;
import org.diningdevelopers.core.business.persistence.VotingPersistence;
import org.diningdevelopers.core.database.entities.LocationEntity;
import org.diningdevelopers.core.database.entities.UserEntity;
import org.diningdevelopers.core.database.entities.VoteEntity;
import org.diningdevelopers.core.frontend.model.VoteModel;
import org.diningdevelopers.utils.CoordinatesParser;
import org.primefaces.model.map.DefaultMapModel;
import org.primefaces.model.map.LatLng;
import org.primefaces.model.map.MapModel;
import org.primefaces.model.map.Marker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Stateless
public class VoteService {

	private Logger logger = LoggerFactory.getLogger(getClass());

	@Inject
	private AuditPersistence auditPersistence;

	@Inject
	private LocationPersistence locationPersistence;

	@Inject
	private VotingPersistence votingPersistence;

	@Inject
	private EventPersistence eventPersistence;

	@Inject
	private UserPersistence userPersistence;

	@Inject
	private CoordinatesParser coordinatesParser;

	public List<VoteModel> getVoteModel(String username) {
		List<LocationEntity> locations = locationPersistence.findActive();
		List<VoteModel> result = new ArrayList<>();

		UserEntity developer = userPersistence.findByUsername(username);

		for (LocationEntity l : locations) {
			VoteModel model = new VoteModel();
			model.setLocationId(l.getId());
			model.setLocationName(l.getName());
			model.setLocationDescription(l.getDescription());
			model.setLocationUrl(l.getUrl());
			model.setLocationCoordinates(l.getCoordinates());
			if (StringUtils.isNotBlank(l.getCoordinates())) {
				MapModel locationModel = new DefaultMapModel();
				LatLng coordinates = coordinatesParser.parseCoordinates(l.getCoordinates());
				locationModel.addOverlay(new Marker(coordinates));
				model.setLocationModel(locationModel);
			}

			VoteEntity latestVote = votingPersistence.findLatestVote(developer, l);
			if (latestVote != null) {
				model.setVote(latestVote.getVote());
			}

			result.add(model);
		}

		return result;
	}

	private boolean needToCreateNewVote(VoteEntity vote) {
		return vote == null;
	}

	public void removeAllVotes() {
		logger.info("Call to removeAllVotes()");
		votingPersistence.removeAllVotes();
	}

	public void removeVotes(String username) {
		UserEntity developer = userPersistence.findByUsername(username);
		votingPersistence.removeVotes(developer);
		String auditMessage = "%s hat sein Voting widerrufen";
		auditPersistence.createAudit(username, String.format(auditMessage, username));

	}

	public void save(String username, List<VoteModel> voteModels) {
		Event event = eventPersistence.findLatestVoting();

		for (VoteModel model : voteModels) {
			UserEntity developer = userPersistence.findByUsername(username);
			LocationEntity location = locationPersistence.findById(model.getLocationId());
			VoteEntity vote = votingPersistence.findLatestVote(developer, location);

			Integer newVote = model.getVote();
			if (newVote == null) {
				model.setVote(0);
				newVote = 0;
			}

			if (needToCreateNewVote(vote)) {
				vote = new VoteEntity();
				vote.setLocation(location);
				vote.setDeveloper(developer);
				vote.setDate(new Date());
				vote.setVote(newVote);

				String auditMessage = "%s hat sein Voting für %s auf %d gesetzt";
				if (vote.getVote() > 0) {
					auditPersistence.createAudit(username, String.format(auditMessage, username, location.getName(), vote.getVote()));
				}

				// TODO: setter for event
				// vote.setEvent(event);

				votingPersistence.save(vote);
			} else {
				Integer oldVote = vote.getVote();
				if (oldVote.equals(newVote) == false) {
					String auditMessage = "%s hat sein Voting für %s geändert. Alt: %d, Neu: %d";
					auditPersistence.createAudit(username, String.format(auditMessage, username, location.getName(), oldVote, newVote));
					vote.setVote(newVote);
				}
			}
		}
	}
}
