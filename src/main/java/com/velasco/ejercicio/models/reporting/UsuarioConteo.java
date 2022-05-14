package com.velasco.ejercicio.models.reporting;

import java.io.Serializable;
import java.math.BigInteger;

public class UsuarioConteo implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private String usuario;
	
	private BigInteger cant;

	public UsuarioConteo(String usuario, BigInteger cant) {
		super();
		this.usuario = usuario;
		this.cant = cant;
	}
	
	public UsuarioConteo() {
		super();
	}
	
	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public BigInteger getCant() {
		return cant;
	}

	public void setCant(BigInteger cant) {
		this.cant = cant;
	}
	
	
}
