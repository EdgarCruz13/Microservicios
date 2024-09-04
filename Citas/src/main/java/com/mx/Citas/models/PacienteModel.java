package com.mx.Citas.models;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PacienteModel {
	private String curp;
	private String nombre;
	private String apellido;
	private Date fechaNacimiento;
	private String genero;
	private String ciudad;
	private int numeroClinica;
}
