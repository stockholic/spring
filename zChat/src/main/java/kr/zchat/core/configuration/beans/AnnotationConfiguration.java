package kr.zchat.core.configuration.beans;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;

@Configuration
@ComponentScan(basePackages = {"kr.zchat.core.configuration.beans"}, excludeFilters = {
		@ComponentScan.Filter(Controller.class), @ComponentScan.Filter(ControllerAdvice.class)})
public class AnnotationConfiguration {
}
