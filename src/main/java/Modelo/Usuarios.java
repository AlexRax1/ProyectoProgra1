/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

/**
 *
 * @author alex1
 */
public class Usuarios {
    private int usuario_id;
    private String nombre;
    private String direccion;
    private String telefono;
    private String email;
    private double saldo;
    private String contrasena;
    private boolean es_administrador;

    public Usuarios(int usuario_id, String nombre, String direccion, String telefono, String email, double saldo, String contrasena, boolean es_administrador) {
        this.usuario_id = usuario_id;
        this.nombre = nombre;
        this.direccion = direccion;
        this.telefono = telefono;
        this.email = email;
        this.saldo = saldo;
        this.contrasena = contrasena;
        this.es_administrador = es_administrador;
    }

    public int getUsuario_id() {
        return usuario_id;
    }

    public void setUsuario_id(int usuario_id) {
        this.usuario_id = usuario_id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public double getSaldo() {
        return saldo;
    }

    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public boolean isEs_administrador() {
        return es_administrador;
    }

    public void setEs_administrador(boolean es_administrador) {
        this.es_administrador = es_administrador;
    }
    
    
}
