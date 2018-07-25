package kr.zchat.core.configuration;

import org.springframework.boot.web.servlet.ErrorPage;
import org.springframework.boot.web.servlet.ErrorPageRegistrar;
import org.springframework.boot.web.servlet.ErrorPageRegistry;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;

import kr.zchat.webapp.front.ErrorPageController;

@Configuration
public class ErrorConfiguration  {
	
	@Bean
	public ErrorPageRegistrar errorPageRegistrar(){
	    return new MyErrorPageRegistrar();
	}
	
	private static class MyErrorPageRegistrar implements ErrorPageRegistrar {
		
		@Override
		public void registerErrorPages(ErrorPageRegistry registry) {
			registry.addErrorPages(new ErrorPage(HttpStatus.UNAUTHORIZED, ErrorPageController.ERROR_401));
			registry.addErrorPages(new ErrorPage(HttpStatus.FORBIDDEN, ErrorPageController.ERROR_403));
			registry.addErrorPages(new ErrorPage(HttpStatus.NOT_FOUND, ErrorPageController.ERROR_404));
			registry.addErrorPages(new ErrorPage(HttpStatus.INTERNAL_SERVER_ERROR, ErrorPageController.ERROR_500));
		}
		
	}
}