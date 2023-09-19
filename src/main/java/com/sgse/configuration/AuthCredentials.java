package com.sgse.configuration;

import java.io.Serializable;

import jakarta.validation.constraints.NotBlank;

/**
 * @author Juan Carlos Arguello Ortiz
 * @version 1.0
 */
public class AuthCredentials implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@NotBlank(message = "Nombre de usuario es obligatorio")
	private String username;
	
	@NotBlank(message = "Password es obligatorio")
	private String password;
	

	public AuthCredentials() {
		this.username = "";
		this.password = "";
	}
	
	public AuthCredentials(String username, String password) {
		this.username = username;
		this.password = password;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
