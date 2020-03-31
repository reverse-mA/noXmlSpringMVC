package com.example.service;


import com.example.config.RootConfig;
import com.example.dao.LoginLogRepository;
import com.example.dao.UserRepository;
import com.example.domain.LoginLog;
import com.example.domain.User;
import com.example.web.LoginController;
import com.example.web.LoginInfo;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.math.BigInteger;
import java.util.Date;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = RootConfig.class)
public class UserServiceTest{


    private UserRepository userRepository;

    private LoginLogRepository loginLogRepository;

    private UserService userService;

    @Before
    public void setUp() throws Exception{
        userRepository = mock(UserRepository.class);
        loginLogRepository = mock(LoginLogRepository.class);
        userService = new UserService();
        userService.userRepository = userRepository;
        userService.loginLogRepository = loginLogRepository;
    }

    @Test
    public void hasMatchUser() {
        when(userRepository.
                countByUserNameAndPassword("admin","123456"))
                .thenReturn(new BigInteger("1"));
        assertTrue(userService.hasMatchUser("admin","123456"));

        when(userRepository.
                countByUserNameAndPassword("admin","1111"))
                .thenReturn(new BigInteger("0"));
        assertFalse(userService.hasMatchUser("admin","1111"));

    }


    @Test
    public void findUserByUserName() {
        User user = new User();
        user.setUserName("admin");
        when(userRepository.findByUserName("admin")).thenReturn(user);
        User findUser = userService.findUserByUserName("admin");
        assertEquals(findUser.getUserName(), "admin");
    }

    @Test
    public void loginSuccess() {
        User user = new User();
        user.setUserId(1);
        user.setUserName("admin");
        user.setPassword("123456");
        user.setLastIp("192.168.12.7");
        user.setLastVisit(new Date());
        userService.saveLog(user);
        verify(userRepository).updateLoginInfo(user.getLastVisit(),user.getLastIp(),1);;

    }
}

