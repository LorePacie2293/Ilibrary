package com.Ilibrary.BooksService.Security.Jwt;

import java.net.URI;
import java.net.URISyntaxException;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.client.support.BasicAuthenticationInterceptor;
import org.springframework.security.core.userdetails.User.UserBuilder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.Ilibrary.BooksService.Entity.Users;
import com.Ilibrary.BooksService.Repository.UsersRepository;

import ch.qos.logback.classic.Logger;
import lombok.SneakyThrows;
import lombok.extern.java.Log;

//Authorization UserDetailsService
@Service("customUserDetailsService")
@Log
public class CustomUserDetailsService implements UserDetailsService
{
	@Autowired
	private UsersRepository userRepository;
	
	private static final Logger log = (Logger) LoggerFactory.getLogger(CustomUserDetailsService.class);

	@Override
	@SneakyThrows
	public UserDetails loadUserByUsername(String username) 
	{
		String ErrMsg = "";
		
		if (username == null || username.length() < 2) 
		{
			ErrMsg = "Nome utente assente o non valido";
			
			log.warn(ErrMsg);
			
	    	throw new UsernameNotFoundException(ErrMsg); 
		} 
		
		Users user = userRepository.findByusername(username);
		
		if (user == null)
		{
			ErrMsg = String.format("Utente %s non Trovato!!", username);
			
			log.warn(ErrMsg);
			
			throw new UsernameNotFoundException(ErrMsg);
		}
		
		UserBuilder builder = null;
		builder = org.springframework.security.core.userdetails.User.withUsername(user.getUsername());
		builder.password(user.getUserPassword());
		builder.authorities("ROLE_" + user.getUserRole());
		return builder.build();
	}
}
	