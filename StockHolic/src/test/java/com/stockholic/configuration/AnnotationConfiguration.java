package com.stockholic.configuration;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;


@Configuration
@ComponentScan(basePackages = {"com.stockholic.configuration","com.stockholic.webapp"}, excludeFilters = {
		@ComponentScan.Filter(Controller.class), @ComponentScan.Filter(ControllerAdvice.class)})
public class AnnotationConfiguration {
}