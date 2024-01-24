package com.sgse.dao;

import java.util.List;

import com.sgse.resources.Paginacion;

public interface EntityDaoGeneric<T> {
	// Definicion de metodos CRUD de la interfaz de entidad Generica
    public void create(T entity);
    public T findById(int id);
    public List<T> findAll();
    public Paginacion<T> getEntidadesPaginado(int numeroPagina, int tamanhoPagina);
    public void update(T entity);
    public void delete(int id);
    public int cantidadFilas();
}
