package org.cr;

import us.codecraft.webmagic.downloader.HttpClientDownloader;
import us.codecraft.webmagic.selector.Html;

/**
 * 
 * @author caorong
 * 
 */
public class MagicRequest {
	public static void main(String[] args) {
		HttpClientDownloader downloader = new HttpClientDownloader();
//		Html html = downloader.download("http://v.163.com/special/Khan/multiples.html","utf-8");
		Html html = downloader.download("http://v.163.com/special/Khan/multiples.html");
		System.out.println(html);
	}
}
