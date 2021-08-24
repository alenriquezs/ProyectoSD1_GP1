/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import java.sql.*;
import java.util.NoSuchElementException;
import java.util.Optional;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
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
public class VentanaBorrarDatos {
    private VBox root = new VBox();
    private GridPane panelBorrarDatos = new GridPane();
    private Button btnBorrar = new Button("Borrar");
    private Label lblTitulo = new Label("* BORRAR DATOS USUARIO*");
    private TextField txtNomUsuario = new TextField();
    Font fuenteT=new Font("Times New Roman",25.0);
    PreparedStatement st=null;
    
    public VentanaBorrarDatos(){
        organizarControles();
        btnBorrar.setOnAction(e-> Borrar());
    }
    
    public void organizarControles(){
        root.setSpacing(10);root.setAlignment(Pos.CENTER);
        root.setBackground(new Background(new BackgroundFill(Color.web("#D4ECE1"),CornerRadii.EMPTY,Insets.EMPTY)));
        lblTitulo.setFont(fuenteT);lblTitulo.setPadding(new Insets(10,0,50,0));lblTitulo.setTextFill(Color.WHITE);
        lblTitulo.setBackground(new Background(new BackgroundFill(Color.web("#039c74"),
                                new CornerRadii(5.0), new Insets(0,-100,40,-100))));
        panelBorrarDatos.setHgap(20);panelBorrarDatos.setVgap(2);panelBorrarDatos.setAlignment(Pos.CENTER);
        panelBorrarDatos.setPadding(new Insets(0,0,10,2));
        panelBorrarDatos.add(new Label("NombreUsuario: "),0,0);panelBorrarDatos.add(txtNomUsuario,1,0);
        
        root.getChildren().addAll(lblTitulo,panelBorrarDatos,btnBorrar);
    }
    
    public void limpiarCampos(){
        txtNomUsuario.clear();
    }
    
    
    public void Borrar(){
        Statement stm = null;
        st = null;
        try {boolean validacion=false;
            String nombreUsuario = txtNomUsuario.getText().toUpperCase();
            
            if (st==null&stm==null&!nombreUsuario.equals("")){
                String Query = "SELECT NombreUsuario FROM Usuario";
                stm = VentanaInicial.conexionDB.createStatement();
                java.sql.ResultSet resultSet;resultSet = stm.executeQuery(Query);
                while (resultSet.next()) {
                    if(nombreUsuario.equals(resultSet.getString("NombreUsuario"))){
                        validacion=true;
                    }
                }
                if(validacion){
                    st = VentanaInicial.conexionDB.prepareStatement("DELETE FROM Usuario WHERE NombreUsuario=\""+nombreUsuario+"\"");
                    Alert al = new Alert(Alert.AlertType.CONFIRMATION,"¿Está seguro de eliminar usuario \""+nombreUsuario+"\"",ButtonType.OK);
                    al.setHeaderText(null);Optional<ButtonType> action = al.showAndWait();

                    if (action.get() == ButtonType.OK){
                        st.executeUpdate();
                        Alert a = new Alert(Alert.AlertType.INFORMATION,"Usuario eliminado correctamente",ButtonType.OK);
                        a.setHeaderText(null);a.showAndWait();VentanaHomeScreen.getStHome().close();limpiarCampos();
                    } else {
                        Alert a = new Alert(Alert.AlertType.INFORMATION,"Usuario \""+nombreUsuario+"\" no eliminado",ButtonType.OK);
                        a.setHeaderText(null);a.showAndWait();
                    }
                }
                if(!validacion){
                Alert a = new Alert(Alert.AlertType.WARNING,"Cliente no registrado || Verifique usuario y contraseña",ButtonType.OK);
                a.setHeaderText(null);a.showAndWait();txtNomUsuario.clear();
                }
            } else {
                Alert a = new Alert(Alert.AlertType.ERROR,"Por favor, ingrese todos los datos",ButtonType.OK);
                a.setHeaderText(null);a.showAndWait();
            }
        } catch (SQLException ex) {
            System.out.println(ex);
            Alert a = new Alert(Alert.AlertType.ERROR,"Error en la eliminación de usuario",ButtonType.OK);
            a.setHeaderText(null);a.showAndWait();
        } catch (NoSuchElementException e){
            Alert a = new Alert(Alert.AlertType.WARNING,"Usuario no eliminado",ButtonType.OK);
            a.setHeaderText(null);a.showAndWait();txtNomUsuario.clear();
        }
    }
    
    public VBox getRoot(){
        return root;
    }
}
