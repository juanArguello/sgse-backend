/*
    Interfaz ClienteDao con sus metodos CRUD
 */
package com.sgse.dao;

import java.util.List;

import com.sgse.entities.Cliente;
import com.sgse.resources.Paginacion;

/**
 *
 * @author Juan Carlos Argüello Ortiz
 * @version 1.0
 */
public interface ClienteDao {
	// Definicion de metodos CRUD de la interfaz ClienteDao
	public void create(Cliente cliente);

	public Cliente findById(int id);

	public List<Cliente> findAll();

	public Paginacion<Cliente> getClientesPaginado(int numeroPagina, int tamanhoPagina);

	public void update(Cliente cliente);

	public void delete(int id);

	public int cantidadClientes();
}
