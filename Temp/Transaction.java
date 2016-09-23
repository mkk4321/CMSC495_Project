/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cmsc495_tt;

import java.util.Date;
import java.sql.*;
import java.util.*;
import java.text.SimpleDateFormat;
import java.text.DateFormat;


public class Transaction {
    protected int transNum;
    protected String transType;
    protected double merchAmount;
    protected double taxRate;
    protected double taxAmount;
    protected int totUnits;
    protected double totAmount;
    protected String paymentType;
    
    
    public Vector<TransactionItem> transItemVector = new <TransactionItem> Vector();
    public Vector<InventoryItem> invVector = new <InventoryItem> Vector();
    public Store storeObj = new Store();
    public Employee employeeObj = new Employee();
    public TransactionTableModel transModelObj;
    
    Transaction() {
        for (int i=0;i<40;i++) {
            transItemVector.add(i, new TransactionItem());
        }
        transModelObj = new TransactionTableModel(transItemVector); 
        transType = "Sales";
        merchAmount = 0;
        taxRate = 0;
        taxAmount = 0;
        totUnits = 0;
        totAmount = 0;
        paymentType = "Cash";
    }
    /*  Construct added by Manoj */
    /*  This populates employee and store objects */
    Transaction(Employee value) {
        
        Connection con = null;
        ResultSet rs = null;
        con = ConnectManager.getConnection();
    
        String sqlStr1 = "select trans_num from transaction_numbers";
        String sqlStr2 = "update transaction_numbers set trans_num = ? ";
             
        
        try {
            Statement stmt1 = con.createStatement();
            rs = stmt1.executeQuery(sqlStr1);
            while(rs.next()) {
               transNum = rs.getInt(1);
            }
            transNum = transNum + 1;
            rs.close();
            stmt1.close();
            PreparedStatement stmt2 = con.prepareStatement(sqlStr2);
            stmt2.setInt(1, transNum);
            stmt2.executeUpdate();
            stmt2.close();
              
            con.close();
        } catch (SQLException ex) {System.out.println(ex);
        }
        this.employeeObj = value;
        this.storeObj = new Store(employeeObj.getStoreNum());
        for (int i=0;i<40;i++) {
            transItemVector.add(i, new TransactionItem());
        }
        transModelObj = new TransactionTableModel(transItemVector);
        transType = "Sales";
        merchAmount = 0;
        taxRate = storeObj.getTaxRate();
        taxAmount = 0;
        totUnits = 0;
        totAmount = 0;
        paymentType = "Cash";
    }
    
    public void setTransNum(int value){
        transNum = value;
    }
    
    public void setTransType(String value) {
        transType = value;
    }
    public void setVectorTransItems(Vector vec){
        transItemVector = vec;
    }
    public void setMerchAmount(double value){
        merchAmount = value;
    }
  
  
    public int getTransNum(){
        return transNum;
    }

    public String getTransType() {
        return transType;
    }
    public Vector getVectorTransItems(){
        return transItemVector;
    }
    
    public double getMerchAmount() {
        return merchAmount;
    }
    public double getTaxAmount() {
        return taxAmount;
    }
    public double getTotAmount() {
        return totAmount;
    }
    public int getTotUnits() {
        return totUnits;
    }
    public String upcChanged(int rw, int cl, String upcCode) {
        String msgStr = "";
        InventoryItem invItem = new InventoryItem(storeObj.getStoreNum(), upcCode);
        if (invItem.getStyle() == null|| invItem.getStyle() == "") {
            msgStr = "Error 2.1 Invalid Item Entered: UPC " + upcCode;
            transItemVector.elementAt(rw).setUPC("");
            return msgStr;
        }
        else
        {
        invVector.add(rw, invItem);
        transItemVector.elementAt(rw).setStyle(invItem.getStyle());
        transItemVector.elementAt(rw).setStyleName(invItem.getStyleName());
        transItemVector.elementAt(rw).setColor(invItem.getColor());
        transItemVector.elementAt(rw).setColorName(invItem.getColorName());
        transItemVector.elementAt(rw).setSize(invItem.getSize());
        transItemVector.elementAt(rw).setGender(invItem.getGender());
        transItemVector.elementAt(rw).setPrice(invItem.getPrice());
        return "";
        }   
    }
    
