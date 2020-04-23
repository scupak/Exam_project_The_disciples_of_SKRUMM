/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package examProjectTheDisciplesOfSkrumm.GUI.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Christina
 */
public class CreateUserViewController implements Initializable
{

    @FXML
    private Button createUserBtn;
    @FXML
    private Button cancelBtn;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        // TODO
    }    

    @FXML
    private void createUser(ActionEvent event)
    {
         Stage createUserView = (Stage) ((Node) event.getSource()).getScene().getWindow();
         createUserView.close();
    }

    @FXML
    private void cancel(ActionEvent event)
    {
        Stage createUserView = (Stage) ((Node) event.getSource()).getScene().getWindow();
         createUserView.close();
    }
    
}
