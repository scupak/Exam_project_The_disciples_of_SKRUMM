/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package examProjectTheDisciplesOfSkrumm.GUI.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author kacpe
 */
public class LoginViewController implements Initializable {

    @FXML
    private JFXTextField enterEmailTextField;
    @FXML
    private JFXPasswordField enterPasswordTextField;
    @FXML
    private JFXButton loginButton;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void handleLogin(ActionEvent event) throws IOException
    {
        if(enterEmailTextField.getText().isEmpty())
        {
        Stage mainView = (Stage) ((Node) event.getSource()).getScene().getWindow();
        
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/examProjectTheDisciplesOfSkrumm/GUI/view/MainView.fxml"));
        Parent root = loader.load();
        MainViewController Controller = loader.getController();
        
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.setMinHeight(423);
        stage.setMinWidth(721);
        stage.setTitle("TimeTracker");
        stage.show();
        mainView.close();
        }else
        {
         Stage mainView = (Stage) ((Node) event.getSource()).getScene().getWindow();
        
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/examProjectTheDisciplesOfSkrumm/GUI/view/MainView.fxml"));
        Parent root = loader.load();
        MainViewController controller = loader.getController();
        controller.setAdminCheck(true);
        
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.setMinHeight(423);
        stage.setMinWidth(721);
        stage.setTitle("TimeTracker");
        stage.show();
        mainView.close();   
        }
    }
    
}
