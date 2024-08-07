/*
    Definicion de la interfaz PlanService
 */
package com.sgse.service;

import java.util.List;

import com.sgse.entities.Plan;

/**
 *
 * @author Juan Carlos Argüello Ortiz
 * @version 1.0
 */
public interface PlanService {
	// Definicion de los metodos CRUD de la interfaz PlanService
	public void create(Plan plan);

	public Plan findById(int id);

	public List<Plan> findAll();

	public void update(Plan plan);

	public void delete(int id);
}
