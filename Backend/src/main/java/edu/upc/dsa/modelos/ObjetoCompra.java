package edu.upc.dsa.modelos;

public class ObjetoCompra {
    String nombreProducto;
    String emailUser;

    public ObjetoCompra() {}

    public ObjetoCompra(String nombreProducto, String emailUser) {
        this.nombreProducto = nombreProducto;
        this.emailUser = emailUser;
    }

    public String getNombreProducto() { return nombreProducto; }
    public void setNombreProducto(String nombreProducto) { this.nombreProducto = nombreProducto; }
    public String getEmailUser() { return emailUser; }
    public void setEmailUser(String emailUser) { this.emailUser = emailUser; }
}
