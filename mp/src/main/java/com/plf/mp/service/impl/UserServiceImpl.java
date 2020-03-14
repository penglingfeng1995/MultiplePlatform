package com.plf.mp.service.impl;

import com.plf.mp.mapper.MpUserMapper;
import com.plf.mp.model.MpUser;
import com.plf.mp.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

/**
 * @author plf
 */
@Slf4j
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private MpUserMapper mpUserMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        MpUser mpUser = mpUserMapper.getUserByUsername(username);
        if (mpUser == null) {
            throw new UsernameNotFoundException("用户名不存在");
        }
        return new User(mpUser.getUsername(), mpUser.getPassword(), Collections.emptyList());
    }
}
