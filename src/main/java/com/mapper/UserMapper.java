package com.mapper;

import com.domain.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * User: liji
 * Date: 18/1/26
 * Time: 上午11:46
 */
@Mapper
public interface UserMapper {

    @Select("select * from fb_user_2  order by user_id desc limit #{pageNum}, #{pageSize} ")
    @Results({
            @Result(property = "id", column = "user_id"),
            @Result(property = "createTime", column = "create_time"),
            @Result(property = "password", column = "pwd")
    })
    List<User> findAll(@Param("pageNum") int pageNum,
                       @Param("pageSize") int pageSize);


    @Select("select * from fb_user_2 where message is not null order by user_id desc limit #{pageNum}, #{pageSize} ")
    @Results({
            @Result(property = "id", column = "user_id"),
            @Result(property = "createTime", column = "create_time"),
            @Result(property = "password", column = "pwd")
    })
    List<User> findAllByIdentifyPhotosOfFriends(@Param("pageNum") int pageNum,
                                                @Param("pageSize") int pageSize);

    @Select("select count(1) from fb_user_2")
    int countUser();

    @Select("select count(1) from fb_user_2 where message is not null")
    int countUserByIdentifyPhotosOfFriends();
}
