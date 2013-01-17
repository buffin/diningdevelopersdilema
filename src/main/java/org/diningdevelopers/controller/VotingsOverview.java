package org.diningdevelopers.controller;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.faces.event.ComponentSystemEvent;
import javax.inject.Inject;
import javax.inject.Named;

import org.diningdevelopers.model.DecisionTable;
import org.diningdevelopers.service.DecisionService;
import org.diningdevelopers.utils.FacesUtils;

@Named
@SessionScoped
public class VotingsOverview implements Serializable {

	@Inject
	private DecisionService decisionService;

	private DecisionTable decisionTable;

	public DecisionTable getDecisionTable() {
		return decisionTable;
	}

	public void init(ComponentSystemEvent event) {
		if (FacesUtils.isNotPostback()) {
			update();
		}
	}

	public void setDecisionTable(DecisionTable decisionTable) {
		this.decisionTable = decisionTable;
	}

	public String update() {
		decisionTable = decisionService.buildDecisionTable(null);

		return null;
	}
}
