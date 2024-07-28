package com.sgse.configuration;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.sgse.configuration.filters.JwtAuthenticationFilter;
import com.sgse.configuration.filters.JwtAuthorizationFilter;
import com.sgse.resources.HostPermitido;

/**
 * @author Juan Carlos Arguello Ortiz
 * @version 1.0
 */
@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfiguration {

	private final UserDetailsService userDetailsService;
	private final JwtAuthorizationFilter authorizationFilter;
	private final AccessDeniedHandlerCustomized accessDeniedHandlerCustomized;

	@Autowired
	private AuthenticationEntryPoint authenticationEntryPoint;

	public SecurityConfiguration(UserDetailsService userDetailsService, JwtAuthorizationFilter authorizationFilter,
			AccessDeniedHandlerCustomized accessDeniedHandlerCustomized) {
		this.userDetailsService = userDetailsService;
		this.authorizationFilter = authorizationFilter;
		this.accessDeniedHandlerCustomized = accessDeniedHandlerCustomized;
	}

	/**
	 * 
	 * @param http
	 * @return {@link SecurityFilterChain}
	 * @throws Exception
	 */
	@Bean
	SecurityFilterChain filterChain(HttpSecurity http, AuthenticationManager authenticationManager) throws Exception {

		JwtAuthenticationFilter authenticationFilter = new JwtAuthenticationFilter();
		authenticationFilter.setAuthenticationManager(authenticationManager);
		authenticationFilter.setFilterProcessesUrl("/login");

		http.csrf(csrf -> csrf.disable()).cors(cors -> cors.configurationSource(corsConfigurationSource()))
				.authorizeHttpRequests((authorize) -> authorize
						/*
						 * .requestMatchers("/api/usuarios") .hasAnyAuthority("crear usuario",
						 * "modificar usuario", "listar usuario", "eliminar usuario")
						 * .requestMatchers("/api/permisos") .hasAnyAuthority("crear permiso",
						 * "modificar permiso", "listar permiso", "eliminar permiso")
						 * .requestMatchers("/api/roles") .hasAnyAuthority("crear rol", "modificar rol",
						 * "listar rol", "eliminar rol")
						 */
						// .requestMatchers(HttpMethod.GET, "/api/clientes").hasAuthority("visualizar
						// clientes")
						// .requestMatchers(HttpMethod.POST, "/api/clientes").hasAuthority("registrar
						// cliente")
						.anyRequest().authenticated())
				.authenticationProvider(authenticationProvider())
				.exceptionHandling(
						exceptionHandling -> exceptionHandling.authenticationEntryPoint(authenticationEntryPoint)
								.accessDeniedHandler(accessDeniedHandlerCustomized))
				.sessionManagement(
						sessionManagement -> sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				.addFilter(authenticationFilter)
				.addFilterBefore(authorizationFilter, UsernamePasswordAuthenticationFilter.class);
		return http.build();
	}

	@Bean
	AuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
		authenticationProvider.setUserDetailsService(userDetailsService);
		authenticationProvider.setPasswordEncoder(passwordEncoder());
		return authenticationProvider;
	}

	@Bean
	BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
		return config.getAuthenticationManager();
	}

	@Bean
	CorsConfigurationSource corsConfigurationSource() {
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		CorsConfiguration corsConfiguration = new CorsConfiguration();
		corsConfiguration.setAllowCredentials(true);
		corsConfiguration.setAllowPrivateNetwork(true);
		corsConfiguration.setAllowedHeaders(Arrays.asList("Content-Type", "Authorization"));
		corsConfiguration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
		corsConfiguration.setAllowedOrigins(Arrays.asList(HostPermitido.HOST_DEV));
		corsConfiguration.setExposedHeaders(Arrays.asList("Content-Type", "Authorization"));
		source.registerCorsConfiguration("/**", corsConfiguration);
		return source;
	}
}
