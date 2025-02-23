package kr.bit.config;

import kr.bit.security.AdminUserDetailsService;
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
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private AdminUserDetailsService adminUserDetailsService;

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

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
        http
                .authorizeRequests()
                .antMatchers("/auth/login", "/controller/auth/login").permitAll()
                .antMatchers("/menu/manager/**", "/controller/menu/manager/**").hasRole("master")
                .antMatchers("/menu/stats", "/controller/menu/stats").hasAnyRole("master", "service_manager")
                // 이 부분이 중요 - 모든 다른 요청은 인증 필요
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/auth/login")
                .loginProcessingUrl("/auth/login")
                .successHandler(authenticationSuccessHandler())
                .failureUrl("/auth/login?error=true")
                .usernameParameter("id")
                .passwordParameter("pass")
                .permitAll()
                .and()
                .logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/auth/logout"))
                .invalidateHttpSession(true)
                .clearAuthentication(true)
                .deleteCookies("JSESSIONID")
                .logoutSuccessHandler(logoutSuccessHandler())
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
                .sessionFixation().migrateSession()
        ;

        // 디버깅 필터
        http.addFilterAfter(new org.springframework.web.filter.OncePerRequestFilter() {
            @Override
            protected void doFilterInternal(HttpServletRequest request,
                                            HttpServletResponse response,
                                            javax.servlet.FilterChain filterChain)
                    throws javax.servlet.ServletException, IOException {

                Authentication auth = SecurityContextHolder.getContext().getAuthentication();
                boolean isAuthenticated = auth != null &&
                        auth.isAuthenticated() &&
                        !(auth instanceof org.springframework.security.authentication.AnonymousAuthenticationToken);

                System.out.println("=== 보안 필터 확인 ===");
                System.out.println("요청 URI: " + request.getRequestURI());
                System.out.println("인증 상태: " + isAuthenticated);
                if (isAuthenticated) {
                    System.out.println("인증된 사용자: " + auth.getName());
                    System.out.println("권한: " + auth.getAuthorities());
                }

                // 인증되지 않은 상태에서 보호된 URL에 직접 접근하는 경우 차단
                if (!isAuthenticated &&
                        !request.getRequestURI().contains("/auth/login") &&
                        !request.getRequestURI().contains("/css/") &&
                        !request.getRequestURI().contains("/images/") &&
                        !request.getRequestURI().contains("/resources/") &&
                        !request.getRequestURI().contains("/static/")) {

                    System.out.println("인증되지 않은 접근 시도 차단!");
                    response.sendRedirect(request.getContextPath() + "/auth/login");
                    return;
                }

                filterChain.doFilter(request, response);
            }
        }, org.springframework.security.web.authentication.AnonymousAuthenticationFilter.class);
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

                // 기본 대시보드로 리다이렉트
                String targetUrl = "/menu/stats";
                // 컨텍스트 경로가 '/controller'인 경우 고려
                if (request.getRequestURI().startsWith("/controller")) {
                    targetUrl = "/controller" + targetUrl;
                }

                getRedirectStrategy().sendRedirect(request, response, targetUrl);
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
        return new BCryptPasswordEncoder();
    }
}