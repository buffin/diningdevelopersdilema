package org.diningdevelopers.frontend.helper;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;

public class Authentication {

	public static String getUsername() {
		Subject subject = SecurityUtils.getSubject();

		if (subject != null) {
			return (String) subject.getPrincipal();
		} else {
			return null;
		}
	}
}
