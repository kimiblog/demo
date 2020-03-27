package com.is.spring.service;

import com.is.spring.model.User;
import com.is.spring.model.UserDto;
import com.is.spring.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public interface UserService {
    //增加用户
    public Long addUser(UserDto member);
    //通过ID获取用户信息
    public UserDto findMemberById(Long id);
    //通过用户名称获取用户信息
    public UserDto findMemberByUsername(String name);

    public List<UserDto> findAllMember();

    User findByUsername(String name);
}
