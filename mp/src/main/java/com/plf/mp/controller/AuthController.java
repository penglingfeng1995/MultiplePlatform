package com.plf.mp.controller;

import com.plf.mp.model.MpUser;
import com.plf.mp.model.Result;
import com.plf.mp.model.Status;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;

/**
 * @author plf
 */
@Slf4j
@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @GetMapping
    public Result auth() {
        Result result = new Result();
        result.setIsLogin(false);
        result.setStatus(Status.OK.getCode());
        return result;
    }

    @PostMapping("/login")
    public Result<MpUser> login(@RequestBody MpUser mpUser) {
        Result<MpUser> result = new Result<>();
        String username = mpUser.getUsername();
        String password = mpUser.getPassword();

//        UserDetails userDetails;
//        try {
//            userDetails = userDetailsService.loadUserByUsername(username);
//        } catch (UsernameNotFoundException e) {
//            log.error("用户名不存在:" + username);
//            result.setStatus(Status.FAIL.getCode());
//            result.setMsg("用户不存在");
//            return result;
//        }
        UsernamePasswordAuthenticationToken token =
            new UsernamePasswordAuthenticationToken(username, password, Collections.emptyList());

        try {
            authenticationManager.authenticate(token);
            SecurityContextHolder.getContext().setAuthentication(token);

        } catch (BadCredentialsException e) {
            log.error("密码错误,该用户名:" + username);
            result.setStatus(Status.FAIL.getCode());
            result.setMsg("密码不正确");
            return result;
        }
        result.setStatus(Status.OK.getCode());
        result.setMsg("登录成功");
        mpUser.setPassword(null);
        mpUser.setAvatar(
            "http://thirdwx.qlogo.cn/mmopen/vi_32/LfSgq4vwXMtH1bYIslr8fW28Q9H5qboYQhfGjIlfxXJudpekjof2OYZARjOjpfQjekiceSzIXDicoSFy7dp11zIA/132");
        mpUser.setId(1L);
        mpUser.setCratedAt("2020 3.1");
        mpUser.setUpdatedAt("2020 3.1");
        result.setData(mpUser);
        return result;
    }
}
