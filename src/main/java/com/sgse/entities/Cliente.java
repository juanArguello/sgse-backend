/*
  Clase entidad Cliente correspondiente a la tabla relacional cliente
  con sus respectivo atributos, getters y setters.
 */
package com.sgse.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;

/**
 *
 * @author Juan Carlos Argüello Ortiz
 * @version 1.0
 */
@Entity
@Table(name = "cliente")
public class Cliente implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private Integer id;

	@Column(name = "cedula", unique = true)
	private Integer cedula;

	@Column(name = "ruc", unique = true)
	private String ruc;

	@NotEmpty(message = "no puede estar vacio")
	@Column(name = "nombre", nullable = false)
	private String nombre;

	@NotEmpty(message = "no puede estar vacio")
	@Column(name = "apellido", nullable = false)
	private String apellido;

	@Column(name = "direccion")
	private String direccion;

	@Column(name = "telefono")
	private String telefono;

	@NotEmpty(message = "no puede estar vacio")
	@Email(message = "No es un correo electronico válido")
	@Column(name = "email", unique = true, nullable = false)
	private String email;

	@Column(name = "estado_cuenta")
	private Integer estadoCuenta;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "cliente")
	private List<Compra> compraList;

	@JoinColumn(name = "id_salon", referencedColumnName = "id")
	@ManyToOne
	private Salon idSalon;

	public Cliente() {
		this.compraList = new ArrayList<>();
	}

	public Cliente(Integer id) {
		this.id = id;
	}

	public Cliente(Integer id, String nombre, String apellido) {
		this.id = id;
		this.nombre = nombre;
		this.apellido = apellido;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getCedula() {
		return cedula;
	}

	public void setCedula(Integer cedula) {
		this.cedula = cedula;
	}

	public String getRuc() {
		return ruc;
	}

	public void setRuc(String ruc) {
		this.ruc = ruc;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellido() {
		return apellido;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Integer getEstadoCuenta() {
		return estadoCuenta;
	}

	public void setEstadoCuenta(Integer estadoCuenta) {
		this.estadoCuenta = estadoCuenta;
	}

	public List<Compra> getCompraList() {
		return Collections.unmodifiableList(compraList);
	}

	public void setCompraList(List<Compra> compraList) {
		this.compraList = compraList;
	}

	public Salon getIdSalon() {
		return idSalon;
	}

	public void setIdSalon(Salon idSalon) {
		this.idSalon = idSalon;
	}

}
