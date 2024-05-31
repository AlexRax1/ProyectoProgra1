/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.mycompany.projectoprogra1fx;

import Modelo.ConexionDB;
import Modelo.Libros;
import java.io.IOException;
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
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextFormatter;
import javafx.stage.Modality;
import javafx.stage.Stage;

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
private TextField bIdtxt; 
@FXML
private TextField bNombretxt; 
@FXML
private TextField bAutortxt;  
@FXML
private TextField bEditorialtxt; 

//agregar
@FXML
private TextField nombretxt;
@FXML
private TextField autortxt;  
@FXML
private TextField editorialtxt; 
@FXML
private DatePicker  publicaciontxt;  
@FXML
private Spinner<Integer> cantidadtxt; 


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
private Button btnAnadir;
@FXML
private Button btnEditar;
@FXML
private Spinner<Integer> acantidadtxt;



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
        
        
        //que no se pueda escribir texto
        SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, Integer.MAX_VALUE, 0);
        cantidadtxt.setValueFactory(valueFactory);
        cantidadtxt.getEditor().setTextFormatter(new TextFormatter<>(c -> {
            if (c.getControlNewText().matches("\\d*")) {
                return c;
            } else {
                return null;
            }
        }));

        SpinnerValueFactory<Integer> valueFactory1 = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, Integer.MAX_VALUE, 0);
        acantidadtxt.setValueFactory(valueFactory1);
        acantidadtxt.getEditor().setTextFormatter(new TextFormatter<>(c -> {
            if (c.getControlNewText().matches("\\d*")) {
                return c;
            } else {
                return null;
            }
        }));
        
   
        btnLimpiar.setOnAction(e -> limpiarCampos());
        btnLimpiarb.setOnAction(e -> limpiarCamposBusqueda());
        btnAgregar.setOnAction(e -> agregarLibro());
        
        
        btnAnadir.setOnAction(e -> AnadirInventario());
        btnEditar.setOnAction(e -> editar());
        
        //al seleccionar un libro
        tablaLibros.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
        if (newValue != null) {
            int idSeleccionado = newValue.getLibroId();
            System.out.println("Libro ID seleccionado: " + idSeleccionado);
            activarBotones();
        }
        });
    }    
    
    
    private void activarBotones() {
        btnAnadir.setDisable(false);
        btnEditar.setDisable(false);
        acantidadtxt.setDisable(false);
    }
    private void desactivarBotones() {
        btnAnadir.setDisable(true);
        btnEditar.setDisable(true);
        acantidadtxt.setDisable(true);
    }
    
    private void CargarLibros() {
        librosList.clear();

        Connection conn = ConexionDB.getConnection();
        String query = "SELECT * FROM \"libros\" ORDER BY libro_id";

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
                desactivarBotones();
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

        StringBuilder query = new StringBuilder("SELECT * FROM \"libros\" WHERE 1=1");

        // Añadir condiciones basadas en la entrada del usuario
        if (!id.isEmpty()) {
            query.append(" AND libro_id = ?");
        }
        if (!nombre.isEmpty()) {
            query.append(" AND LOWER(titulo) LIKE LOWER(?)");
        }
        if (!autor.isEmpty()) {
            query.append(" AND LOWER(autor) LIKE LOWER(?)");
        }
        if (!editorial.isEmpty()) {
            query.append(" AND LOWER(editorial) LIKE LOWER(?)");
        }

        query.append(" ORDER BY libro_id");

        try (PreparedStatement stmt = conn.prepareStatement(query.toString())) {
            int paramIndex = 1;

            // Asignar valores a los parámetros en el PreparedStatement
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
            desactivarBotones();
        } catch (SQLException e) {
            e.printStackTrace();
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
    /*por si se ocupa eliminar en tablas
    private void eliminarLibro() {
        Libros libroSeleccionado = tablaLibros.getSelectionModel().getSelectedItem();
        if (libroSeleccionado != null) {
            int idSeleccionado = libroSeleccionado.getLibroId();
            Connection conn = ConexionDB.getConnection();
            String query = "DELETE FROM libros WHERE libro_id = ?";
            try (PreparedStatement stmt = conn.prepareStatement(query)) {
                stmt.setInt(1, idSeleccionado);
                int filasAfectadas = stmt.executeUpdate();
                if (filasAfectadas > 0) {
                    System.out.println("Libro eliminado exitosamente de la base de datos.");
                    CargarLibros();
                } else {
                    System.out.println("Error al eliminar el libro de la base de datos.");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("No hay ningún libro seleccionado.");
        }
    }*/
    
    private void AnadirInventario() {
        Libros libroSeleccionado = tablaLibros.getSelectionModel().getSelectedItem();
        int cant = acantidadtxt.getValue();
        if (libroSeleccionado != null && cant != 0) {
            int idSeleccionado = libroSeleccionado.getLibroId();
            Connection conn = ConexionDB.getConnection();
            String query = "UPDATE \"libros\" SET disponibles = disponibles + ? WHERE libro_id = ?";
            try (PreparedStatement stmt = conn.prepareStatement(query)) {
                stmt.setInt(1, cant);
                stmt.setInt(2, idSeleccionado);
                int filasAfectadas = stmt.executeUpdate();
                if (filasAfectadas > 0) {
                    System.out.println("Inventario anadido exitosamente");
                    
                    CargarLibros();
                } else {
                    System.out.println("No se pudo añadir inventario");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("No se ha ingresado una cantidad valida");
        }
    }
    
    
    
    
    private void editar() {
        Libros libroSeleccionado = tablaLibros.getSelectionModel().getSelectedItem();
        if (libroSeleccionado != null) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("editarLibro.fxml"));
                Parent root = loader.load();

                EditarLibroController controller = loader.getController();
                controller.setLibroSeleccionado(libroSeleccionado);

                Stage stage = new Stage();
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.setScene(new Scene(root));
                stage.showAndWait();
                
                
                CargarLibros();

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    
    
}
