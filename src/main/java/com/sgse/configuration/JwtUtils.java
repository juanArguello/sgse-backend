package com.sgse.configuration;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.Keys;



public class JwtUtils {

	private static Logger logger = LoggerFactory.getLogger(JwtUtils.class);
	private final static String ACCESS_TOKEN_SECRET = "4qhq8LrEBfycaRHxhdb9zURb2rf8e7Ud";
	private final static Long ACCESS_TOKEN_VALIDITY_SECONDS = 2_592_000L; // 30 dias de duracion del token
	

	public static String generateToken(UserDetailsImpl userDetails) {
		long expirationTime = ACCESS_TOKEN_VALIDITY_SECONDS * 1_000; // converitr en milisegundos
		Date expirationDate = new Date(System.currentTimeMillis() + expirationTime);
		
		Map<String, Object> permisosMap = new HashMap<>();
		permisosMap.put("permisos", userDetails.getAuthorities());
		return Jwts.builder()
				.setSubject(userDetails.getUsername())
				.setExpiration(expirationDate)
				.addClaims(permisosMap)
				.claim("rol", userDetails.getRol())
				.signWith(Keys.hmacShaKeyFor(ACCESS_TOKEN_SECRET.getBytes()),SignatureAlgorithm.HS256)
				.compact();
	}
	
	public static UsernamePasswordAuthenticationToken getAuthentication(String token){
		ObjectMapper mapper = new ObjectMapper();
		List<SimpleGrantedAuthority> authorities = null;
		Claims claims = parseClaims(token);
		String username = claims.getSubject();
		
		mapper.configure(Feature.ALLOW_UNQUOTED_FIELD_NAMES,true);
		try { 
			authorities = mapper.readValue(new ObjectMapper().writeValueAsString(claims.get("permisos",List.class)),
					new TypeReference<List<SimpleGrantedAuthority>>(){});
		} catch (JsonMappingException e) { 
			logger.error(e.getMessage());
		} catch (JsonProcessingException e) {
			logger.error(e.getMessage());
		}
		return new UsernamePasswordAuthenticationToken(username, null, authorities);
	}
	
	@SuppressWarnings("unchecked")
	public static UserDetails getUserDetailsFromToke(String token) {
		Claims claims = parseClaims(token);
		String username = claims.getSubject();
		List<GrantedAuthority> authorities = claims.get("permisos", List.class);
		return new UserDetailsImpl(username, null, authorities);
	}
	
	public static String getUsernameFromToken(String token) {
		return parseClaims(token).getSubject();
	}
	
	public static String getRolFromToken(String token) {
		return parseClaims(token).get("rol", String.class);
	}
	
	@SuppressWarnings("unchecked")
	public static List<GrantedAuthority> getPermisosFromToken(String token) {
		return parseClaims(token).get("permisos",List.class);
	}
	
	private static Claims parseClaims(String token) {
		try {
			return Jwts.parserBuilder()
					.setSigningKey(ACCESS_TOKEN_SECRET.getBytes())
					.build()
					.parseClaimsJws(token)
					.getBody();
		} catch (JwtException jwtException) {
			return null;
		}
	}
	
	public static boolean validateJwtToken(String authToken) {
		try {
			Jwts.parserBuilder()
			.setSigningKey(ACCESS_TOKEN_SECRET.getBytes())
			.build()
			.parseClaimsJws(authToken);
			return true;
		} catch (SecurityException e) {
			logger.error("Invalid JWT signature: {}", e.getMessage());
		} catch (MalformedJwtException e) {
			logger.error("Invalid JWT token: {}", e.getMessage());
		} catch (ExpiredJwtException e) {
			logger.error("JWT token is expired: {}", e.getMessage());
		} catch (UnsupportedJwtException e) {
			logger.error("JWT token is unsupported: {}", e.getMessage());
		} catch (IllegalArgumentException e) {
			logger.error("JWT claims string is empty: {}", e.getMessage());
		}
		return false;
	}
	
}
