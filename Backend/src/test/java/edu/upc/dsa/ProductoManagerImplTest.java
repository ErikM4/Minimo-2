package edu.upc.dsa;

import edu.upc.dsa.modelos.Producto;
import edu.upc.dsa.modelos.User;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class ProductoManagerImplTest {

    private ProductoManagerImpl productoManager;
    private UserManagerImpl userManager;

    @Before
    public void setUp() {
        productoManager = ProductoManagerImpl.getInstance();
        userManager = UserManagerImpl.getInstance();

        productoManager.listadeproductos().clear();
        userManager.getUsuarios().clear();
    }

    @Test
    public void testAnadirProductoExitoso() {
        Producto p = productoManager.anadirproducto("Poción de Vida", 50);

        assertNotNull("El producto no debe ser null", p);
        assertNotNull("El producto debe tener un ID autogenerado", p.getId());
        assertEquals("El nombre debe coincidir", "Poción de Vida", p.getNombreproducto());
        assertEquals("El precio debe coincidir", 50, p.getPrecio());
        assertEquals("Debe haber 1 producto en la lista", 1, productoManager.listadeproductos().size());
    }

    @Test
    public void testAnadirProductoDuplicadoNombre() {
        productoManager.anadirproducto("Poción de Vida", 50);

        Producto repetido = productoManager.anadirproducto("Poción de Vida", 100);

        assertNull("No se debe permitir añadir un producto con nombre repetido", repetido);
        assertEquals("Debe seguir habiendo solo 1 producto", 1, productoManager.listadeproductos().size());
    }

    @Test
    public void testGetProductoPorNombre() {
        productoManager.anadirproducto("Poción de Vida", 50);
        Producto p = productoManager.getproducto("Poción de Vida");

        assertNotNull("El producto debe existir", p);
        assertEquals("El nombre debe ser 'Poción de Vida'", "Poción de Vida", p.getNombreproducto());
    }

    @Test
    public void testEncontrarProductoPorId() {
        Producto p1 = productoManager.anadirproducto("Poción de Vida", 50);
        String idGenerado = p1.getId();

        Producto p2 = productoManager.encontrarproducto(idGenerado);

        assertNotNull("El producto debe existir si se busca por su ID generado", p2);
        assertEquals("Los IDs deben coincidir", idGenerado, p2.getId());
    }

    @Test
    public void testListadoDeProductos() {
        productoManager.anadirproducto("Poción", 50);
        productoManager.anadirproducto("Espada", 150);

        List<Producto> lista = productoManager.listadeproductos();

        assertEquals("La lista debe contener 2 productos", 2, lista.size());
    }

    @Test
    public void testGetProductoNoExistente() {
        productoManager.anadirproducto("Poción", 50);

        Producto pNombre = productoManager.getproducto("Espada");
        assertNull("El producto 'Espada' no debería existir (buscado por nombre)", pNombre);

        Producto pId = productoManager.encontrarproducto("ID-Falso-12345");
        assertNull("El producto 'ID-Falso-12345' no debería existir (buscado por ID)", pId);
    }

    @Test
    public void testComprarProductoExitoso() {
        productoManager.anadirproducto("Escudo Hyliano", 200);
        User u = userManager.registrarUsuario("Link", "link@zelda.com", "1234");
        u.setMonedas(500);

        int codigoError = productoManager.comprarProducto("Escudo Hyliano", "link@zelda.com");

        assertEquals("El código de retorno debe ser 0 (Éxito)", 0, codigoError);
        assertEquals("Las monedas deben haberse restado (500 - 200)", 300, u.getMonedas());

        assertTrue("El inventario debe contener el escudo", u.getInventario().containsKey("Escudo Hyliano"));
        int cantidad = u.getInventario().get("Escudo Hyliano");
        assertEquals("La cantidad del escudo debe ser 1", 1, cantidad);
    }

    @Test
    public void testComprarProductoSinSaldo() {
        productoManager.anadirproducto("Espada Maestra", 1000);
        User u = userManager.registrarUsuario("Link Pobre", "pobre@zelda.com", "1234");
        u.setMonedas(50); // No tiene suficiente

        int codigoError = productoManager.comprarProducto("Espada Maestra", "pobre@zelda.com");

        assertEquals("El código debe ser 3 (Saldo insuficiente)", 3, codigoError);
        assertEquals("Las monedas NO deben cambiar", 50, u.getMonedas());
        assertTrue("El inventario debe estar vacío", u.getInventario().isEmpty());
    }

    @Test
    public void testComprarProductoAcumulativo() {
        productoManager.anadirproducto("Poción", 10);
        User u = userManager.registrarUsuario("Mario", "mario@nintendo.com", "1234");
        u.setMonedas(100);

        productoManager.comprarProducto("Poción", "mario@nintendo.com");
        productoManager.comprarProducto("Poción", "mario@nintendo.com");

        assertEquals("Debe tener 80 monedas (100 - 10 - 10)", 80, u.getMonedas());
        assertTrue("Debe tener Poción en inventario", u.getInventario().containsKey("Poción"));

        int cantidad = u.getInventario().get("Poción");
        assertEquals("Debe tener 2 Pociones", 2, cantidad);
    }
}