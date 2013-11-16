package org.cr.crawler.common.service;

import org.cr.crawler.common.model.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.stereotype.Service;

import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;
import static org.springframework.data.mongodb.core.query.Update.update;

/**
 * Author: caorong
 * Date: 13-11-17
 * Time: 上午12:04
 * To change this template use File | Settings | File Templates.
 */
@Service
public class TaskService {
    @Autowired
    private MongoOperations mongoTemplate;

    public void updateState(Task task) {
        mongoTemplate.updateFirst(query(where("id").is(task.getId())),
                update("state", task.getState()), Task.class);
    }
}
