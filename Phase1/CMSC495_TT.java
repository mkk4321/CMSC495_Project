package CMSC495_TT;
/**
 *
 * @author Manoj
 */
/* Tenacious Turtles Team 
   Apparel Point of Sale (APOS) System

   This is the main class for the applicaiton.
*/
import cmsc495_tt_phase2.*;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;


import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javax.swing.JFrame;

public class CMSC495_TT extends Application {
    
    @Override
    public void start(Stage primaryStage) {

    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
            // Opening log-in form
            LogInForm logIn = new LogInForm();
            logIn.setVisible(true); 
            launch(args);

    }
    
}
