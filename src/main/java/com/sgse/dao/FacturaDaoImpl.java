/*
  Clase implementadora FacturaDaoImpl de la interfaz FacturaDao
 */
package com.sgse.dao;

import com.sgse.entities.Factura;
import java.util.List;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Juan Carlos Argüello Ortiz
 * @version 1.0
 */
@Repository("facturaDao")
public class FacturaDaoImpl implements FacturaDao{

    @Autowired
    private SessionFactory sessionFactory;
    
    // Implementacion de los metodos CRUD
    @Override
    public void create(Factura factura) {
        sessionFactory.getCurrentSession()
            .save(factura);
    }

    @Override
    public Factura findById(int id) {
        return sessionFactory.getCurrentSession().get(Factura.class, id);
    }

    @Override
    public List<Factura> findAll() {
        return sessionFactory.getCurrentSession().createQuery("from Factura f order by f.id asc", Factura.class).getResultList();
    }

    @Override
    public void update(Factura factura) {
        sessionFactory.getCurrentSession().update(factura);
    }

    @Override
    public void delete(int id) {
        sessionFactory.getCurrentSession()
            .delete(sessionFactory.getCurrentSession().get(Factura.class, id));
    }
    
}
