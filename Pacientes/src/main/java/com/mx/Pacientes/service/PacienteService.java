package com.mx.Pacientes.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.mx.Pacientes.dao.PacienteDao;
import com.mx.Pacientes.entidad.Paciente;
import com.mx.Pacientes.models.CitaModel;
import com.mx.Pacientes.models.ClinicaModel;
import com.mx.Pacientes.respuesta.Respuesta;

@Service
public class PacienteService {
	@Autowired
	PacienteDao dao;

	@Autowired
	RestTemplate restTemplate;

	public Respuesta mostrar() {
		Respuesta rs = new Respuesta();
		if (dao.findAll().isEmpty()) {
			rs.setMensaje("No hay pacientes en la lista");
			rs.setSuccess(false);
			rs.setObj(null);
		} else {
			rs.setMensaje("Se han encontrado los siguientes pacientes");
			rs.setSuccess(true);
			rs.setObj(dao.findAll());
		}
		return rs;
	}

	public Respuesta guardar(Paciente paciente) {
		Respuesta rs = new Respuesta();
		ClinicaModel clinica = restTemplate("http://localhost:9003/clinicas/buscar/" + paciente.getNumeroClinica(),
				ClinicaModel.class);
		if (clinica == null) {
			rs.setMensaje("La clinica que le das al paciente no existe");
			rs.setSuccess(false);
			rs.setObj(paciente.getNumeroClinica());
			return rs;
		}

		if (dao.findAll().isEmpty()) {
			dao.save(paciente);
			rs.setMensaje("El paciente ha sido registrado");
			rs.setSuccess(true);
			rs.setObj(paciente);
			return rs;
		}
		if (dao.existsById(paciente.getCurp())) {
			rs.setMensaje("El paciente no se registro porque su curp ya existe");
			rs.setSuccess(false);
			rs.setObj(paciente.getCurp());
			return rs;
		}
		dao.save(paciente);
		rs.setMensaje("El paciente ha sido registrado");
		rs.setSuccess(true);
		rs.setObj(paciente);
		return rs;
	}

	private ClinicaModel restTemplate(String string, Class<ClinicaModel> class1) {
		// TODO Auto-generated method stub
		return null;
	}

	public Respuesta editar(Paciente paciente) {
		Respuesta rs = new Respuesta();
		
		if (dao.existsById(paciente.getCurp())) {
			ClinicaModel clinica = restTemplate("http://localhost:9003/clinicas/buscar/" + paciente.getNumeroClinica(),
					ClinicaModel.class);
			if (clinica == null) {
				rs.setMensaje("La clinica que le das al paciente no existe");
				rs.setSuccess(false);
				rs.setObj(paciente.getNumeroClinica());
				return rs;
			
		}
			dao.save(paciente);
			rs.setMensaje("El paciente ha sido editado");
			rs.setSuccess(true);
			rs.setObj(paciente);
			return rs;
		}
		rs.setMensaje("El paciente que tratas de editar no existe");
		rs.setSuccess(false);
		rs.setObj(paciente.getCurp());
		return rs;
	}

	public Respuesta eliminar(Paciente paciente) {
		Respuesta rs = new Respuesta();
		if (dao.existsById(paciente.getCurp())) {
			paciente = dao.findById(paciente.getCurp()).orElse(null);
			List<CitaModel> citas = restTemplate
					.getForObject("http://localhost:9001/citass/buscarPorCurp" + paciente.getCurp(), List.class);
			if (citas.isEmpty() || citas == null) {
				rs.setObj(new Paciente(paciente.getCurp(), paciente.getNombre(), paciente.getApellido(),
						paciente.getFechaNacimiento(), paciente.getGenero(), paciente.getCiudad(),
						paciente.getNumeroClinica()));
				dao.delete(paciente);
				rs.setMensaje("El paciente ha sido eliminado");
				rs.setSuccess(true);
				return rs;
			}
			rs.setMensaje("El paciente no se elimino porque aun tiene citas asignadas");
			rs.setSuccess(false);
			rs.setObj(citas);
			return rs;
		}
		rs.setMensaje("El paciente que tratas de eliminar no existe");
		rs.setSuccess(false);
		rs.setObj(paciente.getCurp());
		return rs;
	}

	public ResponseEntity<Paciente> buscar(String curp) {
		Paciente paciente = dao.findById(curp).orElse(null);
		if (paciente == null) {
			return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
		}
		return ResponseEntity.status(HttpStatus.OK).body(paciente);
	}

	public ResponseEntity<List<Paciente>> buscarPorClinica(int numeroClinica) {
		List<Paciente> pacientes = dao.findByNumeroClinica(numeroClinica);
		if (pacientes == null || pacientes.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
		}
		return ResponseEntity.status(HttpStatus.OK).body(pacientes);
	}
}
