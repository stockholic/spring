package com.stockholic.core.configuration.beans;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.access.ExceptionTranslationFilter;
import org.springframework.security.web.authentication.RememberMeServices;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.rememberme.TokenBasedRememberMeServices;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.stockholic.core.authority.AuthService;
import com.stockholic.core.configuration.filter.AjaxSessionTimeoutFilter;
import com.stockholic.core.configuration.filter.SSOAuthenticationFilter;
import com.stockholic.core.configuration.handler.LoginFailureHandler;
import com.stockholic.core.configuration.handler.LoginSuccessHandler;
import com.stockholic.core.configuration.handler.SSOLoginHandler;


@Configuration
@EnableWebSecurity
public class SecurityConfiguration  extends WebSecurityConfigurerAdapter {
	
	@Autowired
	@Qualifier("AuthService")
	private AuthService authService;
	
	@Autowired
    private SessionRegistryImpl sessionRegistry;
	
	@Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(authService).passwordEncoder(new BCryptPasswordEncoder());
    }

	@Override
	public void configure( WebSecurity web ) throws Exception {
		web
		    .ignoring()
		        .antMatchers( "/static/**" )
		        ;
	}
 
	@Override
	protected void configure(HttpSecurity http) throws Exception {
 
		http
		.addFilterBefore(ssoAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)		//SSO 인증
		.addFilterAfter(new AjaxSessionTimeoutFilter(), ExceptionTranslationFilter.class)		//Ajax Session time out 체크, redirect 되기전에 상태 코드를 전송하게함
		.csrf().disable()
		.authorizeRequests()
		 	.antMatchers( "/login**" ).permitAll()
		 	.antMatchers("/**").hasAnyRole("ADM","USR")
			.antMatchers("/adm/**").hasAnyRole("ADM")
			.and()
		.formLogin()
			.loginPage( "/login" )
	        .loginProcessingUrl( "/loginProc" )
	        .defaultSuccessUrl( "/" )
	        .usernameParameter( "userNm" )
	        .passwordParameter( "password" )
	        //TODO
	        .successHandler(new LoginSuccessHandler(authService))
	        .failureHandler(new LoginFailureHandler("/login?err=1", authService))
	        .and()
		.logout()
			.logoutRequestMatcher( new AntPathRequestMatcher( "/logout" ) )
			.logoutSuccessUrl( "/login" )
			.deleteCookies( "JSESSIONID" )
			.deleteCookies( "REMEMBER_ME_COOKE" )
			.invalidateHttpSession( true )
			.and()
		.rememberMe()
			.key("REMEBMER_ME_KEY")
	        .rememberMeServices(tokenBasedRememberMeServices())
			.and()
		.sessionManagement()			//첫번째 로그인 사용자는 로그아웃, 두번째 사용자 로그인 session-registry-alias : 접속자 정보보기
			.maximumSessions(1)
			.expiredUrl("/expireSession")
			.sessionRegistry(sessionRegistry())
		;

	}
	
	/**
	 *  REMEMBER_ME
	 * @return
	 */
	@Bean
	public RememberMeServices tokenBasedRememberMeServices() {
		TokenBasedRememberMeServices tokenBasedRememberMeServices = new TokenBasedRememberMeServices("REMEBMER_ME_KEY", userDetailsService());
//		tokenBasedRememberMeServices.setAlwaysRemember(true);								// 체크박스 클릭안해도 무조건 유지
		tokenBasedRememberMeServices.setTokenValiditySeconds(60 * 60 * 24 * 31);		//31일
		tokenBasedRememberMeServices.setCookieName("REMEMBER_ME_COOKE");
		return tokenBasedRememberMeServices;
	}
	
	private SSOAuthenticationFilter ssoAuthenticationFilter() throws Exception  {
		SSOAuthenticationFilter filter = new SSOAuthenticationFilter(new AntPathRequestMatcher( "/loginSSO" ), authService);
		filter.setAuthenticationSuccessHandler(new SSOLoginHandler());
		return filter;
	}
	
	@Bean
	public SessionRegistryImpl sessionRegistry() {
		return new SessionRegistryImpl();
	}
	
  
}