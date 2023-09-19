/*
  Clase entidad Servicios correspondiente a la tabla relacional servicios
  con sus respectivo atributos, getters y setters.
 */
package com.sgse.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;


/**
 *
 * @author Juan Carlos Argüello Ortiz
 * @version 1.0
 */
@Entity
@Table(name = "servicios")
public class Servicios implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Integer id;
    
    @NotEmpty(message = "no puede estar vacio")
    @Column(name = "nombre",unique = true)
    private String nombre;
    
    @NotEmpty(message = "no puede estar vacio")
    @Column(name = "descripcion")
    private String descripcion;
    
    @JsonProperty(access = JsonProperty.Access.AUTO)
    @JsonIgnoreProperties(value = {"serviciosList","hibernateLazyInitializer","handler"})
    @ManyToMany(mappedBy = "serviciosList",fetch = FetchType.LAZY,cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<Plan> planList;
    
    public Servicios() {
        this.planList = new ArrayList<>();
    }

    public Servicios(Integer id) {
        this.id = id;
    }

    public Servicios(Integer id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public List<Plan> getPlanList() {
        return Collections.unmodifiableList(planList);
    }

    public void setPlanList(List<Plan> planList) {
        this.planList = planList;
    }

    
}
