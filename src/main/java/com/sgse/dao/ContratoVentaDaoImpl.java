/*
  Clase implementadora ContratoVentaDaoImpl de la interfaz ContratoVentaDao
 */
package com.sgse.dao;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.sgse.entities.ContratoVenta;

/**
 *
 * @author Juan Carlos Argüello Ortiz
 * @version 1.0
 */
@Repository("contratoVentaDao")
public class ContratoVentaDaoImpl implements ContratoVentaDao {

	@Autowired
	private SessionFactory sessionFactory;

	// Implementacion de los metodos CRUD
	@Override
	public void create(ContratoVenta contratoVenta) {
		sessionFactory.getCurrentSession().persist(contratoVenta);
	}

	@Override
	public ContratoVenta findById(int id) {
		return sessionFactory.getCurrentSession().get(ContratoVenta.class, id);
	}

	@Override
	public List<ContratoVenta> findAll() {
		return sessionFactory.getCurrentSession()
				.createQuery("from ContratoVenta cv order by cv.id asc", ContratoVenta.class).getResultList();
	}

	@Override
	public void update(ContratoVenta contratoVenta) {
		sessionFactory.getCurrentSession().merge(contratoVenta);
	}

	@Override
	public void delete(int id) {
		sessionFactory.getCurrentSession().remove(sessionFactory.getCurrentSession().get(ContratoVenta.class, id));
	}

}
