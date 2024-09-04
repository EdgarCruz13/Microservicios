package com.mx.Citas.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.mx.Citas.dao.CitaDao;
import com.mx.Citas.entidad.Cita;
import com.mx.Citas.models.PacienteModel;
import com.mx.Citas.respuesta.Respuesta;

@Service
public class CitaService {
	@Autowired
	CitaDao dao;

	@Autowired
	RestTemplate restTemplate;

	public Respuesta mostrar() {
		Respuesta rs = new Respuesta();
		if (dao.findAll().isEmpty()) {
			rs.setMensaje("No hay citas disponibles en la base de datos");
			rs.setSuccess(false);
			rs.setObj(null);
		} else {
			rs.setMensaje("Existen estas citas en la base de datos");
			rs.setSuccess(true);
			rs.setObj(dao.findAll());
		}
		return rs;
	}

	public Respuesta guardar(Cita cita) {
		Respuesta rs = new Respuesta();
		PacienteModel paciente = restTemplate.getForObject(
				"http://localhost:9002/pacientes/buscar/"+cita.getCurpPaciente(), PacienteModel.class);
		if(paciente == null) {
			rs.setMensaje("El paciente que tratas de asignar a la cita no existe");
			rs.setSuccess(false);
			rs.setObj(cita.getCurpPaciente());
			return rs;
		}
		if (dao.findAll().isEmpty()) {
			dao.save(cita);
			rs.setMensaje("La cita ha sido registrada");
			rs.setSuccess(true);
			rs.setObj(cita);
			return rs;

		}
		if (dao.existsById(cita.getFolio())) {
			rs.setMensaje("La cita no se registro porque el folio ya existe");
			rs.setSuccess(false);
			rs.setObj(cita.getFolio());
			return rs;
		}
		dao.save(cita);
		rs.setMensaje("La cita ha sido registrada");
		rs.setSuccess(true);
		rs.setObj(cita);
		return rs;
	}

	public Respuesta editar(Cita cita) {
		Respuesta rs = new Respuesta();
		if (dao.existsById(cita.getFolio())) {
			PacienteModel paciente = restTemplate.getForObject(
				"http://localhost:9002/pacientes/buscar/"+cita.getCurpPaciente(), PacienteModel.class);
			
			if(paciente == null) {
				rs.setMensaje("El paciente que tratas de cambiar no existe");
				rs.setSuccess(false);
				rs.setObj(cita.getCurpPaciente());
				return rs;
			}
			
			dao.save(cita);
			rs.setMensaje("La cita ha sido editada");
			rs.setSuccess(true);
			rs.setObj(cita);
			return rs;
		}
		rs.setMensaje("La cita que tratas de editar no existe");
		rs.setSuccess(false);
		rs.setObj(cita.getFolio());
		return rs;
	}

	public Respuesta eliminar(Cita cita) {
		Respuesta rs = new Respuesta();
		cita = dao.findById(cita.getFolio()).orElse(null);
		if (cita != null) {
			rs.setObj(new Cita(cita.getFolio(), cita.getPadecimiento(), cita.getPeso(), cita.getNumeroConsultorio(),
					cita.getCurpPaciente(), cita.getNumeroClinica()));
			dao.delete(cita);
			rs.setMensaje("La cita ha sido eliminada");
			rs.setSuccess(true);
			return rs;
		}
		rs.setMensaje("La cita que tratas de eliminar no existe");
		rs.setSuccess(false);
		rs.setObj(null);
		return rs;
	}

	public ResponseEntity<Cita> buscar(int folio) {
		Cita cita = dao.findById(folio).orElse(null);
		if (cita == null) {
			return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
		}
		return ResponseEntity.status(HttpStatus.OK).body(cita);
	}

	public ResponseEntity<List<Cita>> buscarPorCurp(String curp) {
		List<Cita> lista = dao.findByCurpPaciente(curp);
		if (lista == null || lista.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
		}
		return ResponseEntity.status(HttpStatus.OK).body(lista);
	}

	public ResponseEntity<List<Cita>> buscarPorNumeroClinica(int numeroClinica) {
		List<Cita> lista = dao.findByNumeroClinica(numeroClinica);
		if (lista == null || lista.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
		}
		return ResponseEntity.status(HttpStatus.OK).body(lista);
	}
}
