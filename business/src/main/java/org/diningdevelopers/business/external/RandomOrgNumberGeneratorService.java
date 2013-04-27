package org.diningdevelopers.business.external;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import javax.inject.Inject;
import javax.inject.Named;
import javax.naming.ServiceUnavailableException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.DefaultHttpRequestRetryHandler;
import org.apache.http.util.EntityUtils;
import org.diningdevelopers.business.persistence.AuditPersistence;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Named
public class RandomOrgNumberGeneratorService {
	
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Inject
	private AuditPersistence auditPersistence;
	
	public int generateRandomNumberBetween(int startOfInterval, int endOfInterval) throws ServiceUnavailableException {
		DefaultHttpClient httpclient = new DefaultHttpClient();
		
		httpclient.setHttpRequestRetryHandler(new DefaultHttpRequestRetryHandler(3, false));
		
		URIBuilder builder = new URIBuilder();
		builder.setScheme("http").setHost("www.random.org").setPath("/integers")
		    .setParameter("num", "1")
		    .setParameter("min", String.valueOf(startOfInterval))
		    .setParameter("max", String.valueOf(endOfInterval))
		    .setParameter("col", "1")
		    .setParameter("base", "10")
		    .setParameter("format", "plain")
		    .setParameter("rnd", "new");
		URI uri;
		try {
			uri = builder.build();
		} catch (URISyntaxException e) {
			throw new RuntimeException("URL for webservice call could not be built!", e);
		}
		
		HttpGet httpGet = new HttpGet(uri);
		
		HttpResponse response;
		try {
			response = httpclient.execute(httpGet);
		} catch (IOException e) {
			logger.error("Service could not be called", e);
			throw new ServiceUnavailableException("Service call failed!");
		}
		HttpEntity entity = response.getEntity();
		if (entity != null) {
			String result;
			try {
				result = EntityUtils.toString(entity).trim();
			} catch (ParseException | IOException e) {
				throw new RuntimeException("unexpected result from webservice", e);
			}
			createAndSaveAudit("Called URL: " + uri + ", result was: " + result);
			return Integer.valueOf(result);
		}
		throw new RuntimeException();
	}

	private void createAndSaveAudit(String message) {
		auditPersistence.createAudit("SYSTEM", message);
	}
	
}
