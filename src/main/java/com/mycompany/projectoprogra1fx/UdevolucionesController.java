/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.mycompany.projectoprogra1fx;

import Modelo.ConexionDB;
import Modelo.UsuarioGlobal;
import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author alex1
 */
public class UdevolucionesController implements Initializable {

    /**
     * Initializes the controller class.
     */
    
    
    
@FXML
private TextField bIdtxt; 
@FXML
private TextField bIdPrestamotxt; 
@FXML
private TextField bNombretxt; 
@FXML
private TextField bTelefonotxt;  
@FXML
private TextField bEmailtxt;

@FXML
private Button btnBuscar;

@FXML
private Button btnDevolucion;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        
        
        UsuarioGlobal usuario = UsuarioGlobal.getInstance();
        int valorActual = usuario.getIdUsuario();
        bIdtxt.setText(valorActual + "");       
          
        
        
        btnBuscar.setOnAction( e -> buscarUsuario());
        btnDevolucion.setOnAction( e -> devolver());
        
        buscarUsuario();
    }    
    
    private void buscarUsuario() {
        // Obtener el ID del usuario desde el campo de texto
        String id = bIdtxt.getText().trim();

        // Verificar que el campo ID no esté vacío
        if (id.isEmpty()) {
            mostrarAlertaError("ID Requerido", "Por favor, ingrese un ID de usuario.");
            return;
        }

        Connection conn = ConexionDB.getConnection();

        // Consulta SQL para buscar el usuario por ID
        String queryUsuario = "SELECT * FROM \"usuarios\" WHERE usuario_id = ?";

        try (PreparedStatement stmtUsuario = conn.prepareStatement(queryUsuario)) {
            // Asignar el valor del ID al parámetro en el PreparedStatement
            stmtUsuario.setInt(1, Integer.parseInt(id));

            ResultSet rsUsuario = stmtUsuario.executeQuery();

            // Verificar si se encontró un usuario con el ID especificado
            if (rsUsuario.next()) {
                // Asignar los valores obtenidos a los campos de texto

                // Consulta SQL para verificar préstamos del usuario
                String queryPrestamos = "SELECT prestamo_id FROM \"prestamos\" WHERE usuario_id = ? AND fecha_devolucion IS NULL";
                try (PreparedStatement stmtPrestamos = conn.prepareStatement(queryPrestamos)) {
                    stmtPrestamos.setInt(1, Integer.parseInt(id));

                    ResultSet rsPrestamos = stmtPrestamos.executeQuery();

                    if (rsPrestamos.next()) {
                        // Obtener el prestamo_id del préstamo con fecha_devolucion nula
                        int prestamoId = rsPrestamos.getInt("prestamo_id");
                        
                        bNombretxt.setText(rsUsuario.getString("nombre"));
                        bTelefonotxt.setText(rsUsuario.getString("telefono"));
                        bEmailtxt.setText(rsUsuario.getString("email"));
                        bIdtxt.setEditable(false);
                        bIdPrestamotxt.setText(String.valueOf(prestamoId));  // Asegúrate de tener un TextField llamado bIdPrestamotxt
                        btnDevolucion.setDisable(false);

                    } else {
                        //mostrarAlertaError("Sin préstamos pendientes", "El usuario no tiene préstamos pendientes.");
                        btnDevolucion.setDisable(true);

                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                    mostrarAlertaError("Error en la consulta de préstamos", "Se produjo un error al consultar los préstamos del usuario.");
                }
            } else {
                mostrarAlertaError("Usuario no encontrado", "No se encontró un usuario con el ID especificado.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            mostrarAlertaError("Error en la búsqueda", "Se produjo un error al buscar el usuario.");
        }
    }
    
    
    private void devolver() {
        // Obtener los valores de los campos de texto
        int idUsuario = Integer.parseInt(bIdtxt.getText());
        int idPrestamo = Integer.parseInt(bIdPrestamotxt.getText());

        // Obtener la fecha actual
        LocalDate fechaActual = LocalDate.now();
        // Multa por día en caso de retraso
        float multaDia = 30.0f;
        float multaTotal = 0.0f;

        Connection conn = ConexionDB.getConnection();

        try {
            // Iniciar una transacción
            conn.setAutoCommit(false);
            

            // Actualizar la fecha de devolución del préstamo
            String queryUpdateFechaDevolucion = "UPDATE prestamos SET fecha_devolucion = ? WHERE prestamo_id = ?";
            try (PreparedStatement stmtFechaDevolucion = conn.prepareStatement(queryUpdateFechaDevolucion)) {
                stmtFechaDevolucion.setDate(1, Date.valueOf(fechaActual));
                stmtFechaDevolucion.setInt(2, idPrestamo);
                stmtFechaDevolucion.executeUpdate();
            }

            // Obtener la fecha de vencimiento para calcular la multa
            String queryFechas = "SELECT fecha_vencimiento FROM prestamos WHERE prestamo_id = ?";
            LocalDate fechaVencimiento = null;
            try (PreparedStatement stmtFechas = conn.prepareStatement(queryFechas)) {
                stmtFechas.setInt(1, idPrestamo);
                ResultSet rsFechas = stmtFechas.executeQuery();
                if (rsFechas.next()) {
                    fechaVencimiento = rsFechas.getDate("fecha_vencimiento").toLocalDate();
                }
            }

            // Calcular multa si la fecha de devolución es después de la fecha de vencimiento
            if (fechaActual.isAfter(fechaVencimiento)) {
                long diasAtraso = ChronoUnit.DAYS.between(fechaVencimiento, fechaActual);
                multaTotal = diasAtraso * multaDia;

                // Actualizar el saldo del usuario con la multa
                String queryUpdateSaldo = "UPDATE usuarios SET saldo = saldo + ? WHERE usuario_id = ?";
                try (PreparedStatement stmtUpdateSaldo = conn.prepareStatement(queryUpdateSaldo)) {
                    stmtUpdateSaldo.setFloat(1, multaTotal);
                    stmtUpdateSaldo.setInt(2, idUsuario);
                    stmtUpdateSaldo.executeUpdate();
                }
            }

            // Actualizar la disponibilidad del libro
            String queryUpdateDisponibles = "UPDATE libros SET disponibles = disponibles + 1 WHERE libro_id = (SELECT libro_id FROM prestamos WHERE prestamo_id = ?)";
            try (PreparedStatement stmtUpdateDisponibles = conn.prepareStatement(queryUpdateDisponibles)) {
                stmtUpdateDisponibles.setInt(1, idPrestamo);
                stmtUpdateDisponibles.executeUpdate();
            }
            
            // Insertar en historial_transacciones
            String queryInsertHistorial = "INSERT INTO historial_transacciones(usuario_id, accion) VALUES (?, 'devolucion de libro')";
            try (PreparedStatement stmtHistorial = conn.prepareStatement(queryInsertHistorial)) {
                stmtHistorial.setInt(1, idUsuario);
                stmtHistorial.executeUpdate();
            }

            // Confirmar la transacción
            conn.commit();
            btnDevolucion.setDisable(true);

            mostrarAlertaExito("Devolución Exitosa", "El libro ha sido devuelto correctamente.");
        } catch (SQLException e) {
            try {
                // Revertir la transacción en caso de error
                conn.rollback();
            } catch (SQLException rollbackEx) {
                rollbackEx.printStackTrace();
            }
            e.printStackTrace();
            mostrarAlertaError("Error en la Devolución", "Se produjo un error al devolver el libro.");
        } finally {
            try {
                conn.setAutoCommit(true);
            } catch (SQLException autoCommitEx) {
                autoCommitEx.printStackTrace();
            }
        }
    }
    
    
    private void mostrarAlertaError(String titulo, String mensaje) {
        Alert alerta = new Alert(Alert.AlertType.ERROR);
        alerta.setTitle("Error");
        alerta.setHeaderText(titulo);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }

    private void mostrarAlertaExito(String titulo, String mensaje) {
        Alert alerta = new Alert(Alert.AlertType.INFORMATION);
        alerta.setTitle("Éxito");
        alerta.setHeaderText(titulo);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }
    
}
