package com.is.spring.repository;

import com.is.spring.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    //通过name查找用户
    User findByUsername(String name);
}
