package org.cr.crawler.node;

import com.mongodb.Mongo;
import org.apache.log4j.Logger;
import org.cr.crawler.node.model.Task;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

/**
 * Author: caorong
 * Date: 13-11-9
 * Time: 下午5:08
 * To change this template use File | Settings | File Templates.
 */
public class MangoTest {
    private static final Logger log = Logger.getLogger(MangoTest.class);

    public static void main(String[] args) throws Exception {

        MongoOperations mongoOps = new MongoTemplate(new SimpleMongoDbFactory(new Mongo(), "database"));

        mongoOps.insert(new Task("http://www.baidu.com", 34));

        log.info(mongoOps.findOne(new Query(Criteria.where("abc").is(34)), Task.class));

        mongoOps.dropCollection(Task.class);
    }
}
