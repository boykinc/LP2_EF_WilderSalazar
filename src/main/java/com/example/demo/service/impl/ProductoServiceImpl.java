package com.example.demo.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.example.demo.entity.CategoriaEntiy;
import com.example.demo.entity.ProductoEntity;
import com.example.demo.entity.UsuarioEntity;
import com.example.demo.repository.CategoriaRepository;
import com.example.demo.repository.ProductoRepository;
import com.example.demo.service.ProductoService;

@Service
public class ProductoServiceImpl implements ProductoService{
	
	@Autowired
	private ProductoRepository productoRepository;
	
	@Autowired
	private CategoriaRepository categoriaRepository;
	
	@Override
	public List<ProductoEntity> buscarTodosProductos() {
		return productoRepository.findAll();
	}

	@Override
    public List<CategoriaEntiy> obtenerCategorias() {
        return categoriaRepository.findAll();
    }

	@Override
	public void crearProducto(ProductoEntity productoEntity, Model model) {
		List<CategoriaEntiy> listaCategorias = categoriaRepository.findAll();
		productoRepository.save(productoEntity);
		
		// responder a la vista
		model.addAttribute("listaCategorias" ,listaCategorias);
		model.addAttribute("registroCorrecto", "Producto Registrado");
		model.addAttribute("producto", new ProductoEntity());
		
	}


	@Override
	public ProductoEntity buscarProductoPorId(Integer productoId) {
		// TODO Auto-generated method stub
		return productoRepository.findById(productoId.intValue()).get();
	}
	
	@Override
	public ProductoEntity actualizarProducto(ProductoEntity productoEntity) {
		ProductoEntity buscarProductoPorId = buscarProductoPorId(productoEntity.getProductoId());
		if(buscarProductoPorId != null) { 
			buscarProductoPorId.setNomProdu(productoEntity.getNomProdu());
			buscarProductoPorId.setPrecioProdu(productoEntity.getPrecioProdu());
			buscarProductoPorId.setStockProdu(productoEntity.getStockProdu());
			buscarProductoPorId.setCategorias(productoEntity.getCategorias());
			
			return productoRepository.save(buscarProductoPorId);
			
			
		}
		return null;
	}


	@Override
	public void eliminarProducto(Integer productoId) {
		// TODO Auto-generated method stub
		productoRepository.deleteById(productoId);
	}






}
