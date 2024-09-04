package com.mx.ControlClinicas.entidad;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "CLINICAS")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Clinica {
	@Id
	private int numero;
	@Column(name = "NOMBRE_CLINICA")
	private String nombreClinica;
	private String ciudad;
}
