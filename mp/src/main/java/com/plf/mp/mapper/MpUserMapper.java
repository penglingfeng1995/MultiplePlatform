package com.plf.mp.mapper;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.plf.mp.model.MpUser;

/**
 * @author plf
 */
@Repository
public interface MpUserMapper {

    /**
     * 查询用户根据用户名
     * 
     * @param username
     *            用户名
     * @return 用户
     */
    MpUser getUserByUsername(@Param("username") String username);

    /**
     * 注册用户
     * 
     * @param mpUser
     *            用户
     */
    void addUser(@Param("user") MpUser mpUser);
}
