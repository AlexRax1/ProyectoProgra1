/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Singleton.java to edit this template
 */
package Modelo;

/**
 *
 * @author alex1
 */
public class UsuarioGlobal {
    private static UsuarioGlobal instance;
    private int idUsuario;

    private UsuarioGlobal() {
        // Constructor privado para evitar instanciación directa
    }

    public static UsuarioGlobal getInstance() {
        if (instance == null) {
            instance = new UsuarioGlobal();
        }
        return instance;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int id) {
        idUsuario = id;
    }
}
