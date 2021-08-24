/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.util.converter.LocalDateStringConverter;

/**
 *
 * @author Alfonso E
 */
public class VentanaRegMascota {
    private VBox root = new VBox();
    private GridPane panelRegMascota = new GridPane();
    private Label lblTitulo = new Label("* REGISTRAR MASCOTA *");
    private Button btnRegMascota = new Button("Registrar");
    private DatePicker fechaNac = new DatePicker();
    private TextField txtRaza = new TextField();
    private TextField txtHobbie = new TextField();
    private TextField txtEdad = new TextField();
    Font fuenteT=new Font("Times New Roman",25.0);
    PreparedStatement st = null;
    Statement stmt = null;
    
    public VentanaRegMascota(){
        organizarControles();
        fechaNac.setOnAction(e-> txtEdad.setText(obtenerEdad(fechaNac.getValue(),LocalDate.now())));
        btnRegMascota.setOnAction(e-> registrarMascota());
    }
    
    public void organizarControles(){
        DateTimeFormatter formatoFecha = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        fechaNac.setConverter(new LocalDateStringConverter(formatoFecha, null));
        fechaNac.setEditable(false);txtEdad.setEditable(false);
        root.setSpacing(10);root.setAlignment(Pos.CENTER);
        root.setBackground(new Background(new BackgroundFill(Color.web("#D4ECE1"),CornerRadii.EMPTY,Insets.EMPTY)));
        lblTitulo.setFont(fuenteT);lblTitulo.setPadding(new Insets(10,0,50,0));lblTitulo.setTextFill(Color.WHITE);
        lblTitulo.setBackground(new Background(new BackgroundFill(Color.web("#039c74"),
                                new CornerRadii(5.0), new Insets(0,-100,40,-100))));
        panelRegMascota.setHgap(20);panelRegMascota.setVgap(2);panelRegMascota.setAlignment(Pos.CENTER);
        panelRegMascota.setPadding(new Insets(0,0,10,2));
        panelRegMascota.add(new Label("Raza: "),0,0);panelRegMascota.add(txtRaza,1,0);
        panelRegMascota.add(new Label("Fecha Nacimiento: "),0,1);panelRegMascota.add(fechaNac,1,1);
        panelRegMascota.add(new Label("Edad: "),0,2);panelRegMascota.add(txtEdad,1,2);
        panelRegMascota.add(new Label("Hobbie: "),0,3);panelRegMascota.add(txtHobbie,1,3);
        root.getChildren().addAll(lblTitulo,panelRegMascota,btnRegMascota);
    }
    
    public void limpiarCampos(){
        txtRaza.clear();fechaNac.setValue(LocalDate.now());txtEdad.clear();txtHobbie.clear();
    }
    
    public void registrarHobbie(){
        String hobbie = txtHobbie.getText().toUpperCase();
        st = null;
        stmt = null;
        try {
            if (st==null & stmt==null){
                String Query = "SELECT codigo FROM HobbieMascota ORDER BY codigo DESC LIMIT 1";
                stmt = VentanaInicial.conexionDB.createStatement();
                java.sql.ResultSet result = stmt.executeQuery(Query);result.next();;
                int codigo = result.getInt("Codigo")+1;
                st = VentanaInicial.conexionDB.prepareStatement("INSERT INTO HobbieMascota VALUES (?,?,?)");
                st.setInt(1, codigo);st.setString(2, String.valueOf(codigo));st.setString(3, hobbie);st.executeUpdate();
            }
        } catch (SQLException ex) {
            Alert a = new Alert(Alert.AlertType.ERROR,"Error al ingresar los datos",ButtonType.OK);
            a.setHeaderText(null);a.showAndWait();
        }
    }
    
    public void registrarMascota(){
        Statement stm = null;st=null;
        try {
            String raza = txtRaza.getText().toUpperCase();
            String fecha_Nac = fechaNac.getValue().toString();
            
            if (st==null){
                String Query = "SELECT idMascota FROM Mascota ORDER BY idMascota DESC LIMIT 1";
                stm = VentanaInicial.conexionDB.createStatement();
                java.sql.ResultSet result = stm.executeQuery(Query);result.next();
                st = VentanaInicial.conexionDB.prepareStatement("INSERT INTO Mascota VALUES (?,?,?,?)");
                int idMascota = Integer.parseInt(result.getString("idMascota"))+1;
                st.setString(1, String.valueOf(idMascota));st.setString(2, raza);st.setString(3, fecha_Nac);
                st.setNull(4, 0);st.executeUpdate();registrarHobbie();
                Alert a = new Alert(Alert.AlertType.INFORMATION,"Mascota registrada correctamente",ButtonType.OK);
                a.setHeaderText(null);a.showAndWait();VentanaHomeScreen.getStHome().close();limpiarCampos();
            }             
        } catch (SQLException ex) {
            Alert a = new Alert(Alert.AlertType.ERROR,"Error al ingresar los datos",ButtonType.OK);
            a.setHeaderText(null);a.showAndWait();
        } catch(NullPointerException e){
            Alert a = new Alert(Alert.AlertType.ERROR,"Por favor, ingrese todos los datos",ButtonType.OK);
            a.setHeaderText(null);a.showAndWait();
        }
    }
    
    public VBox getRoot(){
        return root;
    }
    
    public String obtenerEdad(LocalDate start, LocalDate end) { 
        int edad = end.getYear()-start.getYear();
        return String.valueOf(edad);
    }
    
}
