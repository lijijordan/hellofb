package com.controller;

import com.common.IPPool;
import com.facebook.bean.*;
import com.service.RawDataService;
import com.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping(value = "/user")
public class UserController {

    private static final Logger logger = LogManager.getLogger();

    @Autowired
    private RawDataService rawDataService;

    @Autowired
    private UserService userService;

    @PostMapping("/api/upload")
    @ResponseBody
    public ResponseEntity<?> uploadFile(@RequestParam("file") MultipartFile uploadFile) {
        logger.debug("Single file upload!");
        if (uploadFile.isEmpty()) {
            return new ResponseEntity("please select a file!", HttpStatus.OK);
        }
        try {
            this.rawDataService.insertDataFromFile(uploadFile);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity("Successfully uploaded - " +
                uploadFile.getOriginalFilename(), new HttpHeaders(), HttpStatus.OK);
    }

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

    @RequestMapping(value = "/clear",
            method = RequestMethod.POST,
            consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public BaseResponse clear() {
        this.userService.clearUser();
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

    @RequestMapping(value = "/facebook/account/list",
            method = RequestMethod.GET,
            consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public PageResponse listFBAccount(@RequestParam int draw, @RequestParam int length, @RequestParam int start) {
        PageRequest request = new PageRequest();
        request.setDraw(draw);
        request.setLength(length);
        request.setStart(start);
        return userService.findAllFBAccount(request);
    }

    @RequestMapping(value = "/tick",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public BaseResponse tick(@RequestBody TickRequest request) {
        String ip = request.getIp();
        logger.info("tick IP :{}", ip);
        if (ip != null && !ip.equals("")) {
            // do something
            IPPool.addIp(ip);
        }
        return new BaseResponse("OK");
    }


    @RequestMapping(value = "/ip/list",
            method = RequestMethod.GET,
            consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public BaseResponse ipList() {
        return new BaseResponse(IPPool.getIps());
    }
}