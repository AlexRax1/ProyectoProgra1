/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.mycompany.projectoprogra1fx;

import Modelo.ConexionDB;
import Modelo.Libros;
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
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import java.time.LocalDate;

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

//buscar
@FXML
private TextField bIdtxt; // id
@FXML
private TextField bNombretxt; // nombre
@FXML
private TextField bAutortxt;  // autor
@FXML
private TextField bEditorialtxt; // editorial
@FXML
private TextField nombretxt; // nombre
@FXML
private TextField autortxt;  // autor
@FXML
private TextField editorialtxt; // editorial
@FXML
private DatePicker  publicaciontxt;  // autor
@FXML
private Spinner<Integer> cantidadtxt; // editorial


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



    @Override
    public void initialize(URL url, ResourceBundle rb) {
        publicaciontxt.setValue(LocalDate.now());
        // TODO
        
        idColumna.setCellValueFactory(new PropertyValueFactory<>("libroId"));
        nombreColumna.setCellValueFactory(new PropertyValueFactory<>("titulo"));
        autorColumna.setCellValueFactory(new PropertyValueFactory<>("autor"));
        anoColumna.setCellValueFactory(new PropertyValueFactory<>("anoPublicacion"));
        editorialColumna.setCellValueFactory(new PropertyValueFactory<>("editorial"));
        cantidadColumna.setCellValueFactory(new PropertyValueFactory<>("disponibles"));

        librosList = FXCollections.observableArrayList();
        CargarLibros();
        tablaLibros.setItems(librosList);
        
        //buscar
        btnBuscar.setOnAction(e -> buscarLibros());
        btnReiniciar.setOnAction(e -> CargarLibros());
        
        SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, Integer.MAX_VALUE, 0);
        cantidadtxt.setValueFactory(valueFactory);
        
        btnLimpiar.setOnAction(e -> limpiarCampos());
        btnLimpiarb.setOnAction(e -> limpiarCamposBusqueda());
        btnAgregar.setOnAction(e -> agregarLibro());
        
        
    }    
    
    private void CargarLibros() {
        librosList.clear();

        Connection conn = ConexionDB.getConnection();
        String query = "SELECT * FROM \"libros\"";

        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                int libroId = rs.getInt("libro_id");
                String titulo = rs.getString("titulo");
                String autor = rs.getString("autor");
                LocalDate anoPublicacion = rs.getObject("anopublicacion", LocalDate.class);
                String editorial = rs.getString("editorial");
                int disponibles = rs.getInt("disponibles");

                librosList.add(new Libros(libroId, titulo, autor, anoPublicacion, editorial, disponibles));
            }

        } catch (Exception e) {
        }
    }
    
    private void buscarLibros() {
        librosList.clear();
        Connection conn = ConexionDB.getConnection();
        String id = bIdtxt.getText();
        String nombre = bNombretxt.getText();
        String autor = bAutortxt.getText();
        String editorial = bEditorialtxt.getText();

        String query = "SELECT * FROM \"libros\" WHERE 1=1";

        if (!id.isEmpty()) {
            query += " AND libro_id = ?";
        }
        if (!nombre.isEmpty()) {
            query += " AND LOWER(titulo) LIKE LOWER(?)";
        }
        if (!autor.isEmpty()) {
            query += " AND LOWER(autor) LIKE LOWER(?)";
        }
        if (!editorial.isEmpty()) {
            query += " AND LOWER(editorial) LIKE LOWER(?)";
        }

        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            int paramIndex = 1;
            if (!id.isEmpty()) {
                stmt.setInt(paramIndex++, Integer.parseInt(id));
            }
            if (!nombre.isEmpty()) {
                stmt.setString(paramIndex++, "%" + nombre.toLowerCase() + "%");
            }
            if (!autor.isEmpty()) {
                stmt.setString(paramIndex++, "%" + autor.toLowerCase() + "%");
            }
            if (!editorial.isEmpty()) {
                stmt.setString(paramIndex++, "%" + editorial.toLowerCase() + "%");
            }

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                int libroId = rs.getInt("libro_id");
                String titulo = rs.getString("titulo");
                String autorLibro = rs.getString("autor");
                LocalDate anoPub = rs.getObject("anopublicacion", LocalDate.class);
                String editorialLibro = rs.getString("editorial");
                int disponibles = rs.getInt("disponibles");
                
                librosList.add(new Libros(libroId, titulo, autorLibro, anoPub, editorialLibro, disponibles));
            }

        } catch (SQLException e) {
        }
    }
    
    private void limpiarCamposBusqueda() {
        bIdtxt.clear();
        bNombretxt.clear();
        bAutortxt.clear();
        bEditorialtxt.clear();
    }
    private void limpiarCampos() {
        nombretxt.clear();
        autortxt.clear();
        editorialtxt.clear();
        publicaciontxt.setValue(LocalDate.now());
        cantidadtxt.getValueFactory().setValue(0);// Limpia el contenido del Spinner
    }
    
    
    private void agregarLibro() {
        String titulo = nombretxt.getText();
        String autor = autortxt.getText();
        String editorial = editorialtxt.getText();
        LocalDate fechaPublicacion = publicaciontxt.getValue();
        int cantidad = cantidadtxt.getValue();

        Connection conn = ConexionDB.getConnection();
        String query = "INSERT INTO libros (titulo, autor, editorial, anopublicacion, disponibles) VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, titulo);
            stmt.setString(2, autor);
            stmt.setString(3, editorial);
            stmt.setDate(4, Date.valueOf(fechaPublicacion));
            stmt.setInt(5, cantidad);

            int filasAfectadas = stmt.executeUpdate();
            if (filasAfectadas > 0) {
                System.out.println("Libro agregado exitosamente a la base de datos.");
                limpiarCampos();
                CargarLibros();
            } else {
                System.out.println("Error al agregar el libro a la base de datos.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
