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
import examProjectTheDisciplesOfSkrumm.BE.Interval;
import examProjectTheDisciplesOfSkrumm.BE.Project;
import examProjectTheDisciplesOfSkrumm.BE.Task;
import examProjectTheDisciplesOfSkrumm.BE.User;
import examProjectTheDisciplesOfSkrumm.GUI.Model.Interface.ModelFacadeInterface;
import examProjectTheDisciplesOfSkrumm.GUI.Model.ModelFacade;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
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
import javafx.scene.control.Alert;
import javafx.scene.control.TreeTableView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.util.converter.LocalDateTimeStringConverter;
import javafx.util.converter.LocalTimeStringConverter;

/**
 *
 * @author anton
 */
public class CreateTaskController implements Initializable 
{
    
    private ModelFacadeInterface modelfacade;
    @FXML
    private JFXButton createTaskBtn;
    @FXML
    private JFXButton cancelBtn;
    @FXML
    private JFXTextField titleTextField;
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
        if(!titleTextField.getText().isEmpty() && !(projectCombobox.getValue() == null))
        {
            try
            {
                String title = titleTextField.getText();
                Project project = projectCombobox.getValue();
                int duration = 0;
                LocalDateTime lastUsed = LocalDateTime.now();
                LocalDate creationDate = LocalDate.now();
                LocalTime startTime = LocalTime.MIN;
                LocalTime stopTime = LocalTime.MIN;
                ArrayList<Interval> intervals = new ArrayList<Interval>();
                User user = modelfacade.getCurrentuser();
       
        
                Task newtask = new Task(1, title, project, duration, lastUsed, creationDate, startTime, stopTime, user, intervals);
                
                //Create task should return the task it created
                modelfacade.createTask(newtask);
                
                Task task = modelfacade.getTask(newtask);
                Interval interval = new Interval(0, LocalTime.now(), LocalTime.now(), creationDate, 0, task, task.getIsPaid());
                modelfacade.newInterval(interval);
                
                taskViewController.refreshEverything();
                Stage createTaskView = (Stage) ((Node) event.getSource()).getScene().getWindow();
                createTaskView.close();
            }
            catch (NumberFormatException ex)
            {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Oops");
                alert.setHeaderText("Incorrect input\n" + ex);
                alert.setContentText("You wrote a letter in duration, it needs a number.");
                alert.showAndWait();
            } catch (SQLException ex) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Could connect to database\n" + ex);
                alert.setContentText("Please try again");
                alert.showAndWait(); 
            }
            
        }
        else if(!titleTextField.getText().isEmpty() && !(projectCombobox.getValue() == null))
        {
            try
            {
                String title = titleTextField.getText();
                Project project = projectCombobox.getValue();
                int duration = 0;
                LocalDateTime lastUsed = LocalDateTime.now();
                LocalDate creationDate = LocalDate.now();
                LocalTime startTime = LocalTime.MIN;
                LocalTime stopTime = LocalTime.MIN;
                ArrayList<Interval> intervals = new ArrayList<Interval>();
                User user = modelfacade.getCurrentuser();
       
        
                Task newtask = new Task(1, title, project, duration, lastUsed, creationDate, startTime, stopTime, user, intervals);
                modelfacade.createTask(newtask);
                taskViewController.refreshEverything();
                Stage createTaskView = (Stage) ((Node) event.getSource()).getScene().getWindow();
                createTaskView.close();
            }
            catch (NumberFormatException ex)
            {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Oops");
                alert.setHeaderText("Incorrect input\n" + ex);
                alert.setContentText("You wrote a letter in duration, it needs a number.");
                alert.showAndWait();
            } catch (SQLException ex) 
            {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Could connect to database\n" + ex);
                alert.setContentText("Please try again");
                alert.showAndWait(); 
            }
            
        }
        else 
        {
              Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Oops");
                alert.setHeaderText("Incorrect input");
                alert.setContentText("You forgot some input.");
                alert.showAndWait();
        }
       
    }

    @FXML
    private void cancel(ActionEvent event)
    {
        Stage createTaskView = (Stage) ((Node) event.getSource()).getScene().getWindow();
        createTaskView.close();
    }

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
