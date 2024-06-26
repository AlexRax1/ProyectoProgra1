/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.mycompany.projectoprogra1fx;

import Modelo.ConexionDB;
import Modelo.Libros;
import Modelo.UsuarioGlobal;
import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author alex1
 */
public class EditarLibroController implements Initializable{

@FXML
private TextField idtxt;
@FXML
private TextField nombretxt;
@FXML
private TextField autortxt;
@FXML
private DatePicker fechatxt;
@FXML
private TextField editorialtxt;
@FXML
private Button btnCancelar;
@FXML
private Button btnAceptar;


    // Otros campos de texto y controles necesarios

    private Libros libroSeleccionado;

    
    public void setLibroSeleccionado(Libros libro) {
        this.libroSeleccionado = libro;
        mostrarDetalles();
    }

    public void initialize(URL url, ResourceBundle rb) {

        btnCancelar.setOnAction(e -> cancelar());
        btnAceptar.setOnAction(e -> guardar());

    }
    

    private void mostrarDetalles() {
        // Mostrar los detalles del libro en los campos de texto
        if (libroSeleccionado != null) {
            idtxt.setText(String.valueOf(libroSeleccionado.getLibroId()));//esto tiene que ser un entero
            nombretxt.setText(libroSeleccionado.getTitulo());
            autortxt.setText(libroSeleccionado.getAutor());
            fechatxt.setValue(libroSeleccionado.getAnoPublicacion());//esto es un localDate
            editorialtxt.setText(libroSeleccionado.getEditorial());
            // Otros campos de texto
        }
        
    }

    private void guardar() {
        // Recoger los datos actualizados de los campos de texto
        String titulo = nombretxt.getText();
        String autor = autortxt.getText();
        LocalDate fechaPublicacion = fechatxt.getValue();
        String editorial = editorialtxt.getText();
        int libroId = Integer.parseInt(idtxt.getText());

        Connection conn = ConexionDB.getConnection();
        String query = "UPDATE \"libros\" SET titulo = ?, autor = ?, anopublicacion = ?, editorial = ? WHERE libro_id = ?";

        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, titulo);
            stmt.setString(2, autor);
            stmt.setDate(3, Date.valueOf(fechaPublicacion));
            stmt.setString(4, editorial);
            stmt.setInt(5, libroId);

            int filasAfectadas = stmt.executeUpdate();
            if (filasAfectadas > 0) {
                // Insertar en historial_transacciones
                    UsuarioGlobal usuariog = UsuarioGlobal.getInstance();
                    int usuarioga = usuariog.getIdUsuario();
    String queryInsertHistorial = "INSERT INTO historial_transacciones (usuario_id, accion) VALUES (?, 'se edito el libro: ' || ?);";
                    try (PreparedStatement stmtHistorial = conn.prepareStatement(queryInsertHistorial)) {
                        stmtHistorial.setInt(1, usuarioga);
                        stmtHistorial.setInt(2, libroId);
                        stmtHistorial.executeUpdate();
                    }
                
                
                mostrarAlertaExito("Exito", "El libro se edito correctamente.");
            } else {
                mostrarAlertaError("Error", "Error al actualizar el libro.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Cerrar la ventana
        Stage stage = (Stage) nombretxt.getScene().getWindow();
        stage.close();
    }

    private void cancelar() {
        // Cerrar la ventana sin guardar cambios
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
