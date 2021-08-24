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
public class VentanaRealReportes {
    private VBox root = new VBox();
    private GridPane panelRealReportes = new GridPane();
    private Label lblTitulo = new Label("* REALIZAR REPORTES *");
    private Button btnRealReporte = new Button("Registrar");
    private Button btnSubirFoto = new Button("Subir Foto Mascota");
    private DatePicker fechaReporte = new DatePicker();
    private TextField txtAdoptante = new TextField();
    private TextField txtNomUsuario = new TextField();
    private ComboBox<String> cmbMascota = new ComboBox<>();
    private TextArea txtDescripcion = new TextArea();
    Font fuenteT=new Font("Times New Roman",25.0);
    PreparedStatement st=null;
    
    public VentanaRealReportes(){
        organizarControles();
        txtAdoptante.setOnKeyPressed(e -> {if(e.getCode() == KeyCode.ENTER){cmbMascota();}});
        btnRealReporte.setOnAction(e-> RealReportes());
    }
    
    public void organizarControles(){
        cmbMascota.setPrefWidth(175);txtNomUsuario.setEditable(false);txtAdoptante.setPromptText("Pulse ENTER al finalizar");
        DateTimeFormatter formatoFecha = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        fechaReporte.setConverter(new LocalDateStringConverter(formatoFecha, null));
        fechaReporte.setEditable(false);fechaReporte.setValue(LocalDate.now());
        txtDescripcion.setPrefSize(100, 100);txtDescripcion.setPromptText("Ingrese Descripción");
        txtDescripcion.setWrapText(true);root.setSpacing(10);root.setAlignment(Pos.CENTER);
        root.setBackground(new Background(new BackgroundFill(Color.web("#D4ECE1"),CornerRadii.EMPTY,Insets.EMPTY)));
        lblTitulo.setFont(fuenteT);lblTitulo.setPadding(new Insets(10,0,50,0));lblTitulo.setTextFill(Color.WHITE);
        lblTitulo.setBackground(new Background(new BackgroundFill(Color.web("#039c74"),
                                new CornerRadii(5.0), new Insets(0,-100,40,-100))));
        panelRealReportes.setHgap(20);panelRealReportes.setVgap(2);panelRealReportes.setAlignment(Pos.CENTER);
        panelRealReportes.setPadding(new Insets(0,0,10,2));
        panelRealReportes.add(new Label("Fecha: "),0,0);panelRealReportes.add(fechaReporte,1,0);
        panelRealReportes.add(new Label("Cédula Adoptante: "),0,1);panelRealReportes.add(txtAdoptante,1,1);
        panelRealReportes.add(new Label("Nombre Usuario: "),0,2);panelRealReportes.add(txtNomUsuario,1,2);
        panelRealReportes.add(new Label("Mascota: "),0,3);panelRealReportes.add(cmbMascota,1,3);
        panelRealReportes.add(new Label("Descripción: "),0,4);panelRealReportes.add(txtDescripcion,1,4);
        root.getChildren().addAll(lblTitulo,panelRealReportes,btnSubirFoto,btnRealReporte);
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
            Alert a = new Alert(Alert.AlertType.ERROR,"Error en la adquisición de datos",ButtonType.OK);
            a.setHeaderText(null);a.showAndWait();
        } return null;
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
    
    public void limpiarCampos(){
        txtAdoptante.clear();fechaReporte.setValue(LocalDate.now());
        cmbMascota.getItems().clear();txtDescripcion.clear();txtNomUsuario.clear();
    }
    
    public void RealReportes(){
        Statement stm = null;
        st=null;
        try {
            String idMascota = cmbMascota.getValue().substring(0, 3);
            String fecha_Reporte = fechaReporte.getValue().toString();
            String descripcion = txtDescripcion.getText().toUpperCase();
            
            if (st==null){
                String Query = "SELECT idReporte FROM Reportes ORDER BY idReporte DESC LIMIT 1";
                stm = VentanaInicial.conexionDB.createStatement();
                java.sql.ResultSet result = stm.executeQuery(Query);result.next();
                int codigo = result.getInt("idReporte")+1;
                st = VentanaInicial.conexionDB.prepareStatement("INSERT INTO Reportes VALUES (?,?,?,?,?,?)");
                st.setInt(1, codigo);st.setString(2, txtNomUsuario.getText());st.setString(3, descripcion);
                st.setDate(4, Date.valueOf(fecha_Reporte));st.setString(5, idMascota);st.setNull(6, 0);
                st.executeUpdate();
                Alert a = new Alert(Alert.AlertType.INFORMATION,"Reporte registrado correctamente",ButtonType.OK);
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
}
