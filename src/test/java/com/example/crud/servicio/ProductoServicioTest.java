package com.example.crud.servicio;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.times;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.crud.entidad.Producto;
import com.example.crud.repositorio.ProductoRepositorio;

@SpringBootTest
public class ProductoServicioTest {

    private ProductoServicio productoServicio;

    @Mock
    private ProductoRepositorio productoRepositorio;

    @BeforeEach
    void setUp() {
        // Inicializa los mocks anotados en esta instancia de prueba
        MockitoAnnotations.initMocks(this);
        productoServicio = new ProductoServicio(productoRepositorio);
    }

    // Prueba para verificar el correcto funcionamiento de listarTodosLosProductos
    @Test
    void testListarTodosLosProductos() {
        // Configurar el comportamiento esperado del repositorio
        when(productoRepositorio.findAll()).thenReturn(Arrays.asList(new Producto()));

        // Llamar al método bajo prueba
        Iterable<Producto> productos_it = productoServicio.listarTodosLosProductos();
        List<Producto> productos = new ArrayList<>();
        productos_it.forEach(productos::add);
        
        // Verificar el resultado
        assertEquals(1, productos.size(), "Debe haber un producto en la lista");
        verify(productoRepositorio).findAll(); // Verifica que se llame al método findAll del repositorio
    }

    // Prueba para verificar el correcto funcionamiento de obtenerProductoPorId
    @Test
    void testObtenerProductoPorId() {
        // Dado
        Producto producto = new Producto();
        producto.setId(1L);
        producto.setPrecio(3.3);
        when(productoRepositorio.findById(1L)).thenReturn(Optional.of(producto));

        // Cuando
        Producto encontrado = productoServicio.obtenerProductoPorId(1L);

        // Entonces
        assertEquals(producto, encontrado, "El producto encontrado debe coincidir con el mock");
        verify(productoRepositorio).findById(1L); // Verifica que se llame al método findById del repositorio
    }

    // Prueba para verificar el correcto funcionamiento de guardarProducto
    @Test
    void testGuardarProducto() {
        // Dado
        Producto producto = new Producto();
        when(productoRepositorio.save(any(Producto.class))).thenReturn(producto);

        // Cuando
        Producto guardado = productoServicio.guardarProducto(producto);

        // Entonces
        assertEquals(producto, guardado, "El producto guardado debe coincidir con el mock");
        verify(productoRepositorio).save(any(Producto.class)); // Verifica que se llame al método save del repositorio
    }

    // Prueba para verificar el correcto funcionamiento de eliminarProducto
    @Test
    void testEliminarProducto() {
        // Dado
        long id = 1L;

        // Cuando
        productoServicio.eliminarProducto(id);

        // Entonces
        verify(productoRepositorio, times(1)).deleteById(id); // Verifica que se llame al método deleteById del repositorio
    }
}