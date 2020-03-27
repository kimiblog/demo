package com.is.spring.config;

import com.is.spring.service.UserService;
import com.is.spring.service.impl.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.web.context.SecurityContextRepository;

import javax.sql.DataSource;

//定义配置类被注解的类内部包含有一个或多个被@Bean注解的方法，这些方法将会被
//AnnotationConfigApplicationContext或AnnotationConfigWebApplicationContext类进行扫描，
//并用于构建bean定义，初始化Spring容器。
@Configuration
//加载了WebSecurityConfiguration配置类, 配置安全认证策略。
//加载了AuthenticationConfiguration,
//@EnableWebSecurity
//用来构建一个全局的AuthenticationManagerBuilder的标志注解
//开启基于方法的安全认证机制，也就是说在web层的controller启用注解机制的安全确认
@EnableGlobalMethodSecurity(prePostEnabled = true)
//Web Security 配置类
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    //引入成功与失败的处理类
    @Autowired
    private AuthenticationSuccess authenticationSuccess;
    @Autowired
    private AuthenticationFailure authenticationFailure;
    @Autowired
    private UserDetailsServiceImpl userDetailsServiceImpl;


    @Override
    protected void configure(HttpSecurity http) throws Exception {

//        http.formLogin()                    //  定义当需要用户登录时候，转到的登录页面。
//                .loginPage("/index")   // 设置登录页面
//                .failureUrl("/login-error")
//                .loginProcessingUrl("/login")  // 自定义的登录接口
//                .and()
//                .authorizeRequests()        // 定义哪些URL需要被保护、哪些不需要被保护
//                .antMatchers("/index","/registered").permitAll()     // 设置所有人都可以访问登录页面
//                .anyRequest()               // 任何请求,登录后可以访问
//                .authenticated()
//                .and()
//                .csrf().disable();

        http.authorizeRequests()
                //antMatchers无需权限 即可访问，permitAll面向全部用户开放
                .antMatchers("/login").permitAll()
                //除了antmatchers中的例外，其他任何请求都需要权限认证
                .anyRequest().authenticated()
                //formlogin登录配置,and()是链接符，and之间的内容有相同的作用域
                .and().formLogin().loginPage("/login")
                .successHandler(authenticationSuccess)//登陆成功处理
                .failureHandler(authenticationFailure)//登录失败的处理
                //登录验证处理请求，请求逻辑是security内置的，此处只设置自己喜欢的请求就可以了，然后在表单中提交的请求要与此处设置的一致即可
                .loginProcessingUrl("/login")
                //设置security内置的请求表单元素的name名称，此处设置的要与登录表单的用户名密码的name一致 .usernameParameter("loginname").passwordParameter("password").permitAll()
                .and().headers().frameOptions().disable()
                //设置登出后跳转的链接，一般设置登录页面，登录请求也是security内置的默认为logout,自定义登录链接用.logoutUrl(logoutUrl)
                .and().logout().logoutSuccessUrl("/login").permitAll()
                .and().csrf().disable();// 关闭csrf防护

    }

    /**
     * 设定PsswordEncoder为BeanBcrypt加密方式，后面在设定AuthenticationProvider需要用到
     *
     * @return
     */
    @Bean
    public PasswordEncoder passwordEncoderBean() {
        return new BCryptPasswordEncoder();
    }

    @Override
    public void configure(WebSecurity web) throws Exception {

        //放行静态资源被拦截
        web.ignoring().antMatchers("/css/**");
        web.ignoring().antMatchers("/js/**");
        web.ignoring().antMatchers("/images/**");
    }



    /**
     * 创建认证提供者Bean
     * DaoAuthenticationProvider是SpringSecurity提供的AuthenticationProvider默认实现类
     * 授权方式提供者，判断授权有效性，用户有效性，在判断用户是否有效性，
     * 它依赖于UserDetailsService实例，可以自定义UserDetailsService的实现。
     * 技巧01：
     *
     * @return
     */
    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        // 创建DaoAuthenticationProvider实例
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        // 将自定义的认证逻辑添加到DaoAuthenticationProvider
        authProvider.setUserDetailsService(userDetailsServiceImpl);
        // 设置自定义的密码加密
        authProvider.setPasswordEncoder(passwordEncoderBean());
        return authProvider;
    }

    /*
     * 配置好的认证提供者列表
     *
     * */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        // 添加自定义的认证逻辑
        auth.authenticationProvider(authenticationProvider());
    }

    /**
     * 用户认证
     * 添加用户名为admin密码为12345的ADMIN权限用户
     * @param auth
     * @throws Exception
     */

//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.inMemoryAuthentication()
//                .withUser("admin")
//                .password(new BCryptPasswordEncoder().encode("123456"))
//                .roles("ADMIN");
//    }


//    @Bean
//    public SecurityContextRepository securityContextRepository() {
//        //设置对spring security的UserDetails进行session保存,这个必须要有，不然不会保存至session对应的缓存redis中
//        HttpSessionSecurityContextRepository httpSessionSecurityContextRepository =
//                new HttpSessionSecurityContextRepository();
//        return httpSessionSecurityContextRepository;
//    }

}
