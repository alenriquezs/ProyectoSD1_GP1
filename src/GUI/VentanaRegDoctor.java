/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import java.sql.*;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

/**
 *
 * @author Alfonso E
 */
public class VentanaRegDoctor {
    private VBox root = new VBox();
    private GridPane panelRegDoctor = new GridPane();
    private Label lblTitulo = new Label("* REGISTRAR DOCTOR *");
    private Button btnRegDoctor = new Button("Registrar");
    private PasswordField txtContraseña = new PasswordField();
    private TextField txtUsuario = new TextField();
    private TextField txtCorreo = new TextField();
    Font fuenteT=new Font("Times New Roman",25.0);
    
    public VentanaRegDoctor(){
        organizarControles();
        btnRegDoctor.setOnAction(e-> registrarDoctor());
    }
    
    public void organizarControles(){
        root.setSpacing(10);root.setAlignment(Pos.CENTER);
        root.setBackground(new Background(new BackgroundFill(Color.web("#D4ECE1"),CornerRadii.EMPTY,Insets.EMPTY)));
        lblTitulo.setFont(fuenteT);lblTitulo.setPadding(new Insets(10,0,50,0));lblTitulo.setTextFill(Color.WHITE);
        lblTitulo.setBackground(new Background(new BackgroundFill(Color.web("#039c74"),
                                new CornerRadii(5.0), new Insets(0,-100,40,-100))));
        panelRegDoctor.setHgap(20);panelRegDoctor.setVgap(2);panelRegDoctor.setAlignment(Pos.CENTER);
        panelRegDoctor.setPadding(new Insets(0,0,10,2));
        panelRegDoctor.add(new Label("Usuario: "),0,0);panelRegDoctor.add(txtUsuario,1,0);
        panelRegDoctor.add(new Label("Correo: "),0,1);panelRegDoctor.add(txtCorreo,1,1);
        panelRegDoctor.add(new Label("Contraseña: "),0,2);panelRegDoctor.add(txtContraseña,1,2);
        root.getChildren().addAll(lblTitulo,panelRegDoctor,btnRegDoctor);
    }
    
    public VBox getRoot(){
        return root;
    }
    
    public void limpiarCampos(){
        txtUsuario.clear();txtCorreo.clear();txtContraseña.clear();
    }
    
    public void registrarDoctor(){
        PreparedStatement st = null;
        try {
            String usuario = txtUsuario.getText().toUpperCase();
            String correo = txtCorreo.getText().toUpperCase();
            String contraseña = txtContraseña.getText();
            
            if (null==st){
                st = VentanaInicial.conexionDB.prepareStatement("INSERT INTO Doctor VALUES (?,?,?,?)");
                st.setString(1, usuario);st.setString(2, correo);st.setString(3, contraseña);st.setNull(4, 0);st.executeUpdate();
                Alert a = new Alert(Alert.AlertType.INFORMATION,"Doctor registrado correctamente",ButtonType.OK);
                a.setHeaderText(null);a.showAndWait();VentanaRegistrarUsuario.getVentanaRegUsuario().close();limpiarCampos();
            }
        } catch (SQLException ex) {
            Alert a = new Alert(Alert.AlertType.ERROR,"Error al ingresar los datos",ButtonType.OK);
            a.setHeaderText(null);a.showAndWait();
        } catch(NullPointerException e){
            Alert a = new Alert(Alert.AlertType.ERROR,"Por favor, ingrese todos los datos",ButtonType.OK);
            a.setHeaderText(null);a.showAndWait();
        }
    }
}
