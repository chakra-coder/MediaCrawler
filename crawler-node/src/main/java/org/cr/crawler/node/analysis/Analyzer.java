/**
 * 
 */
package org.cr.crawler.node.analysis;

import java.util.List;

/**
 * @author caorong
 * 
 */
public interface Analyzer {
	/**
	 * 从详情页获得真实链接
	 * @param url
	 * @return
	 * @throws Exception
	 */
	List<String> analysis(String url) throws Exception;
}
