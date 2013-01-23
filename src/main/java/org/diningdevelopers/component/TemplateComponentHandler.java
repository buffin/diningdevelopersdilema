package org.diningdevelopers.component;

import javax.inject.Named;

import org.apache.commons.lang3.StringUtils;

@Named
public class TemplateComponentHandler {

	public String getShortHash(String hash) {
		if ((hash == null) || hash.startsWith("${")) {
			return hash;
		}
		return StringUtils.substring(hash, 0, 10);
	}
}
