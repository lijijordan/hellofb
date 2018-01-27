package com.controller;

import com.facebook.bean.BaseResponse;
import com.facebook.bean.PageRequest;
import com.facebook.bean.PageResponse;
import com.facebook.bean.RowRequest;
import com.service.RawDataService;
import com.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(value = "/user")
public class UserController {

    private static final Logger logger = LogManager.getLogger();

    @Autowired
    private RawDataService rawDataService;

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/row/add",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public BaseResponse addRow(@RequestBody RowRequest request) {
        String str = request.getData();
        logger.info("input string:{}", request);
        if (str != null && !str.equals("")) {
            // do something
            this.rawDataService.insertRawData(str);
        }
        return new BaseResponse("OK");
    }

    @RequestMapping(value = "/list",
            method = RequestMethod.GET,
            consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public PageResponse list(@RequestParam int draw, @RequestParam int length, @RequestParam int start) {
        PageRequest request = new PageRequest();
        request.setDraw(draw);
        request.setLength(length);
        request.setStart(start);
        return userService.findAll(request);
    }

    @RequestMapping(value = "/identifyPhotosOfFriends/list",
            method = RequestMethod.GET,
            consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public PageResponse listByIdentifyPhotosOfFriends(@RequestParam int draw, @RequestParam int length, @RequestParam int start) {
        PageRequest request = new PageRequest();
        request.setDraw(draw);
        request.setLength(length);
        request.setStart(start);
        return userService.findAllByIdentifyPhotosOfFriends(request);
    }

}