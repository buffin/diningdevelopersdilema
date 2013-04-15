package org.diningdevelopers.utils;

import java.util.HashMap;
import java.util.Map;

import junit.framework.Assert;

import org.diningdevelopers.core.business.TemplateService;
import org.junit.Test;

public class TemplateServiceTest {

	private TemplateService templateService = new TemplateService();

	@Test
	public void mergeTemplate() throws Exception {
		Map<String, Object> params = new HashMap<>();
		params.put("name", "Max Mustermann");
		String value = templateService.findTemplateByNameAndProcess("test.ftl", params);

		Assert.assertEquals("Hallo, Max Mustermann!", value);
	}
}
