/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.mycompany.projectoprogra1fx;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import Modelo.ConexionDB;
import Modelo.UsuarioGlobal;
import static com.mycompany.projectoprogra1fx.AdministradorController.cerrar;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author alex1
 */
public class UsuariopController implements Initializable {
@FXML
private Tab PrestamosTab;
@FXML
private Tab PagosTab;
@FXML
private Tab DevolucionesTab;
@FXML
private Tab HistorialTab;


@FXML
private Label gusuariotxt;
@FXML
private Label gnombretxt;
@FXML
private Button btnCerrar;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            // Load Tab 1 content
            AnchorPane tab1Content = FXMLLoader.load(getClass().getResource("uprestamos.fxml"));
            PrestamosTab.setContent(tab1Content);

            // Load Tab 2 content
            AnchorPane tab2Content = FXMLLoader.load(getClass().getResource("upagos.fxml"));
            PagosTab.setContent(tab2Content);

            // Load Tab 3 content
            AnchorPane tab3Content = FXMLLoader.load(getClass().getResource("udevoluciones.fxml"));
            DevolucionesTab.setContent(tab3Content);
            
            // Load Tab 4 content

            AnchorPane tab4Content = FXMLLoader.load(getClass().getResource("uhistorial.fxml"));
            HistorialTab.setContent(tab4Content);
            
            
            
            
            UsuarioGlobal usuario = UsuarioGlobal.getInstance();
            int valorActual = usuario.getIdUsuario();
            gusuariotxt.setText(valorActual + "");
            gnombretxt.setText(obtenerNombre(valorActual));
            
            btnCerrar.setOnAction( e -> cerrar());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }    
    
    public String obtenerNombre(int usuarioId) {
        String nombreUsuario = null;
        Connection conn = ConexionDB.getConnection(); 

        String query = "SELECT nombre FROM \"usuarios\" WHERE usuario_id = ?";

        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, usuarioId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                nombreUsuario = rs.getString("nombre");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return nombreUsuario;
    }
    
    
   public static void cerrar(){
    try {
        App.setRoot("inicio");
    } catch (IOException ex) {
        Logger.getLogger(AdministradorController.class.getName()).log(Level.SEVERE, null, ex);
    }

   }
    
}
