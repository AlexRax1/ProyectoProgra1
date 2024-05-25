/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.mycompany.projectoprogra1fx;

import Modelo.ConexionDB;
import Modelo.Libros;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * FXML Controller class
 *
 * @author alex1
 */


public class RLibrosController implements Initializable {

    /**
     * Initializes the controller class.
     */
    
    
@FXML
private TableView<Libros> tablaLibros;

@FXML
private TableColumn<Libros, Integer> idColumna;

@FXML
private TableColumn<Libros, String> nombreColumna;

@FXML
private TableColumn<Libros, String> autorColumna;

@FXML
private TableColumn<Libros, String> anoColumna;

@FXML
private TableColumn<Libros, String> editorialColumna;

@FXML
private TableColumn<Libros, Integer> cantidadColumna;

private ObservableList<Libros> librosList;

    /*
    public ObservableList<Map> getTodosLosLibros(){
        
        var sql  = "SELECT * FROM \"libros\";";
        ObservableList<Map> librosList = FXCollections.observableArrayList();
        try{
            Connection conn = ConexionDB.getConnection();
            PreparedStatement consulta = conn.prepareStatement(sql);
            ResultSet rs = consulta.executeQuery();
            
            while(rs.next()){
                Libros libros = new Libros();
            }
            
            rs.close();
            consulta.close();
        }
        
        
        
        return null; 
    }
    */
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        
        idColumna.setCellValueFactory(new PropertyValueFactory<>("libroId"));
        nombreColumna.setCellValueFactory(new PropertyValueFactory<>("titulo"));
        autorColumna.setCellValueFactory(new PropertyValueFactory<>("autor"));
        anoColumna.setCellValueFactory(new PropertyValueFactory<>("anoPublicacion"));
        editorialColumna.setCellValueFactory(new PropertyValueFactory<>("editorial"));
        cantidadColumna.setCellValueFactory(new PropertyValueFactory<>("disponibles"));

        librosList = FXCollections.observableArrayList();

        // Load data from database
        loadLibrosFromDatabase();

        tablaLibros.setItems(librosList);
        
        
        
    }    
    
    private void loadLibrosFromDatabase() {
        Connection conn = ConexionDB.getConnection();
        String query = "SELECT * FROM \"libros\"";

        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                int libroId = rs.getInt("libro_id");
                String titulo = rs.getString("titulo");
                String autor = rs.getString("autor");
                String anoPublicacion = rs.getDate("anopublicacion").toString();
                String editorial = rs.getString("editorial");
                int disponibles = rs.getInt("disponibles");

                librosList.add(new Libros(libroId, titulo, autor, anoPublicacion, editorial, disponibles));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
}
