package com.is.spring.model;

import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "sys_user")
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    //帐号，不能为空
    @NotBlank
    @Column
    private String username;
    //密码，不能为空, 长度为64
    @NotBlank
    @Column
    private String password;

    @Override
    public String toString() {
        return "UserMember{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

}
