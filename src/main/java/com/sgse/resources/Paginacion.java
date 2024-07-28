package com.sgse.resources;

import java.util.List;

public class Paginacion<E> {
	private int actualNumeroPagina;
	private int ultimoNumeroPagina;
	private int tamanhoPagina;
	private Long totalRegistros;
	private List<E> registros;

	public int getActualNumeroPagina() {
		return actualNumeroPagina;
	}

	public void setActualNumeroPagina(int actualNumeroPagina) {
		this.actualNumeroPagina = actualNumeroPagina;
	}

	public int getUltimoNumeroPagina() {
		return ultimoNumeroPagina;
	}

	public void setUltimoNumeroPagina(int ultimoNumeroPagina) {
		this.ultimoNumeroPagina = ultimoNumeroPagina;
	}

	public int getTamanhoPagina() {
		return tamanhoPagina;
	}

	public void setTamanhoPagina(int tamanhoPagina) {
		this.tamanhoPagina = tamanhoPagina;
	}

	public Long getTotalRegistros() {
		return totalRegistros;
	}

	public void setTotalRegistros(Long totalRegistros) {
		this.totalRegistros = totalRegistros;
	}

	public List<E> getRegistros() {
		return registros;
	}

	public void setRegistros(List<E> registros) {
		this.registros = registros;
	}

}
