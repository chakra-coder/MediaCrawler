package org.cr.crawler.node.model;

import java.util.Date;

/**
 * Author: caorong
 * Date: 13-11-10
 * Time: 下午10:27
 * To change this template use File | Settings | File Templates.
 */
public class Node {

    private String id;
    private String name;
    private Date regtime;


    public Node(String name, Date regtime) {
        this.name = name;
        this.regtime = regtime;
    }

    @Override
    public String toString() {
        return "Node{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", regtime=" + regtime +
                '}';
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getRegtime() {
        return regtime;
    }

    public void setRegtime(Date regtime) {
        this.regtime = regtime;
    }
}

