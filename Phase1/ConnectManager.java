/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cmsc495_tt;

/**
 *
 * @author Manoj
 */
import java.util.*;
import java.sql.*;  
public class ConnectManager  {
    private static String url = "jdbc:mysql://212.1.208.51/mkk4321c_cmsc495";
    private static String driverName = "com.mysql.jdbc.Driver";
    private static String username = "mkk4321c_sa";
    private static String password = "cmsc495";
    private static Connection con;
    
    public static Connection getConnection() {
        try {
            Class.forName(driverName);
            try {
                con = DriverManager.getConnection(url, username, password);
                } catch(SQLException ex) {
                System.out.println(ex);
                throw new RuntimeException(ex);
                }
        } catch (ClassNotFoundException ex) {
            System.out.println(ex);
            
        }
        return con;
    }
    
}
