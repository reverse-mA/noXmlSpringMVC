package com.example.dao;

import com.example.domain.LoginLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

public interface LoginLogRepository extends JpaRepository<LoginLog,Integer> {

    LoginLog save(LoginLog loginLog);

}
