package com.Ilibrary.BooksService.Security.Jwt;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.Ilibrary.BooksService.Controller.Jwt.JwtAuthenticationRestController;

import ch.qos.logback.classic.Logger;
 

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class JWTWebSecurityConfig extends WebSecurityConfigurerAdapter 
{

	//Lista END POINT accessibili da USER
	private static final String[] PUBLIC_END_POINT = { "/api/books/get/**", "/api/users/post", "/auth", "/refresh", "/api/images/get/**" };
			
	//Lista END POINT accessibili da USER
	private static final String[] USER_END_POINT = { "/api/books/test", "/users/get/**", "/carts/get/**", "/ordini/get/**", "/api/ordini/post/**"};
		
	//Lista END POINT accessibili da ADMIN
	private static final String[] ADMIN_END_POINT = { "/api/books/post", "/api/books/put/**", "/api/books/delete/**"};
		
	private static final Logger log = (Logger) LoggerFactory.getLogger(JwtAuthenticationRestController.class);

	
	@Autowired
	private JwtUnAuthorizedResponseAuthenticationEntryPoint jwtUnAuthorizedResponseAuthenticationEntryPoint;
	
	@Autowired
	private JwtTokenAuthorizationOncePerRequestFilter jwtAuthenticationTokenFilter;
	
	@Autowired
	@Qualifier("customUserDetailsService")
	private UserDetailsService userDetailsService;
	
	@Value("${security.uri}")
	private String authenticationPath;

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception 
	{
		auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoderBean());
	}

	@Bean
	public static PasswordEncoder passwordEncoderBean() 
	{
		return new BCryptPasswordEncoder();
	}
		
	@SuppressWarnings("deprecation")
	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception 
	{
		return super.authenticationManagerBean();
	}

	@Override
	protected void configure(HttpSecurity httpSecurity) throws Exception {
		
		httpSecurity.csrf().disable()
		.authorizeRequests()
        .antMatchers(USER_END_POINT).hasAnyRole("USER", "ADMIN")
		.antMatchers(ADMIN_END_POINT).hasAnyRole("ADMIN")
		.anyRequest().authenticated()
		.and()
		.exceptionHandling().authenticationEntryPoint(jwtUnAuthorizedResponseAuthenticationEntryPoint)
		.and()
		.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
	
	

		httpSecurity.addFilterBefore(jwtAuthenticationTokenFilter, UsernamePasswordAuthenticationFilter.class);
		httpSecurity.headers().frameOptions()
		.sameOrigin().cacheControl();  
	}

	//Ignora dalla sicurezza gli END-POINT PUBLIC
	@Override
	public void configure(WebSecurity webSecurity) throws Exception {
		webSecurity.ignoring()
				.antMatchers(HttpMethod.OPTIONS, "/**")
				.and().ignoring()
				.antMatchers(HttpMethod.GET, PUBLIC_END_POINT)
				.and().ignoring()
				.antMatchers(HttpMethod.POST, PUBLIC_END_POINT);
	}
}
