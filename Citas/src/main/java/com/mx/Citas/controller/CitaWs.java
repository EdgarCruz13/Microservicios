package com.mx.Citas.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mx.Citas.entidad.Cita;
import com.mx.Citas.respuesta.Respuesta;
import com.mx.Citas.service.CitaService;

@RestController
@RequestMapping("citas")
@CrossOrigin
public class CitaWs {
	@Autowired
	CitaService service;

	@GetMapping("listar")
	public Respuesta mostrar() {
		return service.mostrar();
	}

	@PostMapping("guardar")
	public Respuesta guardar(@RequestBody Cita cita) {
		return service.guardar(cita);
	}

	@PostMapping("editar")
	public Respuesta editar(@RequestBody Cita cita) {
		return service.editar(cita);
	}

	@PostMapping("eliminar")
	public Respuesta eliminar(@RequestBody Cita cita) {
		return service.eliminar(cita);
	}

	@GetMapping("buscar/{folio}")
	public ResponseEntity<Cita> buscar(@PathVariable("folio") int folio) {
		return service.buscar(folio);
	}

	@GetMapping("buscar/{curp}")
	public ResponseEntity<List<Cita>> buscarPorCurp(@PathVariable("curp") String curp) {
		return service.buscarPorCurp(curp);
	}

	@GetMapping("buscarPorClinica/{numeroClinica}")
	public ResponseEntity<List<Cita>> buscarPorClinica(@PathVariable("numeroClinica") int numeroClinica) {
		return service.buscarPorNumeroClinica(numeroClinica);
	}
}
