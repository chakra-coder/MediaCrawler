package org.cr.crawler.node.parse.impl;

import org.apache.commons.lang3.StringUtils;
import org.cr.crawler.common.model.Task;
import org.cr.crawler.common.utils.HttpConstants;
import org.cr.crawler.node.parse.AbstractParser;
import us.codecraft.webmagic.Site;
import us.codecraft.xsoup.Xsoup;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Author: caorong
 * Date: 13-11-16
 * Time: 下午10:34
 * To change this template use File | Settings | File Templates.
 */
public class openCourseParser extends AbstractParser {

    public static Site opensourceSite = new Site();

    static {
        opensourceSite.addHeader(HttpConstants.USER_AGENT, HttpConstants.FIREFOX_V14);
        // opensourceSite.setCharset("utf-8");
        opensourceSite.setDomain("http://open.163.com/");
        // timeout 100s
        opensourceSite.setTimeOut(100 * 1000);
    }

    @Override
    public List<HashMap<String, String>> getDetailUrls(Task task) throws Exception {
        //因为更改接口而更改此方法  2013-7-15 by caorong
        String url = task.getUrl();

        String node = this.fetch(opensourceSite, url);
        List<HashMap<String, String>> returnList = new ArrayList<HashMap<String, String>>();
        String hrefNode = null;
        String titleNode = null;

        List<String> urlNodes = Xsoup.compile("//TABLE[@id='list2']/TBODY/TR/TD[@class='u-ctitle']").evaluate(node)
                .list();
        for (int i = 0; i < urlNodes.size(); i++) {
            HashMap<String, String> map = new HashMap<String, String>();
            String snode = urlNodes.get(i);
//            System.out.println(snode.getTextContent());
//            System.out.println(snode.getFirstChild().getNodeValue());

            hrefNode = Xsoup.compile("//A/@href").evaluate(snode).get();
            titleNode = Xsoup.compile("//A/text()").evaluate(snode).get();
//            System.out.println(titleNode);
//            System.out.println(hrefNode);
            String sortNum = getSortNum(Xsoup.compile("//body/text()").evaluate(snode).get());
//            System.out.println(sortNum);
            map.put(SORTDATE, sortNum);
//
            if (!StringUtils.isEmpty(hrefNode)) {
                map.put(URL, hrefNode);
            }
            if (!StringUtils.isEmpty(titleNode)) {
                if (!StringUtils.isEmpty(sortNum))
                    map.put(TITLE, "第" + sortNum + "集 " + titleNode);
                else
                    map.put(TITLE, titleNode);
            }
            returnList.add(map);
        }
        return returnList;
    }

    private String getSortNum(String title) {
//        Pattern pattern = Pattern.compile("[0-9]{8}", Pattern.CASE_INSENSITIVE);
        Pattern pattern = Pattern.compile("第(\\d+)集", Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(title);
        if (matcher.find()) {
            return matcher.group(1);
        }
        return "99999999";
    }

    public static void main(String[] args) {
        String url = "http://v.163.com/special/opencourse/english.html";
        url = "http://v.163.com/special/opencourse/environmentalpsychology.html";
        url = "http://v.163.com/special/opencourse/russian.html";
        openCourseParser p = new openCourseParser();
        Task task = new Task();
        task.setUrl(url);
        try {
            System.out.println(p.getDetailUrls(task));
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

}
