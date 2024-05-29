/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.mycompany.projectoprogra1fx;

import Modelo.ConexionDB;
import Modelo.Libros;
import Modelo.Usuarios;
import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Spinner;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author alex1
 */
public class UsuariosController implements Initializable {

    /**
     * Initializes the controller class.
     */
@FXML
private TableView<Usuarios> tablaUsuarios;

@FXML
private TableColumn<Usuarios, Integer> idColumna;

@FXML
private TableColumn<Usuarios, String> nombreColumna;

@FXML
private TableColumn<Usuarios, String> direccionColumna;

@FXML
private TableColumn<Usuarios, String> telefonoColumna;

@FXML
private TableColumn<Usuarios, String> emailColumna;

@FXML
private TableColumn<Usuarios, Float> saldoColumna;
@FXML
private TableColumn<Usuarios, String> contrasenaColumna;
@FXML
private TableColumn<Usuarios, Boolean> administradorColumna;
private ObservableList<Usuarios> usuariosList;


//buscar
@FXML
private TextField bIdtxt; 
@FXML
private TextField bNombretxt; 
@FXML
private TextField bTelefonotxt;  
@FXML
private TextField bEmailtxt;


//agregar
@FXML
private TextField nombretxt;
@FXML
private TextField direcciontxt;  
@FXML
private TextField telefonotxt; 
@FXML
private TextField  emailtxt;  
@FXML
private PasswordField  contrasenatxt;  



@FXML
private Button btnAgregar;
@FXML
private Button btnBuscar;
@FXML
private Button btnReiniciar;



@FXML
private Button btnLimpiar;
@FXML
private Button btnLimpiarb;


@FXML
private Button btnEditar;



