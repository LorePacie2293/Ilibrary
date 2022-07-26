package com.Ilibrary.BooksService.Controller.Jwt;

import java.util.Objects;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.Ilibrary.BooksService.Security.Jwt.JwtTokenUtil;

import ch.qos.logback.classic.Logger;
import lombok.SneakyThrows;
import lombok.extern.java.Log;

//Gestisce l'autenticazione JWT
@RestController
@Log
public class JwtAuthenticationRestController 
{

	@Value("${security.header}")
	private String tokenHeader;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@Autowired
	@Qualifier("customUserDetailsService")
	private UserDetailsService userDetailsService;
	
	private static final Logger log = (Logger) LoggerFactory.getLogger(JwtAuthenticationRestController.class);
	
	//Generazione token, se l'utente è PRESENTE NEL DB
	@PostMapping(value = "${security.uri}")
	@SneakyThrows
	public ResponseEntity<JwtTokenResponse> createAuthenticationToken(@RequestBody JwtTokenRequest authenticationRequest) 
	{
		log.info("Autenticazione e Generazione Token");

		authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());

		final UserDetails userDetails = userDetailsService
				.loadUserByUsername(authenticationRequest.getUsername());

		final String token = jwtTokenUtil.generateToken(userDetails);
		
		log.warn(String.format("Token %s", token));

		return ResponseEntity.ok(new JwtTokenResponse(token));
	}
	
	//Refresh token
	@GetMapping(value = "${security.refresh}")
	@SneakyThrows
	public ResponseEntity<JwtTokenResponse> refreshAndGetAuthenticationToken(HttpServletRequest request) throws Exception 
	{
		log.info("Tentativo Refresh Token");
		String authToken = request.getHeader(tokenHeader);
		
		if (authToken == null)
		{
			throw new Exception("Token assente o non valido!");
		}
		
		final String token = authToken.substring(7); 
		
		//Verifica che il token possa essere AGGIORNATO
		if (jwtTokenUtil.canTokenBeRefreshed(token)) 
		{
			String refreshedToken = jwtTokenUtil.refreshToken(token);
			
			log.warn(String.format("Refreshed Token %s", refreshedToken));
			
			return ResponseEntity.ok(new JwtTokenResponse(refreshedToken));
		} 
		else 
		{
			return ResponseEntity.badRequest().body(null);
		}
	}

	//Gestore AUTHENTICATION EXCEPTION
	@ExceptionHandler({ AuthenticationException.class })
	public ResponseEntity<String> handleAuthenticationException(AuthenticationException e) 
	{
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
	}

	private void authenticate(String username, String password) 
	{
		Objects.requireNonNull(username);
		Objects.requireNonNull(password);

		try 
		{
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
		} 
		catch (DisabledException e) 
		{
			log.warn("UTENTE DISABILITATO");
			throw new AuthenticationException("UTENTE DISABILITATO", e);
		} 
		catch (BadCredentialsException e) 
		{
			log.warn("CREDENZIALI NON VALIDE");
			throw new AuthenticationException("CREDENZIALI NON VALIDE", e);
		}
	}
}
