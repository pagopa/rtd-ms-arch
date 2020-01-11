package eu.sia.meda.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer.AuthorizedUrl;
import org.springframework.security.config.http.SessionCreationPolicy;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	/**
	 * Configure.
	 *
	 * @param http the http
	 * @throws Exception the exception
	 */
	protected void configure(HttpSecurity http) throws Exception {
		((HttpSecurity) ((HttpSecurity) ((HttpSecurity) http.csrf().disable()).cors().and()).sessionManagement()
				.sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()).headers().cacheControl();
		((AuthorizedUrl) http.authorizeRequests().anyRequest()).permitAll();
	}

}
