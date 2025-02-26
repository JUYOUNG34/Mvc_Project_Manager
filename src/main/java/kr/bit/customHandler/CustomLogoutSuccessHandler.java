package kr.bit.customHandler;

import kr.bit.service.LogService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
public class CustomLogoutSuccessHandler implements LogoutSuccessHandler {

    private final LogService logService;

    public CustomLogoutSuccessHandler(LogService logService) {
        this.logService = logService;
    }


    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        if (authentication != null && authentication.getName() != null) {
            String admin_id = authentication.getName();
            String message = String.format("%s|%s|로그아웃",  LocalDateTime.now().format(formatter), admin_id);
            logService.logAction(message);
        }

        response.sendRedirect(request.getContextPath() + "/auth/login?logout=true");
    }
}
