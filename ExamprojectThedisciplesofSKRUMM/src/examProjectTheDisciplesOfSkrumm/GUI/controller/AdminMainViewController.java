/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package examProjectTheDisciplesOfSkrumm.GUI.controller;

import com.jfoenix.controls.JFXButton;
import examProjectTheDisciplesOfSkrumm.GUI.Model.Interface.ModelFacadeInterface;
import examProjectTheDisciplesOfSkrumm.GUI.Model.ModelFacade;
import examProjectTheDisciplesOfSkrumm.enums.UserMode;
import examProjectTheDisciplesOfSkrumm.enums.ViewTypes;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javax.swing.JOptionPane;

/**
 * FXML Controller class
 *
 * @author lumby
 */
public class AdminMainViewController implements Initializable
{

    @FXML
    private JFXButton clientsAndProjectsbtn;
    @FXML
    private JFXButton tasksBtn;
    @FXML
    private JFXButton usersbtn;
    @FXML
    private JFXButton backBtn;
    
    private ModelFacadeInterface modelfacade;
    @FXML
    private Label titleLabel;
    @FXML
    private JFXButton logButton;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        try
        {
            modelfacade = ModelFacade.getInstance();
        } catch (Exception ex)
        {
            Logger.getLogger(AdminMainViewController.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Couln't get the instance of modelfacade" + ex,"ERROR!", JOptionPane.ERROR_MESSAGE);
        }
        
        titleLabel.setText("Welcome " + modelfacade.getCurrentuser().getFirstName());
        titleLabel.setMaxWidth(Double.MAX_VALUE);
        titleLabel.setAlignment(Pos.CENTER);
        
        
    }    

    @FXML
    private void handleClientsAndProjects(ActionEvent event)
    {
        try
        {
            Stage adminmainView = (Stage) ((Node) event.getSource()).getScene().getWindow();
            
            FXMLLoader loader = modelfacade.getLoader(ViewTypes.ADMINCLIENTSANDPROJECTS);
            Parent root = loader.load();
            AdminClientsAndProjectsController controller = loader.getController();
            
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setMinHeight(523);
            stage.setMinWidth(721);
            stage.setTitle("Clients & Projects");
            stage.show();
            adminmainView.close();
        }
        catch (IOException ex)
        {
            Logger.getLogger(AdminMainViewController.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Couln't load in the clients and projects view" + ex,"ERROR!", JOptionPane.ERROR_MESSAGE);
        }
        catch (Exception ex)
        {
            Logger.getLogger(AdminMainViewController.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Couln't load in the clients and projects view" + ex,"ERROR!", JOptionPane.ERROR_MESSAGE);
        }

        
    }

    @FXML
    private void handleTaks(ActionEvent event)
    {
        try
        {
            Stage adminmainView = (Stage) ((Node) event.getSource()).getScene().getWindow();
            
            FXMLLoader loader = modelfacade.getLoader(ViewTypes.ADMINTASKS);
            Parent root = loader.load();
            AdminTasksViewController controller = loader.getController();
            
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setMinHeight(523);
            stage.setMinWidth(721);
            stage.setTitle("Tasks");
            stage.show();
            adminmainView.close();
        } 
        catch (IOException ex)
        {
            Logger.getLogger(AdminMainViewController.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Couln't load in the admin task view" + ex,"ERROR!", JOptionPane.ERROR_MESSAGE);
        }
        catch (Exception ex)
        {
            Logger.getLogger(AdminMainViewController.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Couln't load in the admin task view" + ex,"ERROR!", JOptionPane.ERROR_MESSAGE);
        }
    }

    @FXML
    private void handleUsers(ActionEvent event)
    {
        try
        {
            Stage adminmainView = (Stage) ((Node) event.getSource()).getScene().getWindow();
            
            FXMLLoader loader = modelfacade.getLoader(ViewTypes.ADMINUSER);
            Parent root = loader.load();
            AdminUserViewController controller = loader.getController();
            
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setMinHeight(523);
            stage.setMinWidth(721);
            stage.setTitle("Users");
            stage.show();
            adminmainView.close();
        } 
        catch (IOException ex)
        {
            Logger.getLogger(AdminMainViewController.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Couln't load in the admin user view" + ex,"ERROR!", JOptionPane.ERROR_MESSAGE);
        } 
        catch (Exception ex)
        {
            Logger.getLogger(AdminMainViewController.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Couln't load in the admin user view" + ex,"ERROR!", JOptionPane.ERROR_MESSAGE);
        }
    }

    @FXML
    private void handleBackToMain(ActionEvent event)
    {
        try
        {
            modelfacade.setCurrentUserMode(UserMode.STANDARD);
            
            Stage adminmainView = (Stage) ((Node) event.getSource()).getScene().getWindow();
            
            FXMLLoader loader = modelfacade.getLoader(ViewTypes.MAIN);
            Parent root = loader.load();
            MainViewController controller = loader.getController();
            
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setMinHeight(523);
            stage.setMinWidth(721);
            stage.setTitle("Main Menu");
            stage.show();
            adminmainView.close();
        } 
        catch (IOException ex)
        {
            Logger.getLogger(AdminMainViewController.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Couln't load in the main view" + ex,"ERROR!", JOptionPane.ERROR_MESSAGE);
        }
        catch (Exception ex)
        {
            Logger.getLogger(AdminMainViewController.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Couln't load in the main view" + ex,"ERROR!", JOptionPane.ERROR_MESSAGE);
        }
    }

    @FXML
    private void handleLog(ActionEvent event)
    {
        
        try
        {
            // Stage adminmainView = (Stage) ((Node) event.getSource()).getScene().getWindow();
            
            FXMLLoader loader = modelfacade.getLoader(ViewTypes.LOGWINDOW);
            Parent root = loader.load();
            LogWindowController controller = loader.getController();
            
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setMinHeight(200);
            stage.setMinWidth(400);
            
            stage.setTitle("Log");
            stage.show();
            //adminmainView.close();
        } 
        catch (IOException ex)
        {
            Logger.getLogger(AdminMainViewController.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Couln't load in the log view" + ex,"ERROR!", JOptionPane.ERROR_MESSAGE);
        } 
        catch (Exception ex)
        {
            Logger.getLogger(AdminMainViewController.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Couln't load in the log view" + ex,"ERROR!", JOptionPane.ERROR_MESSAGE);
        }
    }
    
}
