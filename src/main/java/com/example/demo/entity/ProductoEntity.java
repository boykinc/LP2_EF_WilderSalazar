package com.example.demo.entity;



import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "tb_producto")
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ProductoEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer productoId;
	
	@Column(name = "Producto" ,
			nullable = false ,
			length = 100,
			columnDefinition = "VARCHAR(100)"
			)
	private String nomProdu;
	
	@Column(name = "stock" ,
			nullable = false 
			)
	private Integer stockProdu;
	
	@Column(name = "precio" ,
			nullable = false 
			)
	private Double precioProdu;
	
	@ManyToOne
	@JoinColumn(name="idCate")
	private CategoriaEntiy categorias;
	
}