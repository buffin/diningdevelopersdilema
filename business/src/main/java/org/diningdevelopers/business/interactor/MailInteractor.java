package org.diningdevelopers.business.interactor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.diningdevelopers.business.boundary.MailerBoundary;
import org.diningdevelopers.core.frontend.model.SimpleMail;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Stateless
public class MailInteractor implements MailerBoundary, Serializable {

	@Resource(lookup = "java:/mail/ddd")
	private Session session;

	private Logger logger = LoggerFactory.getLogger(getClass());

	@Override
	public void sendMail(SimpleMail mail) {
		logger.debug("sending mail");
		Message msg = new MimeMessage(session);

		try {
			String from = session.getProperty("mail.from");
			msg.setFrom(new InternetAddress(from));

			InternetAddress[] recipients = convert(mail.getTo());
			msg.setRecipients(Message.RecipientType.TO, recipients);

			msg.setSubject(mail.getSubject());

			msg.setContent(mail.getBody(), mail.getContentType());

			Transport.send(msg);
			logger.debug("message sent");
		} catch (MessagingException exc) {
			logger.error("Exception", exc);
		}
	}

	protected InternetAddress[] convert(List<String> addresses) throws AddressException {
		List<InternetAddress> result = new ArrayList<>();

		for (String s : addresses) {
			result.add(new InternetAddress(s));
		}

		return result.toArray(new InternetAddress[0]);
	}
}
