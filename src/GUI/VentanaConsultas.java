/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import java.sql.*;
import java.util.ArrayList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
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
public class VentanaConsultas {
    private VBox root = new VBox();
    private ScrollPane scrollResultados = new ScrollPane();
    private GridPane panelConsultas = new GridPane();
    private GridPane panelResultados = new GridPane();
    private Label lblTitulo = new Label("* CONSULTAS TABLA ADOPTANTE *");
    Font fuenteT=new Font("Times New Roman",25.0);
    PreparedStatement st=null;
    
    public VentanaConsultas(){
        organizarControles();panelResultados();
    }
    
    public void organizarControles(){
        root.setSpacing(10);root.setAlignment(Pos.CENTER);
        root.setBackground(new Background(new BackgroundFill(Color.web("#D4ECE1"),CornerRadii.EMPTY,Insets.EMPTY)));
        lblTitulo.setFont(fuenteT);lblTitulo.setPadding(new Insets(10,0,50,0));lblTitulo.setTextFill(Color.WHITE);
        lblTitulo.setBackground(new Background(new BackgroundFill(Color.web("#039c74"),
                                new CornerRadii(5.0), new Insets(0,-100,40,-100))));
        panelConsultas.setHgap(20);panelConsultas.setVgap(2);panelConsultas.setAlignment(Pos.CENTER);
        panelConsultas.setPadding(new Insets(0,0,10,2));
        panelConsultas.add(new Label("Tipo Consulta: "),0,0);panelConsultas.add(new Label("Adoptante"),1,0);
        root.getChildren().addAll(lblTitulo,panelConsultas,scrollResultados);
    }
    
    
    public void panelResultados(){
        Statement stm = null;
        try {
            if (stm==null){
                String Query = "SELECT * FROM Adoptante";
                stm = VentanaInicial.conexionDB.createStatement();
                java.sql.ResultSet result = stm.executeQuery(Query);
                panelResultados.setHgap(20);panelResultados.setVgap(2);panelResultados.setAlignment(Pos.CENTER);
                panelResultados.setPadding(new Insets(0,0,10,2));
                panelResultados.add(new Label("NombreUusario"),0,0);panelResultados.add(new Label("Cédula"), 1, 0);
                panelResultados.add(new Label("NombreApellidos"), 2, 0);panelResultados.add(new Label("Correo"),3,0);
                panelResultados.add(new Label("Dirección"), 4, 0);panelResultados.add(new Label("InfoVivienda"), 5, 0);
                panelResultados.add(new Label("NumConvivientes"), 6, 0);panelResultados.add(new Label("Patio"), 7, 0);
                int numFila=0;
                
                while(result.next()){
                    numFila+=1;
                    panelResultados.add(new Label(result.getString("NombreUsuario")), 0, numFila);
                    panelResultados.add(new Label(result.getString("CédulaAdoptante")), 1, numFila);
                    panelResultados.add(new Label(result.getString("NombreApellidos")), 2, numFila);
                    panelResultados.add(new Label(result.getString("Correo")), 3, numFila);
                    panelResultados.add(new Label(result.getString("Dirección")), 4, numFila);
                    panelResultados.add(new Label(result.getString("InfoVivienda")), 5, numFila);
                    panelResultados.add(new Label(String.valueOf(result.getInt("NumConvivientes"))), 6, numFila);
                    panelResultados.add(new Label(String.valueOf(result.getBoolean("Patio"))), 7, numFila);
                }
                scrollResultados.setContent(panelResultados);
            }
        } catch (SQLException ex) {
            Alert a = new Alert(Alert.AlertType.ERROR,"Error en la adquisición de datos",ButtonType.OK);
            a.setHeaderText(null);a.showAndWait();
        }
    }
    
    public VBox getRoot(){
        return root;
    }
}
