package com.example.crud.repositorio;

import org.springframework.data.repository.CrudRepository;

import com.example.crud.entidad.Libro;

public interface LibroRepositorio extends CrudRepository<Libro, Long>{

}
