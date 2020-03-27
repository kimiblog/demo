package com.is.spring.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serializable;

@Data
//lombok 注解，NoArgsConstructor 无参数构造函数
@NoArgsConstructor
public class UserDto implements Serializable {
    //用户名
    private String username;
    //用户密码
    private String password;

    @Override
    public String toString() {
        return "UserDto{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
