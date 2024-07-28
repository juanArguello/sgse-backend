package com.sgse.configuration;

import java.io.IOException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * @author Juan Carlos Arguello Ortiz
 * @version 1.0
 */
@Component
public class AuthenticationEntryPointImpl implements AuthenticationEntryPoint {

	private static final Log logger = LogFactory.getLog(AuthenticationEntryPointImpl.class);

	// Permite enviar el codigo de respuesta del Http
	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException authException) throws IOException, ServletException {
		// response.sendError(HttpServletResponse.SC_UNAUTHORIZED,
		// authException.getMessage());
		logger.info("Respondiendo con codigo de estado 401");
		response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
		response.getWriter().flush();
	}
}
