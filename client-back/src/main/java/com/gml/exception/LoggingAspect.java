package com.gml.exception;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.hibernate.annotations.Comment;
import org.springframework.stereotype.Component;

@Aspect
@Slf4j
@Component
public class LoggingAspect {

    @Pointcut("execution(* com.gml.controller.*.*(..))"+
              "execution(* com.gml.service.*.*(..))" +
              "execution(* com.gml.repository.*.*(..))") // Pointcut example
    public void allPackageMethods() {}

    @Before("allPackageMethods()")
    public void logBeforeMethod(JoinPoint joinPoint) {
        log.info("Invocando método: {} with params {} " , joinPoint.getSignature(),joinPoint.getArgs());
    }
  /*  @After("allPackageMethods()")
    public void logAfterMethod(JoinPoint joinPoint) {
        log.info("Salien método: {}" , joinPoint.getSignature());
    }
 */
    @AfterReturning(pointcut = "allPackageMethods()", returning = "returnValue")
    public void logAfterReturningMethod(JoinPoint joinPoint, Object returnValue) {
        log.info("Método finalizado con éxito: {} , valor de retorno: {}" , joinPoint.getSignature() , returnValue);
    }

    @AfterThrowing(pointcut = "allPackageMethods()", throwing = "ex")
    public void logAfterThrowingMethod(JoinPoint joinPoint, Exception ex) {
        log.error("Error al invocar método: {} {} ,exception" , joinPoint.getSignature(),  ex.getMessage(),ex);
    }
}