package org.cr.crawler.node;

import org.apache.log4j.Logger;
import org.cr.crawler.node.model.Task;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

import java.util.List;

import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

/**
 * Author: caorong
 * Date: 13-11-9
 * Time: 下午5:52
 * To change this template use File | Settings | File Templates.
 */
//@Component
public class Mangott implements InitializingBean {
    private static final Logger log = Logger.getLogger(Mangott.class);

    @Autowired
    private MongoOperations mongoTemplate;

    @Override
    public void afterPropertiesSet() throws Exception {
//        System.out.println(mongoTemplate);
//        System.out.println(mongoOps.);

        Task p = new Task("Joe", 34);

        // Insert is used to initially store the object into the database.
        mongoTemplate.insert(p);
        log.info("Insert: " + p);

        // Find
        p = mongoTemplate.findById(p.getId(), Task.class);
        log.info("Found: " + p + " id=" + p.getId());

        // Update
//        mongoTemplate.updateFirst(new Query(Criteria.where("url").is("Joe")), Update.update("abc", 1), Task.class);
        mongoTemplate.updateFirst(query(where("url").is("Joe")), Update.update("abc", 1), Task.class);

//        p = mongoTemplate.findOne(new Query(Criteria.where("url").is("Joe")), Task.class);
        p = mongoTemplate.findOne(query(where("url").is("Joe")), Task.class);
        log.info("Updated: " + p);

        // Delete
        mongoTemplate.remove(p);

        // Check that deletion worked
        List<Task> people = mongoTemplate.findAll(Task.class);
        log.info("Number of people = : " + people.size());


        mongoTemplate.dropCollection(Task.class);
    }
}
