/*
    Interfaz ContratoVentaDao con sus metodos CRUD
 */
package com.sgse.dao;

import java.util.List;

import com.sgse.entities.ContratoVenta;

/**
 *
 * @author Juan Carlos Argüello Ortiz
 * @version 1.0
 */ 
public interface ContratoVentaDao {
    // Definicion de metodos CRUD de la interfaz ContratoVentaDao
    public void create(ContratoVenta contratoVenta);
    public ContratoVenta findById(int id);
    public List<ContratoVenta> findAll();
    public void update(ContratoVenta contratoVenta);
    public void delete(int id);
}
