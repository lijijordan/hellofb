package com.common;

import com.facebook.bean.FBLoginNode;
import com.facebook.bean.NodeOption;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * User: liji
 * Date: 18/2/9
 * Time: 下午7:25
 */
public class IPPool {

    /**
     * 5 minute
     */
    private static final Long EXPIRED_TIME = 1000L * 5 * 60;
    private static Map<String, FBLoginNode> POOL = new HashMap<>();

    public static Set<String> getIps() {
        return POOL.keySet();
    }

    public static void addIp(String ip) {
        FBLoginNode node = POOL.get(ip);
        if (node == null) {
            node = new FBLoginNode();
        }
        node.setIp(ip);
        node.setDatetime(new Date());
        POOL.put(ip, node);
    }

    public static void activityNode(String ip) {
        POOL.get(ip).setNodeOption(NodeOption.ON);
    }

    public static void inactivityNode(String ip) {
        POOL.get(ip).setNodeOption(NodeOption.OFF);
    }

    public static void cleanExpiredNode() {
        POOL.forEach((ip, node) -> {
            // is expired
            if ((System.currentTimeMillis() - node.getDatetime().getTime()) > EXPIRED_TIME) {
                System.out.println(String.format("%s is expired", ip));
                POOL.remove(ip);
            }
        });
    }
}
