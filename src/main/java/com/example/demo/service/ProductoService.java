package com.example.demo.service;

import java.util.List;

import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.entity.CategoriaEntiy;
import com.example.demo.entity.ProductoEntity;


import jakarta.servlet.http.HttpSession;

public interface ProductoService {
	List<CategoriaEntiy> obtenerCategorias();
	List<ProductoEntity>buscarTodosProductos();
	void crearProducto(ProductoEntity productoEntity, Model model);
	ProductoEntity buscarProductoPorId(Integer productoId);
	ProductoEntity actualizarProducto(ProductoEntity productoEntity);
	void eliminarProducto(Integer productoId);
}
