/**
 * 
 */
package org.cr.crawler.node.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author caorong
 * 
 */
@Component
public class ConfigConstant {

	@Value("${crawler.node.name}")
	private String name;

	@Value("${crawler.node.reportInterval}")
	private Integer reportInterval;

	public String getWorkerNodeName() {
		return name;
	}
}
