package prueba;


import java.sql.*;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;


public class ConexionBD1 {
    private static final String URL = "jdbc:postgresql://viaduct.proxy.rlwy.net:59451/railway";
    private static final String USER = "postgres";
    private static final String PASSWORD = "AwxZAzvdzXARqEmPmFWCgZbMFyiAgSvQ";
    private static Connection conn = null;
    
    private ConexionBD1() {
        try {
            Properties properties = new Properties();
            properties.setProperty("user", USER);
            properties.setProperty("password", PASSWORD);
            conn = DriverManager.getConnection(URL, properties);
        } catch (SQLException ex) {
            Logger.getLogger(ConexionBD1.class.getName()).log(Level.SEVERE, "Error en la conexión", ex);
        }
    }
    
    public static Connection getConnection() {
        if (conn == null) {
            new ConexionBD1();
        }
        return conn;
    }
}