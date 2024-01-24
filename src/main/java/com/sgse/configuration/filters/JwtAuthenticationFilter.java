package com.sgse.configuration.filters;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sgse.configuration.AuthCredentials;
import com.sgse.configuration.JwtUtils;
import com.sgse.configuration.UserDetailsImpl;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * @author Juan Carlos Argüello Ortiz
 * @version 1.0
 */
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter{
	
	private static Logger logger = LoggerFactory.getLogger(JwtAuthenticationFilter.class);
	
	
	// metodo attemptAuthentication obtiene el username y el password, e intenta autenticar 
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
	
	// Si la autenticacion fue exitosa, se obtiene el token en la cabecera de HttpResponse
	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			Authentication authResult) throws IOException, ServletException {
		UserDetailsImpl userDetails = (UserDetailsImpl) authResult.getPrincipal();
		
		logger.info("Usuario: "+userDetails.getUsername()+" autenticado exitosamente");
		String token = JwtUtils.generateToken(userDetails);
		response.addHeader("Authorization", "Bearer " + token);
		response.getWriter().flush();
		super.successfulAuthentication(request, response, chain, authResult);	
	}
}
