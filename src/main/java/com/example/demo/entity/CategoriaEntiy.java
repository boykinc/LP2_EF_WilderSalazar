package com.example.demo.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "tb_categoria")
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class CategoriaEntiy {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer idCate;
	
	@Column(name = "Categoria" ,
			nullable = false ,
			length = 100,
			columnDefinition = "VARCHAR(100)"
			)
	private  String nomCate;
	
}
