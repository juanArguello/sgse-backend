/*
  Clase implementadora EmpresaDaoImpl de la interfaz EmpresaDao
 */
package com.sgse.dao;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.sgse.entities.Empresa;

/**
 *
 * @author Juan Carlos Argüello Ortiz
 * @version 1.0
 */
@Repository("empresaDao")
public class EmpresaDaoImpl implements EmpresaDao{
    
    @Autowired
    private SessionFactory sessionFactory;

    // Implementacion de los metodos CRUD
    @Override
    public void create(Empresa empresa) {
        sessionFactory.getCurrentSession()
            .persist(empresa);
    }

    @Override
    public Empresa findById(int id) {
        return sessionFactory.getCurrentSession().get(Empresa.class, id);
    }

    @Override
    public List<Empresa> findAll() {
        return sessionFactory.getCurrentSession().createQuery("from Empresa e order by e.id asc", Empresa.class).getResultList();
    }

    @Override
    public void update(Empresa empresa) {
        sessionFactory.getCurrentSession().merge(empresa);
    }

    @Override
    public void delete(int id) {
        sessionFactory.getCurrentSession()
            .remove(sessionFactory.getCurrentSession().get(Empresa.class, id));
    }
    
}
