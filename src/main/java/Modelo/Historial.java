/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 *
 * @author alex1
 */
public class Historial {
    private int transaccion_id;
    private int usuario_id;
    private String nombreUsuario;
    private String accion;
    private LocalDate fecha_transaccion;

    public Historial(int transaccion_id, int usuario_id, String nombreUsuario, String accion, LocalDate fecha_transaccion) {
        this.transaccion_id = transaccion_id;
        this.usuario_id = usuario_id;
        this.nombreUsuario = nombreUsuario;
        this.accion = accion;
        this.fecha_transaccion = fecha_transaccion;
    }

    public int getTransaccion_id() {
        return transaccion_id;
    }

    public void setTransaccion_id(int transaccion_id) {
        this.transaccion_id = transaccion_id;
    }

    public int getUsuario_id() {
        return usuario_id;
    }

    public void setUsuario_id(int usuario_id) {
        this.usuario_id = usuario_id;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public String getAccion() {
        return accion;
    }

    public void setAccion(String accion) {
        this.accion = accion;
    }

    public LocalDate getFecha_transaccion() {
        return fecha_transaccion;
    }

    public void setFecha_transaccion(LocalDate fecha_transaccion) {
        this.fecha_transaccion = fecha_transaccion;
    }

    
    
    
    
   
}


