/*
    Servicio Rest de Empresa para realizar CRUD con sus respectivos metodos HTTP
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

import com.sgse.entities.Empresa;
import com.sgse.resources.HostPermitido;
import com.sgse.service.EmpresaService;

import jakarta.validation.Valid;

/**
 *
 * @author Juan Carlos Argüello Ortiz
 * @version 1.0
 */
@RestController
@CrossOrigin(origins = { HostPermitido.HOST_DEV })
@RequestMapping(path = "/api/empresas")
public class EmpresaRestController {

	@Autowired
	private EmpresaService empresaService;

	@PreAuthorize("hasAuthority('Registrar empresa')")
	@PostMapping(consumes = "application/json")
	public ResponseEntity<?> crearEmpresa(@Valid @RequestBody Empresa empresa, BindingResult result) {
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
			empresaService.create(empresa); // crea la empresa
		} catch (DataAccessException e) { // Envia una excepcion con el error de insert en la BBDD
			map.put("mensaje", "Error al realizar insert en la base de datos");
			map.put("error", e.getMessage() + ": " + e.getMostSpecificCause().getMessage());
			return new ResponseEntity<>(map, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		map.put("mensaje", "La empresa " + empresa.getNombreEmpresa() + " ha sido creado con éxito");
		return new ResponseEntity<>(map, HttpStatus.CREATED);
	}

	@PreAuthorize("hasAuthority('Listar empresa')")
	@GetMapping(produces = "application/json")
	@ResponseStatus(HttpStatus.OK)
	public List<Empresa> getEmpresas() {
		return empresaService.findAll();
	}

	@PreAuthorize("hasAuthority('Listar empresa')")
	@GetMapping(path = "/{id}", produces = "application/json")
	public ResponseEntity<?> getEmpresaById(@PathVariable("id") String id) {
		Empresa empresa = null;
		Map<String, Object> map = new HashMap<>();
		try {
			empresa = empresaService.findById(Integer.valueOf(id));
		} catch (DataAccessException e) {
			map.put("mensaje", "Error al realizar la consulta en la base de datos");
			map.put("error", e.getMessage() + ": " + e.getMostSpecificCause().getMessage());
			return new ResponseEntity<>(map, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		if (empresa == null) { // Si no existe la empresa con el id ingresado retorna 404
			map.put("mensaje", "La Empresa ID: " + id + " no existe en la base de datos");
			return new ResponseEntity<>(map, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(empresa, HttpStatus.OK);
	}

	@PreAuthorize("hasAuthority('Actualizar datos de empresa')")
	@PutMapping(path = "/{id}", consumes = "application/json")
	public ResponseEntity<?> updateEmpresa(@Valid @RequestBody Empresa empresa, BindingResult result,
			@PathVariable("id") String id) {
		Empresa empresaNueva = empresaService.findById(Integer.valueOf(id));
		Map<String, Object> map = new HashMap<>();

		if (result.hasErrors()) { // verifica si hay errores en los campos de datos JSON
			List<String> errores = new ArrayList<>();
			result.getFieldErrors().forEach((err) -> {
				errores.add("El campo '" + err.getField() + "' " + err.getDefaultMessage());
			});
			map.put("errores", errores);
			return new ResponseEntity<>(map, HttpStatus.BAD_REQUEST);
		}

		if (empresaNueva == null) { // comprueba si existe la empresa con el id ingresado
			map.put("mensaje", "Error: no se pudo editar, la empresa ID: " + id + " no existe en la base de datos");
			return new ResponseEntity<>(map, HttpStatus.NOT_FOUND);
		}

		try {
			empresaNueva.setRuc(empresa.getRuc());
			empresaNueva.setNombreEmpresa(empresa.getNombreEmpresa());
			empresaNueva.setNombreMarca(empresa.getNombreMarca());
			empresaNueva.setEmail(empresa.getEmail());
			empresaNueva.setRubro(empresa.getRubro());
			empresaService.update(empresaNueva);
		} catch (DataAccessException e) {
			map.put("mensaje", "Error al actualizar la empresa en la base de datos");
			map.put("error", e.getMessage() + ": " + e.getMostSpecificCause().getMessage());
			return new ResponseEntity<>(map, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		map.put("mensaje", "La Empresa " + empresaNueva.getNombreEmpresa() + " ha sido actualizado con éxito");
		return new ResponseEntity<>(map, HttpStatus.NO_CONTENT);
	}

	@PreAuthorize("hasAuthority('Eliminar empres')")
	@DeleteMapping(path = "/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathVariable("id") String id) {
		empresaService.delete(Integer.valueOf(id)); // Elimina la empresa de acuerdo al ID
	}

}
