/*
    Definicion de la interfaz ClienteService
 */
package com.sgse.service;

import java.util.List;

import com.sgse.entities.Cliente;
import com.sgse.resources.Paginacion;

/**
 *
 * @author Juan Carlos Argüello Ortiz
 * @version 1.0
 */
public interface ClienteService {
    // Definicion de los metodos CRUD de la interfaz ClienteService
    public void create(Cliente cliente);
    public Cliente findById(int id);
    public List<Cliente> findAll();
    public Paginacion<Cliente> getClientesPaginado(int numeroPagina, int tamanhoPagina);
    public void update(Cliente cliente);
    public void delete(int id);
    public int cantidadClientes();
}
