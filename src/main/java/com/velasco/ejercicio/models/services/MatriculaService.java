package com.velasco.ejercicio.models.services;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.ParameterMode;
import javax.persistence.PersistenceContext;
import javax.persistence.StoredProcedureQuery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.velasco.ejercicio.models.dao.IMatricula;
import com.velasco.ejercicio.models.entities.Materia;
import com.velasco.ejercicio.models.entities.Matricula;
import com.velasco.ejercicio.models.reporting.RptEstudiantesMateria;
import com.velasco.ejercicio.models.reporting.RptMatriculadosMateria;
import com.velasco.ejercicio.models.reporting.RptRendimientoMateria;
import com.velasco.ejercicio.models.reporting.UsuarioConteo;

@Service
public class MatriculaService implements IMatriculaService {

	@Autowired //Inyección de dependencia
	private IMatricula dao;
	
	@Autowired 
	private IMateriaService srvMateria;
	
	@PersistenceContext
	private EntityManager em; //Es la instancia de persistencia con la BDD
			
	@Override
	@Transactional
	public void save(Matricula a) {
		float costo = 0;
		a.setFecha(Calendar.getInstance());
		switch(a.getTipo()) {
			case "S":
				costo = a.getCurso().getCreditos() * 12.50f;
				break;
			case "T":
				costo = a.getCurso().getCreditos() * 22.25f;
				break;		
		}
		a.setCosto(costo);		
		dao.save(a);
	}

	@Override
	@Transactional
	public Matricula findById(Integer id) {		
		return dao.findById(id).get();
	}

	@Override
	@Transactional
	public void delete(Integer id) {
		dao.deleteById(id);				
	}

	@Override
	@Transactional
	public List<Matricula> findAll() {		
		return (List<Matricula>) dao.findAll();
	}
	
	@Override
	@Transactional
	public List<Matricula> findByAlumno(Integer id) {
		try {
			List<Matricula> resultado = dao.findByEstudiante(id);
			return resultado;
		}
		catch(Exception ex) {
			System.out.println("Error =>" + ex.getMessage());
			return null;
		}
		
	}
	
	@Override
	public List<RptEstudiantesMateria> rptMatriculadosMateria() {		
		StoredProcedureQuery query = em.createStoredProcedureQuery("matriculados_en_materias");
		query.execute();
		List<Object[]> datos = query.getResultList();		
		return datos.stream()
				.map(r -> new RptEstudiantesMateria((BigInteger)r[0], (String)r[1], (BigInteger)r[2]))
				.collect(Collectors.toList());		
	}
	
	@Override
	public List<RptMatriculadosMateria> rptConteoMatriculasMateria(){
		List<RptMatriculadosMateria> lista = new ArrayList<RptMatriculadosMateria>();
		List<Materia> materias = srvMateria.findAll();
		for(Materia m : materias) {
			StoredProcedureQuery query = em.createStoredProcedureQuery("usuario_matriculas");
			query.registerStoredProcedureParameter("materia", Integer.class, ParameterMode.IN);
			query.setParameter("materia", m.getIdmateria());		
			query.execute();
			List<Object[]> us = query.getResultList();
			if(!us.isEmpty()) {
				List<UsuarioConteo> usuCon = us.stream()
						.map(r -> new UsuarioConteo((String)r[1], (BigInteger)r[2]))
						.collect(Collectors.toList());
				lista.add(new RptMatriculadosMateria(m.getNombre(),usuCon));
			}
		}
		return lista;
	}
	
	@Override
	public RptRendimientoMateria rptRendimientoMateria(Integer pr_materia) {

		StoredProcedureQuery query = em.createStoredProcedureQuery("aprobados_en_materias");
		query.registerStoredProcedureParameter("pr_materia", Integer.class, ParameterMode.IN);
		query.setParameter("pr_materia", pr_materia);		
		query.execute();
		List<Object[]> aprobados = query.getResultList();
				
		StoredProcedureQuery query1 = em.createStoredProcedureQuery("reprobados_en_materia");
		query1.registerStoredProcedureParameter("pr_materia", Integer.class, ParameterMode.IN);
		query1.setParameter("pr_materia", pr_materia);
		query1.execute();
		List<Object[]> reprobados = query1.getResultList();
		
		RptRendimientoMateria result = new RptRendimientoMateria();
		try {
			result.setId((Integer)aprobados.get(0)[0]);
			result.setNombre((String)aprobados.get(0)[1]);
			result.setAprobados((BigInteger)aprobados.get(0)[2]);
			result.setReprobados((BigInteger)reprobados.get(0)[2]);
		}
		catch(Exception ex) {
			System.out.println(ex.getMessage());
			throw ex;
		}
		
		return result;
	}

	@Override
	public List<Matricula> findByMateria(Integer id) {
		
		return dao.findByMateria(id);
	}	
	
	
	
}
