package com.plf.mp.service.impl;

import java.util.Collections;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.plf.mp.exception.RegisterException;
import com.plf.mp.mapper.MpUserMapper;
import com.plf.mp.model.MpUser;
import com.plf.mp.service.UserService;

import lombok.extern.slf4j.Slf4j;

/**
 * @author plf
 */
@Slf4j
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private MpUserMapper mpUserMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        MpUser mpUser = mpUserMapper.getUserByUsername(username);
        if (mpUser == null) {
            throw new UsernameNotFoundException("用户名不存在");
        }
        return new User(mpUser.getUsername(), mpUser.getPassword(), Collections.emptyList());
    }

    @Override
    public MpUser getMpUserByUsername(String username) {
        return mpUserMapper.getUserByUsername(username);
    }

    @Override
    public void registerUser(MpUser mpUser) {
        if (StringUtils.isEmpty(mpUser.getUsername())) {
            throw new RegisterException("用户名不能为空");
        } else if (StringUtils.isEmpty(mpUser.getPassword())) {
            throw new RegisterException("密码不能为空");
        }
        MpUser mpUserByUsername = getMpUserByUsername(mpUser.getUsername());
        if (mpUserByUsername != null) {
            throw new RegisterException("用户已存在");
        }
        mpUser.setPassword(passwordEncoder.encode(mpUser.getPassword()));
        mpUser.setAvatar(
            "http://thirdwx.qlogo.cn/mmopen/vi_32/LfSgq4vwXMtH1bYIslr8fW28Q9H5qboYQhfGjIlfxXJudpekjof2OYZARjOjpfQjekiceSzIXDicoSFy7dp11zIA/132");
        mpUserMapper.addUser(mpUser);
    }
}
