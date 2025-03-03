package kr.bit.security;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class SecurityFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        String uri = request.getRequestURI();

        // 인증이 필요 없는 리소스는 바로 통과
        if (uri.contains("/auth/login") ||
                uri.contains("/css/") ||
                uri.contains("/images/") ||
                uri.contains("/resources/") ||
                uri.contains("/static/")) {

            filterChain.doFilter(request, response);
            return;
        }

        // 인증 상태 확인
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        boolean isAuthenticated = auth != null &&
                auth.isAuthenticated() &&
                !(auth instanceof AnonymousAuthenticationToken);

        System.out.println("=== 보안 필터 체크 ===");
        System.out.println("요청 URI: " + uri);
        System.out.println("인증 여부: " + isAuthenticated);

        if (!isAuthenticated) {
            System.out.println("인증되지 않은 접근 차단! URI: " + uri);
            response.sendRedirect(request.getContextPath() + "/auth/login");
            return;
        }

        // 인증된 요청은 계속 진행
        filterChain.doFilter(request, response);
    }
}