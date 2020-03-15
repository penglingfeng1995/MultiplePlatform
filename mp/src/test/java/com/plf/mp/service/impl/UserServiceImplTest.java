package com.plf.mp.service.impl;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.plf.mp.mapper.MpUserMapper;
import com.plf.mp.model.MpUser;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    MpUserMapper mpUserMapper;
    @Mock
    PasswordEncoder passwordEncoder;
    @InjectMocks
    UserServiceImpl userService = new UserServiceImpl();

    @Test
    public void registerUser() {

        Mockito.when(passwordEncoder.encode("123")).thenReturn("encode123");
        Mockito.when(mpUserMapper.getUserByUsername("zzzzz")).thenReturn(null);

        MpUser mpUser = new MpUser();
        mpUser.setUsername("zzzzz");
        mpUser.setPassword("123");
        userService.registerUser(mpUser);

        MpUser userVerify = new MpUser();
        userVerify.setUsername("zzzzz");
        userVerify.setPassword("encode123");
        userVerify.setAvatar(
            "http://thirdwx.qlogo.cn/mmopen/vi_32/LfSgq4vwXMtH1bYIslr8fW28Q9H5qboYQhfGjIlfxXJudpekjof2OYZARjOjpfQjekiceSzIXDicoSFy7dp11zIA/132");
        Mockito.verify(mpUserMapper).getUserByUsername("zzzzz");
        Mockito.verify(mpUserMapper).addUser(userVerify);
    }

    @Test
    void getMpUserByUsername() {

        userService.getMpUserByUsername("zzz");

        Mockito.verify(mpUserMapper).getUserByUsername("zzz");
    }

    @Test
    void loadUserByUsernameException() {
        Assertions.assertThrows(UsernameNotFoundException.class, () -> userService.loadUserByUsername("zzz"));
    }

    @Test
    void loadUserByUsername() {
        MpUser mockUser= new MpUser();
        mockUser.setUsername("zzz");
        mockUser.setPassword("encode123");
        Mockito.when(mpUserMapper.getUserByUsername("zzz")).thenReturn(mockUser);
        UserDetails userDetails = userService.loadUserByUsername("zzz");
        Assertions.assertEquals("zzz",userDetails.getUsername());
        Assertions.assertEquals("encode123",userDetails.getPassword());
    }
}