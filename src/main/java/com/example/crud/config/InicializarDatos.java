package com.example.crud.config;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.crud.entidad.Libro;
import com.example.crud.servicio.LibroServicio;

import jakarta.annotation.PostConstruct;

@Component
public class InicializarDatos {
	 @Autowired
	    private LibroServicio libroServicio;
	    
	    @PostConstruct
	    public void init() {
	        // Crear y guardar algunos productos de ejemplo
	        if (libroServicio.listarTodosLosLibros().iterator().hasNext()) {
	            // Si ya hay datos, no inicializamos.
	            return;
	        }
	        
	        Libro libro1 = new Libro();
	        libro1.setTitulo("Titulo1");
	        libro1.setAutor("Autor1");
	        libro1.setIsbn("1234");
	        libro1.setPublicadoEn(LocalDate.now());
	        libroServicio.guardarLibro(libro1);
	        
	        Libro libro2 = new Libro();
	        libro2.setTitulo("Titulo2");
	        libro2.setAutor("Autor2");
	        libro2.setIsbn("4321");
	        libro2.setPublicadoEn(LocalDate.now());
	        libroServicio.guardarLibro(libro2);

	    }
}
