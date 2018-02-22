package com.controller;

import com.common.IPPool;
import com.service.RawDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

@Controller
public class WelcomeController {

    // inject via application.properties
    @Value("${welcome.message:test}")
    private String message = "Hello World";

    @Autowired
    private RawDataService rawDataService;

    @RequestMapping("/index")
    public String index(Map<String, Object> model) {
        return "welcome";
    }

    @RequestMapping("/")
    public String welcome(Map<String, Object> model) {
        model.put("message", this.message);
        try {
            model.put("unreadMessage", this.rawDataService.unreadMessages());
            model.put("ips", IPPool.getIps());
        } catch (Exception e) {
            model.put("unreadMessage", "error:" + e.getMessage());
        }
        return "welcome";
    }

}