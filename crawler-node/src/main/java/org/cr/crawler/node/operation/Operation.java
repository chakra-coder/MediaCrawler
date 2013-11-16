package org.cr.crawler.node.operation;

import org.cr.crawler.common.model.Task;

/**
 * Author: caorong
 * Date: 13-11-16
 * Time: 下午10:17
 * To change this template use File | Settings | File Templates.
 */
public interface Operation {
    Task operate(Task task);

    String getName();
}
