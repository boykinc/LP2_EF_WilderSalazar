package com.example.demo.entity;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString	
@Table(name="tb_usuario")
public class UsuarioEntity {

	@Id
	@Column(name = "correo" ,
			nullable = false ,
			length = 100,
			columnDefinition = "VARCHAR(100)",
			unique = true
	)
	private String correoUsu;
	
	@Column(name = "contrasena" ,
			nullable = false 
			)
	private String contraUsu;
	
	@Column(name = "nombre" ,
			nullable = false ,
			length = 100,
			columnDefinition = "VARCHAR(100)"
			)
	private String nomUsu;
	
	
	@Column(name = "apellido" ,
			nullable = false ,
			length = 100,
			columnDefinition = "VARCHAR(100)"
			)
	private String apeUsu;
	
	@Column(name = "fecha_nacimiento", nullable = false, updatable = false)
    @Temporal(TemporalType.DATE) 
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date fenacUsu;
	

	
	private String urlImagen;
	
	

	
	
	
}