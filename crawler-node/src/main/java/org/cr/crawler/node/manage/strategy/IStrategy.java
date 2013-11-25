package org.cr.crawler.node.manage.strategy;

import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Author: caorong
 * Date: 13-11-23
 * Time: 下午8:53
 * To change this template use File | Settings | File Templates.
 */
public interface IStrategy {
    public List<Integer> analysis(Map<Integer, AtomicInteger> data);
}
