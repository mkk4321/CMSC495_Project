package CMSC495_TT;

/**
 *
 * @author vance.molhusen
 */

/*
    Tenacious Turtles Team
    Apparel Point of Sale(APOS) system

    Store class
    This contains store attributes and methods.

*/

import java.sql.*;
import java.util.*;
import java.lang.RuntimeException;
public class Store {
    
    private int storeNum;
    private String address1;
    private String address2;
    private String city;
    private String state;
    private String zipcode;
    private double tax_rate;
    private Connection con = null;
    private Statement stmt = null;
    private ResultSet rs = null;   
    
    // Default constructor
    Store(){
        storeNum = -1;
        address1 = null;
        address2 = null;
        city = null;
        state = null;
        zipcode = null;
        tax_rate =0;
    }
    
    // Consgtructor with store number parameter
    Store(int storeNumIn) {   
    
      // Creating db connection 
      con = ConnectManager.getConnection();
     
        String store_str = String.valueOf(storeNumIn);
     
        String sqlStr = "select * from store_master where store_num = " +
                     store_str ;
        // Getting values from database
        try {
            stmt = con.createStatement(); 
            rs = stmt.executeQuery(sqlStr);
            while(rs.next()) {
                this.address1 = rs.getString(2);
                this.address2 = rs.getString(3);
                this.city = rs.getString(4);
                this.state = rs.getString(5);
                this.zipcode = rs.getString(6);
                this.tax_rate = rs.getDouble(7);
                         }
            this.storeNum = storeNumIn;
            con.close();
        } catch (Exception ex) {
            System.out.println("ex");
            throw new RuntimeException(ex);
           
        }      
    }
    // GETTERS
    public int getStoreNum(){
        return storeNum;
    }
    public String getAddress1() {
        return address1;
      
    }
    public String getAddress2() {
        return address2;
    }
    public String getCity() {
        return city;
    }
    public String getState() {
        return state;
    }
    public String getZipcode() {
        return zipcode;
    }
    public double getTaxRate() {
        return tax_rate;
    }
    // Full address
    public String getFullAddress() {
        String fullAddress = address1 + " " + address2 + ", " +
                city + ", " + state + " " + zipcode;
        return fullAddress;
    }
}