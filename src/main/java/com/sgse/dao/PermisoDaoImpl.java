/*
 
 */
package com.sgse.dao;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.sgse.entities.Permisos;
import com.sgse.resources.Paginacion;

import jakarta.persistence.TypedQuery;

/**
 *
 * @author Juan Carlos Argüello Ortiz
 * @version 1.0
 */
@Repository(value = "permisoDao")
public class PermisoDaoImpl implements PermisoDao {

	@Autowired
	private SessionFactory sessionFactory;

	// Implementacion de los metodos CRUD
	@Override
	public void create(Permisos permisos) {
		sessionFactory.getCurrentSession()
			.persist(permisos);
	}

	@Override
	public Permisos findById(int id) {
		return sessionFactory.getCurrentSession().get(Permisos.class, id);
	}

	@Override
	public List<Permisos> findAll() {
		return sessionFactory.getCurrentSession().createQuery("from Permisos p order by p.id asc", Permisos.class)
				.getResultList();
	}
	
	@Override
	public Paginacion<Permisos> getPermisosPaginado(int numeroPagina, int tamanhoPagina) {
		int ultimoNumeroPagina;
		Long totalRegistros;
		totalRegistros = sessionFactory.getCurrentSession()
				.createQuery("SELECT COUNT(p.id) from Permisos p", Long.class)
				.getSingleResult();
		if (totalRegistros % tamanhoPagina == 0) {
			ultimoNumeroPagina = (int) (totalRegistros / tamanhoPagina);
		} else {
			ultimoNumeroPagina = (int) (totalRegistros / tamanhoPagina) + 1;
		}
		TypedQuery<Permisos> query = sessionFactory.getCurrentSession().createQuery("from Permisos p order by p.id asc",
				Permisos.class);

		query.setFirstResult((numeroPagina - 1) * tamanhoPagina);
		query.setMaxResults(tamanhoPagina);
		Paginacion<Permisos> resultado = new Paginacion<>();
		resultado.setActualNumeroPagina(numeroPagina);
		resultado.setTamanhoPagina(tamanhoPagina);
		resultado.setUltimoNumeroPagina(ultimoNumeroPagina);
		resultado.setTotalRegistros(totalRegistros);
		resultado.setRegistros(query.getResultList());
		return resultado;
	}

	@Override
	public void update(Permisos permisos) {
		sessionFactory.getCurrentSession().merge(permisos);
	}

	@Override
	public void delete(int id) {
		sessionFactory.getCurrentSession()
			.remove(sessionFactory.getCurrentSession().get(Permisos.class, id));
	}

	@Override
	public int cantidadFilas() {
		return sessionFactory.getCurrentSession()
				.createQuery("select count(*) from Permisos p",Long.class).getSingleResult()
				.intValue();
	}

}
