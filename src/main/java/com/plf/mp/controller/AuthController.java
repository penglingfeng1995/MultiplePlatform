package com.plf.mp.controller;

import com.plf.mp.exception.RegisterException;
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
import org.springframework.security.core.userdetails.UsernameNotFoundException;
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
    private UserService userService;

    @Autowired
    private AuthenticationManager authenticationManager;

    /**
     * 判断是否登录
     * 
     * @return 登录状态
     */
    @GetMapping
    public Result<MpUser> auth() {
        Result<MpUser> result = new Result<>();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication instanceof AnonymousAuthenticationToken || authentication == null) {
            result.setIsLogin(false);
        } else {
            String username = authentication.getName();
            MpUser mpUser = userService.getMpUserByUsername(username);
            result.setIsLogin(true);
            result.setData(mpUser);
            result.setAvatar(mpUser.getAvatar());
        }
        result.setStatus(Status.OK.getCode());
        return result;
    }

    /**
     * 登录
     * 
     * @param mpUser
     *            用户实体
     * @return 用户信息
     */
    @PostMapping("/login")
    public Result<MpUser> login(@RequestBody MpUser mpUser) {
        Result<MpUser> result = new Result<>();
        String username = mpUser.getUsername();
        String password = mpUser.getPassword();

        UsernamePasswordAuthenticationToken token =
            new UsernamePasswordAuthenticationToken(username, password, Collections.emptyList());

        try {
            authenticationManager.authenticate(token);
            SecurityContextHolder.getContext().setAuthentication(token);

        } catch (UsernameNotFoundException e) {
            log.error("用户名不存在:" + username);
            result.setStatus(Status.FAIL.getCode());
            result.setMsg("用户不存在");
            return result;
        } catch (BadCredentialsException e) {
            log.error("密码错误,该用户名a:" + username);
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

    /**
     * 注册
     * 
     * @param mpUser
     *            用户实体
     * @return 用户信息
     */
    @PostMapping("/register")
    public Result<MpUser> register(@RequestBody MpUser mpUser) {
        String password = mpUser.getPassword();
        Result<MpUser> result = new Result<>();
        try {
            userService.registerUser(mpUser);
            result.setStatus(Status.OK.getCode());
            result.setMsg("注册成功");
            MpUser mpUserFull = userService.getMpUserByUsername(mpUser.getUsername());
            result.setData(mpUserFull);

            // 注册同时登录
            UsernamePasswordAuthenticationToken token =
                new UsernamePasswordAuthenticationToken(mpUser.getUsername(), password, Collections.emptyList());
            authenticationManager.authenticate(token);
            SecurityContextHolder.getContext().setAuthentication(token);
        } catch (RegisterException e) {
            log.error("注册失败");
            result.setStatus(Status.FAIL.getCode());
            result.setMsg(e.getMessage());
        }
        return result;
    }

    /**
     * 注销
     * 
     * @return 注销成功
     */
    @GetMapping("/logout")
    public Result<Object> logout() {
        Result<Object> result = new Result<>();
        result.setStatus(Status.FAIL.getCode());
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication instanceof AnonymousAuthenticationToken) {
            result.setMsg("用户尚未登录");
        } else {
            SecurityContextHolder.clearContext();
            result.setMsg("注销成功");
        }
        return result;
    }
}
