package board.backend.global.Logging;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class CommonLoggingAspect {

    /**
     * Command Handler 실행 로깅
     * @param joinPoint ProceedingJoinPoint
     * Command Handler 실행 시작/완료 로깅
     * 실행 시간 측정
     * 예외 로깅
     * @return
     */
    @Around("execution(* board.backend..Application.Command..*Handler.handle(..))")
    public Object logCommandHandler(ProceedingJoinPoint joinPoint) {
        Object[] args = joinPoint.getArgs();
        String commandName = args.length > 0 ? args[0].getClass().getSimpleName() : "Unknown";
        String handlerName = joinPoint.getSignature().getDeclaringType().getSimpleName();

        log.info("┌─ [Command] {} 실행 시작", commandName);
        log.debug("│  Handler: {}", handlerName);

        long startTime = System.currentTimeMillis();

        try {
            Object result = joinPoint.proceed();
            long executionTime = System.currentTimeMillis() - startTime;


            log.info("└─ [Command] {} 완료 ({}ms)", commandName, executionTime);

            return result;
        } catch (Throwable e) {
            long executionTime  = System.currentTimeMillis() - startTime;

            log.error("└─ [Command] {} 실패 ({}ms)", commandName, executionTime);
            log.error("   예외: {}", e.getMessage(), e);

            throw new RuntimeException(e);
        }
    }

    /**
     * Command 생성 로깅
     */
    @Around("execution(board.backend..Application.Command..*Command.new(..))")
    public Object logCommandCreation(ProceedingJoinPoint joinPoint) throws Throwable {
        String commandName = joinPoint.getSignature().getDeclaringType().getSimpleName();
        Object[] args = joinPoint.getArgs();

        log.debug("[Command Created] {} - params: {}", commandName, args);

        return joinPoint.proceed();
    }
}
