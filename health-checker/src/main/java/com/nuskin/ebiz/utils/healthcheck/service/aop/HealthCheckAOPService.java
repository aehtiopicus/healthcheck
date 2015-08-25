package com.nuskin.ebiz.utils.healthcheck.service.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Aspect
@Order(900)
public class HealthCheckAOPService {

	@AfterThrowing(value="publicMethod() && anAnnotatedMethod()",throwing="ex")
	 public void logAfterThrowing(JoinPoint joinPoint, Throwable ex) {
		System.out.println("error");
	}
	
	@Pointcut("within(@com.nuskin.ebiz.utils.healthcheck.annotations.HealthCheck *)")
	public void anAnnotatedMethod() {
		System.out.println("error");
	}

	@Pointcut("execution(public * *(..))")
	public void publicMethod() {
		System.out.println("error");
	}

	@Pointcut("publicMethod() && anAnnotatedMethod()")
	public void publicMethodInsideAClassMarkedWithAtMonitor() {
		
	}
}
