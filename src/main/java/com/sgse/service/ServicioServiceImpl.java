/*
    Clase ServicioServiceImpl que integra la capa servicio de la aplicacion
 */
package com.sgse.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sgse.dao.ServicioDao;
import com.sgse.entities.Servicios;
import com.sgse.resources.Paginacion;

/**
 *
 * @author Juan Carlos Argüello Ortiz
 * @version 1.0
 */
@Service(value = "servicioService")
public class ServicioServiceImpl implements ServicioService {

	@Autowired
	private ServicioDao servicioDao;

	// Implementacion de los metodos CRUD
	@Override
	@Transactional
	public void create(Servicios servicios) {
		servicioDao.create(servicios);
	}

	@Override
	@Transactional(readOnly = true)
	public Servicios findById(int id) {
		return servicioDao.findById(id);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Servicios> findAll() {
		return servicioDao.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public Paginacion<Servicios> getServiciosPaginado(int numeroPagina, int tamanhoPagina) {
		return servicioDao.getServiciosPaginado(numeroPagina, tamanhoPagina);
	}

	@Override
	@Transactional
	public void update(Servicios servicios) {
		servicioDao.update(servicios);
	}

	@Override
	@Transactional
	public void delete(int id) {
		servicioDao.delete(id);
	}

}
