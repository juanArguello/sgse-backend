/*
    Definicion de la interfaz InventarioService
 */
package com.sgse.service;

import java.util.List;

import com.sgse.entities.Inventario;

/**
 *
 * @author Juan Carlos Argüello Ortiz
 * @version 1.0
 */
public interface InventarioService {
    // Definicion de los metodos CRUD de la interfaz InventarioService
    public void create(Inventario inventario);
    public Inventario findById(int id);  
    public List<Inventario> findAll();
    public void update(Inventario inventario);
    public void delete(int id);
}
