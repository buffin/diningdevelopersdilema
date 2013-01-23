package org.diningdevelopers.controller;

import java.io.Serializable;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.faces.event.ComponentSystemEvent;
import javax.inject.Inject;
import javax.inject.Named;

import org.diningdevelopers.model.VoteModel;
import org.diningdevelopers.service.VoteService;
import org.diningdevelopers.utils.Authentication;
import org.diningdevelopers.utils.FacesUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Named
@SessionScoped
public class VoteController implements Serializable {

	private Logger logger = LoggerFactory.getLogger(getClass());

	private List<VoteModel> voteModels;

	@Inject
	private VoteService voteService;

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
			voteModels = voteService.getVoteModel(Authentication.getUsername());
		}
	}

	private boolean isSumValid(List<VoteModel> voteModels) {
		int sum = getSumPoints();

		if (sum == 100) {
			return true;
		} else {
			return false;
		}
	}

	public String reset() {
		voteService.removeVotes(Authentication.getUsername());

		FacesUtils.addMessage("Ihr Voting wurde widerrufen.");

		voteModels = voteService.getVoteModel(Authentication.getUsername());
		return null;
	}

	public void save() {
		if (isSumValid(voteModels) == false) {
			FacesUtils.addMessage("Fehler: Es müssen genau 100 Punkte vergeben werden");
			return;
		}

		try {

			voteService.save(Authentication.getUsername(), voteModels);
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
