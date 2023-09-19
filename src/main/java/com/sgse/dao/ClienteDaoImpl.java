/*
  Clase implementadora ClienteDaoImpl de la interfaz ClienteDao
 */

package com.sgse.dao;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.sgse.entities.Cliente;
import com.sgse.resources.Paginacion;

import jakarta.persistence.TypedQuery;

/**
 *
 * @author Juan Carlos Argüello Ortiz
 * @version 1.0
 */
@Repository("clienteDao")
public class ClienteDaoImpl implements ClienteDao{

    @Autowired
    private SessionFactory sessionFactory;

    // Implementacion de los metodos CRUD
    @Override
    public void create(Cliente cliente) {
        sessionFactory.getCurrentSession()
            .persist(cliente);
    }

    @Override
    public Cliente findById(int id) {
        return sessionFactory.getCurrentSession().get(Cliente.class, id);
    }

    @Override
    public List<Cliente> findAll() {
        return sessionFactory.getCurrentSession().createQuery("from Cliente  s order by s.id asc", Cliente.class).getResultList();
    }
    
    public Paginacion<Cliente> getClientesPaginado(int numeroPagina, int tamanhoPagina) {
		int ultimoNumeroPagina;
		Long totalRegistros;
		totalRegistros = sessionFactory.getCurrentSession()
				.createQuery("SELECT COUNT(c.id) from Cliente c", Long.class)
				.getSingleResult();
		if (totalRegistros % tamanhoPagina == 0) {
			ultimoNumeroPagina = (int) (totalRegistros / tamanhoPagina);
		} else {
			ultimoNumeroPagina = (int) (totalRegistros / tamanhoPagina) + 1;
		}
		TypedQuery<Cliente> query = sessionFactory.getCurrentSession()
				.createQuery("from Cliente c order by c.id asc",
				Cliente.class);

		query.setFirstResult((numeroPagina - 1) * tamanhoPagina);
		query.setMaxResults(tamanhoPagina);
		Paginacion<Cliente> resultado = new Paginacion<>();
		resultado.setActualNumeroPagina(numeroPagina);
		resultado.setTamanhoPagina(tamanhoPagina);
		resultado.setUltimoNumeroPagina(ultimoNumeroPagina);
		resultado.setTotalRegistros(totalRegistros);
		resultado.setRegistros(query.getResultList());
		return resultado;
	}

    @Override
    public void update(Cliente cliente) {
        sessionFactory.getCurrentSession().merge(cliente);
    }

    @Override
    public void delete(int id) {
        sessionFactory.getCurrentSession()
        	.remove(sessionFactory.getCurrentSession().get(Cliente.class, id));
    }

    @Override
    public int cantidadClientes() {
        return sessionFactory.getCurrentSession()
        		.createQuery("select count(*) from Cliente c",Long.class)
        		.getSingleResult().intValue();
    }
    
}
