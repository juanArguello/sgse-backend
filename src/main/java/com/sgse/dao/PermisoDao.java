/*

 */
package com.sgse.dao;

import java.util.List;

import com.sgse.entities.Permisos;
import com.sgse.resources.Paginacion;

/**
 *
 * @author Juan Carlos Argüello Ortiz
 * @version 1.0
 */
public interface PermisoDao {
	// Definicion de metodos CRUD de la interfaz PermisoDao
	public void create(Permisos permisos);

	public Permisos findById(int id);

	public List<Permisos> findAll();

	public Paginacion<Permisos> getPermisosPaginado(int numeroPagina, int tamanhoPagina);

	public void update(Permisos permisos);

	public void delete(int id);

	public int cantidadFilas();
}
