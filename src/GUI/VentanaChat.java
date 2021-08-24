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
public class VentanaChat {
    private VBox root = new VBox();
    private GridPane panelChat = new GridPane();
    private Label lblTitulo = new Label("* CHAT *");
    private Button btnEnviar = new Button("Enviar");
    private DatePicker fechaChat = new DatePicker();
    private TextField txtFecha = new TextField();
    private ComboBox<String> cmbDe = new ComboBox<>();
    private ComboBox<String> cmbPara = new ComboBox<>();
    private TextArea txtMensaje = new TextArea();
    Font fuenteT=new Font("Times New Roman",25.0);
    PreparedStatement st=null;
    
    public VentanaChat(){
        organizarControles();cmbUsuarios();
        btnEnviar.setOnAction(e->Enviar());
    }
    
    public void organizarControles(){
        cmbDe.setPrefWidth(175);cmbPara.setPrefWidth(175);txtFecha.setEditable(false);
        DateTimeFormatter formatoFecha = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        fechaChat.setConverter(new LocalDateStringConverter(formatoFecha, null));
        fechaChat.setEditable(false);fechaChat.setValue(LocalDate.now());txtFecha.setText(fechaChat.getValue().toString());
        txtMensaje.setPrefSize(100, 100);txtMensaje.setPromptText("Ingrese Mensaje");txtMensaje.setWrapText(true);
        root.setSpacing(10);root.setAlignment(Pos.CENTER);
        root.setBackground(new Background(new BackgroundFill(Color.web("#D4ECE1"),CornerRadii.EMPTY,Insets.EMPTY)));
        lblTitulo.setFont(fuenteT);lblTitulo.setPadding(new Insets(10,0,50,0));lblTitulo.setTextFill(Color.WHITE);
        lblTitulo.setBackground(new Background(new BackgroundFill(Color.web("#039c74"),
                                new CornerRadii(5.0), new Insets(0,-100,40,-100))));
        panelChat.setHgap(20);panelChat.setVgap(2);panelChat.setAlignment(Pos.CENTER);
        panelChat.setPadding(new Insets(0,0,10,2));
        panelChat.add(new Label("Fecha: "),0,0);panelChat.add(txtFecha,1,0);
        panelChat.add(new Label("De: "),0,1);panelChat.add(cmbDe,1,1);
        panelChat.add(new Label("Para: "),0,2);panelChat.add(cmbPara,1,2);
        panelChat.add(new Label("Mensaje: "),0,3);panelChat.add(txtMensaje,1,3);
        root.getChildren().addAll(lblTitulo,panelChat,btnEnviar);
    }
    
    public void cmbUsuarios(){
        Statement stm = null;
        ArrayList<String> listaUsuarios = new ArrayList<>();
        try {
            if (stm==null){
                String Query = "SELECT Correo FROM Usuario ORDER BY NombreUsuario";
                stm = VentanaInicial.conexionDB.createStatement();
                java.sql.ResultSet result = stm.executeQuery(Query);
                
                while(result.next()){
                    listaUsuarios.add(result.getString("Correo"));
                }
                cmbDe.getItems().addAll(listaUsuarios);cmbPara.getItems().addAll(listaUsuarios);
            }
        } catch (SQLException ex) {
            System.out.println(ex);
            Alert a = new Alert(Alert.AlertType.ERROR,"Error en la adquisición de datos",ButtonType.OK);
            a.setHeaderText(null);a.showAndWait();cmbDe.getItems().clear();cmbPara.getItems().clear();
        }
    }
    
    public String obtenerNombreUsuario(String correo){
        Statement stm = null;
        try {
            if (stm==null){
                String Query = "SELECT NombreUsuario FROM Usuario WHERE Correo=\""+correo+"\"";
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
    
    public void limpiarCampos(){
        fechaChat.setValue(LocalDate.now());cmbDe.getItems().clear();cmbPara.getItems().clear();txtMensaje.clear();
    }
    
    public String verificarUsuario(String usuario){
        Statement stm = null;
        Statement stmt = null;
        Statement smt = null;
        try {
            if(stm==null&stmt==null&smt==null){
                String query = "SELECT NombreUsuario FROM Adoptante";
                stm = VentanaInicial.conexionDB.createStatement();
                java.sql.ResultSet result = stm.executeQuery(query);
                while (result.next()){
                    if(result.getString("NombreUsuario").equals(usuario)){
                        return "Adoptante";
                    }
                }
                String Query = "SELECT NombreUsuario FROM Empleado";
                stmt = VentanaInicial.conexionDB.createStatement();
                java.sql.ResultSet resultQ = stmt.executeQuery(Query);
                
                while (resultQ.next()){
                    if(resultQ.getString("NombreUsuario").equals(usuario)){
                        return "Empleado";
                    }
                }
                String q = "SELECT NombreUsuario FROM Doctor";
                smt = VentanaInicial.conexionDB.createStatement();
                java.sql.ResultSet resultq = stmt.executeQuery(q);
                
                while (resultq.next()){
                    if(resultq.getString("NombreUsuario").equals(usuario)){
                        return "Doctor";
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
    
    public void Enviar(){
        Statement stm = null;
        st=null;
        try {
            String de = obtenerNombreUsuario(cmbDe.getValue());
            String para = obtenerNombreUsuario(cmbPara.getValue());
            String fecha_Chat = fechaChat.getValue().toString();
            String mensaje = txtMensaje.getText().toUpperCase();
            
            if (st==null&stm==null){
                String Query = "SELECT idChat FROM Chat ORDER BY idChat DESC LIMIT 1";
                stm = VentanaInicial.conexionDB.createStatement();
                java.sql.ResultSet result = stm.executeQuery(Query);result.next();;
                int codigo = result.getInt("idChat")+1;
                st = VentanaInicial.conexionDB.prepareStatement("INSERT INTO Chat VALUES (?,?,?,?,?,?)");
                st.setInt(1, codigo);st.setDate(5, Date.valueOf(fecha_Chat));st.setString(6, mensaje);
                if (verificarUsuario(de).equals("Empleado")&verificarUsuario(para).equals("Adoptante")){
                    st.setString(2, de);st.setString(3, para);st.setNull(4, 0);
                } else if (verificarUsuario(de).equals("Adoptante")&verificarUsuario(para).equals("Empleado")){
                    st.setString(2, para);st.setString(3, de);st.setNull(4, 0);
                } else if (verificarUsuario(de).equals("Empleado")&verificarUsuario(para).equals("Doctor")){
                    st.setString(2, de);st.setNull(3, 0);st.setString(4, para);
                } else if (verificarUsuario(de).equals("Doctor")&verificarUsuario(para).equals("Empleado")){
                    st.setString(2, para);st.setNull(3, 0);st.setString(4, de);
                } else if (verificarUsuario(de).equals("Adoptante")&verificarUsuario(para).equals("Doctor")){
                    st.setNull(2, 0);st.setString(3, de);st.setString(4, para);
                } else if (verificarUsuario(de).equals("Doctor")&verificarUsuario(para).equals("Adoptante")){
                    st.setNull(2, 0);st.setString(3, para);st.setString(4, de);
                }
                st.executeUpdate();
                Alert a = new Alert(Alert.AlertType.INFORMATION,"Mensaje enviado correctamente",ButtonType.OK);
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
