package com.stockholic.core.configuration.beans;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;

@Configuration
@ComponentScan(basePackages = {"com.stockholic.core.configuration.beans","com.stockholic.core.authority","com.stockholic.core.module.service"}, excludeFilters = {
		@ComponentScan.Filter(Controller.class), @ComponentScan.Filter(ControllerAdvice.class)})
public class AnnotationConfiguration {
}