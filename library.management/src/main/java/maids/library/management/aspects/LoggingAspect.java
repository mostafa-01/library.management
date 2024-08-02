package maids.library.management.aspects;


import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class LoggingAspect {

    @Pointcut("@annotation(logExecution)")
    public void logExecutionPointcut(LogExecutionA logExecutionA) {}

    @Before("logExecutionPointcut(logExecution)")
    public void logBefore(JoinPoint joinPoint, LogExecutionA logExecutionA) {
        log.info("Entering method: {} with arguments: {}", joinPoint.getSignature(), joinPoint.getArgs());
    }

    @After("logExecutionPointcut(logExecution)")
    public void logAfter(JoinPoint joinPoint, LogExecutionA logExecutionA) {
        log.info("Exiting method: {}", joinPoint.getSignature());
    }
}
