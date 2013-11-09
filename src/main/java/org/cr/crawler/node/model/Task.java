package org.cr.crawler.node.model;

/**
 * Author: caorong
 * Date: 13-11-9
 * Time: 下午5:08
 * To change this template use File | Settings | File Templates.
 */
public class Task {
    private String id;
    private String url;
    private int abc;

    public Task(String url, int abc) {
        this.abc = abc;
        this.url = url;
    }

    @Override
    public String toString() {
        return "Task{" +
                "id='" + id + '\'' +
                ", url='" + url + '\'' +
                ", abc=" + abc +
                '}';
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getAbc() {
        return abc;
    }

    public void setAbc(int abc) {
        this.abc = abc;
    }
}
