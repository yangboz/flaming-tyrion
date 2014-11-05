package hello;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configurers.GlobalAuthenticationConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.servlet.configuration.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Configuration
@EnableWebMvcSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter
{
	final Logger LOG = LoggerFactory.getLogger(WebSecurityConfig.class); 
	
	//@see: http://raymondhlee.wordpress.com/tag/spring-boot/
	@Value("${ldap.domain}")
	private String DOMAIN;
	@Value("${ldap.url}")
	private String URL;
	
	@Override
	protected void configure(HttpSecurity http) throws Exception{
		LOG.info("@Value(URL):{}",URL);
		http.authorizeRequests().antMatchers("/css/**").permitAll().anyRequest().fullyAuthenticated().and().formLogin();
	}
	
	@Configuration
	public static class AuthenticationConfiguration extends GlobalAuthenticationConfigurerAdapter{
		@Override 
		public void init(AuthenticationManagerBuilder auth) throws Exception{
			auth.ldapAuthentication().userDnPatterns("uid={0},ou=people")
			.groupSearchBase("ou=groups").contextSource()
			.ldif("classpath:test-server.ldif");
		}
	}
}