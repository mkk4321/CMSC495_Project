/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cmsc495_tt;

/**
 *
 
* @author vance.molhusen
 */
public class TransactionItem {
    
    protected String upc;
    protected String style;
    protected String styleName;
    protected String color;
    protected String colorName;
    protected String size;
    protected String gender;
    protected double price;
    protected int units;
    protected double amount;
    
    TransactionItem(){
    this.upc = "";
    this.style = "";
    this.styleName = "";
    this.color = "";
    this.colorName = "";
    this.size = "";
    this.gender = "";
    this.price = 0;
    this.units = 0;
    this.amount = 0;
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
    
    public double getAmount(){
        return this.amount;
    }
    
    //SETTERS
    public void setUPC(String value){
        this.upc = value;
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
    
    public void setAmount(double value){
        this.amount = value;
    }
}
