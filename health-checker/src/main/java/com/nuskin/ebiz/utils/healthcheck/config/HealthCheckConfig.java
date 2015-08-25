package com.nuskin.ebiz.utils.healthcheck.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@Configuration
@ComponentScan("com.nuskin.ebiz.utils")
@EnableAspectJAutoProxy(proxyTargetClass=true)	
public class HealthCheckConfig {

	
}
