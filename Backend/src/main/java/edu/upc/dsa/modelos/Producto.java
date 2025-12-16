package edu.upc.dsa.modelos;

public class Producto {
    private String id;
    private String nombreproducto;
    private int precio;

    public Producto() {}

    public Producto(String id, String nombreproducto, int precio) {
        this.id = id;
        this.nombreproducto = nombreproducto;
        this.precio = precio;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombreproducto() {
        return nombreproducto;
    }

    public void setNombreproducto(String nombreproducto) {
        this.nombreproducto = nombreproducto;
    }

    public int getPrecio() {
        return precio;
    }

    public void setPrecio(int precio) {
        this.precio = precio;
    }

}
