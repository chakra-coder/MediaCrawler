package org.cr.crawler.node.operation.impl;

import org.cr.crawler.node.operation.Operation;
import org.cr.crawler.node.parse.Parser;

import java.util.Map;

/**
 * Author: caorong
 * Date: 13-11-16
 * Time: 下午10:19
 * To change this template use File | Settings | File Templates.
 */
public abstract class PageOperation implements Operation {

    protected Map<String, Parser> parsers;

    public void setParsers(Map<String, Parser> parsers) {
        this.parsers = parsers;
    }

    protected Parser getParser(String url) {
        String group = getGroupName(url);
        Parser retParser = parsers.get(group);
        return retParser;
    }

    public String getGroupName(String url) {
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
