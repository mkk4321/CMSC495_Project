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

import java.sql.*;
import java.util.*;
public class InventoryItem {
    protected int store_num;
    protected String upc;
    protected String style;
    protected String styleName;
    protected String color;
    protected String colorName;
    protected String size;
    protected String gender;
    protected double price;
    protected int units;
    protected int salesUnits;
    protected int returnUnits;
    private Connection con = null;
    private Statement stmt = null;
    private ResultSet rs = null;
    
    
    InventoryItem(){
        this.store_num = 0;
        this.upc = "";
        this.style = "";
        this.styleName = "";
        this.color = "";
        this.colorName = "";
        this.size = "";
        this.gender = "";
        this.price = 0;
        this.units = 0;
    }
    InventoryItem(int storeIn, String upcIn) {
        // this.upc = upcIn;
             
            
        con = ConnectManager.getConnection();
        String store_str = String.valueOf(storeIn);
     
        String sqlStr = "select * from store_inventory where store_num = " +
                     store_str + " and upc_code = '" + upcIn + "'";
        
        try {
            stmt = con.createStatement(); 
            rs = stmt.executeQuery(sqlStr);
            while(rs.next()) {
                this.style = rs.getString(3);
                this.color = rs.getString(4);
                this.size = rs.getString(5);
                this.gender = rs.getString(6);
                this.units = rs.getInt(7);
                this.price = rs.getDouble(8);
                this.salesUnits = 0;
                this.returnUnits = 0;
            }
        } catch (SQLException ex) {System.out.println("Failed to create the Statment");
        }
        try {
         sqlStr = "select * from style_master where style = '" +
                  this.style + "' ";
            rs = stmt.executeQuery(sqlStr);
            while(rs.next()) {
                this.styleName = rs.getString(2);
              }
        } catch (SQLException ex) {System.out.println("Failed to create the Statment");
        } 
        try {
         sqlStr = "select * from color_master where color_code = '" +
                  this.color + "' ";
            rs = stmt.executeQuery(sqlStr);
            while(rs.next()) {
                this.colorName = rs.getString(2);
              }
        } catch (SQLException ex) {System.out.println("Failed to create the Statment");
        } 
    }
       //GETTERS
        
    public String getUPC(){
        return this.upc;
    }
    
    public String getStyle(){
        return this.style;
    }
    
    public String getStyleName(){
        return this.styleName;
    }
    
    public String getColor(){
        return this.color;
    }
    
    public String getColorName(){
        return this.colorName;
    }
    
    public String getSize(){
        return this.size;
    }
    
    public String getGender(){
        return this.gender;
    }
    
    public double getPrice(){
        return this.price;
    }
    
    public int getUnits(){
        return this.units;
    }
    
  
    
    //SETTERS
    public void setUPC(String value){
        this.upc = value;
        setStyle("MKK1");
        setStyleName("Set Style Name");
    }
    
    public void setStyle(String value){
        this.style = value;
    }
    
    public void setStyleName(String value){
        this.styleName = value;
    }
    
    public void setColor(String value){
        this.color = value;
    }
    
    public void setColorName(String value){
        this.colorName = value;
    }
    
    public void setSize(String value){
        this.size = value;
    }
    
    public void setGender(String value){
        this.gender = value;
    }
    
    public void setPrice(double value){
        this.price = value;
    }
    
    public void setUnits(int value){
        this.units = value;

    }
    public void addSalesUnits(int value) {
       this.salesUnits += value; 
    }
    public void addReturnsUnits(int value) {
        this.returnUnits += value;
    }
}
