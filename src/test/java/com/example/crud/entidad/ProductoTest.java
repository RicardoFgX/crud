package com.example.crud.entidad;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class ProductoTest {

	@Test
	void crearValoresCorrectoProducto() {
		Producto producto = new Producto();
        producto.setId(1L);
        producto.setNombre("Cafetera");
        producto.setPrecio(99.99);

        assertEquals(1L, producto.getId(), "El ID no coincide");
        assertEquals("Cafetera", producto.getNombre(), "El nombre no coincide");
        assertEquals(99.99, producto.getPrecio(), "El precio no coincide");
	}


}
