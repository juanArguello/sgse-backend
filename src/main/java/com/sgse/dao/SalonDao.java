/*
    Interfaz SalonDao con sus metodos CRUD
 */
package com.sgse.dao;

import java.util.List;

import com.sgse.entities.Salon;

/**
 *
 * @author Juan Carlos Argüello Ortiz
 * @version 1.0
 */ 
public interface SalonDao {
    // Definicion de metodos CRUD de la interfaz SalonDao
    public void create(Salon salon);
    public Salon findById(int id);
    public List<Salon> findAll();
    public void update(Salon salon);
    public void delete(int id);
}
