package kr.bit.aspect;

import kr.bit.service.AdminService;
import kr.bit.service.LogService;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Aspect
@Component
public class LoggingAspect {
    @Autowired
    private LogService logService;
    @Autowired
    private AdminService adminService;
    private static final ThreadLocal<Set<String>> executedMethods =
            ThreadLocal.withInitial(() -> new HashSet<String>());

    private String getCurrentUsername() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.isAuthenticated() && !"anonymousUser".equals(auth.getPrincipal())) {
            Object principal = auth.getPrincipal();
            if (principal instanceof UserDetails) {
                return ((UserDetails) principal).getUsername();
            } else {
                return principal.toString();
            }
        }
        return "UnknownUser";
    }

    // 🚀 모든 서비스 메서드에 AOP 적용하되, logAction() 메서드는 제외
//    @Pointcut("execution(* kr.bit.service..*(..)) && !execution(* kr.bit.service.LogService.logAction(..))")
//    public void applicationPackagePointcut() {
//    }

//    @Before("execution(* kr.bit.service..*(..))")
//    public void logBeforeMethodExecution(JoinPoint joinPoint) {
//        String methodName = joinPoint.getSignature().toShortString();
//        System.out.println(methodName);
//        // 🚨 동일 요청 내에서 이미 실행된 메서드라면 로그 기록 생략
//        if (executedMethods.get().contains(methodName)) {
//            return;
//        }
//
//        executedMethods.get().add(methodName);
//
//        String admin_id = getCurrentUsername();
//        String message = String.format("%s|%s|%s",
//                LocalDateTime.now(), admin_id, methodName);
//
//        logService.logAction(message);
//    }
//    @After("execution(* kr.bit.service..*(..))")
//    public void clearExecutedMethods() {
//        executedMethods.remove();
//    }
}
