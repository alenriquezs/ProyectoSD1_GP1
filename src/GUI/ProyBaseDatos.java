/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 *
 * @author Alfonso E
 */
public class ProyBaseDatos extends Application {
    
    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage primaryStage) {
        Scene s1= new Scene(new VentanaInicial().getRoot(),300,260);
        primaryStage.getIcons().add(new Image("/Archivos/icon.png"));
        primaryStage.setTitle("PETLAND");
        primaryStage.setScene(s1);
        primaryStage.show();
        
    }
    
}
