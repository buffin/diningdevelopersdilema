package org.diningdevelopers.controller;

import java.io.Serializable;
import java.util.Arrays;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.diningdevelopers.core.business.EventInteractor;
import org.diningdevelopers.core.business.MailService;
import org.diningdevelopers.core.business.UserInteractor;
import org.diningdevelopers.core.business.VoteInteractor;
import org.diningdevelopers.core.database.dao.helper.JpaUtils;
import org.diningdevelopers.core.frontend.model.SimpleMail;
import org.diningdevelopers.core.frontend.model.UserModel;
import org.diningdevelopers.utils.Authentication;
import org.diningdevelopers.utils.FacesUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Named
@SessionScoped
public class AdminController implements Serializable {

	@PersistenceContext
	private EntityManager entityManager;

	@Inject
	private VoteInteractor voteService;

	@Inject
	private EventInteractor eventService;

	@Inject
	private MailService mailService;

	@Inject UserInteractor developerService;

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
		eventService.openVoting();
		return null;
	}

	public String reopenVoting() {
		eventService.reopenVoting();
		return null;
	}

	public String closeVoting() {
		eventService.closeVoting();
		return null;
	}

	public String sendTestMail() {
		SimpleMail mail = new SimpleMail();

		String username = Authentication.getUsername();
		UserModel developer = developerService.findByUsername(username);

		mail.setTo(Arrays.asList(developer.getEmail()));
		mail.setSubject("DDD Testmail");
		mail.setBody("Theaterhaus rockt!");

		mailService.sendMail(mail);

		FacesUtils.addMessage("Testmail verschickt");

		return null;
	}
}
