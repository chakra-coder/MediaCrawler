package org.cr.crawler.node.handle;

import org.cr.crawler.common.model.Task;

/**
 * Author: caorong
 * Date: 13-11-16
 * Time: 下午11:53
 * To change this template use File | Settings | File Templates.
 */
public interface CallbackHandler {

    public void handle(Task task);
}
