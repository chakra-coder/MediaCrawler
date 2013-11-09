package org.cr.crawler.node;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Author: caorong
 * Date: 13-11-9
 * Time: 下午5:49
 * To change this template use File | Settings | File Templates.
 */
public class ClientMain {
    public static void main(String[] args) throws Exception {

        ApplicationContext applicationContext = new ClassPathXmlApplicationContext(
                new String[] { "classpath:applicationContext.xml" });

    }
}
