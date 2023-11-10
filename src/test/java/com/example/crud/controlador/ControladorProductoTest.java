package com.example.crud.controlador;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import com.example.crud.entidad.Producto;
import com.example.crud.servicio.ProductoServicio;
// @WebMvcTest es una anotación que se utiliza para realizar pruebas de integración de los componentes MVC de Spring Boot.
	// Indica que solo se debe configurar la infraestructura de Spring MVC y no toda la aplicación durante las pruebas.
	// En este caso, se aplica a ProductoController para indicar que queremos probar solo este controlador.
	@WebMvcTest(ProductoController.class)
	public class ControladorProductoTest {

	    // @Autowired inyecta un objeto en el contexto de Spring, que aquí se usa para
	    // inyectar un objeto MockMvc. MockMvc es una herramienta principal en las pruebas de Spring MVC
	    // que nos permite enviar peticiones HTTP a nuestro controlador y validar la lógica del mismo sin necesidad de levantar un servidor.
	    @Autowired
	    private MockMvc mockMvc;

	    // @MockBean es una anotación que crea un mock (simulación) para el bean de Spring especificado.
	    // En este caso, estamos creando un mock para ProductoServicio, lo que permite simular el comportamiento
	    // de la capa de servicio sin tener que involucrar la lógica de negocio real o la base de datos.
	    @MockBean
	    private ProductoServicio productoServicio;

	    // Aquí estamos declarando una instancia de la entidad Producto, la cual se utilizará en los métodos de prueba.
	    // Al no estar anotada con @Autowired o @MockBean, se trata de un objeto regular que se inicializará en el método setUp().
	    private Producto producto;
	    
	    // El método anotado con @BeforeEach se ejecutará antes de cada prueba, es decir,
	    // antes de cada método anotado con @Test. Se utiliza para preparar el estado inicial
	    // necesario para las pruebas. Esto puede incluir la inicialización de objetos de datos,
	    // la configuración de mocks, o cualquier otra configuración previa necesaria para
	    // que las pruebas se ejecuten en un estado consistente.
	    @BeforeEach
	    void setUp() {
	        producto = new Producto();
	        producto.setId(1L);
	        producto.setNombre("Producto Test");
	        producto.setPrecio(3.3);
	    }

	 // Prueba que verifica el correcto funcionamiento de la ruta que lista los productos.
	    @Test
	    void testListarProductos() throws Exception {
	        // Crea una lista de productos para la prueba
	        List<Producto> productos = Arrays.asList(producto);
	        // Configura el servicio simulado para que retorne la lista de productos cuando se invoque
	        given(productoServicio.listarTodosLosProductos()).willReturn(productos);

	        // Realiza la solicitud GET a la ruta "/" y verifica que:
	        mockMvc.perform(get("/"))
	                // La vista devuelta sea "index"
	                .andExpect(view().name("index"))
	                // El modelo contenga un atributo "productos"
	                .andExpect(model().attributeExists("productos"))
	                // El atributo "productos" contenga la lista de productos
	                .andExpect(model().attribute("productos", productos));
	    }

	    // Prueba que verifica el correcto funcionamiento de la ruta que muestra el formulario para crear un nuevo producto.
	    @Test
	    void testMostrarFormularioDeNuevoProducto() throws Exception {
	        // Realiza la solicitud GET a la ruta "/producto/nuevo" y verifica que:
	        mockMvc.perform(get("/producto/nuevo"))
	                // La vista devuelta sea "producto-form"
	                .andExpect(view().name("producto-form"))
	                // El modelo contenga un atributo "producto" para un nuevo producto
	                .andExpect(model().attributeExists("producto"));
	    }

	    // Prueba que verifica el correcto funcionamiento de la ruta que muestra el formulario para editar un producto existente.
	    @Test
	    void testMostrarFormularioDeEditarProducto() throws Exception {
	        // Configura el servicio simulado para que retorne un producto cuando se invoque con cualquier ID
	        given(productoServicio.obtenerProductoPorId(any())).willReturn(producto);

	        // Realiza la solicitud GET a la ruta "/producto/editar/{id}" con el ID del producto y verifica que:
	        mockMvc.perform(get("/producto/editar/{id}", producto.getId()))
	                // La vista devuelta sea "producto-form" (debería ser "producto-editar" si es una vista diferente)
	                .andExpect(view().name("producto-form"))
	                // El modelo contenga un atributo "producto" con el producto a editar
	                .andExpect(model().attributeExists("producto"))
	                // El atributo "producto" contenga el producto proporcionado por el servicio simulado
	                .andExpect(model().attribute("producto", producto));
	    }

	    // Prueba que verifica el correcto funcionamiento de la ruta que guarda un producto.
	    @Test
	    void testGuardarProducto() throws Exception {
	        // Realiza la solicitud POST a la ruta "/producto" con un atributo "producto" y verifica que:
	        mockMvc.perform(post("/producto")
	                // Se pasan los datos del producto en el flash attribute (para simular el envío del formulario)
	                .flashAttr("producto", producto))
	                // Se redirige a la ruta "/", que es el comportamiento esperado después de guardar un producto
	                .andExpect(redirectedUrl("/"));
	    }

	    // Prueba que verifica el correcto funcionamiento de la ruta que elimina un producto.
	    @Test
	    void testEliminarProducto() throws Exception {
	        // Realiza la solicitud GET a la ruta "/producto/eliminar/{id}" con el ID del producto y verifica que:
	        mockMvc.perform(get("/producto/eliminar/{id}", producto.getId()))
	                // Se redirige a la ruta "/", que es el comportamiento esperado después de eliminar un producto
	                .andExpect(redirectedUrl("/"));
	    }
	    
	}

