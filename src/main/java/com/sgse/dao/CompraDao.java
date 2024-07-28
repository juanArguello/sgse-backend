/*
    Interfaz CompraDao con sus metodos CRUD
 */
package com.sgse.dao;

import java.util.List;

import com.sgse.entities.Compra;

/**
 *
 * @author Juan Carlos Argüello Ortiz
 * @version 1.0
 */
public interface CompraDao {
	// Definicion de metodos CRUD de la interfaz CompraDao
	public void create(Compra compra);

	public Compra findById(int id);

	public List<Compra> findAll();

	public void update(Compra compra);

	public void delete(int id);
}
