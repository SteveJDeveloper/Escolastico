package com.velasco.ejercicio.models.reporting;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.List;

public class RptMatriculadosMateria implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private String materia;
	private List<UsuarioConteo> usuarios;
	
	public RptMatriculadosMateria(String materia, List<UsuarioConteo> usuarios) {
		super();
		this.materia = materia;
		this.usuarios = usuarios;
	}
	
	public RptMatriculadosMateria() {
		super();
	}

	public String getMateria() {
		return materia;
	}

	public void setMateria(String materia) {
		this.materia = materia;
	}

	public List<UsuarioConteo> getUsuarios() {
		return usuarios;
	}

	public void setUsuarios(List<UsuarioConteo> usuarios) {
		this.usuarios = usuarios;
	}
	
}
