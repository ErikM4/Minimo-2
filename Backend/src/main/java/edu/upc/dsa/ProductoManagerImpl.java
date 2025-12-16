package edu.upc.dsa;

import edu.upc.dsa.modelos.Producto;
import edu.upc.dsa.modelos.User;
import edu.upc.dsa.UserManagerImpl;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ProductoManagerImpl implements ProductoManager {

    private static ProductoManagerImpl instance;
    private List<Producto> productos;

    private ProductoManagerImpl() {
        this.productos = new ArrayList<>();
    }

    public static ProductoManagerImpl getInstance() {
        if (instance == null) {
            instance = new ProductoManagerImpl();
        }
        return instance;
    }

    @Override
    public List<Producto> listadeproductos() {
        return this.productos;
    }

    @Override
    public Producto getproducto(String nombreproducto) {
        for (Producto p : this.productos) {
            if (p.getNombreproducto().equals(nombreproducto)) {
                return p;
            }
        }
        return null;
    }

    @Override
    public Producto anadirproducto(String nombreproducto, int precio) {

        if (getproducto(nombreproducto) != null) {
            return null;
        }

        String id = UUID.randomUUID().toString();
        Producto nuevoProducto = new Producto(id, nombreproducto, precio);
        this.productos.add(nuevoProducto);
        return nuevoProducto;
    }

    @Override
    public Producto encontrarproducto(String nombreproducto) {
        for (Producto p : this.productos) {
            if (p.getId().equals(nombreproducto)) {
                return p;
            }
        }
        return null;
    }

    @Override
    public int comprarProducto(String nombreProducto, String emailUsuario) {

        // 1. Buscamos el producto en el catálogo
        Producto p = this.getproducto(nombreProducto);
        if (p == null) return 2; // Producto no existe

        // 2. Buscamos al usuario (Llamamos al OTRO manager)
        // Nota: Usamos getInstance() porque es Singleton
        User u = UserManagerImpl.getInstance().getUsuario(emailUsuario);
        if (u == null) return 1; // Usuario no existe

        // 3. Verificamos si tiene dinero
        if (u.getMonedas() < p.getPrecio()) {
            return 3; // No tiene "pasta"
        }

        // 4. REALIZAR LA TRANSACCIÓN
        // Restamos dinero
        u.setMonedas(u.getMonedas() - p.getPrecio());

        // Añadimos al inventario (usando el método que creamos antes)
        u.addObjetoInventario(p.getNombreproducto(), 1);

        System.out.println("Compra realizada: " + u.getNombre() + " compró " + p.getNombreproducto());

        return 0; // Todo OK
    }
}
