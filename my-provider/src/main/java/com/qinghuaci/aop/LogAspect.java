package com.qinghuaci.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;

import java.util.Arrays;

/**
 * Author: Ansel Qiao Create Time: 15/7/26
 */
@Slf4j
public class LogAspect {

    public void init() {
    }

    public Object around(ProceedingJoinPoint point) throws Throwable {
        String className = point.getTarget().getClass().getSimpleName();
        String methodName = point.getSignature().getName();
        log.info("==== Received start [methodName={}]. params={}.", className + "." + methodName,
                Arrays.toString(point.getArgs()));

        Object result = point.proceed();

        log.info("==== Received end [methodName={}]. result:={}. ", className + "." + methodName,
                result);
        return result;
    }

}
