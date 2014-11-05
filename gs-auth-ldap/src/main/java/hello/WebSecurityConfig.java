package hello;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configurers.GlobalAuthenticationConfigurerAdapter;
import org.springframework.security.config.annotation.authentication.configurers.ldap.LdapAuthenticationProviderConfigurer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.servlet.configuration.*;
import org.springframework.security.ldap.DefaultSpringSecurityContextSource;
import org.springframework.security.ldap.userdetails.DefaultLdapAuthoritiesPopulator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Configuration
@EnableWebMvcSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter
{
	static final Logger LOG = LoggerFactory.getLogger(WebSecurityConfig.class); 
	
	@Override
	protected void configure(HttpSecurity http) throws Exception{
		http.authorizeRequests().antMatchers("/css/**").permitAll().anyRequest().fullyAuthenticated().and().formLogin();
	}
	
	@Configuration
	public static class AuthenticationConfiguration extends GlobalAuthenticationConfigurerAdapter{
		
		//@see: http://raymondhlee.wordpress.com/tag/spring-boot/
		@Value("${ldap.domain}")
		private String DOMAIN;
		@Value("${ldap.url}")
		private String URL;
		
		@Override 
		public void init(AuthenticationManagerBuilder auth) throws Exception{
			LOG.info("@Value(URL):{}",URL);
//			auth.ldapAuthentication().userDnPatterns("uid={0},ou=people")
//			.groupSearchBase("ou=groups").contextSource()
//			.ldif("classpath:test-server.ldif");
			auth.ldapAuthentication()
			.userSearchFilter("(uid={0})")
			.userSearchBase("ou=users,ou=system")
			.groupRoleAttribute("cn")
			.groupSearchFilter("(member={0})")
//			.userDnPatterns("uid={0},ou=people")
//			.groupSearchBase("ou=groups")
			.contextSource()
			.url("ldap://localhost:10389/dc=example,dc=com")
			.managerDn("uid=admin,ou=groups")
			.managerPassword("dirtysecret");
			
//			DefaultSpringSecurityContextSource contextSource = new DefaultSpringSecurityContextSource();
//		    contextSource.setUserDn("uid=admin,ou=system");
//		    contextSource.setPassword("dirtysecret");
//		    contextSource.afterPropertiesSet();
//
//		    DefaultLdapAuthoritiesPopulator defaultLdapAuthoritiesPopulator = new DefaultLdapAuthoritiesPopulator(contextSource, "ou=groups");
//		    defaultLdapAuthoritiesPopulator.setGroupRoleAttribute("ou");
//
//		    LdapAuthenticationProviderConfigurer<AuthenticationManagerBuilder> ldapAuthenticationProviderConfigurer = auth.ldapAuthentication();
//
//		    ldapAuthenticationProviderConfigurer
//		        .userSearchFilter(...)
//		        .groupSearchBase(...)
//		        .contextSource(contextSource)
//		        .ldapAuthoritiesPopulator(defaultLdapAuthoritiesPopulator);
		}
	}
}