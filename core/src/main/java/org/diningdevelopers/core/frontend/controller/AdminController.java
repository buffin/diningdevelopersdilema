package org.diningdevelopers.core.frontend.controller;

import java.io.Serializable;
import java.util.Arrays;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.diningdevelopers.core.business.boundary.EventBoundary;
import org.diningdevelopers.core.business.boundary.MailerBoundary;
import org.diningdevelopers.core.business.boundary.UserBoundary;
import org.diningdevelopers.core.business.boundary.VoteBoundary;
import org.diningdevelopers.core.database.dao.helper.JpaUtils;
import org.diningdevelopers.core.frontend.model.SimpleMail;
import org.diningdevelopers.core.frontend.model.UserModel;
import org.diningdevelopers.core.frontend.util.Authentication;
import org.diningdevelopers.core.frontend.util.FacesUtils;
import org.diningdevelopers.core.util.MappingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Named
@SessionScoped
public class AdminController implements Serializable {

	@PersistenceContext
	private EntityManager entityManager;

	@Inject
	private VoteBoundary voteBoundary;

	@Inject
	private EventBoundary eventBoundary;

	@Inject
	private MailerBoundary mailerBoundary;

	@Inject 
	private UserBoundary userBoundary;
	
	@Inject
	private MappingService mappingService;
	

	private Logger logger = LoggerFactory.getLogger(getClass());

	public String deleteVotes() {
		logger.info("Admin: User removed all votes");
		voteBoundary.removeAllVotes();
		FacesUtils.addMessage("Alle Votings gelöscht.");
		return null;
	}

	public String invalidateCache() {
		FacesUtils.addMessage("Cache invalidated.");
		JpaUtils.evictCache(entityManager);
		return null;
	}

	public String openVoting() {
		eventBoundary.openVoting();
		return null;
	}

	public String reopenVoting() {
		eventBoundary.reopenVoting();
		return null;
	}

	public String closeVoting() {
		eventBoundary.closeVoting();
		return null;
	}

	public String sendTestMail() {
		SimpleMail mail = new SimpleMail();

		String username = Authentication.getUsername();
		UserModel developer = mappingService.map(userBoundary.findByUsername(username), UserModel.class);

		mail.setTo(Arrays.asList(developer.getEmail()));
		mail.setSubject("DDD Testmail");
		mail.setBody("Theaterhaus rockt!");

		mailerBoundary.sendMail(mail);

		FacesUtils.addMessage("Testmail verschickt");

		return null;
	}
}
