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

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        // TODO
    }    

    @FXML
    private void handleClientsAndProjects(ActionEvent event) throws IOException
    {
        Stage adminmainView = (Stage) ((Node) event.getSource()).getScene().getWindow();

        FXMLLoader loader = new FXMLLoader(getClass().
                getResource("/examProjectTheDisciplesOfSkrumm/GUI/view/AdminClientsAndProjects.fxml"));
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

    @FXML
    private void handleTaks(ActionEvent event) throws IOException
    {
        Stage adminmainView = (Stage) ((Node) event.getSource()).getScene().getWindow();

        FXMLLoader loader = new FXMLLoader(getClass().
                getResource("/examProjectTheDisciplesOfSkrumm/GUI/view/AdminTasksView.fxml"));
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

    @FXML
    private void handleUsers(ActionEvent event) throws IOException
    {
        Stage adminmainView = (Stage) ((Node) event.getSource()).getScene().getWindow();

        FXMLLoader loader = new FXMLLoader(getClass().
                getResource("/examProjectTheDisciplesOfSkrumm/GUI/view/AdminUserView.fxml"));
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

    @FXML
    private void handleBackToMain(ActionEvent event) throws IOException
    {
        Stage adminmainView = (Stage) ((Node) event.getSource()).getScene().getWindow();

        FXMLLoader loader = new FXMLLoader(getClass().
                getResource("/examProjectTheDisciplesOfSkrumm/GUI/view/MainView.fxml"));
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
    
}
