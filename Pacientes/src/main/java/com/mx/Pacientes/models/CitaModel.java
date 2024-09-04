package com.mx.Pacientes.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CitaModel {
	private int folio;
	private String padecimiento;
	private double peso;
	private int numeroConsultorio;
	private String curpPaciente;
	private int numeroClinica;
}
