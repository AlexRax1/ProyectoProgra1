/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.mycompany.projectoprogra1fx;
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
import Modelo.ConexionDB;
import java.io.IOException;
import Modelo.UsuarioGlobal;
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
        private void eventAction(ActionEvent event) throws IOException {
            // Obtener el texto de los campos de texto
            String usuario = txtUsuario.getText();
            String contrasena = txtContrasena.getText();

            // Validar que los campos no estén vacíos
            if (usuario.isEmpty() || contrasena.isEmpty()) {
                showAlert(Alert.AlertType.ERROR, "Error", "Usuario o contraseña no pueden estar vacíos");
                return;
            }

            int usuario_id;
            try {
                usuario_id = Integer.parseInt(usuario);
            } catch (NumberFormatException e) {
                showAlert(Alert.AlertType.ERROR, "Error", "Usuario debe ser un número válido");
                return;
            }

            int resultado = autenticarUsuario(usuario_id, contrasena);

            UsuarioGlobal usuariog = UsuarioGlobal.getInstance();
            switch (resultado) {
                case 0:
                    usuariog.setIdUsuario(usuario_id);
                    App.setRoot("usuariop");
                    break;
                case 1:
                    usuariog.setIdUsuario(usuario_id);
                    App.setRoot("administrador");
                    break;
                default:
                    showAlert(Alert.AlertType.ERROR, "Error", "Usuario o contraseña incorrectos");
                    break;
            }
        }

        Connection conn = ConexionDB.getConnection();

        private int autenticarUsuario(int usuario, String contrasena) {
            boolean rol;
            String query = "SELECT es_administrador FROM \"usuarios\" WHERE usuario_id = ? AND contrasena = ?";
            try {
                PreparedStatement statement = conn.prepareStatement(query);
                statement.setInt(1, usuario);
                statement.setString(2, contrasena);
                ResultSet resultSet = statement.executeQuery();

                if (resultSet.next()) {
                    rol = resultSet.getBoolean("es_administrador");
                    System.out.println("Rol: " + rol);
                    System.out.println("Rol: " + rol);
                    System.out.println("Rol: " + rol);
                    System.out.println("Rol: " + rol);
                    int rol1 = rol ? 1 : 0;
                    return rol1;
                } else {
                    System.out.println("No se encontró el usuario o la contraseña es incorrecta.");
                    return 2;
                }
            } catch (SQLException e) {
                showAlert(Alert.AlertType.ERROR, "Error", "Error de conexión a la base de datos");
                e.printStackTrace();
                return 2;
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
            txtUsuario.addEventFilter(KeyEvent.KEY_TYPED, this::filterNonNumeric);
            txtContrasena.addEventFilter(KeyEvent.KEY_TYPED, this::filterSpace);
        }

        private void filterSpace(KeyEvent event) {
            if (event.getCharacter().equals(" ")) {
                event.consume(); // Consume el evento si es un espacio en blanco
            }
        }

        private void filterNonNumeric(KeyEvent event) {
            if (!event.getCharacter().matches("\\d")) {
                event.consume(); // Consume el evento si no es un dígito
            }
        }
    
}
