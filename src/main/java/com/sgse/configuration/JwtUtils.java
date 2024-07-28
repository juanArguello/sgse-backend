package com.sgse.configuration;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.crypto.SecretKey;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.core.JsonParser.Feature;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;

/**
 * @author Juan Carlos Arguello Ortiz
 * @version 1.0
 */
public class JwtUtils {

	private static Logger logger = LoggerFactory.getLogger(JwtUtils.class);

	private final static String ACCESS_TOKEN_SECRET = "ghpTXQnTFjFLc7E6PcxnGdWnKr4oMAdQG3SiQP9CxnKypJf4q3w0m";
	private final static Long ACCESS_TOKEN_VALIDITY_SECONDS = 2_592_000L; // 30 dias de duracion del token

	/**
	 * Genera un token de acceso
	 * 
	 * @param userDetails
	 * @return String
	 */
	public static String generateToken(UserDetailsImpl userDetails) {
		long expirationTime = ACCESS_TOKEN_VALIDITY_SECONDS * 1_000; // converitr en milisegundos
		Date expirationDate = new Date(System.currentTimeMillis() + expirationTime);

		Map<String, Object> permisosMap = new HashMap<>();
		permisosMap.put("permisos", userDetails.getAuthorities());
		return Jwts.builder().subject(userDetails.getUsername()).issuedAt(new Date(System.currentTimeMillis()))
				.expiration(expirationDate).claim("rol", userDetails.getRol()).claims(permisosMap).signWith(getKey())
				.compact();

	}

	/**
	 * El metodo getAuthentication recibe una cadena de token, la cual mapea el
	 * cuerpo payload, retornando una instancia
	 * {@link UsernamePasswordAuthenticationToken}
	 * 
	 * @param token
	 * @return {@link UsernamePasswordAuthenticationToken}
	 */
	public static UsernamePasswordAuthenticationToken getAuthentication(String token) {
		ObjectMapper mapper = new ObjectMapper();
		List<SimpleGrantedAuthority> authorities = null;
		String username = "";
		Claims claims = parseClaims(token);

		if (claims != null) {
			username = claims.getSubject();
		} else {
			return null;
		}

		mapper.configure(Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
		mapper.registerModule(new SimpleModule().addDeserializer(SimpleGrantedAuthority.class,
				new SimpleGrantedAuthorityDeserializer()));
		try {
			authorities = mapper.readValue(new ObjectMapper().writeValueAsString(claims.get("permisos", List.class)),
					new TypeReference<List<SimpleGrantedAuthority>>() {
					});
		} catch (JsonMappingException e) {
			logger.error(e.getMessage());
		} catch (JsonProcessingException e) {
			logger.error(e.getMessage());
		}
		return new UsernamePasswordAuthenticationToken(username, null, authorities);
	}

	/**
	 * Obtiene una implementacion de UserDetails
	 * 
	 * @param token
	 * @return {@link UserDetails}
	 */
	public static UserDetails getUserDetailsFromToken(String token) {
		ObjectMapper mapper = new ObjectMapper();
		List<GrantedAuthority> authorities = null;
		String username = "";
		Claims claims = parseClaims(token);

		if (claims != null) {
			username = claims.getSubject();
		} else {
			return null;
		}

		mapper.configure(Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
		mapper.registerModule(new SimpleModule().addDeserializer(SimpleGrantedAuthority.class,
				new SimpleGrantedAuthorityDeserializer()));
		try {
			authorities = mapper.readValue(new ObjectMapper().writeValueAsString(claims.get("permisos", List.class)),
					new TypeReference<List<GrantedAuthority>>() {
					});
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}

		return new UserDetailsImpl(username, null, authorities);
	}

	/**
	 * Obtiene los permisos a traves del token
	 * 
	 * @param token
	 * @return {@link List<SimpleGrantedAuthority>}
	 */
	public static List<SimpleGrantedAuthority> getPermisosFromToken(String token) {
		ObjectMapper mapper = new ObjectMapper();
		List<SimpleGrantedAuthority> authorities = null;
		Claims claims = parseClaims(token);

		if (claims == null) {
			return null;
		}

		mapper.configure(Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
		mapper.registerModule(new SimpleModule().addDeserializer(SimpleGrantedAuthority.class,
				new SimpleGrantedAuthorityDeserializer()));
		try {
			authorities = mapper.readValue(new ObjectMapper().writeValueAsString(claims.get("permisos", List.class)),
					new TypeReference<List<SimpleGrantedAuthority>>() {
					});
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return authorities;
	}

	// Obtiene el username a traves del token
	public static String getUsernameFromToken(String token) {
		Claims claims = parseClaims(token);
		return claims != null ? claims.getSubject() : "";
	}

	// Obtiene el rol a traves del token
	public static String getRolFromToken(String token) {
		Claims claims = parseClaims(token);
		return claims != null ? claims.get("rol", String.class) : "";
	}

	// Parsea el playload del token
	private static Claims parseClaims(String token) {
		try {
			return Jwts.parser().verifyWith(getKey()).build().parseSignedClaims(token).getPayload();
		} catch (SignatureException e) {
			logger.error("Invalid JWT signature ");
		} catch (MalformedJwtException e) {
			logger.error("Invalid JWT token ");
		} catch (ExpiredJwtException e) {
			logger.error("JWT token is expired ");
		} catch (UnsupportedJwtException e) {
			logger.error("JWT token is unsupported ");
		} catch (IllegalArgumentException e) {
			logger.error("JWT claims string is empty ");
		}
		return null;
	}

	private static SecretKey getKey() {
		byte[] keyBytes = Decoders.BASE64.decode(ACCESS_TOKEN_SECRET);
		return Keys.hmacShaKeyFor(keyBytes);
	}

}
