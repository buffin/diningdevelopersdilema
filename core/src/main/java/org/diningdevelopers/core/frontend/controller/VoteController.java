package org.diningdevelopers.core.frontend.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.faces.event.ComponentSystemEvent;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.commons.lang3.StringUtils;
import org.diningdevelopers.core.business.boundary.EventBoundary;
import org.diningdevelopers.core.business.boundary.LocationBoundary;
import org.diningdevelopers.core.business.boundary.VoteBoundary;
import org.diningdevelopers.core.business.model.Location;
import org.diningdevelopers.core.business.model.Vote;
import org.diningdevelopers.core.business.responsemodels.VotesOfUserResponseModel;
import org.diningdevelopers.core.frontend.model.VoteModel;
import org.diningdevelopers.core.frontend.util.Authentication;
import org.diningdevelopers.core.frontend.util.CoordinatesParser;
import org.diningdevelopers.core.frontend.util.FacesUtils;
import org.primefaces.model.map.DefaultMapModel;
import org.primefaces.model.map.LatLng;
import org.primefaces.model.map.MapModel;
import org.primefaces.model.map.Marker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Named
@SessionScoped
public class VoteController implements Serializable {

	private Logger logger = LoggerFactory.getLogger(getClass());

	private List<VoteModel> voteModels;

	@Inject
	private VoteBoundary voteBoundary;
	
	@Inject
	private LocationBoundary locationBoundary;
	
	@Inject
	private CoordinatesParser coordinateParser;
	
	@Inject
	private EventBoundary eventBoundary;

	public String computeAdditionalStylesForSumPoints(int sumPoints) {
		if (sumPoints > 100) {
			return "color: red;";
		} else {
			return "";
		}
	}

	public int getSumPoints() {
		int sum = 0;

		for (VoteModel model : voteModels) {
			if (model.getVote() != null) {
				sum += model.getVote();
			}
		}
		return sum;
	}

	public List<VoteModel> getVoteModels() {
		return voteModels;
	}

	public void init(ComponentSystemEvent event) {
		if (FacesUtils.isNotPostback()) {
			voteModels = createVoteModels();
		}
	}

	private List<VoteModel> createVoteModels() {
		VotesOfUserResponseModel votesOfUser = voteBoundary.getVotesOfUser(Authentication.getUsername());
		List<Location> activeLocations = locationBoundary.getActiveLocations();
		
		List<VoteModel> newVoteModels = new ArrayList<VoteModel>();
		
		for (Location l : activeLocations) {
			VoteModel model = new VoteModel();
			model.setLocationId(l.getId());
			model.setLocationName(l.getName());
			model.setLocationDescription(l.getDescription());
			model.setLocationUrl(l.getUrl());
			model.setLocationCoordinates(l.getCoordinates());
			if (StringUtils.isNotBlank(l.getCoordinates())) {
				MapModel locationModel = new DefaultMapModel();
				LatLng coordinates = coordinateParser.parseCoordinates(l.getCoordinates());
				locationModel.addOverlay(new Marker(coordinates));
				model.setLocationModel(locationModel);
			}

			Vote latestVote = votesOfUser.getVoteForLocation(l.getName());
			if (latestVote != null) {
				model.setVote(latestVote.getVote());
			}

			newVoteModels.add(model);
		}
		return newVoteModels;
	}

	private boolean isSumValid(List<VoteModel> voteModels) {
		int sum = getSumPoints();

		if (sum == 100) {
			return true;
		} else {
			return false;
		}
	}

	public boolean isVotingOpen() {
		return eventBoundary.isVotingOpen();
	}

	public String reset() {
		voteBoundary.removeVotes(Authentication.getUsername());

		FacesUtils.addMessage("Ihr Voting wurde widerrufen.");

		voteModels = createVoteModels();
		return null;
	}

	public void save() {
		if (isSumValid(voteModels) == false) {
			FacesUtils.addMessage("Fehler: Es müssen genau 100 Punkte vergeben werden");
			return;
		}

		try {
			
			List<Vote> votes = new ArrayList<Vote>();
			for (VoteModel voteModel : voteModels) {
				Vote vote = new Vote();
				Location location = new Location();
				location.setId(voteModel.getLocationId());
				location.setName(voteModel.getLocationName());
				location.setUrl(voteModel.getLocationUrl());
				vote.setLocation(location);
				vote.setVote(voteModel.getVote());
				
				votes.add(vote);
			}
			
			voteBoundary.save(Authentication.getUsername(), votes);
			FacesUtils.addMessage("Das Voting wurde gespeichert!");
		} catch (Exception e) {
			logger.error("Fehler beim Speichern", e);
			FacesUtils.addMessage("Es ist ein Fehler aufgetreten. Voting konnte nicht gespeichert werden.");
		}
	}

	public void setVoteModels(List<VoteModel> voteModels) {
		this.voteModels = voteModels;
	}

}
