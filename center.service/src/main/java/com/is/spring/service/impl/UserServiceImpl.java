package com.is.spring.service.impl;

import com.is.spring.model.User;
import com.is.spring.model.UserDto;
import com.is.spring.repository.UserRepository;
import com.is.spring.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service("userService")
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public Long addUser(UserDto member) {
        //检查用户名是否已经存在
        if(userRepository.findByUsername(member.getUsername()) != null){
            //走存在处理分支
            return -1L;
        }
        User user = new User();
        //用户姓名
        user.setUsername(member.getUsername());
        //加密密码
        BCryptPasswordEncoder encode = new BCryptPasswordEncoder();
        user.setPassword(encode.encode(member.getPassword()));
        //加入到数据库
        userRepository.saveAndFlush(user);
        return user.getId();
    }

    @Override
    public UserDto findMemberById(Long id) {
        UserDto userDto = new UserDto();
        Optional<User> userMemberOptional = userRepository.findById(id);

        if(!userMemberOptional.isPresent()){
            //用户存在，走不存在处理
            return userDto;
        }

        User user = userMemberOptional.get();

        userDto.setUsername(user.getUsername());
        userDto.setPassword(user.getPassword());

        return userDto;
    }

    @Override
    public UserDto findMemberByUsername(String name) {

        User userMember = userRepository.findByUsername(name);

        UserDto userDto = new UserDto();
        userDto.setUsername(userMember.getUsername());
        userDto.setPassword(userMember.getPassword());

        return userDto;
    }

    @Override
    public List<UserDto> findAllMember() {
        List<User> userMemberList =  userRepository.findAll();
        List<UserDto> userDtoList = new ArrayList<>();;
        for (int i = 0; i < userMemberList.size(); i++) {
            User userMember = (User) userMemberList.get(i);

            UserDto userDto = new UserDto();
            userDto.setUsername(userMember.getUsername());
            userDto.setPassword(userMember.getPassword());

            userDtoList.add(userDto);
        }
        return userDtoList;
    }

    @Override
    public User findByUsername(String name) {
        return  userRepository.findByUsername(name);
    }
}
