/**
 * 
 */
package org.cr.crawler.node.analysis.impl;

import java.util.ArrayList;
import java.util.List;

import org.cr.crawler.node.analysis.Analyzer;
import org.cr.crawler.node.parse.AbstractParser;
import org.cr.crawler.node.parse.impl.openCourseParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import us.codecraft.xsoup.Xsoup;

/**
 * @author caorong
 * 
 */
public class OpenCourseAnalyzer extends openCourseParser implements Analyzer {

	private Logger logger = LoggerFactory.getLogger(OpenCourseAnalyzer.class);

	@Override
	public List<String> analysis(String url) throws Exception {
		List<String> relist = null;
		try {

			String node = this.fetch(site, url);
			// System.out.println(node);
			String downUrl = Xsoup
					.select(node,
							"//DIV[@class='opratebar2']/A[@class='opratebar-download']/@href")
					.get();

			relist = new ArrayList<String>();
			relist.add(downUrl);
		} catch (Exception e) {
			logger.error("error when get real url at OpenCourse", url);
		}
		return relist;
	}

	public static void main(String[] args) {
		OpenCourseAnalyzer an = new OpenCourseAnalyzer();
		String url = "http://v.163.com/movie/2012/8/K/L/M95SNFJNO_M95SOP9KL.html";
		try {
			System.out.println(an.analysis(url));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
