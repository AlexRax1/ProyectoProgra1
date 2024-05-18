/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package prueba;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author alex1
 */
public class CursosDB {
    Connection conn;
    
    public CursosDB() {
        conn = ConexionBD.getConnection();
    }
    
    public void agregar(Cursos e) {
        
        PreparedStatement st = null;
    
        try {
            String sql = "INSERT INTO \"cursos\" (cod_curso, nombrecurso, catedratico) VALUES (?, ?, ?)";
            st = conn.prepareStatement(sql);
            st.setString(1, e.getCod_Curso());
            st.setString(2, e.getNombre());
            st.setString(3, e.getCatedratico());

            st.executeUpdate();
            
        } catch (SQLException ex) {
            Logger.getLogger(CursosDB.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (st != null) {
                try {
                    st.close();
                } catch (SQLException ex) {
                    Logger.getLogger(CursosDB.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }
}
