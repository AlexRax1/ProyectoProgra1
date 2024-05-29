/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.mycompany.projectoprogra1fx;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Tab;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author alex1
 */
public class AdministradorController implements Initializable {

    /**
     * Initializes the controller class.
     */
    
    
    
    @FXML
    private Tab PrestamosTab;
    @FXML
    private Tab PagosTab;
    @FXML
    private Tab DevolucionesTab;
    @FXML
    private Tab UsuariosTab;
    @FXML
    private Tab rLibrosTab;
    @FXML
    private Tab HistorialTab;

    @Override
    public void initialize(URL url, ResourceBundle rb) {   
         try {
            // Load Tab 1 content
            AnchorPane tab1Content = FXMLLoader.load(getClass().getResource("prestamos.fxml"));
            PrestamosTab.setContent(tab1Content);

            // Load Tab 2 content
            AnchorPane tab2Content = FXMLLoader.load(getClass().getResource("pagos.fxml"));
            PagosTab.setContent(tab2Content);

            // Load Tab 3 content
            AnchorPane tab3Content = FXMLLoader.load(getClass().getResource("devoluciones.fxml"));
            DevolucionesTab.setContent(tab3Content);
            
            // Load Tab 4 content
            AnchorPane tab4Content = FXMLLoader.load(getClass().getResource("usuarios.fxml"));
            UsuariosTab.setContent(tab4Content);

            // Load Tab 5 content
            AnchorPane tab5Content = FXMLLoader.load(getClass().getResource("rLibros.fxml"));
            rLibrosTab.setContent(tab5Content);

            // Load Tab 6 content
            AnchorPane tab7Content = FXMLLoader.load(getClass().getResource("historial.fxml"));
            HistorialTab.setContent(tab7Content);


        } catch (IOException e) {
            e.printStackTrace();
        }
    }    
    
}
