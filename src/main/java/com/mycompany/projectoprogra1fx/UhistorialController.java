/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.mycompany.projectoprogra1fx;

import Modelo.ConexionDB;
import Modelo.Historial;
import Modelo.UsuarioGlobal;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * FXML Controller class
 *
 * @author alex1
 */
public class UhistorialController implements Initializable {

    /**
     * Initializes the controller class.
     */
@FXML
private TableView<Historial> tablaHistorial;
@FXML
private TableColumn<Historial, Integer> idColumna;
@FXML
private TableColumn<Historial, Integer> usuarioColumna;
@FXML
private TableColumn<Historial, String> nombreColumna;
@FXML
private TableColumn<Historial, String> accionColumna;
@FXML
private TableColumn<Historial, LocalDateTime> fechaColumna;



@FXML
private TextField idUsuariotxt;



@FXML
private Button btnBuscar;



private ObservableList<Historial> historialList;
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        
        
        
        idColumna.setCellValueFactory(new PropertyValueFactory<>("transaccion_id"));
        usuarioColumna.setCellValueFactory(new PropertyValueFactory<>("usuario_id"));
        nombreColumna.setCellValueFactory(new PropertyValueFactory<>("nombreUsuario"));
        accionColumna.setCellValueFactory(new PropertyValueFactory<>("accion"));
        fechaColumna.setCellValueFactory(new PropertyValueFactory<>("fecha_transaccion"));


        historialList = FXCollections.observableArrayList();
        tablaHistorial.setItems(historialList);

        
        
        UsuarioGlobal usuario = UsuarioGlobal.getInstance();
            int valorActual = usuario.getIdUsuario();
            idUsuariotxt.setText(valorActual + "");
        
        
        //buscar
        btnBuscar.setOnAction(e -> buscarHistorial());
        
        
        buscarHistorial();
        
    }    
   

    private void buscarHistorial() {
        historialList.clear();
        Connection conn = ConexionDB.getConnection();
        String usuarioId = idUsuariotxt.getText();

        StringBuilder query = new StringBuilder(
            "SELECT ht.transaccion_id, u.usuario_id, u.nombre, ht.accion, ht.fecha_transaccion " +
            "FROM historial_transacciones ht " +
            "JOIN usuarios u ON ht.usuario_id = u.usuario_id " +
            "WHERE 1=1"
        );

        // Añadir condiciones basadas en la entrada del usuario
        if (!usuarioId.isEmpty()) {
            query.append(" AND u.usuario_id = ?");
        }


        query.append(" ORDER BY ht.transaccion_id");

        try (PreparedStatement stmt = conn.prepareStatement(query.toString())) {
            int paramIndex = 1;

            // Asignar valores a los parámetros en el PreparedStatement
            if (!usuarioId.isEmpty()) {
                stmt.setInt(paramIndex++, Integer.parseInt(usuarioId));
            }


            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                int transaccionIdResult = rs.getInt("transaccion_id");
                int usuarioIdResult = rs.getInt("usuario_id");
                String nombreUsuarioResult = rs.getString("nombre");
                String accion = rs.getString("accion");
                LocalDate fechaTransaccion = rs.getTimestamp("fecha_transaccion").toLocalDateTime().toLocalDate();

                historialList.add(new Historial(transaccionIdResult, usuarioIdResult, nombreUsuarioResult, accion, fechaTransaccion));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }



}
