package com.aehtiopicus.utils.aop;

import org.aspectj.lang.annotation.Aspect;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Aspect
@Order(900)
public class HealthCheckAOPService {

}
