package com.plf.mp.service;

import com.plf.mp.model.MpUser;
import org.springframework.security.core.userdetails.UserDetailsService;

/**
 * @author plf
 */
public interface UserService extends UserDetailsService {
    /**
     * 通过用户名得到用户
     * @param username 用户名
     * @return 用户
     */
    MpUser getMpUserByUsername(String username);
}
