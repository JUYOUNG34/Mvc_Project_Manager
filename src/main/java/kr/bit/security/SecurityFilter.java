package kr.bit.security;

import kr.bit.service.LogService;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Enumeration;

public class SecurityFilter extends OncePerRequestFilter {

//    private final LogService logService;
//
//    public SecurityFilter(LogService logService) {
//        this.logService = logService;
//    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        String uri = request.getRequestURI();

        // 인증이 필요 없는 리소스는 필터 통과
        if (uri.contains("/auth/login") ||
                uri.contains("/css/") ||
                uri.contains("/images/") ||
                uri.contains("/resources/") ||
                uri.contains("/static/")) {
            filterChain.doFilter(request, response);
            return;
        }

        // 인증 정보 확인
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        boolean isAuthenticated = auth != null &&
                auth.isAuthenticated() &&
                !(auth instanceof AnonymousAuthenticationToken);

//        // 사용자 ID 가져오기
//        String adminId = isAuthenticated ? auth.getName() : "Anonymous";
//
//        // 메서드명 및 매개변수 가져오기
//        String methodName = "";
//        String detailMessage="";
//        Enumeration<String> parameterNames = request.getParameterNames();
//        StringBuilder params = new StringBuilder();
//        while (parameterNames.hasMoreElements()) {
//            String paramName = parameterNames.nextElement();
//            String paramValue = request.getParameter(paramName);
//            params.append(paramName).append("=").append(paramValue).append(", ");
//        }
//
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
//        // 로그 메시지 생성
//        String logMessage = String.format("%s|%s|%s|%s",
//                LocalDateTime.now().format(formatter), adminId, methodName, params.toString());
//        if(uri.contains("Proc")){
//            if(uri.contains("manager") && uri.contains("register")){
//                methodName = "관리자 등록 : "+request.getParameter("id");
//            }else if(uri.contains("manager") && uri.contains("modify")){
//                methodName = "관리자 수정 : " + request.getParameter("id");
//                detailMessage = "비밀번호 : " + request.getParameter("pass")+ " 권한 : "+request.getParameter("admin_level");
//            }else if(uri.contains("manager") && uri.contains("delete")){
//                methodName = "관리자 삭제 : " + request.getParameter("id");
//            }
//            if(uri.contains("user") && uri.contains("modify")){
//                methodName = "유저 수정" + request.getParameter("user_id");
//                detailMessage="사진 : " + request.getParameter("photo_image_url") +
//                        " 포인트 : " + request.getParameter("points") +
//                        " 장작 : " + request.getParameter("firewood") +
//                        " 돋보기 : " + request.getParameter("reading_glass");
//            } else if (uri.contains("user") && uri.contains("block")) {
//                methodName = "유저차단 : " + request.getParameter("user_id");
//            }
//            if(uri.contains("board") && uri.contains("blind")){
//                methodName="게시물 블라인드 : " + request.getParameter("id");
//            }
//            logMessage = String.format("%s|%s|%s|%s",
//                    LocalDateTime.now().format(formatter), adminId, methodName,detailMessage);
//            logService.logAction(logMessage);
//        }

        // 인증되지 않은 접근 차단
        if (!isAuthenticated) {
            System.out.println("❌ 인증되지 않은 접근 차단! URI: " + uri);
            response.sendRedirect(request.getContextPath() + "/auth/login");
            return;
        }
        // 다음 필터로 요청 전달
        filterChain.doFilter(request, response);
    }
}
