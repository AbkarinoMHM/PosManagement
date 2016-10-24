package com.ebe.Aspects;

import com.ebe.common.GeneralLogger;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;
import org.aspectj.lang.JoinPoint;


/**
 * Created by saado on 10/24/2016.
 * Log start and end of each method call for all packages start with com.ebe
 */
@Aspect
@Component
public class ServiceLogger {

    @Before("execution(* com.ebe.*.*.*(..))")
    public void logServiceStart(JoinPoint joinpoint){
        GeneralLogger.info(this.getClass(), "Method started: name-- " + joinpoint.getSignature().getName() + ", Params-- " + joinpoint.getArgs());
    }

    @After("execution(* com.ebe.*.*.*(..))")
    public void logServiceEnd(JoinPoint joinpoint){
        GeneralLogger.info(this.getClass(), "Method Completed: name-- " + joinpoint.getSignature().getName() + ", Params-- " + joinpoint.getArgs());
    }
}
