package org.ulearn.hsncodeservice.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.ulearn.hsncodeservice.services.SuperAdminDetailsService;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private SuperAdminDetailsService superAdminDetailsService;

	@Autowired
	private JwtAuthenticationFilter jwtAuthenticationFilter;

	@Autowired
	private YMLConfig myConfig;

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(superAdminDetailsService);
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.
			csrf()
			.disable()
			.cors().and()
			.authorizeRequests()
			.antMatchers(HttpMethod.POST,"/login/login").permitAll()
			.antMatchers(HttpMethod.GET,"/swagger-ui.html").permitAll()
			.antMatchers(HttpMethod.GET,"/webjars/**").permitAll()
			.antMatchers(HttpMethod.GET,"/swagger-resources/**").permitAll()
			.antMatchers(HttpMethod.GET,"/v2/api-docs").permitAll()
			.anyRequest().authenticated()
			.and()
			.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

		http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
	}
}
