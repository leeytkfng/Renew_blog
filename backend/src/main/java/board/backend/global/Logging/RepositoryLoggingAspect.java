package board.backend.global.Logging;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class RepositoryLoggingAspect {

    @Around("execution(* board.backend..Infrastructure.Repository..*(..))")
    public Object logRepository(ProceedingJoinPoint joinPoint) throws Throwable {
        String methodName = joinPoint.getSignature().getName();
        String className = joinPoint.getSignature().getDeclaringType().getSimpleName();

        log.trace("[Repository] {}.{} 호출", className ,methodName);

        try {
            Object result = joinPoint.proceed();
            log.trace("[Repository] {}.{}", className , methodName);
            return result;
        } catch (Exception e) {
            log.error("[Repository] {}.{} 실패: {}", className, methodName, e.getMessage());
            throw e;
        }
    }

}
