package CMSC495_TT;

import cmsc495_tt_phase2.*;
import java.util.Date;

/**
 *
 
* @author vance.molhusen
 */

/*  Tenacious Turtles Team
    Apparel Point of Sale (APOS) system
    Transaction Item class
    This has the item details such as style, color, size,
    gender, units, price, and amount
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
    protected boolean returned;
    protected int returnUnits;
    protected Date returnDate;
    protected int returnTransNum;
    protected int origTransSeq;
    protected int origTransNum;
    protected int origUnits;
    
    // Default constructor
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
    this.returned = false;
    this.returnDate = null;
    this.returnTransNum = 0;
    this.returnUnits = 0;
    this.origTransNum = 0;
    this.origTransSeq = 0;
    this.origUnits = 0;
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
        this.amount = this.units * this.price;

    }
    
    public void setAmount(double value){
        this.amount = value;
    }
    
    public void setReturned(boolean value){
        returned = value;
    }
    public void setReturnDate(Date value){
        returnDate = value;
    }
  
    public void setReturnUnits(int value){
        returnUnits = value;
    }
    public void setReturnTransNum(int value){
        returnTransNum = value;
    }
    public void setOrigTransNum(int value) {
        origTransNum = value;
        
    }
    public void setOrigTransSeq(int value) {
        origTransSeq = value;
        
    }
    public void setOrigUnits(int value) {
        origUnits = value;
    }
        
    
    // GETTERS
    
    public double getTransAmount(){
        return amount;
    }

    public boolean getReturned(){
        return returned;
    }
    public Date getReturnDate(){
        return returnDate;
    }
    public int getReturnUnits(){
        return returnUnits;
    }
    public int getReturnTransNum(){
        return returnTransNum;
    }
    public int getOrigTransNum() {
        return origTransNum;
    }
    public int getOrigTransSeq() {
        return origTransSeq;
    }
    
    public int getOrigUnits() {
        return origUnits;
    }
}
