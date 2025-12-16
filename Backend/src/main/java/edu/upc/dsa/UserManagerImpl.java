package edu.upc.dsa;

import edu.upc.dsa.modelos.User;
import java.util.*;

public class UserManagerImpl implements UserManager {
    private static UserManagerImpl instance;
    private List<User> usuarios;

    private UserManagerImpl() {
        usuarios = new ArrayList<>();
    }

    public static UserManagerImpl getInstance() {
        if (instance == null) instance = new UserManagerImpl();
        return instance;
    }

    @Override
    public User registrarUsuario(String nombre, String email, String password) {
        for (User u : usuarios) {
            if (u.getEmail().equals(email))
                return null;
        }

        User nuevo = new User(UUID.randomUUID().toString(), nombre, email, password);
        nuevo.setEmailVerificado(false);
        usuarios.add(nuevo);
        return nuevo;
    }

    @Override
    public User loginUsuario(String email, String password) {
        // Buscamos al usuario por email
        User u = this.getUsuario(email);

        if (u != null) {
            // Si el usuario existe, se comprueba la contraseña
            if (u.getPassword().equals(password)) {
                return u; //Login correcto
            } else {
                return null; //Contraseña incorrecta
            }
        }
        return null; // Usuario no encontrado
    }

    @Override
    public User getUsuario(String email) {
        for (User u : usuarios) {
            if (u.getEmail().equals(email)) return u;
        }
        return null;
    }

    @Override
    public List<User> getUsuarios() {
        return usuarios;
    }

    public boolean enviarCodigoVerificacion(String email) {
        User user = this.getUsuario(email);

        if (user == null) {
            return false;
        }

        Random random = new Random();
        String codigo = String.format("%06d", random.nextInt(999999));

        user.setCodigoVerificacion(codigo);


        System.out.println("═══════════════════════════════════════");
        System.out.println("📧 CÓDIGO DE VERIFICACIÓN");
        System.out.println("Email: " + email);
        System.out.println("Código: " + codigo);
        System.out.println("═══════════════════════════════════════");

        return true;
    }
    public boolean verificarCodigo(String email, String codigo) {
        User user = this.getUsuario(email);

        if (user == null) {
            return false;
        }

        if (codigo.equals(user.getCodigoVerificacion())) {
            user.setEmailVerificado(true);
            user.setCodigoVerificacion(null);
            System.out.println("Email verificado: " + email);
            return true;
        }

        System.out.println("Código incorrecto para: " + email);
        return false;
    }
}
