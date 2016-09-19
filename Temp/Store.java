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
public class Store {
    
    private int num;
    private String address1;
    private String address2;
    private String city;
    private String state;
    private String zipcode;
    
    Store(){
        num = -1;
        address1 = null;
        address2 = null;
        city = null;
        state = null;
        zipcode = null;
    }
    
    public int getStore(){
        return num;
    }
    
}
