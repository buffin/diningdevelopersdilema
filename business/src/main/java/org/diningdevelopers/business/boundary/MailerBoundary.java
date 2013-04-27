package org.diningdevelopers.business.boundary;

import org.diningdevelopers.core.frontend.model.SimpleMail;

public interface MailerBoundary {

	void sendMail(SimpleMail mail);

}
