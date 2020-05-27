/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package examProjectTheDisciplesOfSkrumm.GUI.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import examProjectTheDisciplesOfSkrumm.BE.User;
import examProjectTheDisciplesOfSkrumm.GUI.Model.Interface.ModelFacadeInterface;
import examProjectTheDisciplesOfSkrumm.GUI.Model.ModelFacade;
import examProjectTheDisciplesOfSkrumm.enums.UserMode;
import examProjectTheDisciplesOfSkrumm.enums.ViewTypes;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javax.swing.JOptionPane;

/**
 * FXML Controller class
 *
 * @author SKRUMM
 */
public class LoginViewController implements Initializable {

    @FXML
    private JFXTextField enterEmailTextField;
    @FXML
    private JFXPasswordField enterPasswordTextField;
    @FXML
    private JFXButton loginButton;
    
    private ModelFacadeInterface model;
    
   
    
   
    /**
     * 
     */
    public LoginViewController()
    {
        try
        {
            model = ModelFacade.getInstance();
        } 
        catch (Exception ex)
        {
            Logger.getLogger(LoginViewController.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Failed to get an intance of modelfacade" + ex,"ERROR!", JOptionPane.ERROR_MESSAGE); 
        }
    }
    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) 
    {
       
    }    
    /**
     * The event handler that tries to log the user in. 
     * @param event 
     */
    @FXML
    private void handelLogIn(ActionEvent event)
    {
        try 
        {
            if(!enterEmailTextField.getText().isEmpty() && !enterPasswordTextField.getText().isEmpty())
            {
                String email = enterEmailTextField.getText().trim();
                String password = model.hashPassword(enterPasswordTextField.getText());

                User user = new User(email, "hello", "friend", password, false);

                if(model.checkUser(user) == true)
                {
                    model.setCurrentuser(model.getUser(user));
                    model.setCurrentUserMode(UserMode.STANDARD);
                    Stage mainView = (Stage) ((Node) event.getSource()).getScene().getWindow();

                    FXMLLoader loader = model.getLoader(ViewTypes.MAIN);
                    Parent root = loader.load();
                    MainViewController controller = loader.getController();

                    Stage stage = new Stage();
                    stage.setScene(new Scene(root));
                    stage.setMinHeight(525);
                    stage.setMinWidth(726);
                    stage.setTitle("Main Menu");
                    stage.show();
                    mainView.close();
                }
                else
                {
                    JOptionPane.showMessageDialog(null, "Your email or password may be incorect","ERROR!", JOptionPane.ERROR_MESSAGE);
                }   
            }
            else
            {
                JOptionPane.showMessageDialog(null, "Missing input","ERROR!", JOptionPane.ERROR_MESSAGE);
            }  
        } 
        catch (SQLException ex)
        {
            Logger.getLogger(LoginViewController.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Failed to connect to database" + ex,"ERROR!", JOptionPane.ERROR_MESSAGE);   
        }
        catch (IOException ex)
        {
            Logger.getLogger(LoginViewController.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Wrong input" + ex,"ERROR!", JOptionPane.ERROR_MESSAGE);
        }
        catch (java.lang.NullPointerException ex)
        {
            Logger.getLogger(LoginViewController.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "This user does not exist","ERROR!", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex)
        {
            Logger.getLogger(LoginViewController.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Given wrong view type" + ex,"ERROR!", JOptionPane.ERROR_MESSAGE);
        } 
    }
    
    

    
    
   

    

}
