/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.mycompany.projectoprogra1fx;

import Modelo.ConexionDB;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author alex1
 */
public class PagosController implements Initializable {

    /**
     * Initializes the controller class.
     */
    
@FXML
private TextField idtxt;
@FXML
private TextField nombretxt;
@FXML
private TextField telefonotxt;
@FXML
private TextField emailtxt; 
@FXML
private TextField saldotxt;
@FXML
private TextField saldotxt1;

    
@FXML
private Button btnBuscar;
@FXML
private Button btnAceptar;
    
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        
        
        
        btnBuscar.setOnAction(e -> buscarUsuarios());
        btnAceptar.setOnAction(e -> hacerPago());
        
    }    
    
    private void activarBotones() {
        btnAceptar.setDisable(false);
    }
    private void desactivarBotones() {
        btnAceptar.setDisable(true);
    }
    
    
    private void buscarUsuarios() {
        String id = idtxt.getText().trim();

        // Verificar si el campo de texto idtxt está vacío
        if (id.isEmpty()) {
            mostrarAlertaError("Campo vacío", "Por favor, ingrese un ID de usuario.");
            return;
        }

        Connection conn = ConexionDB.getConnection();
        String query = "SELECT * FROM \"usuarios\" WHERE usuario_id = ?";

        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, Integer.parseInt(id));

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                idtxt.setText(String.valueOf(rs.getInt("usuario_id")));
                nombretxt.setText(rs.getString("nombre"));
                telefonotxt.setText(rs.getString("telefono"));
                emailtxt.setText(rs.getString("email"));
                saldotxt.setText(String.valueOf(rs.getDouble("saldo")));
                saldotxt1.setText(String.valueOf(rs.getDouble("saldo")));

                activarBotones();
            } else {
                desactivarBotones();
                mostrarAlertaError("Usuario no encontrado", "No se encontró un usuario con el ID especificado.");
            }
        } catch (SQLException e) {
            mostrarAlertaError("Error en la búsqueda", "Se produjo un error al buscar el usuario.");
        }
    }

    private void hacerPago() {
        int usuarioId = Integer.parseInt(idtxt.getText());
        double pago = Double.parseDouble(saldotxt1.getText());

        Connection conn = ConexionDB.getConnection();
        String query = "UPDATE \"usuarios\" SET saldo = saldo - ? WHERE usuario_id = ?";

        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setDouble(1, pago);
            stmt.setInt(2, usuarioId);

            int filasAfectadas = stmt.executeUpdate();
            if (filasAfectadas > 0) {
                limpiarCampos();
                mostrarAlertaExito("Pago realizado", "El pago se realizó correctamente.");
            } else {
                mostrarAlertaError("Error en el pago", "No se pudo realizar el pago.");
            }
        } catch (SQLException e) {
            mostrarAlertaError("Error en el pago", "Se produjo un error al realizar el pago.");
        }
        desactivarBotones();
    }

    private void mostrarAlertaError(String titulo, String mensaje) {
        Alert alerta = new Alert(AlertType.ERROR);
        alerta.setTitle("Error");
        alerta.setHeaderText(titulo);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }

    private void mostrarAlertaExito(String titulo, String mensaje) {
        Alert alerta = new Alert(AlertType.INFORMATION);
        alerta.setTitle("Éxito");
        alerta.setHeaderText(titulo);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }
    
    
    private void limpiarCampos() {
        nombretxt.clear();
        idtxt.clear();
        telefonotxt.clear();
        emailtxt.clear();
        saldotxt.clear();
        saldotxt1.clear();
    }
    
}
