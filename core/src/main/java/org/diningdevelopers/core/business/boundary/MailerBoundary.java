package org.diningdevelopers.core.business.boundary;

import org.diningdevelopers.core.frontend.model.SimpleMail;

public interface MailerBoundary {

	void sendMail(SimpleMail mail);

}
