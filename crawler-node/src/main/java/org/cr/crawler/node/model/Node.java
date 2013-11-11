package org.cr.crawler.node.model;

import java.util.Date;

/**
 * Author: caorong Date: 13-11-10 Time: 下午10:27 To change this template use File
 * | Settings | File Templates.
 */
public class Node {

	private String id;
	private String name;
	private String ip;
	private Date regtime;

	public Node(String name, Date regtime, String ip) {
		super();
		this.name = name;
		this.ip = ip;
		this.regtime = regtime;
	}

	@Override
	public String toString() {
		return "Node [id=" + id + ", name=" + name + ", ip=" + ip
				+ ", regtime=" + regtime + "]";
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
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
