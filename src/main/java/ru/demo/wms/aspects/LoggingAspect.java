package ru.demo.wms.aspects;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.annotation.AfterThrowing;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {

    private static final Logger logger = LoggerFactory.getLogger(LoggingAspect.class);

    @Pointcut("within(ru.demo.wms..*)")
    public void applicationPackagePointcut() {
        // Метод-заглушка, содержащий определение точки среза (Pointcut)
    }

    @Before("applicationPackagePointcut()")
    public void logBefore(JoinPoint joinPoint) {
        logger.info("Вход в метод: " + joinPoint.getSignature().getName());
        logger.info("Имя класса: " + joinPoint.getSignature().getDeclaringTypeName());
        logger.info("Аргументы: " + joinPoint.getArgs());
        logger.info("Целевой класс: " + joinPoint.getTarget().getClass().getName());
    }

    @AfterReturning(pointcut = "applicationPackagePointcut()", returning = "result")
    public void logAfterReturning(JoinPoint joinPoint, Object result) {
        logger.info("Метод вернул значение: " + result);
    }

    @AfterThrowing(pointcut = "applicationPackagePointcut()", throwing = "exception")
    public void logAfterThrowing(JoinPoint joinPoint, Throwable exception) {
        logger.error("Было выброшено исключение в методе: " + joinPoint.getSignature().getName());
        logger.error("Причина: " + exception.getCause());
    }
}
