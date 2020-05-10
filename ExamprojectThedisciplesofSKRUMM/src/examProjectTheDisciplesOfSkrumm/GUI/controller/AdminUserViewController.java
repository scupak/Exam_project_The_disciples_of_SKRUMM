/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package examProjectTheDisciplesOfSkrumm.GUI.controller;

import com.jfoenix.controls.JFXButton;
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
 * @author lumby
 */
public class AdminUserViewController implements Initializable
{

    @FXML
    private JFXButton createUserBtn;
    @FXML
    private JFXButton editBtn;
    @FXML
    private JFXButton deleteUser;
    @FXML
    private JFXButton adminBtn;
    @FXML
    private JFXButton backBtn;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        // TODO
    }    

    @FXML
    private void handleCreateUser(ActionEvent event)
    {
    }

    @FXML
    private void handleEditUser(ActionEvent event)
    {
    }

    @FXML
    private void handleDeleteUser(ActionEvent event)
    {
    }

    @FXML
    private void handleMakeAdmin(ActionEvent event)
    {
    }

    @FXML
    private void handleBack(ActionEvent event) throws IOException
    {
        Stage adminClientsAndProjectsView = (Stage) ((Node) event.getSource()).getScene().getWindow();

        FXMLLoader loader = new FXMLLoader(getClass().
                getResource("/examProjectTheDisciplesOfSkrumm/GUI/view/AdminMainView.fxml"));
        Parent root = loader.load();
        AdminMainViewController controller = loader.getController();

        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.setMinHeight(523);
        stage.setMinWidth(721);
        stage.setTitle("Main Menu");
        stage.show();
        adminClientsAndProjectsView.close();
    }
    
}
