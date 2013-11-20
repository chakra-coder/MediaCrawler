package org.cr.crawler.node.operation;

import java.util.Map;

import org.cr.crawler.node.analysis.Analyzer;
import org.cr.crawler.node.parse.Parser;

/**
 * Author: caorong Date: 13-11-16 Time: 下午10:19 To change this template use File
 * | Settings | File Templates.
 */
public abstract class PageOperation implements Operation {

	protected Map<String, Parser> parsers;

	public void setParsers(Map<String, Parser> parsers) {
		this.parsers = parsers;
	}

	protected Map<String, Analyzer> analyzers;

	public void setAnalyzer(Map<String, Analyzer> analyzers) {
		this.analyzers = analyzers;
	}

	protected Parser getParser(String url) {
		String group = getGroupName(url);
		Parser retParser = parsers.get(group);
		return retParser;
	}

	protected Analyzer getAnalyzer(String url) {
		String group = getGroupName(url);
		Analyzer retAnalyzer = analyzers.get(group);
		return retAnalyzer;
	}

	public static String getGroupName(String url) {
		int endIndex = url.indexOf(".com");
		if (endIndex < 0) {
			endIndex = url.lastIndexOf(".cn");
		}
		if (endIndex <= 0) {
			return "unknown";
		}
		String temp = url.substring(0, endIndex);
		int startIndex = temp.lastIndexOf(".") + 1;
		return url.substring(startIndex, endIndex);
	}

	public static void main(String[] args) {
		String url = "http://tv.video.sina.com.cn/tv/36/detail/gdzc.html";
		int endIndex = url.indexOf(".com");
		String temp = url.substring(0, endIndex);
		int startIndex = temp.lastIndexOf(".") + 1;
		String group = url.substring(startIndex, endIndex);
		System.out.println(group);
	}
}
