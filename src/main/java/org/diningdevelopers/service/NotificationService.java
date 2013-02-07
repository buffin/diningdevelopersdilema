package org.diningdevelopers.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;
import org.diningdevelopers.entity.Voting;
import org.diningdevelopers.model.DecisionTable;
import org.diningdevelopers.model.DeveloperModel;
import org.diningdevelopers.model.ResultModel;
import org.diningdevelopers.model.SimpleMail;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Stateless
public class NotificationService {

	private Logger logger = LoggerFactory.getLogger(getClass());

	@Inject
	private MailService mailService;

	@Inject
	private TemplateService templateService;

	public void notifiyParticipatingUsers(Voting voting, DecisionTable table, ResultModel resultModel) {
		List<DeveloperModel> developers = table.getDevelopers();
		for (DeveloperModel model : developers) {
			try {
				notifyUserVotingResult(model, voting, resultModel);
			} catch (Exception e) {
				logger.error("Notification of user {} ({}) failed with exception {}",
						new Object[] { model.getName(), model.getEmail(), e.getMessage() });
			}
		}
	}

	private void notifyUserVotingResult(DeveloperModel developer, Voting voting, ResultModel resultModel) {
		if (StringUtils.isBlank(developer.getEmail())) {
			return;
		}

		Map<String, Object> params = new HashMap<>();
		params.put("name", developer.getName());
		params.put("result", voting.getResult());
		params.put("locationName", resultModel.getLocationName());

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
