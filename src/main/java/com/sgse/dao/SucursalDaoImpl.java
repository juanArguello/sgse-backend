/*
  Clase implementadora SucursalDaoImpl de la interfaz SucursalDao
 */
package com.sgse.dao;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.sgse.entities.Sucursal;

/**
 *
 * @author Juan Carlos Argüello Ortiz
 * @version 1.0
 */
@Repository("sucursalDao")
public class SucursalDaoImpl implements SucursalDao{
    
    @Autowired
    private SessionFactory sessionFactory;
    
    // Implementacion de los metodos CRUD
    @Override
    public void create(Sucursal sucursal) {
        sessionFactory.getCurrentSession()
            .persist(sucursal);
    }

    @Override
    public Sucursal findById(int id) {
        return sessionFactory.getCurrentSession().get(Sucursal.class, id);
    }

    @Override
    public List<Sucursal> findAll() {
        return sessionFactory.getCurrentSession().createQuery("from Sucursal s order by s.id asc", Sucursal.class).getResultList();
    }

    @Override
    public void update(Sucursal sucursal) {
        sessionFactory.getCurrentSession().merge(sucursal);
    }

    @Override
    public void delete(int id) {
        sessionFactory.getCurrentSession()
            .remove(sessionFactory.getCurrentSession().get(Sucursal.class, id));
    }
   
}
