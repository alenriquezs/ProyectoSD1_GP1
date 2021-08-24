/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
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
public class VentanaSolicitarCita {
    private VBox root = new VBox();
    private GridPane panelSolicitarCita = new GridPane();
    private Label lblTitulo = new Label("* SOLICITAR CITA *");
    private Button btnSolicitarCita = new Button("Solicitar Cita");
    private DatePicker fechaCita = new DatePicker();
    private TextField txtAdoptante = new TextField();
    private TextField txtNomUsuario = new TextField();
    private ComboBox<String> cmbDoctor = new ComboBox<>();
    private ComboBox<String> cmbMascota = new ComboBox<>();
    private TextArea txtDescripcion = new TextArea();
    private ComboBox<String> cmbDuracion = new ComboBox<>();
    Font fuenteT=new Font("Times New Roman",25.0);
    PreparedStatement st=null;
    
    public VentanaSolicitarCita(){
        organizarControles();cmbDoctor();cmbDuracion();
        txtAdoptante.setOnKeyPressed(e -> {if(e.getCode() == KeyCode.ENTER){cmbMascota();}});
        btnSolicitarCita.setOnAction(e-> SolicitarCita());
    }
    
    public void organizarControles(){
        txtNomUsuario.setEditable(false);txtAdoptante.setPromptText("Pulse ENTER al finalizar");
        cmbMascota.setPrefWidth(175);cmbDoctor.setPrefWidth(175);cmbDuracion.setPrefWidth(175);
        DateTimeFormatter formatoFecha = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        fechaCita.setConverter(new LocalDateStringConverter(formatoFecha, null));
        fechaCita.setEditable(false);fechaCita.setValue(LocalDate.now());
        txtDescripcion.setPrefSize(100, 70);txtDescripcion.setPromptText("Ingrese Descripción");
        txtDescripcion.setWrapText(true);root.setSpacing(10);root.setAlignment(Pos.CENTER);
        root.setBackground(new Background(new BackgroundFill(Color.web("#D4ECE1"),CornerRadii.EMPTY,Insets.EMPTY)));
        lblTitulo.setFont(fuenteT);lblTitulo.setPadding(new Insets(10,0,50,0));lblTitulo.setTextFill(Color.WHITE);
        lblTitulo.setBackground(new Background(new BackgroundFill(Color.web("#039c74"),
                                new CornerRadii(5.0), new Insets(0,-100,40,-100))));
        panelSolicitarCita.setHgap(20);panelSolicitarCita.setVgap(2);panelSolicitarCita.setAlignment(Pos.CENTER);
        panelSolicitarCita.setPadding(new Insets(0,0,10,2));
        panelSolicitarCita.add(new Label("Fecha: "),0,0);panelSolicitarCita.add(fechaCita,1,0);
        panelSolicitarCita.add(new Label("Cédula Adoptante: "),0,1);panelSolicitarCita.add(txtAdoptante,1,1);
        panelSolicitarCita.add(new Label("Nombre Usuario: "),0,2);panelSolicitarCita.add(txtNomUsuario,1,2);
        panelSolicitarCita.add(new Label("Seleccione Doctor: "),0,3);panelSolicitarCita.add(cmbDoctor,1,3);
        panelSolicitarCita.add(new Label("Mascota: "),0,4);panelSolicitarCita.add(cmbMascota,1,4);
        panelSolicitarCita.add(new Label("Duración: "),0,5);panelSolicitarCita.add(cmbDuracion,1,5);
        panelSolicitarCita.add(new Label("Descripción: "),0,6);panelSolicitarCita.add(txtDescripcion,1,6);
        root.getChildren().addAll(lblTitulo,panelSolicitarCita,btnSolicitarCita); 
    }
    
    public String obtenerNombreUsuario(String cedulaAdoptante){
        Statement stm = null;
        try {
            if (stm==null){
                String Query = "SELECT NombreUsuario FROM Adoptante WHERE CédulaAdoptante=\""+cedulaAdoptante+"\"";
                stm = VentanaInicial.conexionDB.createStatement();
                java.sql.ResultSet result = stm.executeQuery(Query);result.next();
                return result.getString("NombreUsuario");
                }
        } catch (SQLException ex) {
            System.out.println(ex);
            Alert a = new Alert(Alert.AlertType.ERROR,"Error en la adquisición de datos",ButtonType.OK);
            a.setHeaderText(null);a.showAndWait();
        } return "";
    }
    
