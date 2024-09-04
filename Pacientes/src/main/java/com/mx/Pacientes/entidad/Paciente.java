package com.mx.Pacientes.entidad;

import java.sql.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "PACIENTES2")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Paciente {
	@Id
	private String curp;
	private String nombre;
	private String apellido;
	@Column(name = "FECHA_NACIMIENTO")
	private Date fechaNacimiento;
	private String genero;
	private String ciudad;
	@Column(name = "NUMERO_CLINICA")
	private int numeroClinica;

}
