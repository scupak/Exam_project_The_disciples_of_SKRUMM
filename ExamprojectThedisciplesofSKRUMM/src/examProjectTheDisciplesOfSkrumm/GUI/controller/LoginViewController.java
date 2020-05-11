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
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.input.KeyEvent;
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
    
    private ModelFacadeInterface model;
    
   
    
   
    
    public LoginViewController() throws Exception
    {
        model = ModelFacade.getInstance();
    }
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }    
    
    @FXML
    private void handelLogIn(ActionEvent event) throws IOException, SQLException
    {
        String email = enterEmailTextField.getText().trim();
        String password = model.hashPassword(enterPasswordTextField.getText());
        
        User user = new User(email, "hello", "friend", password, false);
        
        if(model.checkUser(user) == true)
        {
         model.setCurrentuser(model.getUser(user));
        Stage mainView = (Stage) ((Node) event.getSource()).getScene().getWindow();
        
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/examProjectTheDisciplesOfSkrumm/GUI/view/MainView.fxml"));
        Parent root = loader.load();
        MainViewController controller = loader.getController();
        
        if(user.getIsAdmin() == true)
        {
            controller.setAdminCheck(true);
            
        }
        
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.setMinHeight(525);
        stage.setMinWidth(726);
        stage.setTitle("Main Menu");
        stage.show();
        mainView.close();
        }else
        {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Error");
            alert.setHeaderText("your email or password may be incorect");
            alert.setContentText("Please try again");
            alert.showAndWait();
            
        }
    }
    
    

    
    
   

    

}