    public void cmbDoctor(){
        Statement stm = null;
        try {
            ArrayList<String> listaDoctores = new ArrayList<>();
            if (stm==null){
                String Query = "SELECT NombreUsuario FROM Doctor";
                stm = VentanaInicial.conexionDB.createStatement();
                java.sql.ResultSet result = stm.executeQuery(Query);
                
                while (result.next()){
                    listaDoctores.add(result.getString("NombreUsuario"));
                }
                cmbDoctor.getItems().addAll(listaDoctores);
            }             
        } catch (SQLException ex) {
            Alert a = new Alert(Alert.AlertType.ERROR,"Error en la adquisición de datos",ButtonType.OK);
            a.setHeaderText(null);a.showAndWait();
        }
    }
    
    public void cmbMascota(){
        Statement stm = null;
        txtNomUsuario.setText(obtenerNombreUsuario(txtAdoptante.getText()));
        try {
            ArrayList<String> listaMascotas = new ArrayList<>();
            if (stm==null){
                String Query = "SELECT idMascota, Raza FROM Mascota WHERE Adoptante=\""+txtNomUsuario.getText()+"\"";
                stm = VentanaInicial.conexionDB.createStatement();
                java.sql.ResultSet result = stm.executeQuery(Query);
                
                while (result.next()){
                    listaMascotas.add(result.getString("idMascota")+"-"+result.getString("Raza"));
                }
                cmbMascota.getItems().addAll(listaMascotas);
            }             
        } catch (SQLException ex) {
            Alert a = new Alert(Alert.AlertType.ERROR,"Error en la adquisición de datos",ButtonType.OK);
            a.setHeaderText(null);a.showAndWait();cmbMascota.getItems().clear();
        }
    }
    
    public void cmbDuracion(){
        cmbDuracion.getItems().addAll("15 MIN","30 MIN","45 MIN","60 MIN");
    }
    
    public void limpiarCampos(){
        txtAdoptante.clear();fechaCita.setValue(LocalDate.now());cmbDoctor.getItems().clear();
        cmbDuracion.getItems().clear();cmbMascota.getItems().clear();txtDescripcion.clear();txtNomUsuario.clear();
    }
    
    public String verificarUsuario(String usuario){
        Statement stm = null;
        Statement stmt = null;
        try {
            if(stm==null&stmt==null){
                String Query = "SELECT NombreUsuario FROM Adoptante";
                stm = VentanaInicial.conexionDB.createStatement();
                java.sql.ResultSet result = stm.executeQuery(Query);
                while (result.next()){
                    if(result.getString("NombreUsuario").equals(usuario)){
                        return "Adoptante";
                    }
                }
                String query = "SELECT NombreUsuario FROM Empleado";
                stmt = VentanaInicial.conexionDB.createStatement();
                java.sql.ResultSet resultQ = stmt.executeQuery(query);
                
                while (resultQ.next()){
                    if(resultQ.getString("NombreUsuario").equals(usuario)){
                        return "Empleado";
                    }
                }
            }
        } catch (SQLException ex) {
            System.out.println(ex);
            Alert a = new Alert(Alert.AlertType.ERROR,"Error al ingresar los datos",ButtonType.OK);
            a.setHeaderText(null);a.showAndWait();
        }
        return "";
    }
    
    public void SolicitarCita(){
        Statement stm = null;
        st=null;
        try {
            String doctor = cmbDoctor.getValue();
            String idMascota = cmbMascota.getValue().substring(0,3);
            String fecha_Cita = fechaCita.getValue().toString();
            String duracion = cmbDuracion.getValue().substring(0,2);
            String descripcion = txtDescripcion.getText().toUpperCase();
            
            if (st==null&stm==null){
                String Query = "SELECT idCita FROM Cita ORDER BY idCita DESC LIMIT 1";
                stm = VentanaInicial.conexionDB.createStatement();
                java.sql.ResultSet result = stm.executeQuery(Query);result.next();;
                int codigo = result.getInt("idCita")+1;
                st = VentanaInicial.conexionDB.prepareStatement("INSERT INTO Cita VALUES (?,?,?,?,?,?,?,?)");
                st.setInt(1, codigo);st.setString(2, doctor);st.setString(3, descripcion);
                st.setDate(4, Date.valueOf(fecha_Cita));st.setInt(5, Integer.parseInt(duracion));st.setString(6, idMascota);
                if (verificarUsuario(VentanaLogin.getNomUsuario()).equals("Empleado")){
                    st.setString(7, VentanaLogin.getNomUsuario());st.setNull(8, 0);
                } else if (verificarUsuario(VentanaLogin.getNomUsuario()).equals("Adoptante")){
                    st.setNull(7, 0);st.setString(8, txtNomUsuario.getText());
                }
                st.executeUpdate();
                Alert a = new Alert(Alert.AlertType.INFORMATION,"Cita registrada correctamente",ButtonType.OK);
                a.setHeaderText(null);a.showAndWait();VentanaHomeScreen.getStHome().close();limpiarCampos();
            }
        } catch (SQLException ex) {
            System.out.println(ex);
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
    
}
