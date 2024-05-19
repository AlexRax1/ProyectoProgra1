/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package prueba;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author alex1
 */
public class CursosDB {
    Connection conn;
    
    public CursosDB() {
        conn = ConexionBD1.getConnection();
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
    
    
    public void actualizar(Cursos e) {
            PreparedStatement st = null;
    
        try {
            String sql = "UPDATE \"cursos\" SET nombrecurso = ?, catedratico = ? WHERE cod_carnet = ?" ;
            st = conn.prepareStatement(sql);
            st.setString(1, e.getNombre());
            st.setString(2, e.getCatedratico());
            st.setString(3, e.getCod_Curso());
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
    
    public void eliminar(String codCurso) {
            PreparedStatement st = null;
    
        try {
            String sql = "DELETE FROM \"cursos\" WHERE cod_curso = ?";
            st = conn.prepareStatement(sql);
            st.setString(1, codCurso);
            
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
    
    
    public Cursos obtener(String codCurso) {
        
        PreparedStatement st = null;
        ResultSet rs = null;
        Cursos e = new Cursos("", "", "");
        
        try {
            String sql = "SELECT * FROM \"cursos\" WHERE cod_curso = ?";
            st = conn.prepareStatement(sql);
            st.setString(1, codCurso);
            rs = st.executeQuery();
            
            while (rs.next()) {
                e = new Cursos(
                rs.getString("cod_curso"), // Usa los nombres de columnas para mayor claridad
                rs.getString("nombrecurso"),
                rs.getString("catedratico")
            );
            return e;
            }
        } catch (SQLException ex) {
            Logger.getLogger(CursosDB.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                System.out.println(e);
                if (rs != null) {
                    rs.close();
                }
                
                if (st != null) {

                    st.close();
                }
            } catch (SQLException ex) {
                Logger.getLogger(CursosDB.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return null;
    }
    
    public ArrayList<Cursos> obtenerTodos() {
        
        Statement st = null;
        ResultSet rs = null;
        ArrayList<Cursos> a = new ArrayList();
        
        try {
            String sql = "SELECT * FROM \"cursos\" ";
            st = conn.createStatement();
            rs = st.executeQuery(sql);
            while (rs.next()) {
                
                Cursos e = new Cursos("7590", "Calculo 2", "Perez");
                
                e.setCod_Curso(rs.getString(1));
                e.setNombre(rs.getString(2));
                e.setCatedratico(rs.getString(3));               
                a.add(e);
            }
            
            return a;
        } catch (SQLException ex) {
            Logger.getLogger(CursosDB.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                
                if (st != null) {
                    st.close();
                }
            } catch (SQLException ex) {
                Logger.getLogger(CursosDB.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return null;
    }
}
