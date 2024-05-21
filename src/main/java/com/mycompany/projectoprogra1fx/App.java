package com.mycompany.projectoprogra1fx;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import prueba.Cursos;
import prueba.CursosDB;



/**
 * JavaFX App
 */
public class App extends Application {

    private static Scene scene;

    @Override
    public void start(Stage stage) throws IOException {
        
        
        scene = new Scene(loadFXML("inicio"), 854, 503); 
        stage.setScene(scene);
        stage.show();
        
        
        
        //codigo original que me dio
        /*
        
        scene = new Scene(loadFXML("Login"), 640, 480);
        stage.setScene(scene);
        stage.show();
        
        
        //este codigo solo es un recordatorio(no tiene nada que ver)
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/Login.fxml"));
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();        
        */
        
    }

    static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        //CursosDB cursosDb = new CursosDB();
        
        //Insertar registro
        //Cursos e1 = new Cursos("75944000", "Estadistica", "Perez1");
        //cursosDb.agregar(e1);
        
        
        launch();
    }

}