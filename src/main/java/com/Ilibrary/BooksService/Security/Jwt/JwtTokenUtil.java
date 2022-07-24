package com.Ilibrary.BooksService.Security.Jwt;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.Ilibrary.BooksService.Controller.Jwt.JwtAuthenticationRestController;

import ch.qos.logback.classic.Logger;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Clock;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.DefaultClock;
import lombok.extern.java.Log;

//Configurazioni JWT AUTHORIZATION
@Component
@Log
public class JwtTokenUtil implements Serializable {

	static final String CLAIM_KEY_USERNAME = "sub";
	static final String CLAIM_KEY_CREATED = "iat";
	private static final long serialVersionUID = -3301605591108950415L;
	private Clock clock = DefaultClock.INSTANCE;

	@Autowired
	private JwtConfig jwtConfig;

	private static final Logger log = (Logger) LoggerFactory.getLogger(JwtAuthenticationRestController.class);

	//Get USERNAME from token
	public String getUsernameFromToken(String token) 
	{
		return getClaimFromToken(token, Claims::getSubject);
	}

	//Get data creazione
	public Date getIssuedAtDateFromToken(String token) 
	{
		return getClaimFromToken(token, Claims::getIssuedAt);
	}

	//Get data scadenza
	public Date getExpirationDateFromToken(String token) 
	{
		return getClaimFromToken(token, Claims::getExpiration);
	}

	public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) 
	{
		final Claims claims = getAllClaimsFromToken(token);
		
		if (claims != null)
		{
		
			log.info(String.format("Emissione Token:  %s", claims.getIssuedAt().toString()));
			log.info(String.format("Scadenza Token:  %s", claims.getExpiration().toString()));
			
			return claimsResolver.apply(claims);
		}
		else 
			return null;
	}

	private Claims getAllClaimsFromToken(String token) 
	{
		Claims retVal = null;
		
		try
		{
			retVal = Jwts.parser()
					.setSigningKey(jwtConfig.getSecret().getBytes())
					.parseClaimsJws(token)
					.getBody();
		}
		catch (Exception ex)
		{
			log.warn(ex.getMessage());
		}
		
		return retVal;
	}
	
	//Verifica se il token è SCADUTO
	private Boolean isTokenExpired(String token) 
	{
	
		final Date expiration = getExpirationDateFromToken(token);
		boolean retVal = (expiration != null) ? true : false;
		return retVal;
	}

	//GENERAZIONE TOKEN
	public String generateToken(UserDetails userDetails) 
	{
		Map<String, Object> claims = new HashMap<>();
		return doGenerateToken(claims, userDetails);
	}

	private String doGenerateToken(Map<String, Object> claims, UserDetails userDetails) 
	{
		final Date createdDate = clock.now();
		final Date expirationDate = calculateExpirationDate(createdDate);
		
		final String secret = jwtConfig.getSecret();

		return Jwts.builder()
				.setClaims(claims)
				.setSubject(userDetails.getUsername())
				.claim("authorities", userDetails.getAuthorities()
						.stream()
							.map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
				.setIssuedAt(createdDate)
				.setExpiration(expirationDate)
				.signWith(SignatureAlgorithm.HS512, secret.getBytes())
				.compact();
	}

	//Verifica se il token può essere AGGIORNATO
	public Boolean canTokenBeRefreshed(String token) 
	{
		return (isTokenExpired(token));
	}

	//Refresh token
	public String refreshToken(String token) 
	{
		final Date createdDate = clock.now();
		final Date expirationDate = calculateExpirationDate(createdDate);
		
		final String secret = jwtConfig.getSecret();

		final Claims claims = getAllClaimsFromToken(token);
		claims.setIssuedAt(createdDate);
		claims.setExpiration(expirationDate);

		return Jwts.builder()
				.setClaims(claims)
				.signWith(SignatureAlgorithm.HS512, secret.getBytes())
				.compact();
	}

	//Validazione token
	public Boolean validateToken(String token, UserDetails userDetails) 
	{
		final String usernameToken = getUsernameFromToken(token);
		final String usernameUserDetails = userDetails.getUsername();
		
		//Controllo se il nome dell token corrisponde ad un nome presente nel database
		if(usernameToken.equals(usernameUserDetails)) {
			
			//Controllo se il token è SCADUTO
			//Se non e scaduto autentica
			if(isTokenExpired(token)) {
				return true;
			}
			else {
				return false;
			}
		}
		else {
			return false;
		}
		
		
		
	}

	//Calcola il tempo restante alla scadenza del token
	private Date calculateExpirationDate(Date createdDate) 
	{
		return new Date(createdDate.getTime() + jwtConfig.getExpiration() * 1000);
	}
}
