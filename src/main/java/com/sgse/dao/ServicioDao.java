/*
 
 */
package com.sgse.dao;

import java.util.List;

import com.sgse.entities.Servicios;
import com.sgse.resources.Paginacion;

/**
 *
 * @author Juan Carlos Argüello Ortiz
 * @version 1.0
 */
public interface ServicioDao {
    // Definicion de metodos CRUD de la interfaz ServicioDao
    public void create(Servicios servicios);
    public Servicios findById(int id);
    public List<Servicios> findAll();
    public Paginacion<Servicios> getServiciosPaginado(int numeroPagina, int tamanhoPagina);
    public void update(Servicios servicios);
    public void delete(int id);
}
