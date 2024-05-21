package Modelo;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author alex1
 */
public class ConexionDB {
    private static final String URL = "jdbc:postgresql://roundhouse.proxy.rlwy.net:53384/railway";
    private static final String USER = "postgres";
    private static final String PASSWORD = "expmNKTQtUEBAdrtHmLeztLbHJAMNXYf";
    private static Connection conn = null;
    
    private ConexionDB() {
        try {
            Properties properties = new Properties();
            properties.setProperty("user", USER);
            properties.setProperty("password", PASSWORD);
            conn = DriverManager.getConnection(URL, properties);
            System.out.println("Conexión exitosa a la base de datos PostgreSQL");

        } catch (SQLException ex) {
            Logger.getLogger(ConexionDB.class.getName()).log(Level.SEVERE, "Error en la conexión", ex);
        }
    }
    
    public static Connection getConnection() {
        if (conn == null) {
            new ConexionDB();
        }
        return conn;
    }
}



