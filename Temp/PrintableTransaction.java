/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cmsc495_tt;

import java.awt.Graphics;
import java.awt.print.*;


/**
 *
 * @author vance.molhusen
 */
public class PrintableTransaction implements Printable {
    
    public PrinterJob printJob;
    Transaction trans;
   
    PrintableTransaction(Transaction trans){
        this.trans = trans;
        this.printJob = PrinterJob.getPrinterJob();
        this.printJob.setPrintable(this);
    }

    @Override
    public int print(Graphics g, PageFormat pf, int page) throws
                                                        PrinterException {

        if (page > 0) { /* We have only one page, and 'page' is zero-based */
            return NO_SUCH_PAGE;
        }

        /* User (0,0) is typically outside the imageable area, so we must
         * translate by the X and Y values in the PageFormat to avoid clipping
         */

        //Check if return transaction
        if(trans.storeCreditsIssuedFlag){
            printReturnReciept(g, pf, page);
        }
        //Check if a sales transaction
        else if(!trans.storeCreditsIssuedFlag){
            printSalesReciept(g, pf, page);
        }
        
        g.drawString("Test", 100, 100);

        /* tell the caller that this page is part of the printed document */
        return PAGE_EXISTS;
    }
    
    private int printSalesReciept(Graphics g, PageFormat pf, int page)
            throws PrinterException{
        
        if (page > 0) {
            return NO_SUCH_PAGE;
        }
        //drawString(text, x, y)
        //put increment y by 15
        g.drawString("Tenacious Turtles Clothing Co.", 100, 100);
        g.drawString("Store #: 10001", 100, 115);
        g.drawString("1234 Main Street", 100, 130);
        g.drawString("Adelphi, MD 20783", 100, 145);
        g.drawString("321-123-4567", 100, 160);
        //100, 175
        g.drawString("Transaction #: ", 100, 190);
        g.drawString("Date: ", 100, 205);
        g.drawString("Payment Type: ", 100, 220);
        g.drawString("Return Transaction #: ", 100, 235);
        //100, 250
        g.drawString("QTY   ITEM     SIZE     Price", 100, 265);
        g.drawString("*************************************", 100, 280);
        
        //transaction info
        
        //footer
        
        return PAGE_EXISTS;
    }
    
    private int printReturnReciept(Graphics g, PageFormat pf, int page)
            throws PrinterException{
        
        if (page > 0) {
            return NO_SUCH_PAGE;
        }
        //drawString(text, x, y)
        //put increment y by 15
        String testString = "it works";
        g.drawString("Tenacious Turtles Clothing Co.", 100, 100);
        g.drawString("Store #: 10001", 100, 115);
        g.drawString("1234 Main Street", 100, 130);
        g.drawString("Adelphi, MD 20783", 100, 145);
        g.drawString("321-123-4567", 100, 160);
        //100, 175
        g.drawString("RETURN RECEIPT", 100, 190);
        //100, 205 "Original Transaction #: "
        g.drawString(testString, 100, 220);
        g.drawString("Return Transaction #: ", 100, 235);
        //100, 250
        g.drawString("Return Date: ", 100, 265);
        //100, 280
        g.drawString("QTY   ITEM     SIZE     Price", 100, 295);
        g.drawString("*************************************", 100, 310);
        
        //transaction info
        
        //footer
        
        return PAGE_EXISTS;
    }

    /*public void actionPerformed(ActionEvent e) {
         PrinterJob job = PrinterJob.getPrinterJob();
         job.setPrintable(this);
         boolean ok = job.printDialog();
         if (ok) {
             try {
                  job.print();
             } catch (PrinterException ex) {
              //The job did not successfully complete
             }
         }
         
    }*/

}
