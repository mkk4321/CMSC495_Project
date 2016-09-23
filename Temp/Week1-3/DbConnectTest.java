/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Tests;
import java.util.*;
import java.sql.*;  
/**
 *
 * @author Manoj
 */
public class DbConnectTest {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        
                 try {
                      
                Class.forName("com.mysql.jdbc.Driver");  
                Connection con=DriverManager.getConnection(
        "jdbc:mysql://mysql2.000webhost.com/a9638626_cmsc495","a9638626_sa","cmsc495");  
                Statement stmt = con.createStatement();
                ResultSet rs = stmt.executeQuery("select * from color_master");
                  while(rs.next())  
                    System.out.println("fetching " + rs.getString(1)+"  "+rs.getString(2));  
                    con.close();
            }  catch(Exception e){ System.out.println(e); }
               
    }
    
}
