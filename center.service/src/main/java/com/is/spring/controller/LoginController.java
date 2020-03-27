package com.is.spring.controller;

import com.is.spring.model.User;
import com.is.spring.model.UserDto;
import com.is.spring.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import static org.springframework.security.web.context.HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY;

@Controller
@RequestMapping("/")
@Slf4j
public class LoginController {

    @Autowired
    private UserService userService;

    @GetMapping(path = "login")
    public ModelAndView showCreateForm() {
        log.warn("is login ");
        return new ModelAndView("login");
    }

    @RequestMapping(path = "index")
    public String login(User user, BindingResult result, ModelMap map, HttpServletRequest request, HttpServletResponse response) {
        log.warn(" is index");
        if (result.hasErrors()) {
            log.warn("Binding Result: {}", result);
//            map.addAttribute("message", result.toString());
        }

//        try {
//            user = loginService.getUserByUserNoAndPwd(user);
//        }catch (Exception e) {
//            log.error("LoginController,getUserByNoAndPwd excute fail!");
//            e.printStackTrace();
//        }
//        if(user != null){
//            request.getSession().setAttribute("loginUser", user);
//            return "redirect:/index.html";
//        }else{
            return "/starter";
//        }
    }

    //注册一个账号
    @GetMapping(path = "registered")
    public String registered(HttpServletRequest request, HttpServletResponse response) {
        log.warn("login ");
        UserDto userdto=new UserDto();
        userdto.setUsername("admin");
        userdto.setPassword("123456");
        userService.addUser(userdto);
        return null;
    }

    //登陆失败
    @GetMapping("/login-error")
    public String loginError(Model model) {
        log.warn("登陆失败 ");
        model.addAttribute("loginError",true);
        model.addAttribute("errorMsg", "登陆失败，用户名或密码错误！");
        return "login";
    }
}
