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
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Zanaxdk <https://github.com/zanaxdk>
 */
public class AdminViewController implements Initializable
{

    @FXML
    private TableView<?> UserInfoAdminPane;
    @FXML
    private JFXButton AdminAddUsrBtn;
    @FXML
    private JFXButton AdminEditUsrBtn;
    @FXML
    private JFXButton AdminDeleteUsrBtn;
    @FXML
    private JFXButton AdminMakeAdminBtn;
    @FXML
    private JFXButton AdminRemoveAdminBtn;
    @FXML
    private Label AdminLabel;
    @FXML
    private JFXButton ExportTaskTImeBtn;
    @FXML
    private TableView<?> ClientList;
    @FXML
    private JFXButton AdminAddClientBtn;
    @FXML
    private JFXButton AdminEditClientBtn;
    @FXML
    private JFXButton AdminDeleteClientBtn;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        // TODO
    }    

    @FXML
    private void addUser(ActionEvent event) throws IOException
    {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/examProjectTheDisciplesOfSkrumm/GUI/view/CreateUserView.fxml"));
        Parent root = loader.load();
        CreateUserViewController controller = loader.getController();
        
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.setTitle("TimeTracker");
        stage.show();
    }

    @FXML
    private void editUser(ActionEvent event) throws IOException
    {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/examProjectTheDisciplesOfSkrumm/GUI/view/CreateUserView.fxml"));
        Parent root = loader.load();
        CreateUserViewController controller = loader.getController();
        
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.setTitle("TimeTracker");
        stage.show();
    }

    @FXML
    private void deleteUser(ActionEvent event)
    {
    }

    @FXML
    private void adminAccess(ActionEvent event)
    {
    }

    @FXML
    private void exportTaskTime(ActionEvent event)
    {
    }

    @FXML
    private void addClient(ActionEvent event) throws IOException
    {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/examProjectTheDisciplesOfSkrumm/GUI/view/AddClient.fxml"));
        Parent root = loader.load();
        AddClientController controller = loader.getController();
        
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.setTitle("TimeTracker");
        stage.show();
    }

    @FXML
    private void editClient(ActionEvent event) throws IOException
    {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/examProjectTheDisciplesOfSkrumm/GUI/view/EditClient.fxml"));
        Parent root = loader.load();
        EditClientController controller = loader.getController();
        
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.setTitle("TimeTracker");
        stage.show();
    }

    @FXML
    private void deleteClient(ActionEvent event)
    {
    }

    
}
