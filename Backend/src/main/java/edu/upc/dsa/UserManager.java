package edu.upc.dsa;

import edu.upc.dsa.modelos.User;
import java.util.List;

public interface UserManager {
    // De aquí
    List<User> getUsuariosPorEquipo(String equipo);
    //Hasta aquí

    User registrarUsuario(String nombre, String email, String password);
    User loginUsuario(String email, String password);
    User getUsuario(String email);
    List<User> getUsuarios();
    boolean enviarCodigoVerificacion(String email);
    boolean verificarCodigo(String email, String codigo);
}
