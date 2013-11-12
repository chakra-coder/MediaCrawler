package org.cr;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import us.codecraft.webmagic.selector.XsoupSelector;

import java.io.IOException;
import java.util.List;

/**
 * Author: caorong
 * Date: 13-11-12
 * Time: 下午10:01
 * To change this template use File | Settings | File Templates.
 */
public class PparseTest {
    public static void main(String[] args) {
        String url = "http://v.163.com/special/opencourse/entrepreneurshipeducation.html";
        String xpath = "//a/@href";
        Document document = null;
        try {
            document = Jsoup.connect(url).get();
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

//        Document document = Jsoup.parse(url);
//        System.out.println(document.html());
        XsoupSelector xsoupSelector = new XsoupSelector(xpath);
        List<String> ans = xsoupSelector.selectList(document);
        System.out.println(ans);
    }
}
