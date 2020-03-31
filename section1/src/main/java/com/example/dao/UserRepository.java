package com.example.dao;

import com.example.domain.User;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;
import java.util.Date;
public interface UserRepository extends CrudRepository<User,Integer> {

    @Cacheable("userCache")
    User findByUserName(final String userName);


    @Query(value = "SELECT COUNT(t.id) FROM user t WHERE t.user_name=?1 and t.password=?2",nativeQuery = true)
    BigInteger countByUserNameAndPassword(String userName, String password);

    @Transactional
    @Modifying
    @Query(value = "UPDATE user SET last_visit=?1,last_ip=?2 WHERE id =?3",nativeQuery = true)
    void updateLoginInfo(Date lastVisit,String lastIp,int userId);
}
