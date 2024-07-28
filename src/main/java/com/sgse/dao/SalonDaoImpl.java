/*
  Clase implementadora SalonDaoImpl de la interfaz SalonDao
 */
package com.sgse.dao;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.sgse.entities.Salon;

/**
 *
 * @author Juan Carlos Argüello Ortiz
 * @version 1.0
 */
@Repository("salonDao")
public class SalonDaoImpl implements SalonDao {

	@Autowired
	private SessionFactory sessionFactory;

	// Implementacion de los metodos CRUD
	@Override
	public void create(Salon salon) {
		sessionFactory.getCurrentSession().persist(salon);
	}

	@Override
	public Salon findById(int id) {
		return sessionFactory.getCurrentSession().get(Salon.class, id);
	}

	@Override
	public List<Salon> findAll() {
		return sessionFactory.getCurrentSession().createQuery("from Salon s order by s.id asc", Salon.class)
				.getResultList();
	}

	@Override
	public void update(Salon salon) {
		sessionFactory.getCurrentSession().merge(salon);
	}

	@Override
	public void delete(int id) {
		sessionFactory.getCurrentSession().remove(sessionFactory.getCurrentSession().get(Salon.class, id));
	}

}
