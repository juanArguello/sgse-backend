/*

 */
package com.sgse.dao;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.sgse.entities.Servicios;
import com.sgse.resources.Paginacion;

import jakarta.persistence.TypedQuery;

/**
 *
 * @author Juan Carlos Argüello Ortiz
 * @version 1.0
 */
@Repository(value = "servicioDao")
public class ServicioDaoImpl implements ServicioDao{

    @Autowired
    private SessionFactory sessionFactory;
    
    //Implementacion de los metodos CRUD
    @Override
    public void create(Servicios servicios) {
        sessionFactory.getCurrentSession()
        	.persist(servicios);
    }

    @Override
    public Servicios findById(int id) {
        return sessionFactory.getCurrentSession().get(Servicios.class, id);
    }

	@Override
    public List<Servicios> findAll() {
        return sessionFactory.getCurrentSession().createQuery("from Servicios s order by s.id asc", Servicios.class).getResultList();
    }
	
	@Override
	public Paginacion<Servicios> getServiciosPaginado(int numeroPagina, int tamanhoPagina) {
		int ultimoNumeroPagina;
		Long totalRegistros;
		totalRegistros = sessionFactory.getCurrentSession()
				.createQuery("SELECT COUNT(s.id) from Servicios s", Long.class)
				.getSingleResult();
		if (totalRegistros % tamanhoPagina == 0) {
			ultimoNumeroPagina = (int) (totalRegistros / tamanhoPagina);
		} else {
			ultimoNumeroPagina = (int) (totalRegistros / tamanhoPagina) + 1;
		}
		TypedQuery<Servicios> query = sessionFactory.getCurrentSession().createQuery("from Servicios s order by s.id asc",
				Servicios.class);

		query.setFirstResult((numeroPagina - 1) * tamanhoPagina);
		query.setMaxResults(tamanhoPagina);
		Paginacion<Servicios> resultado = new Paginacion<>();
		resultado.setActualNumeroPagina(numeroPagina);
		resultado.setTamanhoPagina(tamanhoPagina);
		resultado.setUltimoNumeroPagina(ultimoNumeroPagina);
		resultado.setTotalRegistros(totalRegistros);
		resultado.setRegistros(query.getResultList());
		return resultado;
	}

    @Override
    public void update(Servicios servicios) {
        sessionFactory.getCurrentSession().merge(servicios);
    }

    @Override
    public void delete(int id) {
        sessionFactory.getCurrentSession()
            .remove(sessionFactory.getCurrentSession().get(Servicios.class, id));
    }
    
}
