/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

/**
 *
 * @author Alfonso E
 */
public class VentanaRegistrarUsuario {
    private VBox root = new VBox();
    private Label lblTitulo = new Label("Registrate!");
    private Button btnRegAdoptante = new Button("Registrar Adoptante");
    private Button btnRegEmpleado = new Button("Registrar Empleado");
    private Button btnRegDoctor = new Button("Registrar Doctor");
    private static Stage stRegistrarUsuario = new Stage();
    private Scene scRegAdoptante = new Scene(new VentanaRegAdoptante().getRoot(),350,460);
    private Scene scRegEmpleado = new Scene(new VentanaRegEmpleado().getRoot(),350,450);
    private Scene scRegDoctor = new Scene(new VentanaRegDoctor().getRoot(),350,280);
    Font fuenteT = new Font("Times New Roman",25.0);
    
    public VentanaRegistrarUsuario(){
        organizarControles();
        btnRegAdoptante.setOnAction(e-> Pantalla_RegAdoptante());
        btnRegEmpleado.setOnAction(e-> Pantalla_RegEmpleado());
        btnRegDoctor.setOnAction(e-> Pantalla_RegDoctor());
    }
    
    public void organizarControles(){
        root.setSpacing(10);root.setAlignment(Pos.CENTER);
        root.setBackground(new Background(new BackgroundFill(Color.web("#DBF3E8"),CornerRadii.EMPTY,Insets.EMPTY)));
        btnRegAdoptante.setPrefWidth(140);btnRegEmpleado.setPrefWidth(140);btnRegDoctor.setPrefWidth(140);
        lblTitulo.setFont(fuenteT);lblTitulo.setPadding(new Insets(10,0,20,0));
        root.getChildren().addAll(lblTitulo,btnRegAdoptante,btnRegEmpleado,btnRegDoctor);
    }
    
    public void Pantalla_RegAdoptante(){
        stRegistrarUsuario.setTitle("PETLAND - Registrar Adoptante");
        stRegistrarUsuario.setScene(scRegAdoptante);
        stRegistrarUsuario.getIcons().add(new Image("/Archivos/home-screen.png"));
        stRegistrarUsuario.show();
    }
    
    public void Pantalla_RegEmpleado(){
        stRegistrarUsuario.setTitle("PETLAND - Registrar Empleado");
        stRegistrarUsuario.setScene(scRegEmpleado);
        stRegistrarUsuario.getIcons().add(new Image("/Archivos/home-screen.png"));
        stRegistrarUsuario.show();
    }
    
    public void Pantalla_RegDoctor(){
        stRegistrarUsuario.setTitle("PETLAND - Registrar Doctor");
        stRegistrarUsuario.setScene(scRegDoctor);
        stRegistrarUsuario.getIcons().add(new Image("/Archivos/home-screen.png"));
        stRegistrarUsuario.show();
    }
    
    public VBox getRoot(){
        return root;
    }

    public static Stage getVentanaRegUsuario(){
        return stRegistrarUsuario;
    }
    
}
