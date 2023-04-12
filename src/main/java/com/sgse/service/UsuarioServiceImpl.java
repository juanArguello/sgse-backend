/*
    Clase UsuarioServiceImpl que integra la capa servicio de la aplicacion
 */
package com.sgse.service;

import com.sgse.dao.UsuarioDao;
import com.sgse.entities.Usuario;
import com.sgse.resources.Paginacion;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
/*import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;*/
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Juan Carlos Argüello Ortiz
 * @version 1.0
 */
@Service(value = "usuarioService")
public class UsuarioServiceImpl implements UsuarioService/*, UserDetailsService*/{
    
	private Logger logger = LoggerFactory.getLogger(UsuarioServiceImpl.class);
	
    @Autowired
    private UsuarioDao usuarioDao;
 
    
   /* @Override
    @Transactional(readOnly = true)
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Usuario usuario = usuarioDao.findByUsername(username);
		
		if(usuario == null) {
			logger.error("Error en login: no existe usuario "+username+" en el sistema");
			throw new UsernameNotFoundException("Error en login: no existe usuario "+username+" en el sistema");
		}
		
		List<GrantedAuthority> authorities = usuario.getIdRol().getPermisosList()
				.stream().map(permiso -> new SimpleGrantedAuthority(permiso.getNombre()))
				.peek(authority -> logger.info("Role: "+authority.getAuthority()))
				.collect(Collectors.toList());
		return new User(usuario.getUsername(),usuario.getPassword(),usuario.getEnabled(),true,true,true,authorities);
	}*/
    
    // Implementacion de los metodos CRUD
    @Override
    @Transactional
    public void create(Usuario usuario) {
        usuarioDao.create(usuario);
    }

    @Override
    @Transactional(readOnly = true)
    public Usuario findById(int id) {
        return usuarioDao.findById(id);
    }
    
    @Override
    @Transactional(readOnly = true)
    public Usuario findByUsername(String username) {
        return usuarioDao.findByUsername(username);
    }
  
    @Override
    @Transactional(readOnly = true)
    public Usuario findByEmail(String correo) {
        return usuarioDao.findByEmail(correo);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Usuario> findAll() {
        return usuarioDao.findAll();
    }
    
    @Override
    @Transactional(readOnly = true)
    public Paginacion<Usuario> getUsuariosPaginado(int numeroPagina, int tamanhoPagina) {
		return usuarioDao.getUsuariosPaginado(numeroPagina, tamanhoPagina);
	}

    @Override
    @Transactional
    public void update(Usuario usuario) {
        usuarioDao.update(usuario);
    }

    @Override
    @Transactional
    public void delete(int id) {
        usuarioDao.delete(id);
    }

    @Override
    @Transactional(readOnly = true)
    public int cantidadFilas() {
        return usuarioDao.cantidadFilas();
    }

	

   

}
