package com.xlx.majiang.aop;

import org.aspectj.lang.JoinPoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;

/**
 * aop日志切面(切面=切入点+通知)
 *
 * @author xielx on 2019/8/20
 */
//@Component
//@Aspect
public class LogAspect {
    
    
    private final Logger logger = LoggerFactory.getLogger(getClass());
    
    
    /**
     * 切入点
     * execution(方法修饰符(可选) 返回类型 方法名 参数 异常模式(可选))
     */
    //@Pointcut("execution(public * com.xlx.majiang.system.controller.*.*(..))")
    public void LogAspect() {}
    
    /**
     * ************************
     * *      各种通知        *
     * ************************
     */
    
    //@Before("LogAspect()")
    public void doBefore(JoinPoint joinPoint) {
    	logger.info("<-----------前置通知,方法执行前执行--------------->");
        //前置通知,方法前执行
        final Object[] args = joinPoint.getArgs();
	    Arrays.stream(args).forEach(System.out::print);
	    
    }
    
}
