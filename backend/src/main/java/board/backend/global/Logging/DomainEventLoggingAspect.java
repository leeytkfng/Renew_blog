package board.backend.global.Logging;


import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class DomainEventLoggingAspect {

    /**
     * Domain Event 발행 로깅
     */
    @AfterReturning(
            pointcut = "execution(* board.backend..Domain..publish*(..))",
            returning = "event"
    )
    public void logDomainEventPublish(JoinPoint joinPoint, Object event) {
        if (event != null) {
            String eventName = event.getClass().getSimpleName();
            log.info("[Domain Event] {} 발행" , eventName);
            log.debug(" Event Data: {}" , event);
        }
    }

    /**
     * Event Handler 실행 로깅
     */
    @AfterReturning("execution(* board.backend..Application.EventHandler..*Handler.handle(..))")
    public void logEventHandler(JoinPoint joinPoint) {
        Object[] args = joinPoint.getArgs();
        String eventName = args.length > 0 ? args[0].getClass().getSimpleName() : "Unknown";
        String handlerName = joinPoint.getSignature().getDeclaringType().getSimpleName();

        log.info("[Event Handler] {} 처리 완료 by {}", eventName, handlerName);
    }
}
