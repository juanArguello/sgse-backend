/*
    Servicio Rest de Servicios para realizar CRUD con sus respectivos metodos HTTP
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

import com.sgse.entities.Servicios;
import com.sgse.resources.HostPermitido;
import com.sgse.service.ServicioService;

import jakarta.validation.Valid;

/**
 *
 * @author Juan Carlos Argüello Ortiz
 * @version 1.0
 */
@RestController
@CrossOrigin(origins = { HostPermitido.HOST_DEV })
@RequestMapping(path = "/api/servicios")
public class ServicioRestController {

	@Autowired
	private ServicioService servicioService;

	@PreAuthorize("hasAuthority('Crear Servicios')")
	@PostMapping(consumes = "application/json")
	public ResponseEntity<?> crearServicio(@Valid @RequestBody Servicios servicios, BindingResult result) {
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
			servicioService.create(servicios); // crea el servicio
		} catch (DataAccessException e) { // Envia una excepcion con el error de insert en la BBDD
			String[] mensajeString = e.getMostSpecificCause().getMessage().replaceAll("[()]", "").split("\n");
			map.put("mensaje", "Error al realizar inserción en la base de datos");
			map.put("error", mensajeString[0].replaceAll("ERROR: ", ""));
			map.put("detalle", mensajeString[1].replaceAll(" Detail: ", "").replaceAll("=", ": "));
			return new ResponseEntity<>(map, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		map.put("mensaje", "El Servicio " + servicios.getNombre() + " ha sido creado con éxito");
		return new ResponseEntity<>(map, HttpStatus.CREATED);
	}

	@PreAuthorize("hasAuthority('Visualizar Servicios')")
	@GetMapping(produces = "application/json")
	@ResponseStatus(HttpStatus.OK)
	public List<Servicios> getServicios() {
		return servicioService.findAll();
	}

	@PreAuthorize("hasAuthority('Visualizar Servicios')")
	@GetMapping(path = "/{id}", produces = "application/json")
	public ResponseEntity<?> getServicioById(@PathVariable("id") String id) {
		Servicios servicios = null;
		Map<String, Object> map = new HashMap<>();
		try {
			servicios = servicioService.findById(Integer.valueOf(id));
		} catch (DataAccessException e) {
			map.put("mensaje", "Error al realizar la consulta en la base de datos");
			map.put("error", e.getMessage() + ": " + e.getMostSpecificCause().getMessage());
			return new ResponseEntity<>(map, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		if (servicios == null) { // Si no existe el servicio con el id ingresado retorna 404
			map.put("mensaje", "El servicio ID: " + id + " no existe en la base de datos");
			return new ResponseEntity<>(map, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(servicios, HttpStatus.OK);
	}

	@PreAuthorize("hasAuthority('Modificar Servicios')")
	@PutMapping(path = "/{id}", consumes = "application/json")
	public ResponseEntity<?> updateServicio(@Valid @RequestBody Servicios servicios, BindingResult result,
			@PathVariable("id") String id) {
		Servicios servicioNuevo = servicioService.findById(Integer.valueOf(id));
		Map<String, Object> map = new HashMap<>();

		if (result.hasErrors()) { // verifica si hay errores en los campos de datos JSON
			List<String> errores = new ArrayList<>();
			result.getFieldErrors().forEach((err) -> {
				errores.add("El campo '" + err.getField() + "' " + err.getDefaultMessage());
			});
			map.put("errores", errores);
			return new ResponseEntity<>(map, HttpStatus.BAD_REQUEST);
		}

		if (servicioNuevo == null) { // comprueba si existe el servicio con el id ingresado
			map.put("mensaje", "Error: no se pudo editar, el servicio ID: " + id + " no existe en la base de datos");
			return new ResponseEntity<>(map, HttpStatus.NOT_FOUND);
		}

		try {
			servicioNuevo.setNombre(servicios.getNombre());
			servicioNuevo.setDescripcion(servicios.getDescripcion());
			servicioService.update(servicioNuevo);
		} catch (DataAccessException e) {
			String[] mensajeString = e.getMostSpecificCause().getMessage().replaceAll("[()]", "").split("\n");
			map.put("mensaje", "Error al actualizar el servicio en la base de datos");
			map.put("error", mensajeString[0].replaceAll("ERROR: ", ""));
			map.put("detalle", mensajeString[1].replaceAll(" Detail: ", "").replaceAll("=", ": "));
			return new ResponseEntity<>(map, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		map.put("mensaje", "El servicio " + servicioNuevo.getNombre() + " ha sido actualizado con éxito");
		return new ResponseEntity<>(map, HttpStatus.NO_CONTENT);
	}

	@PreAuthorize("hasAuthority('Eliminar Servicios')")
	@DeleteMapping(path = "/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteServicio(@PathVariable("id") String id) {
		servicioService.delete(Integer.valueOf(id)); // Elimina el servicio de acuerdo al ID
	}

}
