/**
 * 
 */
package org.cr;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.client.HttpClient;
import org.cr.crawler.common.model.Task;
import org.cr.crawler.common.utils.HttpConstants;
import org.cr.crawler.node.parse.AbstractParser;
import org.cr.crawler.node.parse.Parser;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import us.codecraft.webmagic.Site;
import us.codecraft.xsoup.Xsoup;

/**
 * @author caorong
 * 
 */
public class XsoupJsoup extends AbstractParser implements Parser{
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
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {
		String url = "http://v.163.com/special/opencourse/russian.html";
		XsoupJsoup op = new XsoupJsoup();
		String document1 = op.fetch(opensourceSite, url);
		// Document document = Jsoup.connect(url).get();
		// System.out.println(document);
		// Jsoup.parse
		Long start = new Date().getTime();
		// for (int i1 = 0; i1 < 1; i1++) {
		// List<String> urlNodes = Xsoup
		// .compile("//TABLE[@id='list2']/TBODY/TR/TD[@class='u-ctitle']")
		// .evaluate(document).list();
		// Elements tables = Jsoup.parse(document).getElementsByTag("table");
		for (int i = 0; i < 3000; i++) {
			Document document = Jsoup.parse(document1);
			Element tables = document.getElementById("list2");
			// System.out.println(tables);
			Elements tds = tables.getElementsByClass("u-ctitle");
			for (int j = 0; j < tds.size(); j++) {
				// System.out.println(tables.get(j).getElementById("list2"));
				// System.out.println(tds.get(j).getElementsByTag("a").get(0).text());
				String titleNode = tds.get(j).getElementsByTag("a").get(0)
						.text();
				// System.out.println(tds.get(j));
				String hrefNode = tds.get(j).getElementsByTag("a").get(0)
						.attr("href");
				// System.out.println(hrefNode);
			}
		}
		// System.out.println(urlNodes);

		// for (int i = 0; i < urlNodes.size(); i++) {
		// HashMap<String, String> map = new HashMap<String, String>();
		//
		// String hrefNode = Xsoup.compile("//A/@href")
		// .evaluate(urlNodes.get(i)).get();
		// String titleNode = Xsoup.compile("//A/text()")
		// .evaluate(urlNodes.get(i)).get();
		// }
		// }
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
