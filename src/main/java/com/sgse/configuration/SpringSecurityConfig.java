package com.sgse.configuration;

import org.springframework.context.annotation.Configuration;



@Configuration
public class SpringSecurityConfig {
/*	
	@Autowired
	@Qualifier("usuarioService")
	private UserDetailsService usuarioService;
	
    @Bean
    SecurityFilterChain filterChain(HttpSecurity http, AuthenticationManager authenticationManager) throws Exception {
        http
	        .csrf().disable()
	        .authorizeRequests()
	        .anyRequest()
            .authenticated()
            .and()
            .httpBasic()
            .and()
            .sessionManagement(management -> management.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
	    return http.build();
	}

    
    @Bean
    AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
    	return http.getSharedObject(AuthenticationManagerBuilder.class)
    			.userDetailsService(usuarioService)
    			.passwordEncoder(passwordEncoder())
    			.and().build();
    }

    @Bean
    BCryptPasswordEncoder passwordEncoder() {
    	return new BCryptPasswordEncoder();
	}*/
}
