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
public class InventoryItem {
    
   protected TransactionItem transItem;
    
   InventoryItem(){
       transItem = new TransactionItem();
   }
   
   public void getInventory(){}
   
   public void updateInventory(){}
   
}
