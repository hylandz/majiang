package com.xlx.majiang.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Arrays;

/**
 * aop日志切面
 *
 * @author xielx on 2019/8/20
 */
@Aspect
@Component
public class LogAspect {
    
    
    private Logger logger = LoggerFactory.getLogger(getClass());
    
    //execution(方法修饰符(可选) 返回类型 方法名 参数 异常模式(可选))
    @Pointcut("execution(public * com.xlx.majiang.controller.*.*(..))")
    public void LogAspect() {
        //切入点
    }
    
    
    @Before("LogAspect()")
    public void doBefore(JoinPoint joinPoint) {
    	logger.info("<-------------------------->");
        //前置通知,方法前执行
        final Object[] args = joinPoint.getArgs();
	    Arrays.stream(args).forEach(System.out::print);
	    
    }
    
    @After("LogAspect()")
    public void doAfter(JoinPoint joinPoint) {
        //后置通知,方法后执行
    }
    
    @AfterReturning("LogAspect()")
    public void doAfterReturning(JoinPoint joinPoint) {
        //方法成功返回后通知,方法成功返回后执行
    }
    
    @AfterThrowing("LogAspect()")
    public void doAfterThrowing(JoinPoint joinPoint) {
        //方法抛出异常通知,方法抛出异常后执行
    }
    
    @Around("LogAspect()")
    public Object doAround(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        //环绕通知,方法前执行和方法后执行
        return proceedingJoinPoint.proceed();
    }
}
