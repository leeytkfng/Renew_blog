package board.backend.global.Logging;

import jakarta.servlet.ServletRequestAttributeEvent;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Aspect
@Component
@Slf4j
public class ApiLoggingAspect {

    @Around("execution(* board.backend..Presentation..*Controller.*(..))")
    public Object logApiRequest(ProceedingJoinPoint joinPoint) throws Throwable {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();

        String method = request.getMethod();
        String uri = request.getRequestURI();
        String clientIp = getClientIp(request);

        log.info("┌─ [API Request] {} {} - IP: {}", method, uri, clientIp);

        long startTime = System.currentTimeMillis();
        try {
           Object result = joinPoint.proceed();
           long executionTime = System.currentTimeMillis();

            log.info("└─ [API Response] {} {} - 200 OK ({}ms)", method, uri, executionTime);

            return result;
        } catch (Exception e) {
            long executionTime = System.currentTimeMillis() - startTime;

            log.error("└─ [API Response] {} {} - ERROR ({}ms)", method, uri, executionTime);
            log.error("   예외: {}", e.getMessage());

            throw e;
        }
    }


    private String getClientIp(HttpServletRequest request) {
        String ip  =request.getHeader("X-Forwarded-For");
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)){
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }
}
