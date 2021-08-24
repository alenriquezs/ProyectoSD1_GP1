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
public class VentanaHomeScreen {
    private VBox root = new VBox();
    private Label lblTitulo = new Label("* PETLAND *");
    private Button btnRegMascota = new Button("Registrar Mascota");
    private Button btnAdopMascota = new Button("Adoptar Mascota");
    private Button btnSolicitarCita = new Button("Solicitar Cita");
    private Button btnRealReportes = new Button("Realizar Reportes");
    private Button btnConsultas = new Button("Ver Consultas");
    private Button btnBorrarDatos = new Button("Borrar Datos");
    private Button btnIrChat = new Button("Ir a Chat");
    private static Stage stHome = new Stage();
    private Scene scRegMascota = new Scene(new VentanaRegMascota().getRoot(),350,400);
    private Scene scAdopMascota = new Scene(new VentanaAdopMascota().getRoot(),350,400);
    private Scene scRealReportes = new Scene(new VentanaRealReportes().getRoot(),350,450);
    private Scene scSolicitarCita = new Scene(new VentanaSolicitarCita().getRoot(),350,450);
    private Scene scConsultas = new Scene(new VentanaConsultas().getRoot(),350,450);
    private Scene scBorrarDatos = new Scene(new VentanaBorrarDatos().getRoot(),350,450);
    private Scene scIrChat = new Scene(new VentanaChat().getRoot(),600,680);
    Font fuenteT=new Font("Times New Roman",25.0);
    
    public VentanaHomeScreen(){
        organizarControles();
        btnRegMascota.setOnAction(e-> Pantalla_RegMascota());
        btnAdopMascota.setOnAction(e-> Pantalla_AdopMascota());
        btnRealReportes.setOnAction(e-> Pantalla_RealReportes());
        btnSolicitarCita.setOnAction(e-> Pantalla_SolicitarCita());
        btnIrChat.setOnAction(e-> Pantalla_IrChat());
        btnConsultas.setOnAction(e-> Pantalla_Consultas());
        btnBorrarDatos.setOnAction(e-> Pantalla_BorrarDatos());
        
    }
    
    public void organizarControles(){
        root.setSpacing(10);root.setAlignment(Pos.CENTER);stHome.setWidth(650);stHome.setHeight(600);
        root.setBackground(new Background(new BackgroundFill(Color.web("#DBF3E8"),CornerRadii.EMPTY,Insets.EMPTY)));
        btnRegMascota.setPrefWidth(140);btnAdopMascota.setPrefWidth(140);btnConsultas.setPrefWidth(140);btnBorrarDatos.setPrefWidth(140);
        btnSolicitarCita.setPrefWidth(140);btnRealReportes.setPrefWidth(140);btnIrChat.setPrefWidth(140);
        lblTitulo.setFont(fuenteT);lblTitulo.setPadding(new Insets(10,0,50,0));lblTitulo.setTextFill(Color.WHITE);
        lblTitulo.setBackground(new Background(new BackgroundFill(Color.web("#039c74"),
                                new CornerRadii(5.0), new Insets(0,-100,40,-100))));
        root.getChildren().addAll(lblTitulo,btnRegMascota,btnAdopMascota,btnRealReportes,btnSolicitarCita,btnConsultas,btnBorrarDatos,btnIrChat);
    }
    
    public void Pantalla_RegMascota(){
        stHome.setTitle("PETLAND - Registrar Mascota");
        stHome.setScene(scRegMascota);
        stHome.getIcons().add(new Image("/Archivos/home-screen.png"));
        stHome.show();
    }
    
    public void Pantalla_AdopMascota(){
        stHome.setTitle("PETLAND - Adoptar Mascota");
        stHome.setScene(scAdopMascota);
        stHome.getIcons().add(new Image("/Archivos/home-screen.png"));
        stHome.show();
    }
    
    public void Pantalla_RealReportes(){
        stHome.setTitle("PETLAND - Realizar Reportes");
        stHome.setScene(scRealReportes);
        stHome.getIcons().add(new Image("/Archivos/home-screen.png"));
        stHome.show();
    }
    
    public void Pantalla_SolicitarCita(){
        stHome.setTitle("PETLAND - Solicitar Cita");
        stHome.setScene(scSolicitarCita);
        stHome.getIcons().add(new Image("/Archivos/home-screen.png"));
        stHome.show();
    }
    
    public void Pantalla_Consultas(){
        stHome.setTitle("PETLAND - Consultas");
        stHome.setScene(scConsultas);
        stHome.getIcons().add(new Image("/Archivos/home-screen.png"));
        stHome.show();
    }
    
    public void Pantalla_BorrarDatos(){
        stHome.setTitle("PETLAND - Borrar Datos");
        stHome.setScene(scBorrarDatos);
        stHome.getIcons().add(new Image("/Archivos/home-screen.png"));
        stHome.show();
    }
    
    public void Pantalla_IrChat(){
        stHome.setTitle("PETLAND - Chat");
        stHome.setScene(scIrChat);
        stHome.getIcons().add(new Image("/Archivos/home-screen.png"));
        stHome.show();
    }
    
    public VBox getRoot(){
        return root;
    }
    
    public static Stage getStHome(){
        return stHome;
    }
    
}
