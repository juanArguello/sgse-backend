/*
    Definicion de la interfaz SeguroService
 */
package com.sgse.service;

import java.util.List;

import com.sgse.entities.Seguro;

/**
 *
 * @author Juan Carlos Argüello Ortiz
 * @version 1.0
 */
public interface SeguroService {
    // Definicion de los metodos CRUD de la interfaz SeguroService
    public void create(Seguro seguro);
    public Seguro findById(int id);  
    public List<Seguro> findAll();
    public void update(Seguro seguro);
    public void delete(int id);
}
