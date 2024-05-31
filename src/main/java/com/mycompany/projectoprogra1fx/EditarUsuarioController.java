/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.mycompany.projectoprogra1fx;

import Modelo.ConexionDB;
import Modelo.UsuarioGlobal;
import Modelo.Usuarios;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author alex1
 */
public class EditarUsuarioController implements Initializable {

@FXML
private TextField idtxt;
@FXML
private TextField nombretxt;
@FXML
private TextField direcciontxt;
@FXML
private TextField telefonotxt;
@FXML
private TextField emailtxt;
@FXML
private PasswordField contrasenatxt;
@FXML
private Button btnCancelar;
@FXML
private Button btnAceptar;
@FXML
private CheckBox esAdmin;

    private Usuarios usuarioSeleccionado;

    public void setUsuarioSeleccionado(Usuarios usuario) {
        this.usuarioSeleccionado = usuario;
        mostrarDetalles();
    }

    @FXML
    public void initialize(URL url, ResourceBundle rb) {
        btnCancelar.setOnAction(e -> cancelar());
        btnAceptar.setOnAction(e -> guardar());
    }

    private void mostrarDetalles() {
        if (usuarioSeleccionado != null) {
            idtxt.setText(String.valueOf(usuarioSeleccionado.getUsuario_id()));
            nombretxt.setText(usuarioSeleccionado.getNombre());
            direcciontxt.setText(usuarioSeleccionado.getDireccion());
            telefonotxt.setText(usuarioSeleccionado.getTelefono());
            emailtxt.setText(usuarioSeleccionado.getEmail());
            contrasenatxt.setText(usuarioSeleccionado.getContrasena());
            esAdmin.setSelected(usuarioSeleccionado.isEs_administrador());
        }
    }

    private void guardar() {
        String nombre = nombretxt.getText();
        String direccion = direcciontxt.getText();
        String telefono = telefonotxt.getText();
        String email = emailtxt.getText();
        String contrasena = contrasenatxt.getText();
        int usuarioId = Integer.parseInt(idtxt.getText());
        boolean esAdministrador = esAdmin.isSelected();

        Connection conn = ConexionDB.getConnection();
        String query = "UPDATE \"usuarios\" SET nombre = ?, direccion = ?, telefono = ?, email = ?, contrasena = ?, es_administrador = ? WHERE usuario_id = ?";

        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, nombre);
            stmt.setString(2, direccion);
            stmt.setString(3, telefono);
            stmt.setString(4, email);
            stmt.setString(5, contrasena);
            stmt.setBoolean(6, esAdministrador);
            stmt.setInt(7, usuarioId);

            int filasAfectadas = stmt.executeUpdate();
            if (filasAfectadas > 0) {
                
                // Insertar en historial_transacciones
                String queryInsertHistorial = "INSERT INTO historial_transacciones(usuario_id, accion) VALUES (?, 'Se edito la informacion')";
                try (PreparedStatement stmtHistorial = conn.prepareStatement(queryInsertHistorial)) {
                    stmtHistorial.setInt(1, usuarioId);
                    stmtHistorial.executeUpdate();
                }
                
                
                
                mostrarAlertaExito("Exito", "Se actualizaron los datos correctamente");
            } else {
                mostrarAlertaError("Error", "Se produjo un error al editar los datos");
            }
        } catch (SQLException e) {
            mostrarAlertaError("Error", "Se produjo un error al editar los datos");
            e.printStackTrace();
        }

        // Cerrar la ventana
        Stage stage = (Stage) nombretxt.getScene().getWindow();
        stage.close();
    }

    private void cancelar() {
        Stage stage = (Stage) nombretxt.getScene().getWindow();
        stage.close();
    }   
    
    
    private void mostrarAlertaError(String titulo, String mensaje) {
        Alert alerta = new Alert(Alert.AlertType.ERROR);
        alerta.setTitle("Error");
        alerta.setHeaderText(titulo);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
        //mostrarAlertaError("Error", "Se produjo un error en la base de datos.");

    }

    private void mostrarAlertaExito(String titulo, String mensaje) {
        Alert alerta = new Alert(Alert.AlertType.INFORMATION);
        alerta.setTitle("Éxito");
        alerta.setHeaderText(titulo);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
        //mostrarAlertaExito("Exito", "Se produjo un error en la base de datos.");

    }
    
    
}
