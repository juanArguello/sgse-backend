/*
    Servicio Rest de Plan para realizar CRUD con sus respectivos metodos HTTP
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

import com.sgse.entities.Plan;
import com.sgse.resources.HostPermitido;
import com.sgse.service.PlanService;

import jakarta.validation.Valid;

/**
 *
 * @author Juan Carlos Argüello Ortiz
 * @version 1.0
 */
@RestController
@CrossOrigin(origins = { HostPermitido.HOST_DEV })
@RequestMapping(path = "/api/planes")
public class PlanRestController {

	@Autowired
	private PlanService planService;

	@PreAuthorize("hasAuthority('Crear Plan de seguro')")
	@PostMapping(consumes = "application/json")
	public ResponseEntity<?> crearPlan(@Valid @RequestBody Plan plan, BindingResult result) {
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
			planService.create(plan); // crea el plan
		} catch (DataAccessException e) { // Envia una excepcion con el error de insert en la BBDD
			map.put("mensaje", "Error al realizar insert en la base de datos");
			map.put("error", e.getMessage() + ": " + e.getMostSpecificCause().getMessage());
			return new ResponseEntity<>(map, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		map.put("mensaje", "El Plan ha sido creado con éxito");
		return new ResponseEntity<>(map, HttpStatus.CREATED);
	}

	@PreAuthorize("hasAuthority('Listar Plan de seguro')")
	@GetMapping(produces = "application/json")
	@ResponseStatus(HttpStatus.OK)
	public List<Plan> getPlanes() {
		return planService.findAll();
	}

	@PreAuthorize("hasAuthority('Listar Plan de seguro')")
	@GetMapping(path = "/{id}", produces = "application/json")
	public ResponseEntity<?> getPlanById(@PathVariable("id") String id) {
		Plan plan = null;
		Map<String, Object> map = new HashMap<>();
		try {
			plan = planService.findById(Integer.valueOf(id));
		} catch (DataAccessException e) {
			map.put("mensaje", "Error al realizar la consulta en la base de datos");
			map.put("error", e.getMessage() + ": " + e.getMostSpecificCause().getMessage());
			return new ResponseEntity<>(map, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		if (plan == null) { // Si no existe el plan con el id ingresado retorna 404
			map.put("mensaje", "El Plan ID: " + id + " no existe en la base de datos");
			return new ResponseEntity<>(map, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(plan, HttpStatus.OK);
	}

	@PreAuthorize("hasAuthority('Modificar Plan de seguro')")
	@PutMapping(path = "/{id}", consumes = "application/json")
	public ResponseEntity<?> updatePlan(@Valid @RequestBody Plan plan, BindingResult result,
			@PathVariable("id") String id) {
		Plan planNuevo = planService.findById(Integer.valueOf(id));
		Map<String, Object> map = new HashMap<>();

		if (result.hasErrors()) { // verifica si hay errores en los campos de datos JSON
			List<String> errores = new ArrayList<>();
			result.getFieldErrors().forEach((err) -> {
				errores.add("El campo '" + err.getField() + "' " + err.getDefaultMessage());
			});
			map.put("errores", errores);
			return new ResponseEntity<>(map, HttpStatus.BAD_REQUEST);
		}

		if (planNuevo == null) { // comprueba si existe el plan con el id ingresado
			map.put("mensaje", "Error: no se pudo editar, el Plan ID: " + id + " no existe en la base de datos");
			return new ResponseEntity<>(map, HttpStatus.NOT_FOUND);
		}

		try {
			planNuevo.setNombre(plan.getNombre());
			planNuevo.setDescripcion(plan.getDescripcion());
			planService.update(planNuevo);
		} catch (DataAccessException e) {
			map.put("mensaje", "Error al actualizar el Plan en la base de datos");
			map.put("error", e.getMessage() + ": " + e.getMostSpecificCause().getMessage());
			return new ResponseEntity<>(map, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		map.put("mensaje", "El Plan ha sido actualizado con éxito");
		return new ResponseEntity<>(map, HttpStatus.NO_CONTENT);
	}

	@PreAuthorize("hasAuthority('Eliminar Plan de seguro')")
	@DeleteMapping(path = "/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deletePlan(@PathVariable("id") String id) {
		planService.delete(Integer.valueOf(id)); // Elimina el plan de acuerdo al ID
	}

}
