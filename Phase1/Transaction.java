package CMSC495_TT;
/**
 *
 * @author Manoj
 */

/*  Transaction class.  This class has most of buisiness logic.
    This class creates store object, employee object, and
    vectors of InventoryItem ad TransactionItem objects. 
    The class has transaction completion logic, upc change logic,
    units change logic, getting prior sales information for returns
    logic, and clear transaction logic.
    

*/
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
    protected String salesReceiptForRet;
    protected String storeCreditNumber;
    protected double storeCreditPayAmt;
    protected double cashPayAmt;
    
    
    protected Vector<TransactionItem> transItemVector = new <TransactionItem> Vector();
    protected Vector<InventoryItem> invVector = new <InventoryItem> Vector();
    protected Store storeObj = new Store();
    protected Employee employeeObj = new Employee();  
    protected TransactionTableModel transModelObj;  // For table model
    // Default constructor
    Transaction() {
        //Poupulating vector for table model
        for (int i=0;i<40;i++) {
            transItemVector.add(i, new TransactionItem());
        }
        // Default settings
        transModelObj = new TransactionTableModel(transItemVector); 
        transType = "Sales";
        merchAmount = 0;
        taxRate = 0;
        taxAmount = 0;
        totUnits = 0;
        totAmount = 0;
        paymentType = "Cash";
        storeCreditPayAmt = 0;
        cashPayAmt = 0;
    }
    //  Constructor with parameters
    //  This populates employee and store objects 
    Transaction(Employee value) {
        
        Connection con = null;
        ResultSet rs = null;
        con = ConnectManager.getConnection();
       
        
        // Getting transaction numnber from transaction table
        String sqlStr1 = "select trans_num from transaction_numbers";
        String sqlStr2 = "update transaction_numbers set trans_num = ? ";
             
        
        try {
            Statement stmt1 = con.createStatement();
            rs = stmt1.executeQuery(sqlStr1);
            while(rs.next()) {
               transNum = rs.getInt(1);
            }
            // Incrementing transaction number
            transNum = transNum + 1;
            rs.close();
            stmt1.close();
            // Updating incremented transaction number on table
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
        // Creating table model
        transModelObj = new TransactionTableModel(transItemVector);
        transType = "Sales";
        merchAmount = 0;
        taxRate = storeObj.getTaxRate();
        taxAmount = 0;
        totUnits = 0;
        totAmount = 0;
        paymentType = "Cash";
    }
    // Setters
    public void setTransNum(int value){
        transNum = value;
    }
    
    public void setTransType(String value) {
        transType = value;
    }
    public void setTransItemVector(Vector vec){
        transItemVector = vec;
    }
    public void setInvVector(Vector vec) {
        invVector = vec;
    }
    public void setStoreObj(Store str) {
        storeObj = str;
    }
    public void setTransactionTableModel (TransactionTableModel model) {
        transModelObj = model;
    }
    public void setEmployeeObj(Employee emp) {
        employeeObj = emp;
    }
    public void setMerchAmount(double value){
        merchAmount = value;
    }
    public void setPaymentType(String value) {
        paymentType = value;
    }
    public void setSalesReceiptForRet(String value) {
        salesReceiptForRet = value;
    }
    public void setStoreCreditNumber(String value) {
        storeCreditNumber = value;
    }
    public void setStoreCreditPayAmt(double value) {
        storeCreditPayAmt = value;
    }
    public void setCashPayAmt(double value) {
        cashPayAmt = value;
    }
    
    
    // Getters
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
    public String getPaymentType() {
        return paymentType;
    }
    public String getSalesReceiptForRet() {
        return salesReceiptForRet;
    }
    public String getStoreCreditNumber() {
        return storeCreditNumber;
    }
    public double getStoreCreditPayAmt() {
        return storeCreditPayAmt;
    }
    public double getCashPayAmt() {
        return cashPayAmt;
    }
    public Vector getTransItemVector(){
        return transItemVector;
    }
    public Vector getInvVector() {
        return invVector;
    }
    public Store getStoreObj() {
        return storeObj;
    }
    public TransactionTableModel getTransModelObj () {
        return transModelObj;
    }
    public Employee getEmployeeObj() {
        return employeeObj;
    }
    
    // Business Logic
    
    //  UPC change in transaction item table
    public String upcChanged(int rw, int cl, String upcCode) {
        String msgStr = "";
        InventoryItem invItem = new InventoryItem(storeObj.getStoreNum(), upcCode);
        // Check if entered UPC code is in the inventory
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
    // When units change in transaction item table
    public String unitsChanged(int rw, int cl, int units) 
    {
        String msgStr = "";
        
        // Processing sales units change
        
        if (transType == "Sales") 
        {
            if (invVector.size() - 1 >= rw )   
            {
                if (invVector.elementAt(rw).getStyle() == "") 
                {
                    msgStr = "Error 2.2 No item entered ";
                    transItemVector.elementAt(rw).setUnits(0);
           
                }
                else 
                { 
                    if (units > invVector.elementAt(rw).getUnits()) {
                        msgStr = "Error 2.3  Not enough inventory:  Inv = " + invVector.elementAt(rw).getUnits();
                        transItemVector.elementAt(rw).setUnits(0);
                    }
                    else 
                    {
                        double price = transItemVector.elementAt(rw).getPrice();
                        invVector.elementAt(rw).addReturnsUnits(units);
                        transItemVector.elementAt(rw).setAmount(units * price);
                        merchAmount = 0;
                        taxAmount = 0;
                        totAmount = 0;
                        totUnits = 0;
                        cashPayAmt = 0;
                        for (int i=0;i< transItemVector.size();i++) 
                        {
                            double lineAmt = transItemVector.elementAt(i).getAmount();
                            if (lineAmt != 0)  
                            {
                                merchAmount += lineAmt;
                                totUnits += transItemVector.elementAt(i).getUnits();
                            }
                        }
                        taxAmount = ( merchAmount * taxRate ) / 100;
                        totAmount = merchAmount + taxAmount;
                        cashPayAmt = totAmount - storeCreditPayAmt;
                      
                    }
                }
            }
            else
            {
                msgStr = "Error 2.2 No item entered ";
                transItemVector.elementAt(rw).setUnits(0);
            }                           
        }
        // Processing returns units change
        else            
        {
                
            System.out.println(paymentType);
            
            if (paymentType == "Store Credit") {
                // Returns without original sales receipt
                if (invVector.size() - 1 >= rw ) {    
                    if (invVector.elementAt(rw).getStyle() == "") 
                    {
                        msgStr = "Error 2.2 No item entered ";
                        transItemVector.elementAt(rw).setUnits(0);
                    
                    }
                    else 
                    { 
                      
                        double price = transItemVector.elementAt(rw).getPrice();
                        transItemVector.elementAt(rw).setAmount(units * price * -1);   
                        merchAmount = 0;
                        taxAmount = 0;
                        totAmount = 0;
                        totUnits = 0;
                        cashPayAmt = 0;
                        for (int i=0;i< transItemVector.size();i++) {
                             double lineAmt = transItemVector.elementAt(i).getAmount();
                            if (lineAmt != 0)  {
                                merchAmount += lineAmt;
                                totUnits += transItemVector.elementAt(i).getUnits();
                             }
                        }
                        taxAmount = ( merchAmount * taxRate ) / 100;
                        totAmount = merchAmount + taxAmount;
                        storeCreditPayAmt = totAmount;
                        cashPayAmt =0;
                      
                    }
                }
                else
                {
                    msgStr = "Error 2.2 No item entered ";
                    transItemVector.elementAt(rw).setUnits(0);

                }
            } else
            {   
                // Returns with original sales receipt presented
                System.out.println("Cash");
                double price = transItemVector.elementAt(rw).getPrice();
                // invVector.elementAt(rw).addReturnsUnits(units);
                transItemVector.elementAt(rw).setAmount(units * price * -1);
                merchAmount = 0;
                taxAmount = 0;
                totAmount = 0;
                totUnits = 0;
                cashPayAmt = 0;
                for (int i=0;i< transItemVector.size();i++) {
                    double lineAmt = transItemVector.elementAt(i).getAmount();
                    if (lineAmt != 0)  {
                        merchAmount += lineAmt;
                        totUnits += transItemVector.elementAt(i).getUnits();
                    }
                }
                taxAmount = ( merchAmount * taxRate ) / 100;
                totAmount = merchAmount + taxAmount;
                storeCreditPayAmt = 0;
                cashPayAmt = totAmount;
            }    
                
        }        
                
        return msgStr;   
        
    }
    
    public void transTypeSelected(String value) {
        transType = value;     
    }
    public String salesReceiptForReturnEntered(String value) {
        if (transType == "Returns") 
        {
        salesReceiptForRet = value;
        setReturnsFromPreviousSales(Integer.valueOf(salesReceiptForRet));
        }
        return "";
    }
    
    
    
    // Completing transaction
    public String transactionCompleted() {
        Connection con = null;
        ResultSet rs = null;
        con = ConnectManager.getConnection();
        String dateStr = "";
        String sqlStr = "";
        DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
        
        // Insert transaction details
        
        
        try {
            sqlStr = "insert into transaction_detail values " +
                     "(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
             
            PreparedStatement stmt = con.prepareStatement(sqlStr);
            for (int i=0;i < transItemVector.size(); i++) {
                if (transItemVector.elementAt(i).getUnits() > 0 ) {
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
                    df = new SimpleDateFormat("yyyyMMdd");
                    dateStr = "19010101";
                    if (transItemVector.elementAt(i).getReturnDate() != null)
                    dateStr = df.format(transItemVector.elementAt(i).getReturnDate());
                    stmt.setString(15,dateStr);
                    stmt.setInt(16, transItemVector.elementAt(i).getReturnTransNum());
                    df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    dateStr = df.format(new Date());
                    stmt.setString(17,dateStr);
                    stmt.setString(18, employeeObj.getEmployeeID());
                    stmt.setString(19,dateStr);
                    stmt.setString(20, employeeObj.getEmployeeID());    
                    stmt.executeUpdate();
                }
            }
            stmt.close();
            
            // Store credit 
            if (transType == "Sales" && paymentType == "Store Credit") {
                double stCrdtAmt = 0;
                sqlStr = "select * from store_credit where trans_num = " +
                         storeCreditNumber;
                Statement stmt1 = con.createStatement();
                rs = stmt1.executeQuery(sqlStr);
                while(rs.next()) {
                    stCrdtAmt = rs.getDouble(3);
                }
                rs.close();
                stmt1.close();
                stCrdtAmt = stCrdtAmt + storeCreditPayAmt;
                sqlStr = "update store_credit set store_credit_applied_amt = ? " +
                         "where trans_num = ? ";
                stmt = con.prepareStatement(sqlStr);
                stmt.setDouble(1, stCrdtAmt);
                stmt.setInt(2, Integer.valueOf(storeCreditNumber));
                stmt.executeUpdate();
                
            }
            //  Insert into store credit
            if (transType == "Returns" && paymentType == "Store Credit") {
               sqlStr = "insert into store_credit values (?,?,?,?,?,?,?) ";
               stmt = con.prepareStatement(sqlStr);       
               stmt.setInt(1, transNum);
               stmt.setDouble(2, totAmount * -1);
               stmt.setDouble(3, 0);
               df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
               dateStr = df.format(new Date());
               stmt.setString(4,dateStr);
               stmt.setString(5, employeeObj.getEmployeeID());
               stmt.setString(6,dateStr);
               stmt.setString(7, employeeObj.getEmployeeID());    
               stmt.executeUpdate();
            }
            stmt.close();
            // Cash payment update
            if (transType == "Returns" && paymentType == "Cash") {
               sqlStr = "update transaction_detail set returned = ?, " +
                        "return_units = ?, return_date = ?, return_trans_num = ? " +
                        "where trans_num = ? and trans_seq = ? ";
               stmt = con.prepareStatement(sqlStr);
               for (int i = 0; i < transItemVector.size(); i++) {
                   if (transItemVector.elementAt(i).getUnits() > 0 ) {
                   stmt.setBoolean(1, transItemVector.elementAt(i).getReturned());
                   stmt.setInt(2, transItemVector.elementAt(i).getUnits());
                   dateStr = df.format(new Date());
                   stmt.setString(3,dateStr);
                   stmt.setInt(4, transNum);
                   stmt.setInt(5, transItemVector.elementAt(i).getOrigTransNum());
                   stmt.setInt(6, transItemVector.elementAt(i).getOrigTransSeq());
                   stmt.executeUpdate();
                   }
               }
               stmt.close();
            }
            
            // Insert into transaction header
            sqlStr = "insert into transaction_header values " +
                     "(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
             
            stmt = con.prepareStatement(sqlStr);
            stmt.setInt(1,transNum);
            stmt.setString(2,transType);
            stmt.setInt(3, storeObj.getStoreNum());
            if (salesReceiptForRet != "" && salesReceiptForRet != null) 
            stmt.setInt(4, Integer.valueOf(salesReceiptForRet));
            else stmt.setInt(4, 0);
            df = new SimpleDateFormat("yyyyMMdd");
            dateStr = df.format(new Date());
            stmt.setString(5, dateStr); 
            stmt.setDouble(6, merchAmount);
            stmt.setDouble(7, taxRate);
            stmt.setDouble(8, taxAmount);
            stmt.setDouble(9, totAmount);
            stmt.setString(10, paymentType);
            stmt.setDouble(11, storeCreditPayAmt);
            stmt.setDouble(12, cashPayAmt);
            df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            dateStr = df.format(new Date());
            stmt.setString(13,dateStr);
            
            stmt.setString(14, employeeObj.getEmployeeID());
            stmt.setString(15,dateStr);
            stmt.setString(16, employeeObj.getEmployeeID());    
            stmt.executeUpdate();
            stmt.close();
   
            con.close();
        } catch (SQLException ex) {System.out.println(ex);
        }
        return "Completed";
    }
    
    // Getting new transction number from transaction_number table
    public void getNewTransNum() {
        Connection con = null;
        ResultSet rs = null;
        con = ConnectManager.getConnection();
    
        String sqlStr1 = "select trans_num from transaction_numbers";
        String sqlStr2 = "update transaction_numbers set trans_num = ? ";
             
        // Incrementing transaction number on transaction_number table
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
        
      
        transType = "Sales";
        merchAmount = 0;
        taxRate = storeObj.getTaxRate();
        taxAmount = 0;
        totUnits = 0;
        totAmount = 0;
        storeCreditPayAmt = 0;
        cashPayAmt = 0;
        paymentType = "Cash";
        
    }
    // Clear transaction and ready for a new entry
    public void clearTransaction() {
        transItemVector.clear();
        for (int i=0;i<40;i++) {
            transItemVector.add(i, new TransactionItem());
        }
        transModelObj = new TransactionTableModel(transItemVector);
        merchAmount = 0;
        taxAmount = 0;
        totUnits = 0;
        totAmount = 0;
        storeCreditPayAmt = 0;
        cashPayAmt = 0;
        transModelObj = new TransactionTableModel(transItemVector);
    }
    // When original sales receipt presented for returns,
    // load transaction item detail with original sales items
    // with unreturned units
    public void setReturnsFromPreviousSales(int trans) {
        Connection con = null;
        Statement stmt = null;
        ResultSet rs = null;
        con = ConnectManager.getConnection();
        String trans_str = String.valueOf(trans);
     
        // Getting unreturned units from the database
        String sqlStr = "select * from transaction_detail where trans_num = " +
                     trans_str + " and return_units < units ";
        transItemVector.clear();
        TransactionItem transItemObj;
        try {
            stmt = con.createStatement(); 
            rs = stmt.executeQuery(sqlStr);
            int i = -1;

            while(rs.next()) {
                paymentType = "Cash";
                i++;
                transItemObj = new TransactionItem();
                transItemObj.setUPC(rs.getString(3));
                transItemObj.setStyle(rs.getString(4));
                transItemObj.setStyleName(rs.getString(5));
                transItemObj.setColor(rs.getString(6));
                transItemObj.setColorName(rs.getString(7));
                transItemObj.setSize(rs.getString(8));
                transItemObj.setGender(rs.getString(9));
                transItemObj.setPrice(rs.getDouble(10)); 
                int unts = rs.getInt(11) - rs.getInt(14);
                transItemObj.setUnits(unts);
                double amt = unts * rs.getDouble(10) * -1 ;
                transItemObj.setAmount(amt);
                transItemObj.setOrigTransNum(rs.getInt(1));
                transItemObj.setOrigTransSeq(rs.getInt(2));
                transItemObj.setOrigUnits(unts);
            
                transItemVector.add(i, transItemObj);
                
                System.out.println(i);
                
                
            }
            con.close();
        } catch (SQLException ex) {System.out.println(ex);
        }
        transModelObj = new TransactionTableModel(transItemVector);
        transModelObj.setColumnAccess(0, false);
        merchAmount = 0;
        taxAmount = 0;
        totAmount = 0;
        totUnits = 0;
        cashPayAmt = 0;
        // Populate inv transaction item vector
        for (int i=0;i< transItemVector.size();i++) {
            double lineAmt = transItemVector.elementAt(i).getAmount();
            if (lineAmt != 0)  {
               merchAmount += lineAmt;
               totUnits += transItemVector.elementAt(i).getUnits();
               }
        }
        // Setting amount fields
        taxAmount = ( merchAmount * taxRate ) / 100;
        totAmount = merchAmount + taxAmount;
        cashPayAmt = totAmount;
        storeCreditPayAmt = 0;
        
    }
   
}