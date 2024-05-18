/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package prueba;

import javafx.beans.property.StringProperty;

/**
 *
 * @author alex1
 */
public class Cursos {
   private String cod_Curso;
   private String nombre;
   private String catedratico;
   

    public Cursos(String cod_Curso, String nombre, String catedratico) {
        this.cod_Curso = cod_Curso;
        this.nombre = nombre;
        this.catedratico = catedratico;
    }

    public String getCod_Curso() {
        return cod_Curso;
    }

    public void setCod_Curso(String cod_Curso) {
        this.cod_Curso = cod_Curso;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCatedratico() {
        return catedratico;
    }

    public void setCatedratico(String catedratico) {
        this.catedratico = catedratico;
    }
   
   
   
}
