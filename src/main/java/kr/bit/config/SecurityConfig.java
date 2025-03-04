package kr.bit.config;

import kr.bit.customHandler.CustomLoginFailHandler;
import kr.bit.customHandler.CustomLoginSuccessHandler;
import kr.bit.customHandler.CustomLogoutSuccessHandler;
import kr.bit.security.AdminUserDetailsService;
import kr.bit.service.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.security.web.csrf.CsrfFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.web.filter.CharacterEncodingFilter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private AdminUserDetailsService adminUserDetailsService;

    private final CustomLoginSuccessHandler customLoginSuccessHandler;
    private final CustomLoginFailHandler customLoginFailureHandler;
    private final CustomLogoutSuccessHandler customLogoutSuccessHandler;
    private final LogService logService; // 로그 기록 서비스 추가

    public SecurityConfig(CustomLoginSuccessHandler customLoginSuccessHandler,
                          CustomLoginFailHandler customLoginFailureHandler,
                          CustomLogoutSuccessHandler customLogoutSuccessHandler,
                          LogService logService) {
        this.customLoginSuccessHandler = customLoginSuccessHandler;
        this.customLoginFailureHandler = customLoginFailureHandler;
        this.customLogoutSuccessHandler = customLogoutSuccessHandler;
        this.logService = logService;
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

//    @Override 암호화
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.userDetailsService(adminUserDetailsService)
//                .passwordEncoder(NoOpPasswordEncoder.getInstance());
//    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(adminUserDetailsService)
                .passwordEncoder(NoOpPasswordEncoder.getInstance());
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        // 정적 리소스는 보안 필터 체인에서 제외
        web.ignoring()
                .antMatchers(
                        "/css/**",
                        "/resources/**",
                        "/images/**",
                        "/static/**",
                        "/controller/css/**",
                        "/controller/resources/**",
                        "/controller/images/**",
                        "/controller/static/**"
                );
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        CharacterEncodingFilter encodingFilter = new CharacterEncodingFilter();
        encodingFilter.setEncoding("UTF-8");
        encodingFilter.setForceEncoding(true); // security 수행될 때 글 깨질 까봐 인코딩
        http.addFilterBefore(encodingFilter, CsrfFilter.class); //csrf 보안 처리전 실행되도록

        http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/auth/login").permitAll()
                .antMatchers("/menu/manager/**").hasRole("master")
                .antMatchers("/menu/manager/modify").hasAnyRole("service_manager","master")
                .antMatchers("/menu/stats").hasAnyRole("master", "service_manager")
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/auth/login")
                .loginProcessingUrl("/auth/login")
                .successHandler(customLoginSuccessHandler)
                .failureHandler(customLoginFailureHandler)
                .usernameParameter("id")
                .passwordParameter("pass")
                .permitAll()
                .and()
                .logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/auth/logout"))
                .invalidateHttpSession(true)
                .clearAuthentication(true)
                .deleteCookies("JSESSIONID")
                .logoutSuccessHandler(customLogoutSuccessHandler)
                .permitAll()
                .and()
                .exceptionHandling()
                .accessDeniedPage("/auth/login?denied=true")
                .and()
                // 세션 관리 강화
                .sessionManagement()
                .maximumSessions(1)
                .expiredUrl("/auth/login?expired=true")
                .maxSessionsPreventsLogin(false)
                .and()
                .sessionFixation().migrateSession();

    }

    @Bean
    public AuthenticationSuccessHandler authenticationSuccessHandler() {
        return new SimpleUrlAuthenticationSuccessHandler() {
            @Override
            public void onAuthenticationSuccess(HttpServletRequest request,
                                                HttpServletResponse response,
                                                Authentication authentication)
                    throws IOException, ServletException {

                System.out.println("로그인 성공! 사용자: " + authentication.getName());

                getRedirectStrategy().sendRedirect(request, response, "/menu/stats");
            }
        };
    }

    @Bean
    public LogoutSuccessHandler logoutSuccessHandler() {
        return new LogoutSuccessHandler() {
            @Override
            public void onLogoutSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication)
                    throws IOException, ServletException {

                System.out.println("로그아웃 성공!");

                // 로그인 페이지로 리다이렉트
                String targetUrl = "/auth/login";
                // 컨텍스트 경로가 '/controller'인 경우 고려
                if (request.getRequestURI().startsWith("/controller")) {
                    targetUrl = "/controller" + targetUrl;
                }

                response.sendRedirect(request.getContextPath() + targetUrl);
            }
        };
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        // BCryptPasswordEncode 암호화처리시
        return NoOpPasswordEncoder.getInstance();
    }
}