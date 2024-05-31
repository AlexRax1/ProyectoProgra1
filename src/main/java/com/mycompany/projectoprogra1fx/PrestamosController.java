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
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Spinner;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * FXML Controller class
 *
 * @author alex1
 */
public class PrestamosController implements Initializable {

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

//usuarios
@FXML
private TextField nombretxt;
@FXML
private TextField idUsuariotxt;  



@FXML
private Button btnPrestamo;
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
        
        
        btnLimpiar.setOnAction(e -> limpiarCampos());
        btnLimpiarb.setOnAction(e -> limpiarCamposBusqueda());
        btnPrestamo.setOnAction(e -> agregarLibro());
        
        tablaLibros.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
        if (newValue != null) {
            int idSeleccionado = newValue.getLibroId();
            System.out.println("Libro ID seleccionado: " + idSeleccionado);
            activarBotones();
        }
        });
    }    
    
    
    private void activarBotones() {
        btnPrestamo.setDisable(false);
    }
    private void desactivarBotones() {
        btnPrestamo.setDisable(true);

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
        idUsuariotxt.clear();
    }
    
    private void agregarLibro() {
        Libros libroSeleccionado = tablaLibros.getSelectionModel().getSelectedItem();
        int idSeleccionado = libroSeleccionado.getLibroId();
        int idUsuario = Integer.parseInt(idUsuariotxt.getText());
        LocalDate fVencimiento = LocalDate.now().plusDays(15);

        Connection conn = ConexionDB.getConnection();

        try {
            // Verificar el saldo del usuario
            String saldoQuery = "SELECT saldo FROM usuarios WHERE usuario_id = ?";
            try (PreparedStatement saldoStmt = conn.prepareStatement(saldoQuery)) {
                saldoStmt.setInt(1, idUsuario);
                ResultSet saldoRs = saldoStmt.executeQuery();
                if (saldoRs.next()) {
                    double saldo = saldoRs.getDouble("saldo");

                    // Verificar las fechas de devolución
                    String devolucionQuery = "SELECT fecha_devolucion FROM prestamos WHERE usuario_id = ?";
                    try (PreparedStatement devolucionStmt = conn.prepareStatement(devolucionQuery)) {
                        devolucionStmt.setInt(1, idUsuario);
                        ResultSet devolucionRs = devolucionStmt.executeQuery();

                        boolean allDevolucionesPresent = true;
                        while (devolucionRs.next()) {
                            Date fechaDevolucion = devolucionRs.getDate("fecha_devolucion");
                            if (fechaDevolucion == null) {
                                allDevolucionesPresent = false;
                                break;
                            }
                        }

                        // Si el saldo es <= 0 y todas las fechas de devolución están presentes
                        if (saldo <= 0 && allDevolucionesPresent) {
                            String insertQuery = "INSERT INTO prestamos (libro_id, usuario_id, fecha_vencimiento) VALUES (?, ?, ?)";
                            try (PreparedStatement insertStmt = conn.prepareStatement(insertQuery)) {
                                insertStmt.setInt(1, idSeleccionado);
                                insertStmt.setInt(2, idUsuario);
                                insertStmt.setDate(3, Date.valueOf(fVencimiento));

                                int filasAfectadas = insertStmt.executeUpdate();
                                if (filasAfectadas > 0) {
                                    System.out.println("Libro agregado exitosamente a la base de datos.");
                                    mostrarAlertaExito("Exito", "Prestamo agregado exitosamente a la base de datos.");
                                    limpiarCampos();
                                    CargarLibros();
                                } else {
                                    System.out.println("Error al agregar el libro a la base de datos.");
                                }
                            }
                        } else {
                            if (saldo > 0) {
                                mostrarAlertaError("Error", "El usuario tiene un saldo positivo.");
                            }
                            if (!allDevolucionesPresent) {
                                mostrarAlertaError("Error", "El usuario tiene préstamos sin devolver.");
                            }
                        }
                    }
                } else {
                    mostrarAlertaError("Error", "Usuario no encontrado.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            mostrarAlertaError("Error", "Se produjo un error en la base de datos.");
        }
    }
    
    
    private void mostrarAlertaError(String titulo, String mensaje) {
        Alert alerta = new Alert(Alert.AlertType.ERROR);
        alerta.setTitle("Error");
        alerta.setHeaderText(titulo);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }

    private void mostrarAlertaExito(String titulo, String mensaje) {
        Alert alerta = new Alert(Alert.AlertType.INFORMATION);
        alerta.setTitle("Éxito");
        alerta.setHeaderText(titulo);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }
}
