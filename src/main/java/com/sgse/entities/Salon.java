/*
  Clase entidad Salon correspondiente a la tabla relacional salon
  con sus respectivo atributos, getters y setters.
 */
package com.sgse.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

/**
 *
 * @author Juan Carlos Argüello Ortiz
 * @version 1.0
 */
@Entity
@Table(name = "salon")
public class Salon implements Serializable {

	private static final long serialVersionUID = 1L;
	// @Max(value=?) @Min(value=?)//if you know range of your decimal fields
	// consider using these annotations to enforce field validation
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private Integer id;

	@Column(name = "localidad")
	private String localidad;

	@Column(name = "descripcion")
	private String descripcion;

	@Column(name = "fecha")
	@Temporal(TemporalType.DATE)
	private Date fecha;

	@Column(name = "horario")
	private String horario;

	@OneToMany(mappedBy = "idSalon")
	private List<Cliente> clienteList;

	public Salon() {
		this.clienteList = new ArrayList<>();
	}

	public Salon(Integer id) {
		this.id = id;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getLocalidad() {
		return localidad;
	}

	public void setLocalidad(String localidad) {
		this.localidad = localidad;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public String getHorario() {
		return horario;
	}

	public void setHorario(String horario) {
		this.horario = horario;
	}

	public List<Cliente> getClienteList() {
		return Collections.unmodifiableList(clienteList);
	}

	public void setClienteList(List<Cliente> clienteList) {
		this.clienteList = clienteList;
	}

}
