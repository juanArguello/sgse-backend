/*
  Clase implementadora FacturaDaoImpl de la interfaz FacturaDao
 */
package com.sgse.dao;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.sgse.entities.Factura;

/**
 *
 * @author Juan Carlos Argüello Ortiz
 * @version 1.0
 */
@Repository("facturaDao")
public class FacturaDaoImpl implements FacturaDao {

	@Autowired
	private SessionFactory sessionFactory;

	// Implementacion de los metodos CRUD
	@Override
	public void create(Factura factura) {
		sessionFactory.getCurrentSession().persist(factura);
	}

	@Override
	public Factura findById(int id) {
		return sessionFactory.getCurrentSession().get(Factura.class, id);
	}

	@Override
	public List<Factura> findAll() {
		return sessionFactory.getCurrentSession().createQuery("from Factura f order by f.id asc", Factura.class)
				.getResultList();
	}

	@Override
	public void update(Factura factura) {
		sessionFactory.getCurrentSession().merge(factura);
	}

	@Override
	public void delete(int id) {
		sessionFactory.getCurrentSession().remove(sessionFactory.getCurrentSession().get(Factura.class, id));
	}

}
