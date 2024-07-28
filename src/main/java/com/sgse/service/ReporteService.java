/*
    Definicion de la interfaz ReporteService
 */
package com.sgse.service;

import java.util.List;

import com.sgse.entities.Reporte;

/**
 *
 * @author Juan Carlos Argüello Ortiz
 * @version 1.0
 */
public interface ReporteService {
	// Definicion de los metodos CRUD de la interfaz ReporteService
	public void create(Reporte reporte);

	public Reporte findById(int id);

	public List<Reporte> findAll();

	public void update(Reporte reporte);

	public void delete(int id);
}
