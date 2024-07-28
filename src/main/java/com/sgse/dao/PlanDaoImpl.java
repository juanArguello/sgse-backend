/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sgse.dao;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.sgse.entities.Plan;

/**
 *
 * @author Juan Carlos Argüello Ortiz
 * @version 1.0
 */
@Repository(value = "planDao")
public class PlanDaoImpl implements PlanDao {

	@Autowired
	private SessionFactory sessionFactory;

	// Implementacion de los metodos CRUD
	@Override
	public void create(Plan plan) {
		sessionFactory.getCurrentSession().persist(plan);
	}

	@Override
	public Plan findById(int id) {
		return sessionFactory.getCurrentSession().get(Plan.class, id);
	}

	@Override
	public List<Plan> findAll() {
		return sessionFactory.getCurrentSession().createQuery("from Plan p order by p.id asc", Plan.class)
				.getResultList();
	}

	@Override
	public void update(Plan plan) {
		sessionFactory.getCurrentSession().merge(plan);
	}

	@Override
	public void delete(int id) {
		sessionFactory.getCurrentSession().remove(sessionFactory.getCurrentSession().get(Plan.class, id));
	}

}
