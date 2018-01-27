package com.service;

import com.facebook.bean.PageRequest;
import com.facebook.bean.PageResponse;
import com.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * User: liji
 * Date: 18/1/22
 * Time: 下午1:45
 */
@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;

    /**
     * Find all page response.
     *
     * @param request the request
     * @return the page response
     */
    public PageResponse findAll(PageRequest request) {
        int count = this.userMapper.countUser();
        PageResponse response = new PageResponse();
        response.setRecordsTotal(count);
        response.setDraw(request.getDraw());
        response.setRecordsFiltered(count);
        response.setData(this.userMapper.findAll(request.getStart(), request.getLength()).toArray());
        return response;
    }

    /**
     * Find all by identify photos of friends page response.
     *
     * @param request the request
     * @return the page response
     */
    public PageResponse findAllByIdentifyPhotosOfFriends(PageRequest request) {
        int count = this.userMapper.countUserByIdentifyPhotosOfFriends();
        PageResponse response = new PageResponse();
        response.setRecordsTotal(count);
        response.setDraw(request.getDraw());
        response.setRecordsFiltered(count);
        response.setData(this.userMapper.findAllByIdentifyPhotosOfFriends(request.getStart(), request.getLength()).toArray());
        return response;
    }
}
