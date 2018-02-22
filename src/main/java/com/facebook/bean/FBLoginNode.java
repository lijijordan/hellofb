package com.facebook.bean;

import java.util.Date;

/**
 * User: liji
 * Date: 18/2/11
 * Time: 下午8:53
 */
public class FBLoginNode {
    private String ip;
    private Date datetime;
    // default off
    private NodeOption nodeOption = NodeOption.OFF;

    public NodeOption getNodeOption() {
        return nodeOption;
    }

    public void setNodeOption(NodeOption nodeOption) {
        this.nodeOption = nodeOption;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public Date getDatetime() {
        return datetime;
    }

    public void setDatetime(Date datetime) {
        this.datetime = datetime;
    }
}
