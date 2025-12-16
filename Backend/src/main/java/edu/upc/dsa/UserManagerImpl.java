package edu.upc.dsa;

import edu.upc.dsa.modelos.User;
import java.util.*;

public class UserManagerImpl implements UserManager {
    private static UserManagerImpl instance;
    private List<User> usuarios;

    private UserManagerImpl() {
        usuarios = new ArrayList<>();

        // 1. JUAN (250 Puntos - Porxinos)
        User juan = new User("id_juan", "Juan", "juan@gmail.com", "1234");
        juan.setTeam("Porxinos");
        juan.setMonedas(1000);
        juan.setPuntos(250);
        juan.setAvatar("https://cdn.pixabay.com/photo/2017/07/11/15/51/kermit-2493979_1280.png");
        juan.setEmailVerificado(true);
        this.usuarios.add(juan);

        // 2. PALOMO (200 Puntos - Porxinos)
        User palomo = new User("id_palomo", "Palomo", "palomo@gmail.com", "1234");
        palomo.setTeam("Porxinos");
        palomo.setMonedas(1000);
        palomo.setPuntos(200);
        palomo.setAvatar("https://cdn.pixabay.com/photo/2016/01/10/18/59/cookie-monster-1132275_1280.jpg");
        palomo.setEmailVerificado(true);
        this.usuarios.add(palomo);
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

        // Se pone directamente en el equipo de Porxinos
        nuevo.setTeam("Porxinos");
        nuevo.setAvatar("https://cdn.pixabay.com/photo/2017/07/11/15/51/kermit-2493979_1280.png");
        nuevo.setMonedas(1000);
        nuevo.setPuntos(0);

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

    // De aquí
    public List<User> getUsuariosPorEquipo(String equipo) {
        List<User> lista = new ArrayList<>();
        for (User u : this.usuarios) {
            if (u.getTeam() != null && u.getTeam().equals(equipo)) {
                lista.add(u);
            }
        }
        return lista;
    }
    // Hasta aquí
}