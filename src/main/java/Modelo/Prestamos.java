/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

import java.sql.Date;
import java.time.LocalDate;

/**
 *
 * @author alex1
 */
public class Prestamos {
    private int prestamo_id;
    private int libro_id;
    private int usuario_id;
    private LocalDate fecha_prestamo;
    private LocalDate fecha_vencimiento;
    private LocalDate fecha_devolucion;

    public Prestamos(int prestamo_id, int libro_id, int usuario_id, LocalDate fecha_prestamo, LocalDate fecha_vencimiento, LocalDate fecha_devolucion) {
        this.prestamo_id = prestamo_id;
        this.libro_id = libro_id;
        this.usuario_id = usuario_id;
        this.fecha_prestamo = fecha_prestamo;
        this.fecha_vencimiento = fecha_vencimiento;
        this.fecha_devolucion = fecha_devolucion;
    }

    public int getPrestamo_id() {
        return prestamo_id;
    }

    public void setPrestamo_id(int prestamo_id) {
        this.prestamo_id = prestamo_id;
    }

    public int getLibro_id() {
        return libro_id;
    }

    public void setLibro_id(int libro_id) {
        this.libro_id = libro_id;
    }

    public int getUsuario_id() {
        return usuario_id;
    }

    public void setUsuario_id(int usuario_id) {
        this.usuario_id = usuario_id;
    }

    public LocalDate getFecha_prestamo() {
        return fecha_prestamo;
    }

    public void setFecha_prestamo(LocalDate fecha_prestamo) {
        this.fecha_prestamo = fecha_prestamo;
    }

    public LocalDate getFecha_vencimiento() {
        return fecha_vencimiento;
    }

    public void setFecha_vencimiento(LocalDate fecha_vencimiento) {
        this.fecha_vencimiento = fecha_vencimiento;
    }

    public LocalDate getFecha_devolucion() {
        return fecha_devolucion;
    }

    public void setFecha_devolucion(LocalDate fecha_devolucion) {
        this.fecha_devolucion = fecha_devolucion;
    }

    
    
    
}