    @Override
    public void initialize(URL url, ResourceBundle rb) {
        idColumna.setCellValueFactory(new PropertyValueFactory<>("usuario_id"));
        nombreColumna.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        direccionColumna.setCellValueFactory(new PropertyValueFactory<>("direccion"));
        telefonoColumna.setCellValueFactory(new PropertyValueFactory<>("telefono"));
        emailColumna.setCellValueFactory(new PropertyValueFactory<>("email"));
        saldoColumna.setCellValueFactory(new PropertyValueFactory<>("saldo"));
        contrasenaColumna.setCellValueFactory(new PropertyValueFactory<>("contrasena"));
        administradorColumna.setCellValueFactory(new PropertyValueFactory<>("esAdministrador"));

        
        // Inicializar la lista de usuarios y cargarla en la tabla
        usuariosList = FXCollections.observableArrayList();
        CargarUsuarios(); // Método para cargar los datos en usuariosList
        tablaUsuarios.setItems(usuariosList);

        //buscar
        btnBuscar.setOnAction(e -> buscarUsuarios());
        btnReiniciar.setOnAction(e -> CargarUsuarios());
        
        btnLimpiar.setOnAction(e -> limpiarCampos());
        btnLimpiarb.setOnAction(e -> limpiarCamposBusqueda());
        btnAgregar.setOnAction(e -> agregarUsuario());
        
        
        btnEditar.setOnAction(e -> editarUsuario());
        
        //al seleccionar un libro
        tablaUsuarios.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
        if (newValue != null) {
            int idSeleccionado = newValue.getUsuario_id();
            System.out.println("Usuario ID seleccionado: " + idSeleccionado);
            activarBotones();
        }
        });
              
    }    
    
    
    private void activarBotones() {
        btnEditar.setDisable(false);
    }
    private void desactivarBotones() {
        btnEditar.setDisable(true);
    }
    
    private void CargarUsuarios() {
        usuariosList.clear();

        Connection conn = ConexionDB.getConnection();
        String query = "SELECT * FROM \"usuarios\" ORDER BY usuario_id";

        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                int usuarioId = rs.getInt("usuario_id");
                String nombre = rs.getString("nombre");
                String direccion = rs.getString("direccion");
                String telefono = rs.getString("telefono");
                String email = rs.getString("email");
                double saldo = rs.getDouble("saldo");
                String contrasena = rs.getString("contrasena");
                boolean esAdministrador = rs.getBoolean("es_administrador");

                usuariosList.add(new Usuarios(usuarioId, nombre, direccion, telefono, email, saldo, contrasena, esAdministrador));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    private void buscarUsuarios() {
        usuariosList.clear();
        Connection conn = ConexionDB.getConnection();
        String id = bIdtxt.getText();
        String nombre = bNombretxt.getText();
        String telefono = bTelefonotxt.getText();
        String email = bEmailtxt.getText();

        StringBuilder query = new StringBuilder("SELECT * FROM \"usuarios\" WHERE 1=1");

        // Añadir condiciones basadas en la entrada del usuario
        if (!id.isEmpty()) {
            query.append(" AND usuario_id = ?");
        }
        if (!nombre.isEmpty()) {
            query.append(" AND LOWER(nombre) LIKE LOWER(?)");
        }
        if (!telefono.isEmpty()) {
            query.append(" AND telefono LIKE ?");
        }
        if (!email.isEmpty()) {
            query.append(" AND LOWER(email) LIKE LOWER(?)");
        }

        query.append(" ORDER BY usuario_id");

        try (PreparedStatement stmt = conn.prepareStatement(query.toString())) {
            int paramIndex = 1;

            // Asignar valores a los parámetros en el PreparedStatement
            if (!id.isEmpty()) {
                stmt.setInt(paramIndex++, Integer.parseInt(id));
            }
            if (!nombre.isEmpty()) {
                stmt.setString(paramIndex++, "%" + nombre.toLowerCase() + "%");
            }
            if (!telefono.isEmpty()) {
                stmt.setString(paramIndex++, "%" + telefono + "%");
            }
            if (!email.isEmpty()) {
                stmt.setString(paramIndex++, "%" + email.toLowerCase() + "%");
            }

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                int usuarioId = rs.getInt("usuario_id");
                String nombreUsuario = rs.getString("nombre");
                String direccion = rs.getString("direccion");
                String telefonoUsuario = rs.getString("telefono");
                String emailUsuario = rs.getString("email");
                double saldo = rs.getDouble("saldo");
                String contrasena = rs.getString("contrasena");
                boolean esAdministrador = rs.getBoolean("es_administrador");

                usuariosList.add(new Usuarios(usuarioId, nombreUsuario, direccion, telefonoUsuario, emailUsuario, saldo, contrasena, esAdministrador));
            }
            desactivarBotones();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    private void limpiarCamposBusqueda() {
        bIdtxt.clear();
        bNombretxt.clear();
        bTelefonotxt.clear();
        bEmailtxt.clear();
    }
    
    private void limpiarCampos() {
        nombretxt.clear();
        direcciontxt.clear();
        telefonotxt.clear();
        emailtxt.clear();
        contrasenatxt.clear();
    }
    
    private void agregarUsuario() {
        String nombre = nombretxt.getText();
        String direccion = direcciontxt.getText();
        String telefono = telefonotxt.getText();
        String email = emailtxt.getText();
        String contrasena = contrasenatxt.getText();

        Connection conn = ConexionDB.getConnection();
        String query = "INSERT INTO usuarios (nombre, direccion, telefono, email, contrasena) VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, nombre);
            stmt.setString(2, direccion);
            stmt.setString(3, telefono);
            stmt.setString(4, email);
            stmt.setString(5, contrasena);

            int filasAfectadas = stmt.executeUpdate();
            if (filasAfectadas > 0) {
                System.out.println("Usuario agregado exitosamente a la base de datos.");
                limpiarCampos();
                CargarUsuarios(); // Actualiza la lista de usuarios después de agregar uno nuevo
            } else {
                System.out.println("Error al agregar el usuario a la base de datos.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    
    private void editarUsuario() {
        Usuarios usuarioSeleccionado = tablaUsuarios.getSelectionModel().getSelectedItem();
        if (usuarioSeleccionado != null) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("editarUsuario.fxml"));
                Parent root = loader.load();

                EditarUsuarioController controller = loader.getController();
                controller.setUsuarioSeleccionado(usuarioSeleccionado);

                Stage stage = new Stage();
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.setScene(new Scene(root));
                stage.showAndWait();

                CargarUsuarios(); // Actualiza la lista de usuarios después de editar uno

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
   
    
}
