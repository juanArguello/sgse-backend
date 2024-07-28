package com.sgse.configuration;

import java.io.IOException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandlerImpl;
import org.springframework.stereotype.Component;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class AccessDeniedHandlerCustomized extends AccessDeniedHandlerImpl {

	private static final Log logger = LogFactory.getLog(AccessDeniedHandlerCustomized.class);

	@Override
	public void handle(HttpServletRequest request, HttpServletResponse response,
			AccessDeniedException accessDeniedException) throws IOException, ServletException {
		logger.info("Respondiendo con codigo de estado 403");
		response.setStatus(HttpServletResponse.SC_FORBIDDEN);
		response.getWriter().flush();
	}

}
