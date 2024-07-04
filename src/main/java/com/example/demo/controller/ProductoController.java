package com.example.demo.controller;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.entity.CategoriaEntiy;
import com.example.demo.entity.ProductoEntity;
import com.example.demo.entity.UsuarioEntity;
import com.example.demo.repository.CategoriaRepository;
import com.example.demo.service.ProductoService;
import com.example.demo.service.UsuarioService;
import com.example.demo.service.PDFService;

import jakarta.servlet.http.HttpSession;

@Controller
public class ProductoController {

	@Autowired
	private PDFService pdfService;
	@Autowired
	private UsuarioService usuarioService;
	
	@Autowired
	private ProductoService productoService;
	
	@Autowired
	private CategoriaRepository categoriaRepository;
	
	@GetMapping("/menu")
	public String showMenu(HttpSession session, Model model) {
		if(session.getAttribute("usuario") == null) {
			return "redirect:/";
		}
		
		String correo = session.getAttribute("usuario").toString();
		UsuarioEntity usuarioEntity = usuarioService.buscarUsuarioPorCorreo(correo);
		model.addAttribute("foto", usuarioEntity.getUrlImagen());
		String nombreCompleto = usuarioEntity.getNomUsu() + " " + usuarioEntity.getApeUsu();
        model.addAttribute("nombreUsuario", nombreCompleto);
        model.addAttribute("foto", usuarioEntity.getUrlImagen());
		
		List<ProductoEntity>productos = productoService.buscarTodosProductos();
		model.addAttribute("productos", productos);
		
		return "menu";
	}
	
	@GetMapping("/registrarProducto")
	public String showRegistrarProducto(HttpSession session ,Model model) {
		if(session.getAttribute("usuario") == null) {
			return "redirect:/";
		}
		
		String correo = session.getAttribute("usuario").toString();
		UsuarioEntity usuarioEntity = usuarioService.buscarUsuarioPorCorreo(correo);
		model.addAttribute("foto", usuarioEntity.getUrlImagen());
		String nombreCompleto = usuarioEntity.getNomUsu() + " " + usuarioEntity.getApeUsu();
        model.addAttribute("nombreUsuario", nombreCompleto);
        model.addAttribute("foto", usuarioEntity.getUrlImagen());
        
        
		List<CategoriaEntiy> listaCategorias = categoriaRepository.findAll();
		model.addAttribute("listaCategorias" ,listaCategorias);
		model.addAttribute("producto", new ProductoEntity());
		return "registrarProducto";
	}
	
	@PostMapping("/registrarProducto")
	public String registrarProducto(HttpSession session ,ProductoEntity productoEntity, Model model) {
		
		if(session.getAttribute("usuario") == null) {
			return "redirect:/";
		}
		
		String correo = session.getAttribute("usuario").toString();
		UsuarioEntity usuarioEntity = usuarioService.buscarUsuarioPorCorreo(correo);
		model.addAttribute("foto", usuarioEntity.getUrlImagen());
		String nombreCompleto = usuarioEntity.getNomUsu() + " " + usuarioEntity.getApeUsu();
        model.addAttribute("nombreUsuario", nombreCompleto);
        model.addAttribute("foto", usuarioEntity.getUrlImagen());
		
		
		productoService.crearProducto(productoEntity, model);
		
		return "registrarProducto";
	}
	

	@GetMapping("/editarProducto/{id}")
    public String showEditarProducto(@PathVariable("id") Integer id, Model model, HttpSession session) {
        if (session.getAttribute("usuario") == null) {
            return "redirect:/";
        }

        String correo = session.getAttribute("usuario").toString();
        UsuarioEntity usuarioEntity = usuarioService.buscarUsuarioPorCorreo(correo);
        model.addAttribute("nombreUsuario", usuarioEntity.getNomUsu() + " " + usuarioEntity.getApeUsu());
        model.addAttribute("foto", usuarioEntity.getUrlImagen());

        ProductoEntity productoEditar = productoService.buscarProductoPorId(id);
        if (productoEditar == null) {
           
            return "redirect:/menu";
        }

        model.addAttribute("producto", productoEditar);

        List<CategoriaEntiy> listaCategorias = productoService.obtenerCategorias();
        model.addAttribute("listaCategorias", listaCategorias);

        return "editarProducto";
    }

    @PostMapping("/editarProducto")
    public String actualizarProducto(Model model, @ModelAttribute("producto") ProductoEntity productoEntity, HttpSession session) {
        if (session.getAttribute("usuario") == null) {
            return "redirect:/"; 
        }

        String correo = session.getAttribute("usuario").toString();
        UsuarioEntity usuarioEntity = usuarioService.buscarUsuarioPorCorreo(correo);
        model.addAttribute("nombreUsuario", usuarioEntity.getApeUsu() + " " + usuarioEntity.getApeUsu());
        model.addAttribute("foto", usuarioEntity.getUrlImagen());

      
        if (productoEntity == null || productoEntity.getProductoId() == null) {
            return "redirect:/menu"; 
        }

        productoService.actualizarProducto(productoEntity);

        return "redirect:/menu";
    }
	
	
	
	@GetMapping("/generarPDFproductos")
    public ResponseEntity<InputStreamResource> generarPdf(HttpSession session) throws IOException {
        String correo = session.getAttribute("usuario").toString();
        UsuarioEntity usuarioEntity = usuarioService.buscarUsuarioPorCorreo(correo);

        List<ProductoEntity> productos = productoService.buscarTodosProductos(); 

        BigDecimal total = BigDecimal.ZERO;
        for (ProductoEntity producto : productos) {
            total = total.add(producto.getPrecioProdu().multiply(new BigDecimal(producto.getStockProdu())));
        }

        Map<String, Object> datosPdf = new HashMap<>();
        datosPdf.put("productos", productos);
        datosPdf.put("usuario", usuarioEntity.getNomUsu() + " " + usuarioEntity.getApeUsu());

        ByteArrayInputStream pdfBytes = pdfService.generarPdfDeHtml("GenerarProductos", datosPdf);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Content-Disposition", "inline; filename=productos.pdf");

        return ResponseEntity.ok()
                .headers(httpHeaders)
                .contentType(MediaType.APPLICATION_PDF)
                .body(new InputStreamResource(pdfBytes));
    }
	
	
}
