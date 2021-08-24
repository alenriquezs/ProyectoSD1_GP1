/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import Clases.DbConnection;
import java.sql.*;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

/**
 *
 * @author Alfonso E
 */
public class VentanaInicial {
    public static Connection conexionDB = new DbConnection().getConnection();
    private VBox root = new VBox();
    private HBox panelBotones = new HBox();
    private Label lblTitulo = new Label("~ PETLAND ~");
    private Button btnIniciar = new Button("Iniciar");
    private Button btnRegistrar = new Button("Registrarse");
    private static Stage stVentanaInicial = new Stage();
    private Scene scLogin = new Scene(new VentanaLogin().getRoot(), 300,220);
    private Scene scRegistrar = new Scene(new VentanaRegistrarUsuario().getRoot(),350,280);
    Font fuenteT=new Font("Times New Roman",25.0);
    
    public VentanaInicial(){
        organizarControles();
        btnIniciar.setOnAction(e-> Pantalla_Login());
        btnRegistrar.setOnAction(e-> registrar());
        root.setOnKeyPressed(e -> {if(e.getCode() == KeyCode.ENTER){Pantalla_Login();}});
    }
    
    public void organizarControles(){
        root.setAlignment(Pos.CENTER);root.setSpacing(20);
        root.setBackground(new Background(new BackgroundFill(Color.web("#DBF3E8"),CornerRadii.EMPTY,Insets.EMPTY)));
        lblTitulo.setFont(fuenteT);lblTitulo.setPadding(new Insets(10,0,50,0));lblTitulo.setTextFill(Color.WHITE);
        lblTitulo.setBackground(new Background(new BackgroundFill(Color.web("#039c74"),
                                new CornerRadii(5.0), new Insets(0,-100,40,-100))));
        panelBotones.getChildren().addAll(btnIniciar,btnRegistrar);
        panelBotones.setSpacing(7);panelBotones.setAlignment(Pos.CENTER);
        root.getChildren().addAll(lblTitulo,panelBotones);
    }
    
    public void Pantalla_Login(){
        stVentanaInicial.setTitle("Inicio de Sesión");
        stVentanaInicial.setScene(scLogin);
        stVentanaInicial.getIcons().add(new Image("/Archivos/login.png"));
        stVentanaInicial.show();
    }
    
    public void registrar(){
        stVentanaInicial.setScene(scRegistrar);
        stVentanaInicial.setTitle("Registrar Nuevo Usuario");
        stVentanaInicial.getIcons().add(new Image("/Archivos/singup.png"));
        stVentanaInicial.show();
    }

    public VBox getRoot() {
        return root;
    }
    
    public static Stage getStVentanaInicial() {
        return stVentanaInicial;
    }

    
}
