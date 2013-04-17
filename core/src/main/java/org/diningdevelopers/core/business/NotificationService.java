package org.diningdevelopers.core.business;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;
import org.diningdevelopers.core.business.model.Event;
import org.diningdevelopers.core.business.model.User;
import org.diningdevelopers.core.business.util.TemplateService;
import org.diningdevelopers.core.frontend.model.SimpleMail;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Stateless
public class NotificationService {

	private Logger logger = LoggerFactory.getLogger(getClass());

	@Inject
	private MailService mailService;

	@Inject
	private TemplateService templateService;
	
	public void notifiyParticipatingUsers(Event event) {
		// TODO: method in persistence for participants of event
		List<User> developers = null;
		for (User user : developers) {
			try {
				notifyUserVotingResult(user, event);
			} catch (Exception e) {
				logger.error("Notification of user {} ({}) failed with exception {}",
						new Object[] { user.getName(), user.getEmail(), e.getMessage() });
			}
		}
	}

	private void notifyUserVotingResult(User developer, Event event) {
		if (StringUtils.isBlank(developer.getEmail())) {
			return;
		}

		Map<String, Object> params = new HashMap<>();
		params.put("name", developer.getName());
		params.put("result", event.getResult());
		params.put("locationName", event.getWinningLocation().getName());

		String message = templateService.findTemplateByNameAndProcess("votingResultNotificationBody.ftl", params);
		String subject = templateService.findTemplateByNameAndProcess("votingResultNotificationSubject.ftl", params);

		SimpleMail mail = new SimpleMail();
		mail.setBody(message);
		mail.setContentType("text/plain");
		mail.setSubject(subject);
		mail.addTo(developer.getEmail());

		mailService.sendMail(mail);
	}
}
