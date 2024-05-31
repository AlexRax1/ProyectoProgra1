/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.mycompany.projectoprogra1fx;

import Modelo.ConexionDB;
import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
public class DevolucionesController implements Initializable {

    /**
     * Initializes the controller class.
     */
    
    
//buscar
@FXML
private TextField bIdtxt; 
@FXML
private TextField bNombretxt; 
@FXML
private TextField bTelefonotxt;  
@FXML
private TextField bEmailtxt;

@FXML
private Button btnBuscar;
@FXML
private Button btnLimpiarb;
@FXML
private Button btnDevolucion;



    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        
        btnBuscar.setOnAction( e -> buscarUsuario());
        btnLimpiarb.setOnAction( e -> limpiar());
        btnDevolucion.setOnAction( e -> devolver());
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
                String queryPrestamos = "SELECT fecha_devolucion FROM \"prestamos\" WHERE usuario_id = ?";
                try (PreparedStatement stmtPrestamos = conn.prepareStatement(queryPrestamos)) {
                    stmtPrestamos.setInt(1, Integer.parseInt(id));

                    ResultSet rsPrestamos = stmtPrestamos.executeQuery();

                    boolean tienePrestamosPendientes = false;
                    while (rsPrestamos.next()) {
                        Date fechaDevolucion = rsPrestamos.getDate("fecha_devolucion");
                        if (fechaDevolucion == null) {
                            tienePrestamosPendientes = true;
                            break;
                        }
                    }
                    if (tienePrestamosPendientes) {
                        bIdtxt.setEditable(false);
                        bNombretxt.setText(rsUsuario.getString("nombre"));
                        bTelefonotxt.setText(rsUsuario.getString("telefono"));
                        bEmailtxt.setText(rsUsuario.getString("email"));
                    } else {
                        mostrarAlertaError("Sin préstamos pendientes", "El usuario no tiene préstamos pendientes.");
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
    
    
    private void devolver(){
        int idUsuario = Integer.parseInt(bIdtxt.getText());
        
        
        
        
    }
    
    private void limpiar(){
        bIdtxt.setEditable(true);
        bIdtxt.clear();
        bNombretxt.clear();
        bTelefonotxt.clear();
        bEmailtxt.clear();
        
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
