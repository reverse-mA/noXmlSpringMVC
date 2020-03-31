package com.example.service;

import com.example.dao.LoginLogRepository;
import com.example.dao.UserRepository;
import com.example.domain.LoginLog;
import com.example.domain.User;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;


@Service
@EnableJpaRepositories(basePackages = "com.example.dao")
@Aspect
@Transactional(value = "transactionManager")
public class UserService {

    @Autowired
    public UserRepository userRepository;

    @Autowired
    public LoginLogRepository loginLogRepository;

    public boolean hasMatchUser(String userName, String password) {
        BigInteger matchCount = userRepository.countByUserNameAndPassword(userName,password);
        return matchCount.intValue() > 0;
    }

    public User findUserByUserName(String userName) {
        return userRepository.findByUserName(userName);
    }

    public boolean saveLog(User user) {
        LoginLog loginLog = new LoginLog();
        loginLog.setUserId(user.getUserId());
        loginLog.setIp(user.getLastIp());
        loginLog.setLoginDate(user.getLastVisit());
        loginLogRepository.save(loginLog);
        userRepository.updateLoginInfo(user.getLastVisit(),user.getLastIp(),user.getUserId());
        return true;
    }
}
