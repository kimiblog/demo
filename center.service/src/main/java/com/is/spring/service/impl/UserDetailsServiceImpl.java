package com.is.spring.service.impl;


import com.is.spring.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.User;
import com.is.spring.model.UserDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.Collection;

@Slf4j
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("用户的用户名: {}", username);
        // TODO 根据用户名，查找到对应的密码，与权限


        // 封装用户信息，并返回。参数分别是：用户名，密码，用户权限

//        UserDto userDto=new UserDto();
//        userDto.setUsername(username);
//        userDto.setPassword("123456");
//        return createUser(userDto);

        UserDto userDto = userService.findMemberByUsername(username);
        if (userDto == null) {
            //如果没找到用户信息，抛出用户没找到异常
            throw new EntityNotFoundException("name：" + username + " not found");
        } else {
            User user = new User(username,userDto.getPassword() ,
                    AuthorityUtils.commaSeparatedStringToAuthorityList("admin"));
            return user;
        }
    }

}
