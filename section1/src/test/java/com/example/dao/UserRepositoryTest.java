package com.example.dao;


import com.example.config.RootConfig;
import com.example.domain.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.math.BigInteger;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = RootConfig.class)
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    public void hasMatchUser() {
        BigInteger count = userRepository.countByUserNameAndPassword("admin","123456");
        assertTrue(count.intValue() > 0);
    }

    @Test
    public void findUserByUserName() {
        User user = userRepository.findByUserName("admin");
        assertNotNull(user);
        assertEquals(user.getUserName(), "admin");
    }

}
