/*
  Clase entidad Compra correspondiente a la tabla relacional compra
  con sus respectivo atributos, getters y setters.
 */
package com.sgse.entities;

import java.io.Serializable;
import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

/**
 *
 * @author Juan Carlos Argüello Ortiz
 * @version 1.0
 */
@Entity
@Table(name = "compra")
public class Compra implements Serializable {

	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private CompraPK compraPK;

	@Column(name = "fecha")
	@Temporal(TemporalType.DATE)
	private Date fecha;

	@Column(name = "monto")
	private int monto;

	@JoinColumn(name = "id_cliente", referencedColumnName = "id", insertable = false, updatable = false)
	@ManyToOne(optional = false)
	private Cliente cliente;

	@JoinColumn(name = "id_seguro", referencedColumnName = "id", insertable = false, updatable = false)
	@ManyToOne(optional = false)
	private Seguro seguro;

	public Compra() {
	}

	public Compra(CompraPK compraPK) {
		this.compraPK = compraPK;
	}

	public Compra(CompraPK compraPK, Date fecha, int monto) {
		this.compraPK = compraPK;
		this.fecha = fecha;
		this.monto = monto;
	}

	public Compra(int idCliente, int idSeguro) {
		this.compraPK = new CompraPK(idCliente, idSeguro);
	}

	public CompraPK getCompraPK() {
		return compraPK;
	}

	public void setCompraPK(CompraPK compraPK) {
		this.compraPK = compraPK;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public int getMonto() {
		return monto;
	}

	public void setMonto(int monto) {
		this.monto = monto;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public Seguro getSeguro() {
		return seguro;
	}

	public void setSeguro(Seguro seguro) {
		this.seguro = seguro;
	}

}
