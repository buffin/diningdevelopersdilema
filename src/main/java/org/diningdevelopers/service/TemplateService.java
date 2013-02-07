package org.diningdevelopers.service;

import java.io.StringWriter;
import java.io.Writer;
import java.util.Locale;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import freemarker.cache.ClassTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;

public class TemplateService {

	private Logger logger = LoggerFactory.getLogger(getClass());

	private Configuration configuration;

	public TemplateService() {
		configuration = new Configuration();
		configuration.setEncoding(Locale.GERMANY, "UTF-8");

		ClassTemplateLoader classTemplateLoader = new ClassTemplateLoader(getClass(), "/templates/");
		configuration.setTemplateLoader(classTemplateLoader);
	}

	public String findTemplateByNameAndProcess(String name, Map<String, Object> model) {
		try {
			Template template = configuration.getTemplate(name);
			Writer writer = new StringWriter();
			template.process(model, writer);
			return writer.toString();
		} catch (Exception e) {
			logger.error("Could not process template. Returning null.", e);
			return null;
		}
	}
}
