/*

 */
package com.sgse.dao;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.sgse.entities.Rol;

/**
 *
 * @author Juan Carlos Argüello Ortiz
 * @version 1.0
 */
@Repository(value = "rolDao")
public class RolDaoImpl implements RolDao{
    
    @Autowired
    private SessionFactory sessionFactory;

    //Implementacion de los metodos CRUD
    @Override
    public void create(Rol rol) {
        sessionFactory.getCurrentSession()
            .persist(rol);
    }

    @Override
    public Rol findById(int id) {
        return sessionFactory.getCurrentSession().get(Rol.class, id);
    }
    
    @Override
    public Rol findByRolName(String nombreRol) {
        return (Rol) sessionFactory.getCurrentSession()
        		.createQuery("from Rol r where r.nombre = :nombreRol", Rol.class)
        		.setParameter("nombreRol", nombreRol).getSingleResult();
    }

    @Override
    public List<Rol> findAll() {
        return sessionFactory.getCurrentSession()
        		.createQuery("from Rol r order by r.id asc", Rol.class).getResultList();
    }

    @Override
    public void update(Rol rol) {
        sessionFactory.getCurrentSession()
            .merge(rol);
    }

    @Override
    public void delete(int id) {
        sessionFactory.getCurrentSession()
            .remove(sessionFactory.getCurrentSession().get(Rol.class, id));
    }
    
    @Override
	public int cantidadFilas() {
		return sessionFactory.getCurrentSession()
				.createQuery("select count(*) from Rol r",Long.class).getSingleResult()
				.intValue();
	}
}
