package com.sgse.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sgse.configuration.UserDetailsImpl;
import com.sgse.dao.UsuarioDao;
import com.sgse.entities.Usuario;

@Service(value = "userDetailsService")
public class UserDetailsServiceImpl implements UserDetailsService {

	private Logger logger = LoggerFactory.getLogger(UserDetailsImpl.class);
	
	@Autowired
	private UsuarioDao usuarioDao;

	@Override
	@Transactional(readOnly = true)
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException{
		Usuario usuario = usuarioDao.findByUsername(username);
		if(usuario == null) {
			logger.error("El usuario "+username+" no existe en el sistema");
			throw new UsernameNotFoundException("El usuario "+username+" no existe en el sistema");
		}
		return new UserDetailsImpl(usuario);
	}
}
