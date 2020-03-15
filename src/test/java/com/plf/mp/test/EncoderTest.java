package com.plf.mp.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class EncoderTest {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    public void test1() {
        String encode = passwordEncoder.encode("123");
        // $2a$10$eX0awSEW4YQS4NAONhjiFeq.3YHAq1dBqckwDGpgm2IoLUHfoK7zK
        log.info(encode);
    }
}
