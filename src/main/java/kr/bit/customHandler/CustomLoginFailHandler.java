package kr.bit.customHandler;

import kr.bit.service.LogService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


@Component
public class CustomLoginFailHandler implements AuthenticationFailureHandler {

    private final LogService logService;

    public CustomLoginFailHandler(LogService logService) {
        this.logService = logService;
    }

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException e) throws IOException, ServletException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        String username = request.getParameter("username");
        String message = String.format("%s|%s|로그인 실패|%s",  LocalDateTime.now().format(formatter), username,"");
        logService.logAction(message);

        response.sendRedirect(request.getContextPath() + "/auth/login?error=true");
    }
}

