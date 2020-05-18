/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package examProjectTheDisciplesOfSkrumm;

import examProjectTheDisciplesOfSkrumm.BE.Client;
import examProjectTheDisciplesOfSkrumm.BE.Interval;
import examProjectTheDisciplesOfSkrumm.BE.Project;
import examProjectTheDisciplesOfSkrumm.BE.Task;
import examProjectTheDisciplesOfSkrumm.BE.User;
import examProjectTheDisciplesOfSkrumm.GUI.Model.Interface.ModelFacadeInterface;
import examProjectTheDisciplesOfSkrumm.GUI.Model.ModelFacade;
import examProjectTheDisciplesOfSkrumm.enums.ViewTypes;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javax.swing.JOptionPane;

/**
 *
 * @author anton
 */
public class Exam_project_The_disciples_of_SKRUMM extends Application 
{
    ModelFacadeInterface modelfacade;
    
    @Override
    public void start(Stage stage) 
    {
        try 
        {
            modelfacade = ModelFacade.getInstance();
        } 
        catch (Exception ex) 
        {
            Logger.getLogger(Exam_project_The_disciples_of_SKRUMM.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Couldn't get instance of modelfacade" + ex,"ERROR!", JOptionPane.ERROR_MESSAGE);
        }
              
        try 
        {
            FXMLLoader loader;
            loader = modelfacade.getLoader(ViewTypes.LOGIN);
            Parent root = loader.load();
            //Parent root = FXMLLoader.load(getClass().getResource("GUI/view/TimeView.fxml"));
             Scene scene = new Scene(root);
            //stage.setMinHeight(525);
            // stage.setMinWidth(726);
            stage.setScene(scene); 
            stage.setTitle("Login");
            stage.setMinHeight(200);
            stage.setMinWidth(300);
            stage.show();
        } 
        catch (IOException ex) 
        {
            Logger.getLogger(Exam_project_The_disciples_of_SKRUMM.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Couldn't load the login view" + ex,"ERROR!", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            Logger.getLogger(Exam_project_The_disciples_of_SKRUMM.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Couldn't load the login view" + ex,"ERROR!", JOptionPane.ERROR_MESSAGE);
        } 
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
