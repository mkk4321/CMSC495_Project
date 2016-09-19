/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cmsc495_tt;

import java.util.Date;
import java.util.Vector;

/**
 *
 * @author vance.molhusen
 */
public class Transaction {
    
    protected int transNum;
    protected int transSequence;
    protected Vector vectorTransItems;
    protected double transAmount;
    protected Date dateTime;
    protected boolean storeCreditsIssuedFlag;
    protected Date storeCreditDate;
    protected boolean storeCreditAppliedFlag;
    protected Date storeCreditApplyDate;
    protected int storeCreditApplyTransNum;
    
    public void setTransNum(int value){
        transNum = value;
    }
    public void setTransSequence(int value){
        transSequence = value;
    }
    public void setVectorTransItems(Vector vec){
        vectorTransItems = vec;
    }
    public void setTransAmount(double value){
        transAmount = value;
    }
    public void setTransDateTime(Date value){
        dateTime = value;
    }
    public void setStoreCreditsIssuedFlag(boolean value){
        storeCreditsIssuedFlag = value;
    }
    public void setStoreCreditDate(Date value){
        storeCreditDate = value;
    }
    public void setStoreCreditAppliedFlag(boolean value){
        storeCreditAppliedFlag = value;
    }
    public void setStoreCreditApplyDate(Date value){
        storeCreditApplyDate = value;
    }
    public void setStoreCreditApplyTransNum(int value){
        storeCreditApplyTransNum = value;
    }
    public int getTransNum(){
        return transNum;
    }
    public int getTransSequence(){
        return transSequence;
    }
    public Vector getVectorTransItems(){
        return vectorTransItems;
    }
    public double getTransAmount(){
        return transAmount;
    }
    public Date getTransDateTime(){
        return dateTime;
    }
    public boolean getStoreCreditsIssuedFlag(){
        return storeCreditsIssuedFlag;
    }
    public Date getStoreCreditDate(){
        return storeCreditDate;
    }
    public boolean getStoreCreditAppliedFlag(){
        return storeCreditAppliedFlag;
    }
    public Date getStoreCreditApplyDate(){
        return storeCreditApplyDate;
    }
    public int getStoreCreditApplyTransNum(){
        return storeCreditApplyTransNum;
    }
}
