package com.Ilibrary.BooksService.Security.Jwt;


import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.Ilibrary.BooksService.Controller.Jwt.JwtAuthenticationRestController;

import ch.qos.logback.classic.Logger;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.extern.java.Log;

//Filtro JWT
@Component
@Log
public class JwtTokenAuthorizationOncePerRequestFilter extends OncePerRequestFilter {

	@Autowired
	@Qualifier("customUserDetailsService")
	private UserDetailsService userDetailsService;

	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@Value("${security.header}")
	private String tokenHeader;

	private static final Logger log = (Logger) LoggerFactory.getLogger(JwtAuthenticationRestController.class);

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws ServletException, IOException 
	{
		log.info(String.format("Authentication Request For '{%s}'", request.getRequestURL()));

		final String requestTokenHeader = request.getHeader(this.tokenHeader);
		
		log.warn("Token: " + requestTokenHeader);

		String username = null;
		String jwtToken = null;
		
		//Verifica che sia presente il TOKEN HEADER e
		//che inizi con BEARER 
		if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ")) 
		{
			jwtToken = requestTokenHeader.substring(7);
			
			try 
			{
				username = jwtTokenUtil.getUsernameFromToken(jwtToken);
			} 
			catch (IllegalArgumentException e) 
			{
				logger.error("IMPOSSIBILE OTTENERE LA USERID", e);
			} 
			catch (ExpiredJwtException e) 
			{
				logger.warn("TOKEN SCADUTO", e);
			}
		} 
		else 
		{
			logger.warn("TOKEN NON VALIDO");
		}

		log.warn(String.format("JWT_TOKEN_USERNAME_VALUE '{%s}'", username));
		
		//AUTORIZZA
		if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) 
		{

			UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);

			if (jwtTokenUtil.validateToken(jwtToken, userDetails)) 
			{
				UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = 
						new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
				
				usernamePasswordAuthenticationToken
						.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				
				SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
			}
		}

		chain.doFilter(request, response);
	}
}