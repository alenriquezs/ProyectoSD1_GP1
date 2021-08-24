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
import javafx.scene.control.PasswordField;
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
public class VentanaRegAdoptante {
    private VBox root = new VBox();
    private GridPane panelRegAdoptante = new GridPane();
    private Label lblTitulo = new Label("* REGISTRAR ADOPTANTE *");
    private Button btnRegAdoptante = new Button("Registrar");
    private Button btnSubirFoto = new Button("Subir Foto Perfil");
    private DatePicker fechaNac = new DatePicker();
    private TextField txtCedula = new TextField();
    private TextField txtNomApel = new TextField();
    private TextField txtEdad = new TextField();
    private TextField txtDireccion = new TextField();
    private TextField txtTelefono = new TextField();
    private PasswordField txtContraseña = new PasswordField();
    private TextField txtUsuario = new TextField();
    private TextField txtCorreo = new TextField();
    Font fuenteT=new Font("Times New Roman",25.0);
    PreparedStatement st = null;
    Statement stmt = null;
    
    public VentanaRegAdoptante(){
        organizarControles();
        fechaNac.setOnAction(e-> txtEdad.setText(obtenerEdad(fechaNac.getValue(),LocalDate.now())));
        btnRegAdoptante.setOnAction(e-> registrarAdoptante());
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
        panelRegAdoptante.setHgap(20);panelRegAdoptante.setVgap(2);panelRegAdoptante.setAlignment(Pos.CENTER);
        panelRegAdoptante.setPadding(new Insets(0,0,10,2));
        panelRegAdoptante.add(new Label("Cedula: "),0,0);panelRegAdoptante.add(txtCedula,1,0);
        panelRegAdoptante.add(new Label("Nombre Completo: "),0,1);panelRegAdoptante.add(txtNomApel,1,1);
        panelRegAdoptante.add(new Label("Fecha Nacimiento: "),0,2);panelRegAdoptante.add(fechaNac,1,2);
        panelRegAdoptante.add(new Label("Edad: "),0,3);panelRegAdoptante.add(txtEdad,1,3);
        panelRegAdoptante.add(new Label("Direccion: "),0,4);panelRegAdoptante.add(txtDireccion,1,4);
        panelRegAdoptante.add(new Label("Telefono: "),0,5);panelRegAdoptante.add(txtTelefono,1,5);
        panelRegAdoptante.add(new Label("Usuario: "),0,6);panelRegAdoptante.add(txtUsuario,1,6);
        panelRegAdoptante.add(new Label("Correo: "),0,7);panelRegAdoptante.add(txtCorreo,1,7);
        panelRegAdoptante.add(new Label("Contraseña: "),0,8);panelRegAdoptante.add(txtContraseña,1,8);
        root.getChildren().addAll(lblTitulo,panelRegAdoptante,btnSubirFoto,btnRegAdoptante);
        
    }
    
    public void limpiarCampos(){
        txtCedula.clear();txtNomApel.clear();fechaNac.setValue(LocalDate.now());txtEdad.clear();
        txtDireccion.clear();txtTelefono.clear();txtUsuario.clear();txtCorreo.clear();txtContraseña.clear();
    }
    
    public void registrarTelAdoptante(String usuario){
        st = null;
        stmt = null;
        String telefono = txtTelefono.getText();
        try {
            if (st==null & stmt==null){
                String Query = "SELECT codigo FROM TelefonoAdoptante ORDER BY codigo DESC LIMIT 1";
                stmt = VentanaInicial.conexionDB.createStatement();
                java.sql.ResultSet result = stmt.executeQuery(Query);result.next();;
                int codigo = result.getInt("Codigo")+1;
                st = VentanaInicial.conexionDB.prepareStatement("INSERT INTO TelefonoAdoptante VALUES (?,?,?)");
                st.setInt(1, codigo);st.setString(2, usuario);st.setString(3, telefono);st.executeUpdate();
            }
        } catch (SQLException ex) {
            Alert a = new Alert(Alert.AlertType.ERROR,"Error al ingresar los datos",ButtonType.OK);
            a.setHeaderText(null);a.showAndWait();
        }
    }
    
    public void registrarAdoptante(){
        try {
            String cedula = txtCedula.getText().toUpperCase();
            String nombre = txtNomApel.getText().toUpperCase();
            String fecha_Nac = fechaNac.getValue().toString();
            String direccion = txtDireccion.getText().toUpperCase();
            String usuario = txtUsuario.getText().toUpperCase();
            String correo = txtCorreo.getText().toUpperCase();
            String contraseña = txtContraseña.getText();
            
            if (st==null){
                st = VentanaInicial.conexionDB.prepareStatement("INSERT INTO Adoptante VALUES (?,?,?,?,?,?,?,?,?,?,?)");
                st.setString(1, usuario);st.setString(2, cedula);st.setString(3, nombre);st.setDate(4, Date.valueOf(fecha_Nac));
                st.setString(5, correo);st.setString(6, direccion);st.setString(7, contraseña);st.setNull(8, 0);
                st.setNull(9, 0);st.setNull(10, 0);st.setNull(11, 0);st.executeUpdate();registrarTelAdoptante(usuario);
                Alert a = new Alert(Alert.AlertType.INFORMATION,"Adoptante registrado correctamente",ButtonType.OK);
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
    
    public VBox getRoot(){
        return root;
    }
    
    public String obtenerEdad(LocalDate start, LocalDate end) { 
        int edad = end.getYear()-start.getYear();
        return String.valueOf(edad);
    }
    
}
