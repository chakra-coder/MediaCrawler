/**
 * 
 */
package org.cr;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.client.HttpClient;
import org.cr.crawler.common.model.Task;
import org.cr.crawler.common.utils.HttpConstants;
import org.cr.crawler.node.parse.AbstractParser;
import org.cr.crawler.node.parse.Parser;

import us.codecraft.webmagic.Site;
import us.codecraft.xsoup.Xsoup;

/**
 * @author caorong
 * 
 */
public class Open163Test extends AbstractParser implements Parser {
	public static Site opensourceSite = new Site();
	static {
		opensourceSite.addHeader(HttpConstants.USER_AGENT,
				HttpConstants.FIREFOX_V14);
		// opensourceSite.setCharset("utf-8");
		opensourceSite.setDomain("http://open.163.com/");
		// timeout 100s
		opensourceSite.setTimeOut(100 * 1000);

	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String url = "http://v.163.com/special/opencourse/russian.html";
		Open163Test op = new Open163Test();
		String document = op.fetch(opensourceSite, url);
		// System.out.println(document);

		Long start = new Date().getTime();
		for (int i1 = 0; i1 < 1; i1++) {
			List<String> urlNodes = Xsoup
					.compile(
							"//TABLE[@id='list2']/TBODY/TR/TD[@class='u-ctitle']")
					.evaluate(document).list();
			for (int i = 0; i < urlNodes.size(); i++) {
				HashMap<String, String> map = new HashMap<String, String>();

				String hrefNode = Xsoup.compile("//A/@href")
						.evaluate(urlNodes.get(i)).get();
				String titleNode = Xsoup.compile("//A/text()")
						.evaluate(urlNodes.get(i)).get();
			}
		}
		Long end = new Date().getTime();
		System.out.println(end - start);
	}

	@Override
	public List<Map<String, String>> getDetailUrls(Task task)
			throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
}
