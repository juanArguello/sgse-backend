/*
    Definicion de la interfaz UsuarioService
 */
package com.sgse.service;

import java.util.List;

import com.sgse.entities.Usuario;
import com.sgse.resources.Paginacion;

/**
 *
 * @author Juan Carlos Argüello Ortiz
 * @version 1.0
 */
public interface UsuarioService {
	// Definicion de los metodos CRUD de la interfaz UsuarioService
	public void create(Usuario usuario);

	public Usuario findById(int id);

	public Usuario findByUsername(String username);

	public Usuario findByEmail(String correo);

	public List<Usuario> findAll();

	public Paginacion<Usuario> getUsuariosPaginado(int numeroPagina, int tamanhoPagina);

	public void update(Usuario usuario);

	public void delete(int id);

	public int cantidadFilas();
}