    public String unitsChanged(int rw, int cl, int units) {
        String msgStr = "";
        if (invVector.size() - 1 >= rw ) {
         
            if (invVector.elementAt(rw).getStyle() == "") 
            {
                msgStr = "Error 2.2 No item entered ";
                transItemVector.elementAt(rw).setUnits(0);
                return msgStr;
           
            }
            else 
            { 
                if (units > invVector.elementAt(rw).getUnits()) {
                msgStr = "Error 2.3  Not enough inventory:  Inv = " + units;
                transItemVector.elementAt(rw).setUnits(0);
                return msgStr;
                }
                else 
                {
                    double price = transItemVector.elementAt(rw).getPrice();
                    if (this.transType == "Returns")
                    {
                        invVector.elementAt(rw).addReturnsUnits(units);
                        transItemVector.elementAt(rw).setAmount(units * price * -1);
                    
                    }
                    else
                    {
                        invVector.elementAt(rw).addSalesUnits(units);
                        transItemVector.elementAt(rw).setAmount(units * price);
                    }
                    merchAmount = 0;
                    taxAmount = 0;
                    totAmount = 0;
                    totUnits = 0;
                    for (int i=0;i<40;i++) {
                        double lineAmt = transItemVector.elementAt(i).getAmount();
                        if (lineAmt != 0)  {
                            merchAmount += lineAmt;
                            totUnits += transItemVector.elementAt(i).getUnits();
                        }
                    }
                    taxAmount = ( merchAmount * taxRate ) / 100;
                    totAmount = merchAmount + taxAmount;
                    return "";
                }
                
            } 
        }
        else
        {
        msgStr = "Error 2.2 No item entered ";
                transItemVector.elementAt(rw).setUnits(0);
                return msgStr;
        }
    }
    
    public void transTypeSelected(String value) {
        transType = value;     
    }
    public String salesReceiptForReturnEntered(String value) {
        return "";
    }
    public String paymentTypeSelected(String value) {
        return "";
    }
    public String transactionCompleted() {
        Connection con = null;
        ResultSet rs = null;
        con = ConnectManager.getConnection();
        // Insert transaction details
        String sqlStr = "insert into transaction_header values " +
                        "(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
             
        
        try {
            PreparedStatement stmt = con.prepareStatement(sqlStr);
            for (int i=0;i < transItemVector.size(); i++) {
                stmt.setInt(1, transNum);
                stmt.setInt(2, i);
                stmt.setString(3, transItemVector.elementAt(i).getUPC());
                stmt.setString(4, transItemVector.elementAt(i).getStyle());
                stmt.setString(5, transItemVector.elementAt(i).getStyleName());
                stmt.setString(6, transItemVector.elementAt(i).getColor());
                stmt.setString(7, transItemVector.elementAt(i).getColorName());
                stmt.setString(8, transItemVector.elementAt(i).getSize());
                stmt.setString(9, transItemVector.elementAt(i).getGender());
                stmt.setDouble(10, transItemVector.elementAt(i).getPrice());
                stmt.setInt(11,transItemVector.elementAt(i).getUnits());
                stmt.setDouble(12, transItemVector.elementAt(i).getAmount());
                stmt.setBoolean(13, transItemVector.elementAt(i).getReturned());
                stmt.setInt(14, transItemVector.elementAt(i).getReturnUnits());
                DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
                String dateStr = df.format(transItemVector.elementAt(i).getReturnDate());
                stmt.setString(15,dateStr);
                stmt.setInt(18, transItemVector.elementAt(i).getReturnTransNum());
                df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                dateStr = df.format(new Date());
                stmt.setString(19,dateStr);
                stmt.setString(20, employeeObj.getEmployeeID());
                stmt.setString(21,dateStr);
                stmt.setString(22, employeeObj.getEmployeeID());    
                stmt.executeUpdate();
            }
            stmt.close();
            if (transType == "Returns" && paymentType == "Store Credit") {
                    
               
            }
            if (transType == "Returns" && paymentType == "Cash") {
                
            }
              
            con.close();
        } catch (SQLException ex) {System.out.println(ex);
        }
        return "";
    }
    public void clearTransaction() {
        transItemVector.clear();
        for (int i=0;i<40;i++) {
            transItemVector.add(i, new TransactionItem());
        }
        transModelObj = new TransactionTableModel(transItemVector);
        transType = "Sales";
        merchAmount = 0;
        taxAmount = 0;
        totUnits = 0;
        totAmount = 0;
        paymentType = "Cash";
        transModelObj = new TransactionTableModel(transItemVector);
    }
    
    public String setReturnsFromPreviousSales(int trans) {
        Connection con = null;
        Statement stmt = null;
        ResultSet rs = null;
        con = ConnectManager.getConnection();
        String trans_str = String.valueOf(trans);
     
        String sqlStr = "select * from transaction_details where trans_num = " +
                     trans_str + " and store_credit_issued = 0 ";
        
        try {
            stmt = con.createStatement(); 
            rs = stmt.executeQuery(sqlStr);
            transItemVector.clear();
            int i = -1;
            while(rs.next()) {
                i++;
                TransactionItem transItemObj = new TransactionItem();
                transItemObj.setUPC(rs.getString(3));
                transItemObj.setStyle(rs.getString(4));
                transItemObj.setColor(rs.getString(5));
                transItemObj.setColorName(rs.getString(6));
                transItemObj.setSize(rs.getString(7));
                transItemObj.setPrice(rs.getDouble(8));
                transItemObj.setUnits(rs.getInt(9));
                transItemObj.setAmount(rs.getDouble(10)* -1);
                transItemObj.setOrigTransNum(rs.getInt(1));
                transItemObj.setOrigTransSeq(rs.getInt(2));
                
             
                transItemVector.add(i, transItemObj);
                
                
            }
            con.close();
        } catch (SQLException ex) {System.out.println("Failed to create the Statment");
        }
        transModelObj = new TransactionTableModel(transItemVector);
        return ""; 
    }
   
}