/*
  Clase implementadora RegistrarVentaDaoImpl de la interfaz RegistrarVentaDao
 */
package com.sgse.dao;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.sgse.entities.RegistrarVenta;

/**
 *
 * @author Juan Carlos Argüello Ortiz
 * @version 1.0
 */
@Repository("registrarVentaDao")
public class RegistrarVentaDaoImpl implements RegistrarVentaDao {

	@Autowired
	private SessionFactory sessionFactory;

	// Implementacion de los metodos CRUD
	@Override
	public void create(RegistrarVenta registrarVenta) {
		sessionFactory.getCurrentSession().persist(registrarVenta);
	}

	@Override
	public RegistrarVenta findById(int id) {
		return sessionFactory.getCurrentSession().get(RegistrarVenta.class, id);
	}

	@Override
	public List<RegistrarVenta> findAll() {
		return sessionFactory.getCurrentSession()
				.createQuery("from RegistrarVenta  rv order by rv.id asc", RegistrarVenta.class).getResultList();
	}

	@Override
	public void update(RegistrarVenta registrarVenta) {
		sessionFactory.getCurrentSession().merge(registrarVenta);
	}

	@Override
	public void delete(int id) {
		sessionFactory.getCurrentSession().remove(sessionFactory.getCurrentSession().get(RegistrarVenta.class, id));
	}

}
