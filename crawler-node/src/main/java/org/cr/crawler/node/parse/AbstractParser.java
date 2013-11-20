package org.cr.crawler.node.parse;

import com.google.common.collect.Sets;
import org.apache.commons.io.IOUtils;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.downloader.HttpClientGenerator;
import us.codecraft.webmagic.utils.UrlUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Author: caorong Date: 13-11-16 Time: 下午10:32 To change this template use File
 * | Settings | File Templates.
 */
public abstract class AbstractParser {

	private Logger logger = LoggerFactory.getLogger(getClass());

	protected static final String URL = "url";

	protected static final String TITLE = "title";

	protected static final String INTRO = "intro";

	protected static final String GUEST = "guest";

	protected static final String SORTDATE = "sortdate";

	private final Map<String, CloseableHttpClient> httpClients = new HashMap<String, CloseableHttpClient>();

	private HttpClientGenerator httpClientGenerator = new HttpClientGenerator();

	private CloseableHttpClient getHttpClient(Site site) {
		if (site == null) {
			return httpClientGenerator.getClient(null);
		}
		String domain = site.getDomain();
		CloseableHttpClient httpClient = httpClients.get(domain);
		if (httpClient == null) {
			synchronized (this) {
				if (httpClient == null) {
					httpClient = httpClientGenerator.getClient(site);
					httpClients.put(domain, httpClient);
				}
			}
		}
		return httpClient;
	}

	protected String fetch(Site site, String url) {
		Set<Integer> acceptStatCode;
		String charset = null;
		Map<String, String> headers = null;
		if (site != null) {
			acceptStatCode = site.getAcceptStatCode();
			charset = site.getCharset();
			headers = site.getHeaders();
		} else {
			acceptStatCode = Sets.newHashSet(200);
		}
		logger.info("downloading page " + url);
		RequestBuilder requestBuilder = RequestBuilder.get().setUri(url);
		if (headers != null) {
			for (Map.Entry<String, String> headerEntry : headers.entrySet()) {
				requestBuilder.addHeader(headerEntry.getKey(),
						headerEntry.getValue());
			}
		}
		RequestConfig.Builder requestConfigBuilder = RequestConfig.custom()
				.setConnectionRequestTimeout(site.getTimeOut())
				.setConnectTimeout(site.getTimeOut())
				.setCookieSpec(CookieSpecs.BEST_MATCH);
		if (site != null && site.getHttpProxy() != null) {
			requestConfigBuilder.setProxy(site.getHttpProxy());
		}
		requestBuilder.setConfig(requestConfigBuilder.build());
		CloseableHttpResponse httpResponse = null;

		try {
			httpResponse = getHttpClient(site).execute(requestBuilder.build());
			int statusCode = httpResponse.getStatusLine().getStatusCode();
			if (acceptStatCode.contains(statusCode)) {
				// charset
				if (charset == null) {
					String value = httpResponse.getEntity().getContentType()
							.getValue();
					charset = UrlUtils.getCharset(value);
				}
				return IOUtils.toString(httpResponse.getEntity().getContent(),
						charset);
			} else {
				logger.warn("code error " + statusCode + "\t" + url);
				return null;
			}
		} catch (IOException e) {
			logger.warn("download page " + url + " error", e);
			return null;
		} finally {
			try {
				if (httpResponse != null) {
					// ensure the connection is released back to pool
					EntityUtils.consume(httpResponse.getEntity());
				}
			} catch (IOException e) {
				logger.warn("close response fail", e);
			}
		}
	}

}
