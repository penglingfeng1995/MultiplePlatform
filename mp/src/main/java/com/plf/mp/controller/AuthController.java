package com.plf.mp.controller;

import com.plf.mp.model.MpUser;
import com.plf.mp.model.Result;
import com.plf.mp.model.Status;
import com.plf.mp.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

/**
 * @author plf
 */
@Slf4j
@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserService userService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @GetMapping
    public Result<MpUser> auth() {
        Result<MpUser> result = new Result<>();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication instanceof AnonymousAuthenticationToken) {
            result.setIsLogin(false);
        } else {
            String username =authentication.getName();
            MpUser mpUser = userService.getMpUserByUsername(username);
            result.setIsLogin(true);
            result.setData(mpUser);
            result.setAvatar(mpUser.getAvatar());
        }
        result.setStatus(Status.OK.getCode());
        return result;
    }

    @PostMapping("/login")
    public Result<MpUser> login(@RequestBody MpUser mpUser) {
        Result<MpUser> result = new Result<>();
        String username = mpUser.getUsername();
        String password = mpUser.getPassword();

        UserDetails userDetails;
        try {
            userDetails = userService.loadUserByUsername(username);
        } catch (UsernameNotFoundException e) {
            log.error("用户名不存在:" + username);
            result.setStatus(Status.FAIL.getCode());
            result.setMsg("用户不存在");
            return result;
        }
        UsernamePasswordAuthenticationToken token =
            new UsernamePasswordAuthenticationToken(userDetails, password, userDetails.getAuthorities());

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
        MpUser mpUserByUsername = userService.getMpUserByUsername(username);
        mpUserByUsername.setPassword(null);
        result.setData(mpUserByUsername);
        return result;
    }
}
