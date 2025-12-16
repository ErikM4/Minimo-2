package edu.upc.dsa;

import edu.upc.dsa.modelos.Producto;
import edu.upc.dsa.modelos.User;
import java.util.List;

public interface ProductoManager {
    List<Producto> listadeproductos();
    Producto getproducto (String nombreproducto);
    Producto anadirproducto(String nombreproducto, int precio);//pongo este por si queremos que el id venga del rest
    Producto encontrarproducto (String nombreproducto);
    public int comprarProducto (String nombreproducto, String emailUser);
}
