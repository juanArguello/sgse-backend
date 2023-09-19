/*
    Definicion de la interfaz RolService
 */
package com.sgse.service;

import java.util.List;

import com.sgse.entities.Rol;

/**
 *
 * @author Juan Carlos Argüello Ortiz
 * @version 1.0
 */
public interface RolService {
    // Definicion de los metodos CRUD de la interfaz RolService
    public void create(Rol rol);
    public Rol findById(int id);  
    public Rol findByRolName(String nombreRol); 
    public List<Rol> findAll();
    public void update(Rol rol);
    public void delete(int id);
    public int cantidadFilas();
}
