package com.sgse.configuration;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.fasterxml.jackson.databind.ObjectMapper;

public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter{
	
	private static Logger logger = LoggerFactory.getLogger(JwtAuthorizationFilter.class);
	
	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException {
		AuthCredentials authCredentials = new AuthCredentials();
		try {
			authCredentials = new ObjectMapper().readValue(request.getReader(), AuthCredentials.class);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
				authCredentials.getUsername(), authCredentials.getPassword());
		return getAuthenticationManager().authenticate(authenticationToken);
	} 
	
	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			Authentication authResult) throws IOException, ServletException {
		UserDetailsImpl userDetails = (UserDetailsImpl) authResult.getPrincipal();
		
		logger.info("Nombre usuario: "+userDetails.getUsername()+" Rol: "+userDetails.getRol());
		logger.info("Permisos: ".concat(userDetails.getAuthorities().toString()));
		
		String token = JwtUtils.generateToken(userDetails);
		response.addHeader("Authorization", "Bearer " + token);
		response.getWriter().flush();
		super.successfulAuthentication(request, response, chain, authResult);
	}
}
