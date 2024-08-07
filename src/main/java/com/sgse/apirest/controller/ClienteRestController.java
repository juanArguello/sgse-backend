/*
    Servicio Rest de Cliente para realizar CRUD con sus respectivos metodos HTTP
    y en formato Json
 */
package com.sgse.apirest.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.sgse.entities.Cliente;
import com.sgse.resources.Paginacion;
import com.sgse.service.ClienteService;

import jakarta.validation.Valid;

/**
 * 
 * @author Juan Carlos Argüello Ortiz
 * @version 1.0
 */
@RestController
@CrossOrigin
@RequestMapping(path = "/api/clientes")
public class ClienteRestController {

	@Autowired
	private ClienteService clienteService;

	// @Secured("ROLE_VEND")
	@PreAuthorize("hasAuthority('registrar cliente')")
	@PostMapping(consumes = "application/json")
	public ResponseEntity<?> crearCliente(@Valid @RequestBody Cliente cliente, BindingResult result) {
		Map<String, Object> map = new HashMap<>();
		if (result.hasErrors()) { // verifica si hay errores en los campos de datos JSON
			List<String> errores = new ArrayList<>();
			result.getFieldErrors().forEach((err) -> {
				errores.add("El campo '" + err.getField() + "' " + err.getDefaultMessage());
			});
			map.put("errores", errores);
			return new ResponseEntity<>(map, HttpStatus.BAD_REQUEST);
		}

		try {
			clienteService.create(cliente); // crea el cliente
		} catch (DataAccessException e) { // Envia una excepcion con el error de insert en la BBDD
			map.put("mensaje", "Error al realizar insert en la base de datos");
			map.put("error", e.getMessage() + ": " + e.getMostSpecificCause().getMessage());
			return new ResponseEntity<>(map, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		map.put("mensaje", "El Cliente " + cliente.getNombre() + " ha sido creado con éxito");
		return new ResponseEntity<>(map, HttpStatus.CREATED);
	}

	@PreAuthorize("hasAuthority('visualizar clientes')")
	@GetMapping(produces = "application/json")
	@ResponseStatus(HttpStatus.OK)
	public List<Cliente> getClientes() {
		return clienteService.findAll();
	}

	@PreAuthorize("hasAuthority('visualizar clientes')")
	@GetMapping(path = "/page/{page}", produces = "application/json")
	public Paginacion<Cliente> getClientesPaginado(@PathVariable("page") Integer page) {
		int tamanhoPagina = 8;
		return clienteService.getClientesPaginado(page, tamanhoPagina);
	}

	@PreAuthorize("hasAuthority('visualizar clientes')")
	@GetMapping(path = "/{id}", produces = "application/json")
	public ResponseEntity<?> getClienteById(@PathVariable("id") String id) {
		Cliente cliente = null;
		Map<String, Object> map = new HashMap<>();
		try {
			cliente = clienteService.findById(Integer.valueOf(id));
		} catch (DataAccessException e) {
			map.put("mensaje", "Error al realizar la consulta en la base de datos");
			map.put("error", e.getMessage() + ": " + e.getMostSpecificCause().getMessage());
			return new ResponseEntity<>(map, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		if (cliente == null) { // Si no existe el cliente con el id ingresado retorna 404
			map.put("mensaje", "El Cliente ID: " + id + " no existe en la base de datos");
			return new ResponseEntity<>(map, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(cliente, HttpStatus.OK);
	}

	// @Secured("ROLE_VEND")
	@PreAuthorize("hasAuthority('Actualizar Clientes')")
	@PutMapping(path = "/{id}", consumes = "application/json")
	public ResponseEntity<?> updateCliente(@Valid @RequestBody Cliente cliente, BindingResult result,
			@PathVariable("id") String id) {
		Cliente clienteNuevo = clienteService.findById(Integer.valueOf(id));
		Map<String, Object> map = new HashMap<>();

		if (result.hasErrors()) { // verifica si hay errores en los campos de datos JSON
			List<String> errores = new ArrayList<>();
			result.getFieldErrors().forEach((err) -> {
				errores.add("El campo '" + err.getField() + "' " + err.getDefaultMessage());
			});
			map.put("errores", errores);
			return new ResponseEntity<>(map, HttpStatus.BAD_REQUEST);
		}

		if (clienteNuevo == null) { // comprueba si existe el cliente con el id ingresado
			map.put("mensaje", "Error: no se pudo editar, el Cliente ID: " + id + " no existe en la base de datos");
			return new ResponseEntity<>(map, HttpStatus.NOT_FOUND);
		}

		try {
			clienteNuevo.setCedula(cliente.getCedula());
			clienteNuevo.setRuc(cliente.getRuc());
			clienteNuevo.setNombre(cliente.getNombre());
			clienteNuevo.setApellido(cliente.getApellido());
			clienteNuevo.setDireccion(cliente.getDireccion());
			clienteNuevo.setTelefono(cliente.getTelefono());
			clienteNuevo.setEmail(cliente.getEmail());
			clienteNuevo.setEstadoCuenta(cliente.getEstadoCuenta());
			clienteService.update(clienteNuevo);
		} catch (DataAccessException e) {
			map.put("mensaje", "Error al actualizar el cliente en la base de datos");
			map.put("error", e.getMessage() + ": " + e.getMostSpecificCause().getMessage());
			return new ResponseEntity<>(map, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		map.put("mensaje", "El Cliente " + clienteNuevo.getNombre() + " ha sido actualizado con éxito");
		return new ResponseEntity<>(map, HttpStatus.NO_CONTENT);
	}

	@PreAuthorize("hasAuthority('Eliminar Clientes')")
	@DeleteMapping(path = "/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteCliente(@PathVariable("id") String id) {
		clienteService.delete(Integer.valueOf(id)); // Elimina el cliente de acuerdo al ID
	}

	@PreAuthorize("hasAuthority('visualizar clientes')")
	@GetMapping(path = "/cantidad", produces = "text/plain")
	@ResponseStatus(HttpStatus.OK)
	public int cantidadClientes() {
		return clienteService.cantidadClientes();
	}

}
