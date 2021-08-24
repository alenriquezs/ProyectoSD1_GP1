/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Clases;

import java.sql.*;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

/**
 *
 * @author Alfonso E
 */
public class DbConnection {
    /**Parametros de conexion*/
    static String bd = "proyecto";
    static String login = "root";
    static String password = "1800admin";
    static String timezone = "?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC&useServerPrepStmts=true";
    static String url = "jdbc:mysql://localhost/"+bd+timezone;

    Connection connection = null;

    /** Constructor de DbConnection */
    public DbConnection() {
        try{
            //obtenemos el driver de para mysql
            Class.forName("com.mysql.cj.jdbc.Driver");
            //obtenemos la conexión
            connection = DriverManager.getConnection(url,login,password);

            if (connection!=null){
               Alert a = new Alert(Alert.AlertType.CONFIRMATION,"Conexión a base de datos \""+bd+"\" OK\n",ButtonType.OK);
               a.setHeaderText(null);a.showAndWait();
            }
        }
        catch(SQLException e){
           System.out.println(e);
        }catch(ClassNotFoundException e){
           System.out.println(e);
        }catch(Exception e){
           System.out.println(e);
        }
    }
    /**Permite retornar la conexión*/
    public Connection getConnection(){
       return connection;
    }

    public void desconectar(){
       connection = null;
    }

    public void getValues(String table_name, Connection connection) {
        try {
            String Query = "SELECT * FROM " + table_name;
            Statement st = connection.createStatement();
            java.sql.ResultSet resultSet;
            resultSet = st.executeQuery(Query);

            while (resultSet.next()) {
                System.out.println(resultSet.getString("NombreUsuario"));
            }

        } catch (SQLException ex) {
             Alert a = new Alert(Alert.AlertType.ERROR,"Error en la adquisición de datos \""+bd+"\" OK\n",ButtonType.OK);
             a.setHeaderText(null);a.showAndWait();
        }
    }
    
}

