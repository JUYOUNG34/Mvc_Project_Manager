//package kr.bit.security;
//
//import org.aspectj.lang.annotation.Aspect;
//import org.springframework.security.authentication.AnonymousAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.stereotype.Component;
//import org.springframework.web.servlet.HandlerInterceptor;
//import org.springframework.web.servlet.ModelAndView;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
//
//public class SecurityInterceptor implements HandlerInterceptor {
//
//    @Override
//    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
//        String uri = request.getRequestURI();
//
//        // 인증 필요 없는 경로는 통과
//        if (uri.contains("/auth/login") ||
//                uri.contains("/css/") ||
//                uri.contains("/images/") ||
//                uri.contains("/resources/") ||
//                uri.contains("/static/")) {
//            return true;
//        }
//
//        // 현재 인증 상태 확인
//        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//        boolean isAuthenticated = auth != null &&
//                auth.isAuthenticated() &&
//                !(auth instanceof AnonymousAuthenticationToken);
//
//        System.out.println("인터셉터 검사: " + uri);
//        System.out.println("인증 상태: " + isAuthenticated);
//
//        // 인증되지 않았으면 로그인 페이지로 리다이렉트
//        if (!isAuthenticated) {
//            response.sendRedirect(request.getContextPath() + "/auth/login");
//            return false;
//        }
//
//        return true;
//    }
//
//    @Override
//    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
//        // 필요시 구현
//    }
//
//    @Override
//    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
//        // 필요시 구현
//    }
//}