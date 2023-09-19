/*
    Interfaz FacturaDao con sus metodos CRUD
 */
package com.sgse.dao;

import java.util.List;

import com.sgse.entities.Factura;

/**
 *
 * @author Juan Carlos Argüello Ortiz
 * @version 1.0
 */ 
public interface FacturaDao {
    // Definicion de metodos CRUD de la interfaz FacturaDao
    public void create(Factura factura);
    public Factura findById(int id);
    public List<Factura> findAll();
    public void update(Factura factura);
    public void delete(int id);
}
