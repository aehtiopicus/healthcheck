package com.aehtiopicus.utils.configuration;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@Configuration
@ComponentScan("com.aehtiopicus.utils")
@EnableAspectJAutoProxy(proxyTargetClass=true)	
public class HealthCheckConfig {

	
}
