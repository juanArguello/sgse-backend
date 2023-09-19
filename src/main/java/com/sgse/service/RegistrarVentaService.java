/*
    Definicion de la interfaz RegistrarVentaService
 */
package com.sgse.service;

import java.util.List;

import com.sgse.entities.RegistrarVenta;

/**
 *
 * @author Juan Carlos Argüello Ortiz
 * @version 1.0
 */
public interface RegistrarVentaService {
    // Definicion de los metodos CRUD de la interfaz RegistrarVentaService
    public void create(RegistrarVenta registrarVenta);
    public RegistrarVenta findById(int id);  
    public List<RegistrarVenta> findAll();
    public void update(RegistrarVenta registrarVenta);
    public void delete(int id);
}
