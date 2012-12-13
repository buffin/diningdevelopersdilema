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

	public List<VoteModel> getVoteModels() {
		return voteModels;
	}

	public void init(ComponentSystemEvent event) {
		if (FacesUtils.isNotPostback()) {
			voteModels = voteService.getVoteModel(Authentication.getUsername());
		}
	}

	public void save() {
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
