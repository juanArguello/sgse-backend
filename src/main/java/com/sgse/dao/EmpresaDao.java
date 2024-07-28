/*
    Interfaz EmpresaDao con sus metodos CRUD
 */
package com.sgse.dao;

import java.util.List;

import com.sgse.entities.Empresa;

/**
 *
 * @author Juan Carlos Argüello Ortiz
 * @version 1.0
 */
public interface EmpresaDao {
	// Definicion de metodos CRUD de la interfaz EmpresaDao
	public void create(Empresa empresa);

	public Empresa findById(int id);

	public List<Empresa> findAll();

	public void update(Empresa empresa);

	public void delete(int id);
}
