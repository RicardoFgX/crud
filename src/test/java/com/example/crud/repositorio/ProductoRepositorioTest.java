package com.example.crud.repositorio;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import com.example.crud.entidad.Producto;
import com.example.crud.repositorio.ProductoRepositorio;
//Clase de prueba para ProductoRepositorio usando @DataJpaTest para configurar un contexto de prueba de JPA
@DataJpaTest
public class ProductoRepositorioTest {

 // TestEntityManager proporcionado por Spring Boot para usar en pruebas de JPA
 @Autowired
 private TestEntityManager entityManager;

 // El repositorio que se está probando
 @Autowired
 private ProductoRepositorio productoRepositorio;

 // Un producto de prueba que se usará en los métodos de prueba
 private Producto producto;

 // Método que se ejecuta antes de cada prueba para configurar los objetos necesarios
 @BeforeEach
 void setUp() {
     producto = new Producto();
     producto.setNombre("Test");
     producto.setPrecio(10.0);
     // Persistir el producto en la base de datos en memoria usando entityManager
     entityManager.persist(producto);
     // Forzar la sincronización de la transacción actual con la base de datos
     entityManager.flush();
 }

 // Prueba que verifica si el método save del repositorio funciona correctamente
 @Test
 public void testGuardarProducto() {
     // Crear un nuevo producto y guardarlo usando el repositorio
     Producto nuevoProducto = new Producto();
     nuevoProducto.setNombre("Test Nuevo");
     nuevoProducto.setPrecio(15.0);
     Producto guardado = productoRepositorio.save(nuevoProducto);

     // Verificar que el producto guardado tiene el nombre "Test Nuevo"
     assertThat(guardado).hasFieldOrPropertyWithValue("nombre", "Test Nuevo");
 }

 // Prueba que verifica si el método findById del repositorio puede encontrar un producto
 @Test
 public void testBuscarProductoPorId() {
     // Buscar el producto por ID usando el repositorio y verificar que sea el mismo que el producto persistido
     Producto encontrado = productoRepositorio.findById(producto.getId()).orElse(null);
     assertThat(encontrado).isEqualTo(producto);
 }

 // Prueba que verifica si el método findAll del repositorio puede listar todos los productos
 @Test
 public void testListarProductos() {
     // Listar todos los productos y verificar que la lista no esté vacía y contenga el producto persistido
     List<Producto> productos = (List<Producto>) productoRepositorio.findAll();
     assertThat(productos).isNotEmpty();
     assertThat(productos).contains(producto);
 }

 // Prueba que verifica si el método delete del repositorio puede eliminar un producto
 @Test
 public void testEliminarProducto() {
     // Eliminar el producto y verificar que no pueda ser encontrado después de la eliminación
     productoRepositorio.delete(producto);
     Producto eliminado = productoRepositorio.findById(producto.getId()).orElse(null);
     assertThat(eliminado).isNull();
 }
}