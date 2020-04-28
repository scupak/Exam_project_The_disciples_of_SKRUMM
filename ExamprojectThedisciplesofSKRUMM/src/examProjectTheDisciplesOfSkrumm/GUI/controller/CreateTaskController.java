/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package examProjectTheDisciplesOfSkrumm.GUI.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import examProjectTheDisciplesOfSkrumm.BE.Client;
import examProjectTheDisciplesOfSkrumm.BE.Project;
import examProjectTheDisciplesOfSkrumm.BE.Task;
import examProjectTheDisciplesOfSkrumm.GUI.Model.Interface.ModelFacadeInterface;
import examProjectTheDisciplesOfSkrumm.GUI.Model.ModelFacade;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TreeTableView;
import javafx.stage.Stage;
import javafx.util.converter.LocalDateTimeStringConverter;
import javafx.util.converter.LocalTimeStringConverter;

/**
 *
 * @author anton
 */
public class CreateTaskController implements Initializable 
{
    
    ModelFacadeInterface modelfacade;
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
    
    private  TaskViewController taskViewController; 
    
    
    
   
    
    @Override
    public void initialize(URL url, ResourceBundle rb) 
    {
        try {
            modelfacade = ModelFacade.getInstance();
        } catch (Exception ex) {
            Logger.getLogger(CreateTaskController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        projectCombobox.getItems().addAll(modelfacade.getProjects());
      
    }    

    @FXML
    private void createTask(ActionEvent event) throws IOException
    {
        String title = titleTextField.getText();
        Project project = projectCombobox.getValue();
        int duration = Integer.parseInt(timeTextField.getText());
        int isPaid = 1;
        LocalDateTime lastUsed = LocalDateTime.now();
        LocalDate creationDate = LocalDate.now();
        LocalTime startTime = LocalTime.MIN;
        LocalTime stopTime = LocalTime.MIN;
        ArrayList<Task> intervals = new ArrayList<Task>(); 
       
        
        Task newtask = new Task(title, project, duration, isPaid, lastUsed, creationDate, startTime, stopTime, intervals);
        modelfacade.createTask(newtask);
        taskViewController.RefreshTreeView();
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

    void settaskViewController( TaskViewController taskViewController) {
     this.taskViewController = taskViewController;
     
        //System.out.println(taskViewController);
    }

    
    
    
    
}
