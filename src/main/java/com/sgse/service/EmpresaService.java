/*
    Definicion de la interfaz EmpresaService
 */
package com.sgse.service;

import java.util.List;

import com.sgse.entities.Empresa;

/**
 *
 * @author Juan Carlos Argüello Ortiz
 * @version 1.0
 */
public interface EmpresaService {
	// Definicion de los metodos CRUD de la interfaz EmpresaService
	public void create(Empresa empresa);

	public Empresa findById(int id);

	public List<Empresa> findAll();

	public void update(Empresa empresa);

	public void delete(int id);
}
