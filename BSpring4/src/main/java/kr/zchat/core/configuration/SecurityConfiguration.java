package kr.zchat.core.configuration;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.access.ExceptionTranslationFilter;
import org.springframework.security.web.authentication.RememberMeServices;
import org.springframework.security.web.authentication.rememberme.TokenBasedRememberMeServices;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import kr.zchat.core.authority.AuthService;


@Configuration
@EnableWebSecurity
public class SecurityConfiguration  extends WebSecurityConfigurerAdapter {
	
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(authService()).passwordEncoder( new BCryptPasswordEncoder());
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
		.addFilterAfter(new AjaxSessionTimeoutFilter(), ExceptionTranslationFilter.class)		//Ajax Session time out 체크, redirect 되기전에 상태 코드를 전송하게함
		.csrf().disable()
		.authorizeRequests()
		 	.antMatchers( "/login**" ).permitAll()
//			.antMatchers("/adm").access("hasAnyRole('ROLE_ADM')")
//			.antMatchers("/**").access("hasAnyRole('ROLE_ADM','ROLE_USR')")
			.antMatchers("/adm").hasAnyRole("ADM")
			.antMatchers("/**").hasAnyRole("ADM","USR")
			.and()
		.formLogin()
			.loginPage( "/login" )
	        .loginProcessingUrl( "/loginProc" )
	        .defaultSuccessUrl( "/" )
	        .usernameParameter( "userNm" )
	        .passwordParameter( "password" )
	        //TODO
	        .successHandler(new LoginSuccessHandler(authService()))
	        .failureHandler(new LoginFailureHandler("/login?err=1", authService()))
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
		;

	}
	
	@Bean
    public UserDetailsService authService(){
        return new AuthService();
    }
	
	/**
	 *  REMEMBER_ME
	 * @return
	 */
	@Bean
	public RememberMeServices tokenBasedRememberMeServices() {
		TokenBasedRememberMeServices tokenBasedRememberMeServices = new TokenBasedRememberMeServices("REMEBMER_ME_KEY", userDetailsService());
		tokenBasedRememberMeServices.setAlwaysRemember(true);
		tokenBasedRememberMeServices.setTokenValiditySeconds(60 * 60 * 24 * 31);		//31일
		tokenBasedRememberMeServices.setCookieName("REMEMBER_ME_COOKE");
		return tokenBasedRememberMeServices;
	}
	
  
}