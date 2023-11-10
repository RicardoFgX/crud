package com.example.crud.controlador;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.crud.entidad.Libro;
import com.example.crud.servicio.LibroServicio;

@Controller
public class LibroController {
	 @Autowired
	 LibroServicio libroServicio;
	 @GetMapping("/")
	    public String listarProductos(Model model) {
	        model.addAttribute("libros", libroServicio.listarTodosLosLibros());
	        return "index";
	    }

	    @GetMapping("/libro/nuevo")
	    public String mostrarFormularioDeNuevoLibro(Model model) {
	        Libro libro = new Libro();
	        model.addAttribute("libro", libro);
	        return "libro-form";
	    }

	    @GetMapping("/libro/editar/{id}")
	    public String mostrarFormularioDeEditarLibro(@PathVariable Long id, Model model) {
	    	Libro libro = libroServicio.obtenerLibroPorId(id);
	        model.addAttribute("libro", libro);
	        return "libro-form"; 
	    }

	    @PostMapping("/libro")
	    public String guardarLibro(@ModelAttribute Libro libro) {
	        libroServicio.guardarLibro(libro);
	        return "redirect:/";
	    }

	    @GetMapping("/libro/eliminar/{id}")
	    public String eliminarLibro(@PathVariable Long id) {
	    	libroServicio.eliminarLibro(id);
	        return "redirect:/";
	    }
	}

