/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import java.sql.*;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

/**
 *
 * @author Alfonso E
 */
public class VentanaLogin {
    private VBox rootLogin = new VBox();
    private GridPane panelLogin = new GridPane();
    private Label lblLogin = new Label("Inicio de Sesión");
    private Button btnLogin = new Button("Iniciar Sesión"); 
    private static String nomUsuario;
    private TextField txtUsuario = new TextField();
    private PasswordField txtContraseña = new PasswordField();
    private static Stage stHomeScreen = new Stage();
    private Scene scHomeScreen = new Scene (new VentanaHomeScreen().getRoot(),350,400);
    Font fuenteT=new Font("Times New Roman",25.0);
    
    public VentanaLogin(){
        organizarControles();
        btnLogin.setOnAction(e-> validarClientes());
        txtContraseña.setOnKeyPressed(e->{if(e.getCode() == KeyCode.ENTER){validarClientes();}});
    }
    
    public void organizarControles(){
        rootLogin.setSpacing(20);rootLogin.setAlignment(Pos.CENTER);
        rootLogin.setBackground(new Background(new BackgroundFill(Color.web("#DBF3E8"),CornerRadii.EMPTY,Insets.EMPTY)));
        lblLogin.setFont(fuenteT);lblLogin.setPadding(new Insets(10,0,10,0));
        panelLogin.setHgap(20);panelLogin.setVgap(2);panelLogin.setAlignment(Pos.CENTER);
        panelLogin.setPadding(new Insets(0,0,10,2));
        panelLogin.add(new Label("Usuario: "),0,0);panelLogin.add(txtUsuario,1,0);
        panelLogin.add(new Label("Contraseña: "),0,2);panelLogin.add(txtContraseña,1,2);
        rootLogin.getChildren().addAll(lblLogin,panelLogin,btnLogin);
    }
    
    public void Pantalla_HomeScreen(){
        stHomeScreen.setTitle("PETLAND - Home Screen");
        stHomeScreen.setScene(scHomeScreen);
        stHomeScreen.getIcons().add(new Image("/Archivos/home-screen.png"));
        stHomeScreen.show();stHomeScreen.setWidth(500);stHomeScreen.setHeight(550);
    }
    
    public void validarClientes() {
        try {
            String usuario = txtUsuario.getText().toUpperCase();
            String contraseña = txtContraseña.getText();
            
            String Query = "SELECT * FROM Usuario";
            Statement st = VentanaInicial.conexionDB.createStatement();
            java.sql.ResultSet resultSet;
            resultSet = st.executeQuery(Query);
            
            if(!usuario.equalsIgnoreCase("") & !contraseña.equalsIgnoreCase("")){
                boolean validacion= false;nomUsuario = usuario;
                while (resultSet.next()) {
                    String userName = resultSet.getString("NombreUsuario");
                    String correo = resultSet.getString("Correo");
                    String clave = resultSet.getString("Clave");
                    if((userName.equals(usuario) | correo.equals(usuario)) & clave.equals(contraseña)){
                        validacion=true;txtUsuario.clear();txtContraseña.clear();
                        Pantalla_HomeScreen();VentanaInicial.getStVentanaInicial().close();
                    }
                }
                if(!validacion){
                Alert a = new Alert(Alert.AlertType.WARNING,"Cliente no registrado || Verifique usuario y contraseña",ButtonType.OK);
                a.setHeaderText(null);a.showAndWait();txtUsuario.clear();txtContraseña.clear();
                }
            } else{
            Alert a = new Alert(Alert.AlertType.WARNING,"Por favor ingrese sus credenciales",ButtonType.OK);
            a.setHeaderText(null);a.showAndWait();
            }

        } catch (SQLException ex) {
             Alert a = new Alert(Alert.AlertType.ERROR,"Error en la adquisición de datos",ButtonType.OK);
             a.setHeaderText(null);a.showAndWait();
        }
    }
    
    public VBox getRoot(){
        return rootLogin;
    }
    
    public static String getNomUsuario(){
        return nomUsuario;
    }
    
}
