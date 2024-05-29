/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

import java.sql.Timestamp;
import java.time.LocalDate;

/**
 *
 * @author alex1
 */
public class Historial {
    private int transaccion_id;
    private int prestamo_id;
    private String accion;
    private LocalDate fecha_transaccion;

    public Historial(int transaccion_id, int prestamo_id, String accion, LocalDate fecha_transaccion) {
        this.transaccion_id = transaccion_id;
        this.prestamo_id = prestamo_id;
        this.accion = accion;
        this.fecha_transaccion = fecha_transaccion;
    }

    public int getTransaccion_id() {
        return transaccion_id;
    }

    public void setTransaccion_id(int transaccion_id) {
        this.transaccion_id = transaccion_id;
    }

    public int getPrestamo_id() {
        return prestamo_id;
    }

    public void setPrestamo_id(int prestamo_id) {
        this.prestamo_id = prestamo_id;
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


