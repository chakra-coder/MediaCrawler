package org.cr.crawler.node.manage.strategy;

import org.cr.crawler.common.Constant;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Author: caorong
 * Date: 13-11-23
 * Time: 下午8:51
 * To change this template use File | Settings | File Templates.
 */
@Service
public class SimpleStrategy implements IStrategy {

    @Value("${crawler.node.strategy.downloadLimit}")
    private Integer downloadLimit;

    @Value("${crawler.node.strategy.pageParseLimit}")
    private Integer pageParseLimit;

    @Override
    public List<Integer> analysis(Map<Integer, AtomicInteger> resultMap) {
        List<Integer> returnValue = new ArrayList<Integer>(2);

        returnValue.add(Constant.LIST_TASK_TYPE);
        returnValue.add(Constant.DETAIL_TASK_TYPE);
        AtomicInteger step2 = resultMap.get(Constant.TASK_STATE_DOWNLOADING);
        AtomicInteger listStep1 = resultMap.get(Constant.TASK_STATE_FETCHING + 10);
        if (listStep1.get() >= pageParseLimit) {
            returnValue.remove(Constant.LIST_TASK_TYPE);
        }
        if (step2.get() >= downloadLimit) {
            returnValue.remove(Constant.DETAIL_TASK_TYPE);
        }
        return returnValue;
    }
}
