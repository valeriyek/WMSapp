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

/**
 * Аспект логирования для отслеживания выполнения методов в приложении.
 * <p>
 * Логирует вход в методы, возвращаемые значения и выброшенные исключения
 * для всех компонентов внутри пакета {@code ru.demo.wms}.
 */
@Aspect
@Component
public class LoggingAspect {

    private static final Logger logger = LoggerFactory.getLogger(LoggingAspect.class);

    /**
     * Точка среза, охватывающая все компоненты внутри пакета {@code ru.demo.wms}.
     */
    @Pointcut("within(ru.demo.wms..*)")
    public void applicationPackagePointcut() {
        // Метод-заглушка, содержащий определение точки среза
    }

    /**
     * Логирует информацию перед выполнением метода.
     *
     * @param joinPoint содержит информацию о вызываемом методе
     */
    @Before("applicationPackagePointcut()")
    public void logBefore(JoinPoint joinPoint) {
        logger.info("Вход в метод: " + joinPoint.getSignature().getName());
        logger.info("Имя класса: " + joinPoint.getSignature().getDeclaringTypeName());
        logger.info("Аргументы: " + joinPoint.getArgs());
        logger.info("Целевой класс: " + joinPoint.getTarget().getClass().getName());
    }

    /**
     * Логирует возвращаемое значение после успешного выполнения метода.
     *
     * @param joinPoint точка соединения
     * @param result возвращённое значение
     */
    @AfterReturning(pointcut = "applicationPackagePointcut()", returning = "result")
    public void logAfterReturning(JoinPoint joinPoint, Object result) {
        logger.info("Метод вернул значение: " + result);
    }

    /**
     * Логирует исключения, выброшенные в методе.
     *
     * @param joinPoint точка соединения
     * @param exception выброшенное исключение
     */
    @AfterThrowing(pointcut = "applicationPackagePointcut()", throwing = "exception")
    public void logAfterThrowing(JoinPoint joinPoint, Throwable exception) {
        logger.error("Было выброшено исключение в методе: " + joinPoint.getSignature().getName());
        logger.error("Причина: " + exception.getCause());
    }
}
