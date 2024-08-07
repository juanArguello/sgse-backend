/*
    Servicio Rest de Rol para realizar CRUD con sus respectivos metodos HTTP
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

import com.sgse.entities.Rol;
import com.sgse.resources.HostPermitido;
import com.sgse.service.RolService;

import jakarta.validation.Valid;

/**
 *
 * @author Juan Carlos Argüello Ortiz
 * @version 1.0
 */
@RestController
@CrossOrigin(origins = { HostPermitido.HOST_DEV })
@RequestMapping(path = "/api/roles")
//@PreAuthorize("hasRole('ADMINISTRADOR')")
public class RolRestController {

	@Autowired
	private RolService rolService;

	// @Secured("ROLE_ADMINISTRADOR")
	@PreAuthorize("hasAuthority('crear rol')")
	@PostMapping(consumes = "application/json")
	public ResponseEntity<?> crearRol(@Valid @RequestBody Rol rol, BindingResult result) {
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
			rolService.create(rol); // crea el rol
		} catch (DataAccessException e) { // Envia una excepcion con el error de insert en la BBDD
			map.put("mensaje", "Error al realizar insert en la base de datos");
			map.put("error", e.getMessage() + ": " + e.getMostSpecificCause().getMessage());
			return new ResponseEntity<>(map, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		map.put("mensaje", "El Rol " + rol.getNombre() + " ha sido creado con éxito");
		return new ResponseEntity<>(map, HttpStatus.CREATED);
	}

	// @Secured("ROLE_ADMINISTRADOR")
	@PreAuthorize("hasAuthority('listar rol')")
	@GetMapping(produces = "application/json")
	@ResponseStatus(HttpStatus.OK)
	public List<Rol> getRoles() {
		return rolService.findAll();
	}

	// @Secured("ROLE_ADMINISTRADOR")
	@PreAuthorize("hasAuthority('listar rol')")
	@GetMapping(path = "/{id}", produces = "application/json")
	public ResponseEntity<?> getRolById(@PathVariable("id") String id) {
		Rol rol = null;
		Map<String, Object> map = new HashMap<>();
		try {
			rol = rolService.findById(Integer.valueOf(id));
		} catch (DataAccessException e) {
			map.put("mensaje", "Error al realizar la consulta en la base de datos");
			map.put("error", e.getMessage() + ": " + e.getMostSpecificCause().getMessage());
			return new ResponseEntity<>(map, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		if (rol == null) { // Si no existe el rol con el id ingresado retorna 404
			map.put("mensaje", "El Rol ID: " + id + " no existe en la base de datos");
			return new ResponseEntity<>(map, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(rol, HttpStatus.OK);
	}

	// @Secured("ROLE_ADMINISTRADOR")
	@PreAuthorize("hasAuthority('modificar rol')")
	@PutMapping(path = "/{id}", consumes = "application/json")
	public ResponseEntity<?> updateRol(@Valid @RequestBody Rol rol, BindingResult result,
			@PathVariable("id") String id) {
		Rol rolNuevo = rolService.findById(Integer.valueOf(id));
		Map<String, Object> map = new HashMap<>();

		if (result.hasErrors()) { // verifica si hay errores en los campos de datos JSON
			List<String> errores = new ArrayList<>();
			result.getFieldErrors().forEach((err) -> {
				errores.add("El campo '" + err.getField() + "' " + err.getDefaultMessage());
			});
			map.put("errores", errores);
			return new ResponseEntity<>(map, HttpStatus.BAD_REQUEST);
		}

		if (rolNuevo == null) { // comprueba si existe el rol con el id ingresado
			map.put("mensaje", "Error: no se pudo editar, el rol ID: " + id + " no existe en la base de datos");
			return new ResponseEntity<>(map, HttpStatus.NOT_FOUND);
		}

		try {
			rolNuevo.setNombre(rol.getNombre());
			rolNuevo.setDescripcion(rol.getDescripcion());
			rolNuevo.setPermisosList(rol.getPermisosList());
			rolNuevo.setUsuarioList(rol.getUsuarioList());
			rolService.update(rolNuevo);
		} catch (DataAccessException e) {
			map.put("mensaje", "Error al actualizar el rol en la base de datos");
			map.put("error", e.getMessage() + ": " + e.getMostSpecificCause().getMessage());
			return new ResponseEntity<>(map, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		map.put("mensaje", "El rol " + rolNuevo.getNombre() + " ha sido actualizado con éxito");
		return new ResponseEntity<>(map, HttpStatus.NO_CONTENT);
	}

	// @Secured("ROLE_ADMINISTRADOR")
	@PreAuthorize("hasAuthority('eliminar rol')")
	@DeleteMapping(path = "/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteRol(@PathVariable("id") String id) {
		rolService.delete(Integer.valueOf(id)); // Elimina el rol de acuerdo al ID
	}

	@PreAuthorize("hasAuthority('listar rol')")
	@GetMapping(path = "/cantidad", produces = "text/plain")
	@ResponseStatus(HttpStatus.OK)
	public String cantidadRoles() {
		return String.valueOf(rolService.cantidadFilas());
	}

}
