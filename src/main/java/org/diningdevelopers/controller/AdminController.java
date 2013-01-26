package org.diningdevelopers.controller;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.diningdevelopers.dao.JpaUtils;
import org.diningdevelopers.service.VoteService;
import org.diningdevelopers.utils.FacesUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Named
@SessionScoped
public class AdminController implements Serializable {

	@PersistenceContext
	private EntityManager entityManager;

	@Inject
	private VoteService voteService;

	private Logger logger = LoggerFactory.getLogger(getClass());

	public String deleteVotes() {
		logger.info("Admin: User removed all votes");
		voteService.removeAllVotes();
		FacesUtils.addMessage("Alle Votings gelöscht.");
		return null;
	}

	public String invalidateCache() {
		FacesUtils.addMessage("Cache invalidated.");
		JpaUtils.evictCache(entityManager);
		return null;
	}
	
	public String openVoting() {
		voteService.openVoting();
		return null;
	}
	
	public String closeVoting() {
		voteService.closeVoting();
		return null;
	}
}
