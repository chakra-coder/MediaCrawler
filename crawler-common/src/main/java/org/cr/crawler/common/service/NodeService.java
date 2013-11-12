package org.cr.crawler.common.service;

import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;
import static org.springframework.data.mongodb.core.query.Update.update;

import java.util.Date;

import org.cr.crawler.common.model.Node;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.stereotype.Service;

/**
 * Author: caorong Date: 13-11-10 Time: 下午10:31 To change this template use File
 * | Settings | File Templates.
 */
@Service
public class NodeService {
	@Autowired
	private MongoOperations mongoTemplate;

	public void insertOrUpdateNode(Node node) {
		Node oldNode = mongoTemplate.findOne(
				query(where("name").is(node.getName())), Node.class);
		// mongoTemplate.insert(node);
		System.out.println(oldNode);
		if (null == oldNode) {
			// System.out.println("in -> " + node);
			mongoTemplate.insert(node);
		} else {
			mongoTemplate.updateFirst(query(where("name").is(node.getName())),
					update("regtime", new Date()), Node.class);
			// System.out.println(oldNode);
		}
	}
}
