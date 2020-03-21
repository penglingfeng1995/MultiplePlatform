package com.plf.mp.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.plf.mp.model.MpUser;
import com.plf.mp.service.UserService;

@ExtendWith(MockitoExtension.class)
@ExtendWith(SpringExtension.class)
class AuthControllerTest {

    private MockMvc mvc;
    @Mock
    private AuthenticationManager authenticationManager;
    @Mock
    private UserService userService;
    @InjectMocks
    private AuthController authController;
    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @BeforeEach
    public void before() {
        mvc = MockMvcBuilders.standaloneSetup(authController).build();
    }

    @Test
    void auth() throws Exception {
        mvc.perform(get("/auth")).andExpect(status().isOk()).andExpect(mvcResult -> {
            String content = mvcResult.getResponse().getContentAsString();
            Assertions.assertTrue(content.contains("\"isLogin\":false"));
        });
    }

    @Test
    void login() throws Exception {
        mvc.perform(get("/auth")).andExpect(status().isOk()).andExpect(mvcResult -> {
            String content = mvcResult.getResponse().getContentAsString();
            Assertions.assertTrue(content.contains("\"isLogin\":false"));
        });

        Map<String, String> userPwd = new HashMap<>(2);
        userPwd.put("username", "myUser");
        userPwd.put("password", "myPwd");
        String responseBody = new ObjectMapper().writeValueAsString(userPwd);

        User user = new User("myUser", passwordEncoder.encode("myPwd"), Collections.emptyList());
        when(userService.loadUserByUsername("myUser")).thenReturn(user);
        MpUser mpUser = new MpUser();
        mpUser.setUsername("myUser");
        when(userService.getMpUserByUsername("myUser")).thenReturn(mpUser);
        MvcResult mvcResult =
            mvc.perform(post("/auth/login").contentType(MediaType.APPLICATION_JSON).content(responseBody))
                .andExpect(status().isOk()).andReturn();
        HttpSession session = mvcResult.getRequest().getSession();

        mvc.perform(get("/auth").session((MockHttpSession)session)).andExpect(status().isOk()).andExpect(result -> {
            String content = result.getResponse().getContentAsString();
            Assertions.assertTrue(content.contains("\"isLogin\":true"));
        });
    }

    @Test
    void register() {
        System.out.println("测试 ci");
    }

    @Test
    void logout() {}
}