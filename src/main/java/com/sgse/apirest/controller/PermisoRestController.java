/*
    Servicio Rest de Permisos para realizar CRUD con sus respectivos metodos HTTP
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

import com.sgse.entities.Permisos;
import com.sgse.resources.Paginacion;
import com.sgse.service.PermisoService;

import jakarta.validation.Valid;

/**
 *
 * @author Juan Carlos Argüello Ortiz
 * @version 1.0
 */
@RestController
@CrossOrigin
@RequestMapping(path = "/api/permisos")
public class PermisoRestController {

	@Autowired
	private PermisoService permisoService;

	// @Secured("ROLE_ADMINISTRADOR")
	@PreAuthorize("hasAuthority('crear permiso')")
	@PostMapping(consumes = "application/json")
	public ResponseEntity<?> crearPermisos(@Valid @RequestBody Permisos permisos, BindingResult result) {
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
			permisoService.create(permisos); // crea el permiso
		} catch (DataAccessException e) { // Envia una excepcion con el error de insert en la BBDD
			String[] mensajeString = e.getMostSpecificCause().getMessage().replaceAll("[()]", "").split("\n");
			map.put("mensaje", "Error al realizar inserción en la base de datos");
			map.put("error", mensajeString[0].replaceAll("ERROR: ", ""));
			map.put("detalle", mensajeString[1].replaceAll(" Detail: ", ""));
			return new ResponseEntity<>(map, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		map.put("mensaje", "El permiso " + permisos.getNombre() + " ha sido creado con éxito");
		return new ResponseEntity<>(map, HttpStatus.CREATED);
	}

	// @Secured("ROLE_ADMINISTRADOR")
	@PreAuthorize("hasAuthority('listar permiso')")
	@GetMapping(produces = "application/json")
	@ResponseStatus(HttpStatus.OK)
	public List<Permisos> getPermisos() {
		return permisoService.findAll();
	}

	@PreAuthorize("hasAuthority('listar permiso')")
	@GetMapping(path = "/page/{page}", produces = "application/json")
	public Paginacion<Permisos> getPermisosPaginado(@PathVariable("page") Integer page) {
		int tamanhoPagina = 8;
		return permisoService.getPermisosPaginado(page, tamanhoPagina);
	}

	// @Secured("ROLE_ADMINISTRADOR")
	@PreAuthorize("hasAuthority('listar permiso')")
	@GetMapping(path = "/{id}", produces = "application/json")
	public ResponseEntity<?> getPermisoById(@PathVariable("id") String id) {
		Permisos permisos = null;
		Map<String, Object> map = new HashMap<>();
		try {
			permisos = permisoService.findById(Integer.valueOf(id));
		} catch (DataAccessException e) {
			map.put("mensaje", "Error al realizar la consulta en la base de datos");
			map.put("error", e.getMessage() + ": " + e.getMostSpecificCause().getMessage());
			return new ResponseEntity<>(map, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		if (permisos == null) { // Si no existe el permiso con el id ingresado retorna 404
			map.put("mensaje", "El Permiso ID: " + id + " no existe en la base de datos");
			return new ResponseEntity<>(map, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Permisos>(permisos, HttpStatus.OK);
	}

	// @Secured("ROLE_ADMINISTRADOR")
	@PreAuthorize("hasAuthority('modificar permiso')")
	@PutMapping(path = "/{id}", consumes = "application/json")
	public ResponseEntity<?> updatePermiso(@Valid @RequestBody Permisos permisos, BindingResult result,
			@PathVariable("id") String id) {
		Permisos permisoNuevo = permisoService.findById(Integer.valueOf(id));
		Map<String, Object> map = new HashMap<>();

		if (result.hasErrors()) { // verifica si hay errores en los campos de datos JSON
			List<String> errores = new ArrayList<>();
			result.getFieldErrors().forEach((err) -> {
				errores.add("El campo '" + err.getField() + "' " + err.getDefaultMessage());
			});
			map.put("errores", errores);
			return new ResponseEntity<>(map, HttpStatus.BAD_REQUEST);
		}

		if (permisoNuevo == null) { // comprueba si existe el permiso con el id ingresado
			map.put("mensaje", "Error: no se pudo editar, el permiso ID: " + id + " no existe en la base de datos");
			return new ResponseEntity<>(map, HttpStatus.NOT_FOUND);
		}

		try {
			permisoNuevo.setNombre(permisos.getNombre());
			permisoNuevo.setDescripcion(permisos.getDescripcion());
			permisoNuevo.setRolList(permisos.getRolList());
			permisoService.update(permisoNuevo);
		} catch (DataAccessException e) {
			map.put("mensaje", "Error al actualizar el permiso en la base de datos");
			map.put("error", e.getMessage() + ": " + e.getMostSpecificCause().getMessage());
			return new ResponseEntity<>(map, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		map.put("mensaje", "El Permiso " + permisoNuevo.getNombre() + " ha sido actualizado con éxito");
		return new ResponseEntity<>(map, HttpStatus.NO_CONTENT);
	}

	@PreAuthorize("hasAuthority('eliminar permiso')")
	// @Secured("ROLE_ADMINISTRADOR")
	@DeleteMapping(path = "/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deletePermiso(@PathVariable("id") String id) {
		permisoService.delete(Integer.valueOf(id)); // Elimina el permiso de acuerdo al ID
	}

	@PreAuthorize("hasAuthority('listar permiso')")
	@GetMapping(path = "/cantidad", produces = "text/plain")
	@ResponseStatus(HttpStatus.OK)
	public String cantidadPermisos() {
		return String.valueOf(permisoService.cantidadFilas());
	}

}
