/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cmsc495_tt;
import java.util.Vector;
import javax.swing.table.AbstractTableModel;
import javax.swing.*;
/**
 *
 * @author vance.molhusen
 */
public class TransactionTableModel extends AbstractTableModel {
    
    public static final int UPC_INDEX = 0;
    public static final int STYLE_INDEX = 1;
    public static final int STYLENAME_INDEX = 2;
    public static final int COLOR_INDEX = 3;
    public static final int COLORNAME_INDEX = 4;
    public static final int SIZE_INDEX = 5;
    public static final int GENDER_INDEX = 6;
    public static final int PRICE_INDEX = 7;
    public static final int UNITS_INDEX = 8;
    public static final int AMOUNT_INDEX = 9;
    protected boolean[] canEdit = new boolean [] {
                true, false, false, false, false, false, false, false, true, false
            };
    /*
    protected Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Double.class, java.lang.Integer.class, java.lang.Double.class
            };
    */
    protected Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Double.class, java.lang.Integer.class, java.lang.Double.class
            };
    protected String[] columnNames = {"UPC Code", "Style", "Style Name", "Color", "Color Name", "Size", "Gender", "               Price", "            Units", "            Amount"};
    protected Vector transactionData;
    
    public TransactionTableModel(Vector inputVector){
        this.transactionData = inputVector;
    }
   

    @Override
    public int getRowCount() {
        return transactionData.size();
    }

    public String getColumnName(int index){
        return columnNames[index];
    }
    
    @Override
    public int getColumnCount() {
        return columnNames.length;
    }
    @Override
    public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
    }
    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
    
    
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        TransactionItem transItem = (TransactionItem) transactionData.get(rowIndex);
        switch(columnIndex){
            case UPC_INDEX:
                return transItem.getUPC();
            case STYLE_INDEX:    
                return transItem.getStyle();
            case STYLENAME_INDEX:    
                return transItem.getStyleName();
            case COLOR_INDEX:    
                return transItem.getColor();
            case COLORNAME_INDEX:    
                return transItem.getColorName();
            case SIZE_INDEX:    
                return transItem.getSize();
            case GENDER_INDEX:    
                return transItem.getGender();
            case PRICE_INDEX:    
                return transItem.getPrice();
            case UNITS_INDEX:    
                return transItem.getUnits();
            case AMOUNT_INDEX:    
                return transItem.getAmount();
            default:
                return new Object();
        }
    }
    
    public void setColumnAccess(int column, boolean access) {
        this.canEdit[column] = access;
    }
    @Override
    public void setValueAt(Object value, int rowIndex, int columnIndex){
        TransactionItem transItem = (TransactionItem) transactionData.get(rowIndex);
        switch(columnIndex){
            case UPC_INDEX:
                transItem.setUPC((String) value);
                break;
            case STYLE_INDEX:    
                transItem.setStyle((String) value);
                break;
            case STYLENAME_INDEX:    
                transItem.setStyleName((String) value);
                break;
            case COLOR_INDEX:    
                transItem.setColor((String) value);
                break;
            case COLORNAME_INDEX:    
                transItem.setColorName((String) value);
                break;
            case SIZE_INDEX:    
                transItem.setSize((String) value);
                break;
            case GENDER_INDEX:    
                transItem.setGender((String) value);
                break;
            case PRICE_INDEX:   
                transItem.setPrice((double) value);
            case UNITS_INDEX:    
                transItem.setUnits((int) value);
                break;
            case AMOUNT_INDEX: 
                transItem.setAmount((double) value);
                break;
            default:
                System.out.println("TransactionTableModel.setValueAt: Invalid index.");
         
        }
                 
    }
}
