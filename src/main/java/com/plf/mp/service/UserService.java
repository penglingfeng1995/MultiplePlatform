package com.plf.mp.service;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.plf.mp.model.MpUser;

/**
 * @author plf
 */
public interface UserService extends UserDetailsService {
    /**
     * 通过用户名得到用户
     * 
     * @param username
     *            用户名
     * @return 用户
     */
    MpUser getMpUserByUsername(String username);

    /**
     * 注册用户
     * 
     * @param mpUser
     *            用户
     */
    void registerUser(MpUser mpUser);
}
