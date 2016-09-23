
package CMSC495_TT;
/**
 *
 * @author vance.molhusen
 */

/*  Tenacious Turtles Team
    Apparel Point of Sale (APOS) system
    Employee class
*/

import java.sql.*;
import java.util.*;

public class Employee {
    
    protected int storeNum;
    protected String employeeID;
    protected String firstName;
    protected String lastName;
    protected String designation;
    protected String password;
    private Connection con = null;
    private Statement stmt = null;
    private ResultSet rs = null;
    // Constructor
    Employee(){
        storeNum = -1;
        employeeID = null;
        firstName = null;
        lastName = null;
        designation = null;
    }
    // Constructor with store numner and employee ID password
    Employee(int storeNumIn, String employeeIDIn) {
        con = ConnectManager.getConnection();
        String store_str = String.valueOf(storeNumIn);
     
        String sqlStr = "select * from employee_master where store_num = " +
                     store_str + " and employee_id = '" + employeeIDIn + "'";
        
        try {
            stmt = con.createStatement(); 
            rs = stmt.executeQuery(sqlStr);
            while(rs.next()) {
                this.firstName = rs.getString(3);
                this.lastName = rs.getString(4);
                this.designation = rs.getString(5);
                this.password = rs.getString(6);
                         }
            this.employeeID = employeeIDIn;
            this.storeNum = storeNumIn;
            con.close();
        } catch (SQLException ex) {System.out.println("Failed to create the Statment");
        }
    }
    
    // Setters
    public void setStoreNum(int value){ storeNum = value;}
    public void setEmployeeID(String value){ employeeID = value;}
    public void setFirstName(String value){ firstName = value;}
    public void setLastName(String value){ lastName = value;}
    public void setDesignation(String value){designation = value;}
    
    // Getters
    public int getStoreNum(){
        return storeNum;
    }
    public String getEmployeeID(){
        return employeeID;
    }
    public String getFirstName(){
        return firstName;
    }
    public String getLastName(){
        return lastName;
    }
    public String getDesignation(){
        return designation;
    }
    public String getPassword() {
        return password;
    }

}