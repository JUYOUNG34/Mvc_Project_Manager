package kr.bit.config;

import kr.bit.security.AdminUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

//    @Autowired
//    private AdminUserDetailsService adminUserDetailsService;
//
//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.userDetailsService(adminUserDetailsService)
//                .passwordEncoder(passwordEncoder());
//    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/css/**", "/resources/**", "/images/**", "/auth/login").permitAll()
                .antMatchers("/menu/manager/**").hasRole("master")
                .antMatchers("/menu/stats").hasAnyRole("master", "service_manager")
                .anyRequest().authenticated() //나머지 요청들은 권한이 있어야함
                .and()
                .formLogin()// 폼 로그인 방식 허용
                .loginPage("/auth/login")
                .permitAll()
                .defaultSuccessUrl("/menu/stats")
                .failureUrl("/auth/login?error=true")
                .usernameParameter("id")
                .passwordParameter("pass")
                .and()
                .logout()
                .logoutSuccessUrl("/auth/login")
                .deleteCookies("JSESSIONID")
                .invalidateHttpSession(true)
                .permitAll();
    }



    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


}