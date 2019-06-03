/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package database;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 *
 * @author Cristian
 */
public class ConexionMysql {
    private Connection conn;
    private String userName;
    private String password;
    private String url;
    
    
    public ConexionMysql(){
    
        try{
           /** Class.forName("com.mysql.jdbc.Driver");
            url = "jdbc:mysql://192.168.43.147:3306/myspa";
            userName = "user1";
            password = "root";**/
            Class.forName("com.mysql.jdbc.Driver");
            url = "jdbc:mysql://localhost:3306/sistemaNomina";
            userName = "root";
            password = "root";
        }
        catch(Exception e){
            throw new RuntimeException(e);
        }
    }
    
    public Connection getConn(){
        return conn;
    }
    
    public Connection abrir() throws Exception{
        conn = DriverManager.getConnection(url, userName, password);
        return conn;
    }
    
    public void cerrar(){
        try{
            if(conn != null){
                conn.close();
                conn = null;
            }
        }catch(Exception e){
            throw new RuntimeException(e);
        }
    }
}
