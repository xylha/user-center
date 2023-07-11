package com.xyl.usercenter3;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.DigestUtils;

import java.nio.charset.StandardCharsets;
import java.security.DigestException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@SpringBootTest
class UserCenter3ApplicationTests {

    @Test
    void testDigest() throws NoSuchAlgorithmException {
        MessageDigest md5 = MessageDigest.getInstance("MD5");
        String s = DigestUtils.md5DigestAsHex(("abcd" + "mypassword").getBytes());
        System.out.println(s);
    }
    @Test
    void contextLoads() {
    }

}
