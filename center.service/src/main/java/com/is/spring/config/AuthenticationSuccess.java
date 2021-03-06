package com.is.spring.config;

import com.is.spring.model.User;
import com.is.spring.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

//验证成功返回处理类
@Component("authenticationSuccess")
public class AuthenticationSuccess implements AuthenticationSuccessHandler {
    //验证成功后进入系统前进行的处理，比如初始化权限用户信息等
    @Autowired
    private UserService userService;
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
            throws IOException, ServletException {
        //authentication.getName()这个是认证通过的账号
        User ul=userService.findByUsername(authentication.getName());
        //例如把用户信息存入session
        request.getSession().setAttribute("USERSESSION", ul);
        //处理完成后重定向到系统主页面的链接
        new DefaultRedirectStrategy().sendRedirect(request, response, "/index");
    }
}
