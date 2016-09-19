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
public class Employee {
    
    protected int storeNum;
    protected int employeeID;
    protected String firstName;
    protected String lastName;
    protected String designation;
    
    Employee(){
        storeNum = -1;
        employeeID = -1;
        firstName = null;
        lastName = null;
        designation = null;
    }
    
    public void setStoreNum(int value){}
    public void setEmployeeID(int value){}
    public void setFirstName(String value){}
    public void setLastName(String value){}
    public void setDesignation(String value){}
    public int getStoreNum(){
        return storeNum;
    }
    public int getEmployeeID(){
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

}
