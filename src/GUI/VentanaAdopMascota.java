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
public class VentanaAdopMascota {
    private VBox root = new VBox();
    private GridPane panelAdopMascota = new GridPane();
    private Label lblTitulo = new Label("* ADOPTAR MASCOTA *");
    private Button btnRegMascota = new Button("Registrar");
    private TextField txtAdoptante = new TextField();
    private ComboBox<String> cmbMascota = new ComboBox<>();
    private TextField txtConvivientes = new TextField();
    private TextField txtCartasRecom = new TextField();
    private TextField txtInfoVivienda = new TextField();
    private ComboBox<String> cmbPatio = new ComboBox<>();
    Font fuenteT=new Font("Times New Roman",25.0);
    PreparedStatement st=null;
    Statement stmt = null;
    
    public VentanaAdopMascota(){
        organizarControles();
        cmbMascota();cmbPatio();
        btnRegMascota.setOnAction(e-> AdoptarMascota());
    }
    
    public void organizarControles(){
        cmbMascota.setPrefWidth(162);cmbPatio.setPrefWidth(162);
        root.setSpacing(10);root.setAlignment(Pos.CENTER);
        root.setBackground(new Background(new BackgroundFill(Color.web("#D4ECE1"),CornerRadii.EMPTY,Insets.EMPTY)));
        lblTitulo.setFont(fuenteT);lblTitulo.setPadding(new Insets(10,0,50,0));lblTitulo.setTextFill(Color.WHITE);
        lblTitulo.setBackground(new Background(new BackgroundFill(Color.web("#039c74"),
                                new CornerRadii(5.0), new Insets(0,-100,40,-100))));
        panelAdopMascota.setHgap(20);panelAdopMascota.setVgap(2);panelAdopMascota.setAlignment(Pos.CENTER);
        panelAdopMascota.setPadding(new Insets(0,0,10,2));
        panelAdopMascota.add(new Label("Cédula Adoptante: "),0,0);panelAdopMascota.add(txtAdoptante,1,0);
        panelAdopMascota.add(new Label("Seleccione Mascota: "),0,1);panelAdopMascota.add(cmbMascota,1,1);
        panelAdopMascota.add(new Label("Tiene Patio: "),0,2);panelAdopMascota.add(cmbPatio,1,2);
        panelAdopMascota.add(new Label("N° Convivientes: "),0,3);panelAdopMascota.add(txtConvivientes,1,3);
        panelAdopMascota.add(new Label("Info. Vivienda: "),0,4);panelAdopMascota.add(txtInfoVivienda,1,4);
        panelAdopMascota.add(new Label("Cartas Recomend.: "),0,5);panelAdopMascota.add(txtCartasRecom,1,5);        
        root.getChildren().addAll(lblTitulo,panelAdopMascota,btnRegMascota);
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
        try {
            ArrayList<String> listaMascotas = new ArrayList<>();
            if (stm==null){
                String Query = "SELECT idMascota, Raza FROM Mascota WHERE Adoptante IS NULL";
                stm = VentanaInicial.conexionDB.createStatement();
                java.sql.ResultSet result = stm.executeQuery(Query);
                
                while (result.next()){
                    listaMascotas.add(result.getString("idMascota")+"-"+result.getString("Raza"));
                }
                cmbMascota.getItems().addAll(listaMascotas);
            }             
        } catch (SQLException ex) {
            Alert a = new Alert(Alert.AlertType.ERROR,"Error en la adquisición de datos",ButtonType.OK);
            a.setHeaderText(null);a.showAndWait();
        }
    }
    
    public void cmbPatio(){
        cmbPatio.getItems().addAll("SI","NO");
    }
    
    public void limpiarCampos(){
        txtAdoptante.clear();cmbMascota.getItems().clear();cmbPatio.getItems().clear();
        txtConvivientes.clear();txtInfoVivienda.clear();txtCartasRecom.clear();
    }
    
    public void registrarAdoptMascota(String adoptante, String idMascota){
        st=null;
        try {
            if (st==null){
                st = VentanaInicial.conexionDB.prepareStatement("UPDATE MASCOTA SET Adoptante=? WHERE idMascota=\""+idMascota+"\"");
                st.setString(1, adoptante);st.executeUpdate();
            }             
        } catch (SQLException ex) {
            Alert a = new Alert(Alert.AlertType.ERROR,"Error al ingresar los datos",ButtonType.OK);
            a.setHeaderText(null);a.showAndWait();
        }
    }
    
    public void registrarCartaRecom(String adoptante){
        String cartaRecom = txtCartasRecom.getText().toUpperCase();
        st = null;
        stmt = null;
        try {
            if (st==null & stmt==null){
                String Query = "SELECT idCarta FROM CartaRecomendacion ORDER BY idCarta DESC LIMIT 1";
                stmt = VentanaInicial.conexionDB.createStatement();
                java.sql.ResultSet result = stmt.executeQuery(Query);result.next();;
                int idCarta = result.getInt("idCarta")+1;
                st = VentanaInicial.conexionDB.prepareStatement("INSERT INTO CartaRecomendacion VALUES (?,?,?)");
                st.setInt(1, idCarta);st.setString(2, adoptante);st.setString(3, cartaRecom);st.executeUpdate();
            }
        } catch (SQLException ex) {
            Alert a = new Alert(Alert.AlertType.ERROR,"Error al ingresar los datos",ButtonType.OK);
            a.setHeaderText(null);a.showAndWait();
        }
    }
    
    public void AdoptarMascota(){
        st=null;
        try {
            String adoptante = obtenerNombreUsuario(txtAdoptante.getText());
            String idMascota = cmbMascota.getValue().substring(0, 3);
            String NumConv = txtConvivientes.getText();
            String infoVivienda = txtInfoVivienda.getText().toUpperCase();
            boolean patio = false;
            if (cmbPatio.getValue().equals("SI")){
                patio = true; 
            } else if (cmbPatio.getValue().equals("NO")) {
                patio = false;
            }
            if (st==null){
                st = VentanaInicial.conexionDB.prepareStatement("UPDATE Adoptante SET NumConvivientes=?,Patio=?,InfoVivienda=?"
                        + " WHERE NombreUsuario=\""+adoptante+"\"");
                st.setInt(1, Integer.parseInt(NumConv));st.setBoolean(2, patio);st.setString(3, infoVivienda);
                st.executeUpdate();registrarCartaRecom(adoptante);registrarAdoptMascota(adoptante,idMascota);
                Alert a = new Alert(Alert.AlertType.INFORMATION,"Mascota adoptada correctamente",ButtonType.OK);
                a.setHeaderText(null);a.showAndWait();VentanaHomeScreen.getStHome().close();limpiarCampos();
                cmbMascota();cmbPatio();
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
