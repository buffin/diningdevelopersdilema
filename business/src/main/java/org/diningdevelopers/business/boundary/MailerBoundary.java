package org.diningdevelopers.business.boundary;

import org.diningdevelopers.business.model.SimpleMail;


public interface MailerBoundary {

	void sendMail(SimpleMail mail);

}
