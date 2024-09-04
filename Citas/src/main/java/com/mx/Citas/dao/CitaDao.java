package com.mx.Citas.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mx.Citas.entidad.Cita;

public interface CitaDao extends JpaRepository<Cita, Integer> {
	List<Cita>findByCurpPaciente(String curpPaciente);
	
	List<Cita> findByNumeroClinica(int numeroClinica);

}
