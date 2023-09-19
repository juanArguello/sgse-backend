package com.sgse.configuration;

//@RestControllerAdvice
public class CustomExceptionHandler {
	
	/*
	 * @ExceptionHandler(Exception.class) public ProblemDetail
	 * handleSecurityException(Exception exception) { ProblemDetail problemaDetalle
	 * = null; if(exception instanceof BadCredentialsException) { problemaDetalle =
	 * ProblemDetail .forStatusAndDetail(HttpStatusCode.valueOf(401),
	 * exception.getMessage()); problemaDetalle.setProperty("access_denied_reason",
	 * "Fallo de autentificacion"); }else if(exception instanceof
	 * AccessDeniedException) { problemaDetalle = ProblemDetail
	 * .forStatusAndDetail(HttpStatusCode.valueOf(403), exception.getMessage());
	 * problemaDetalle.setProperty("access_denied_reason", "No autorizado"); }else
	 * if(exception instanceof SignatureException) { problemaDetalle = ProblemDetail
	 * .forStatusAndDetail(HttpStatusCode.valueOf(403), exception.getMessage());
	 * problemaDetalle.setProperty("access_denied_reason", "JWT Firma no válido");
	 * }else if(exception instanceof ExpiredJwtException) { problemaDetalle =
	 * ProblemDetail .forStatusAndDetail(HttpStatusCode.valueOf(403),
	 * exception.getMessage()); problemaDetalle.setProperty("access_denied_reason",
	 * "JWT token ya expiro"); }
	 * 
	 * return problemaDetalle; }
	 */
}