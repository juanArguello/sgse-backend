/*
    Clase UsuarioServiceImpl que integra la capa servicio de la aplicacion
 */
package com.sgse.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sgse.dao.UsuarioDao;
import com.sgse.entities.Usuario;
import com.sgse.resources.Paginacion;

/**
 *
 * @author Juan Carlos Argüello Ortiz
 * @version 1.0
 */
@Service(value = "usuarioService")
public class UsuarioServiceImpl implements UsuarioService {

	@Autowired
	private UsuarioDao usuarioDao;

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
