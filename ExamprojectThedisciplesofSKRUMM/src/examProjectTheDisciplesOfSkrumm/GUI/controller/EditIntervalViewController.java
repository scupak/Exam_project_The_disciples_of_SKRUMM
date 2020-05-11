/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package examProjectTheDisciplesOfSkrumm.GUI.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTimePicker;
import examProjectTheDisciplesOfSkrumm.BE.Interval;
import examProjectTheDisciplesOfSkrumm.BE.Task;
import examProjectTheDisciplesOfSkrumm.GUI.Model.TaskModel;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Christina
 */
public class EditIntervalViewController implements Initializable
{

    @FXML
    private JFXRadioButton paid;
    @FXML
    private JFXRadioButton notPaid;
    @FXML
    private JFXTextField durationField;
    @FXML
    private JFXDatePicker creationDate;
    @FXML
    private JFXTimePicker startTime;
    @FXML
    private JFXTimePicker stopTime;
    @FXML
    private JFXButton saveButton;
    @FXML
    private JFXButton cancelButton;
    
    private TaskModel taskmodel;
    
    private Task currentTask;
    private Interval currentInterval;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        
    }    
    
    public void fillView(Interval interval)
    {
        currentInterval = interval;
        
        System.out.println(interval + "!!!!!!!!");
        System.out.println(currentInterval + "@@@@@@");
        
        if(interval.getIsPaid() == 0)
        {
            notPaid.setSelected(true);
        }
        else if(interval.getIsPaid() == 1)
        {
            paid.setSelected(true);
        }
        
        durationField.setText(interval.getFormatedIntervaltime());
        creationDate.setValue(interval.getCreationDate());
        startTime.setValue(interval.getStartTime());
        stopTime.setValue(interval.getStopTime());
        
        currentTask = interval.getTask();
    }

    @FXML
    private void handleSave(ActionEvent event) throws SQLException
    {
        String[] time = durationField.getText().split(":");
        int hours = Integer.parseInt(time[0])*60*60; 
        int minutes = Integer.parseInt(time[1])*60;
        int seconds = Integer.parseInt(time[2]);
        
        int intervalTime = hours + minutes + seconds;
        
        int paidOrNot = 0;
                
        if(paid.isSelected())
        {
            paidOrNot = 1;
        }
        else if(notPaid.isSelected())
        {
            paidOrNot = 0;
        }
        
        Interval newInterval = new Interval(currentInterval.getId(), startTime.getValue(), stopTime.getValue(), 
                creationDate.getValue(), intervalTime, currentTask, paidOrNot);
        
        taskmodel.updateInterval(newInterval);  
        
        Stage stage = (Stage) saveButton.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void handleCancel(ActionEvent event)
    {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }
    
}
