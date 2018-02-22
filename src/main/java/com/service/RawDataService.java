package com.service;

import com.facebook.bean.RawData;
import com.rmq.Consumer;
import com.rmq.Producer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.Arrays;

/**
 * User: liji
 * Date: 18/1/22
 * Time: 下午1:45
 */
@Service
public class RawDataService {

    private static final Logger logger = LogManager.getLogger();

    private static final String TOPIC = "fb_user";
    private Producer producer;
    private Consumer consumer;

    @Value("${redis.host}")
    private String redisHost;

    /**
     * Init.
     */
    @PostConstruct
    public void init() {
        JedisPool pool = new JedisPool(new JedisPoolConfig(), redisHost);
        producer = new Producer(pool.getResource(), TOPIC);
        consumer = new Consumer(pool.getResource(), "a subscriber", TOPIC);
    }

    /**
     * Parse user raw data.
     *
     * @param source the source
     * @return the raw data
     */
    public RawData parseUser(String source) {
        String[] s1 = source.split("\\s+");
        if (s1 != null) {
            String name = s1[0];
            String password = s1[1];
            RawData accountObj = new RawData();
            accountObj.setName(name);
            accountObj.setPassword(password);
            return accountObj;
        }
        return null;
    }

    /**
     * Insert raw data.
     *
     * @param string the string
     */
    public void insertRawData(String string) {
        String[] s1 = string.split("\\r?\\n");
        Arrays.stream(s1).forEach(s -> {
            s = s.trim();
            logger.info("publish string:{}", s);
            if (s != null && !s.equals("")) {
                this.producer.publish(s);
            }
        });
    }

    public void insertDataFromFile(MultipartFile file) throws IOException {
        byte[] bytes = file.getBytes();
        String completeData = new String(bytes);
        this.insertRawData(completeData);
    }

    /**
     * Unread messages int.
     *
     * @return the int
     */
    public int unreadMessages() {
        return consumer.unreadMessages();
    }
}
