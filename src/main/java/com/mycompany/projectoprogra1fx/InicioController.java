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
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;

/**
 * FXML Controller class
 *
 * @author alex1
 */
public class InicioController implements Initializable {

    /**
     * Initializes the controller class.
     */
    
    @FXML
    private TextField txtUsuario;
    
    @FXML
    private PasswordField txtContrasena;
    
    @FXML
    private Button btnLogin;
    
    
    
   
    @FXML
    private void eventAction(ActionEvent event) {
        // Obtener el texto de los campos de texto
        
        int usuario = Integer.parseInt(txtUsuario.getText());
        String contrasena = txtContrasena.getText();

        // Validar que los campos no estén vacíos
        if (usuario.isEmpty() || contrasena.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Error", "Usuario o contraseña no pueden estar vacíos");
            return;
        }

        // Hacer la consulta a la base de datos
        if (autenticarUsuario(usuario, contrasena)) {
            showAlert(Alert.AlertType.INFORMATION, "Éxito", "Inicio de sesión exitoso");
            System.out.println("se ingreso");
            // Aquí puedes redirigir a otra escena o realizar alguna acción adicional
        } else {
            System.out.println("no se ingreso");

            showAlert(Alert.AlertType.ERROR, "Error", "Usuario o contraseña incorrectos");
        }
    }
    
    private boolean autenticarUsuario(Integer usuario, String contrasena) {
 
        String query = "SELECT * FROM \"usuarios\" WHERE usuario_id = ? AND contrasena = ?";
        try (Connection conn = ConexionDB.getConnection();
            PreparedStatement statement = conn.prepareStatement(query)) {
            statement.setInt(1, usuario);
            statement.setString(2, contrasena);
            ResultSet resultSet = statement.executeQuery();
            System.out.println("hasta aca esta corrceto");
            return resultSet.next(); // Retorna true si hay una fila, false de lo contrario
        } catch (SQLException e) {
            showAlert(Alert.AlertType.ERROR, "Error", "Error de conexión a la base de datos");
            return false;
        }
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Añadir el filtro de eventos a los campos de texto y contraseña
        txtUsuario.addEventFilter(KeyEvent.KEY_TYPED, this::filterSpace);
        txtContrasena.addEventFilter(KeyEvent.KEY_TYPED, this::filterSpace);
    }

    private void filterSpace(KeyEvent event) {
        if (event.getCharacter().equals(" ")) {
            event.consume(); // Consume el evento si es un espacio en blanco
        }
    } 
    
    
    
}
