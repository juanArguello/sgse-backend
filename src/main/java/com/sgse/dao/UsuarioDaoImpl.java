/*
  Clase implementadora UsuarioDaoImpl de la interfaz UsuarioDao
 */
package com.sgse.dao;

import com.sgse.entities.Usuario;
import com.sgse.resources.Paginacion;

import java.util.List;

import javax.persistence.TypedQuery;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;


/**
 *
 * @author Juan Carlos Argüello Ortiz
 * @version 1.0
 */
@Repository(value = "usuarioDao")
public class UsuarioDaoImpl implements UsuarioDao{
    
    @Autowired
    private SessionFactory sessionFactory;
    
    
    //Implementacion de los metodos CRUD
    @Override
    public void create(Usuario usuario) {
        //hibernateTemplate.save(usuario);
        sessionFactory.getCurrentSession().save(usuario);
    }

    @Override
    public Usuario findById(int id) {
        return this.sessionFactory.getCurrentSession().get(Usuario.class, id);
    }
    
    @Override
    public Usuario findByUsername(String username) {
        return this.sessionFactory.getCurrentSession()
    		.createQuery("from Usuario u where u.username = :username", Usuario.class)
    		.setParameter("username", username)
    		.setReadOnly(true)
    		.uniqueResult();
    }
    
    @Override
    public Usuario findByEmail(String correo) {
        return  (Usuario) this.sessionFactory.getCurrentSession()
        		.createQuery("from Usuario u where u.email = :email", Usuario.class)
        		.setParameter("email",correo).getSingleResult();
    }

    @Override
    public List<Usuario> findAll() {
        return this.sessionFactory.getCurrentSession()
        		.createQuery("from Usuario u order by u.id asc", Usuario.class).getResultList();
    }
    
    public Paginacion<Usuario> getUsuariosPaginado(int numeroPagina, int tamanhoPagina) {
		int ultimoNumeroPagina;
		Long totalRegistros;
		totalRegistros = sessionFactory.getCurrentSession()
				.createQuery("SELECT COUNT(u.id) from Usuario u", Long.class)
				.getSingleResult();
		if (totalRegistros % tamanhoPagina == 0) {
			ultimoNumeroPagina = (int) (totalRegistros / tamanhoPagina);
		} else {
			ultimoNumeroPagina = (int) (totalRegistros / tamanhoPagina) + 1;
		}
		TypedQuery<Usuario> query = sessionFactory.getCurrentSession().createQuery("from Usuario u order by u.id asc",
				Usuario.class);

		query.setFirstResult((numeroPagina - 1) * tamanhoPagina);
		query.setMaxResults(tamanhoPagina);
		Paginacion<Usuario> resultado = new Paginacion<>();
		resultado.setActualNumeroPagina(numeroPagina);
		resultado.setTamanhoPagina(tamanhoPagina);
		resultado.setUltimoNumeroPagina(ultimoNumeroPagina);
		resultado.setTotalRegistros(totalRegistros);
		resultado.setRegistros(query.getResultList());
		return resultado;
	}

    @Override
    public void update(Usuario usuario) {
        //hibernateTemplate.update(usuario);
    	this.sessionFactory.getCurrentSession().update(usuario);
    }

    @Override
    public void delete(int id) {
//        Query query = getSession().createQuery("delete from Usuario u where u.id=:pId");
//        query.setParameter("pId", id);
//        query.executeUpdate();
    	this.sessionFactory.getCurrentSession().delete(this.sessionFactory.getCurrentSession().get(Usuario.class, id));
    }

    @Override
	public int cantidadFilas() {
		return this.sessionFactory.getCurrentSession()
				.createQuery("select count(*) from Usuario u",Long.class).getSingleResult()
				.intValue();
	}
  
    
}
