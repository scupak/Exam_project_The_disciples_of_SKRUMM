/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package examProjectTheDisciplesOfSkrumm.GUI.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import examProjectTheDisciplesOfSkrumm.BE.Project;
import examProjectTheDisciplesOfSkrumm.BE.Task;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author anton
 */
public class CreateTaskController implements Initializable {

    @FXML
    private JFXButton createTaskBtn;
    @FXML
    private JFXButton cancelBtn;
    @FXML
    private JFXTextField titleTextField;
    @FXML
    private JFXTextField timeTextField;
    @FXML
    private JFXButton createNewProjectButton;
    @FXML
    private JFXComboBox<Project> projectCombobox;
    
    
   
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
      
    }    

    @FXML
    private void createTask(ActionEvent event) throws IOException
    {
        Stage createTaskView = (Stage) ((Node) event.getSource()).getScene().getWindow();
        createTaskView.close();
    }

    @FXML
    private void cancel(ActionEvent event)
    {
        Stage createTaskView = (Stage) ((Node) event.getSource()).getScene().getWindow();
        createTaskView.close();
    }

    @FXML
    private void handleCreateNewProject(ActionEvent event) throws IOException 
    {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/examProjectTheDisciplesOfSkrumm/GUI/view/AddProjectView.fxml"));
        Parent root = loader.load();
        AddProjectViewController controller = loader.getController();
        
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.setTitle("TimeTracker");
        stage.show();
    }

    
    
    
    
}
