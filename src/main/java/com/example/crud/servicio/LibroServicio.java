package com.example.crud.servicio;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.crud.entidad.Libro;
import com.example.crud.repositorio.LibroRepositorio;

@Service
public class LibroServicio {
	
	@Autowired
	private LibroRepositorio libroRepositorio;
	
    public LibroServicio(LibroRepositorio libroRepositorio) {
        this.libroRepositorio = libroRepositorio;
    }

    public Iterable<Libro> listarTodosLosLibros() {
        return libroRepositorio.findAll();
    }

    public Libro guardarLibro(Libro libro) {
        return libroRepositorio.save(libro);
    }

    public Libro obtenerLibroPorId(Long id) {
        return libroRepositorio.findById(id).orElseThrow(() -> 
            new IllegalArgumentException("Producto no encontrado con id: " + id));
    }

    public void eliminarLibro(Long id) {
    	libroRepositorio.deleteById(id);
    }
}
