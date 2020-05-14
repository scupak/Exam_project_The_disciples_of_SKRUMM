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
import com.microsoft.sqlserver.jdbc.SQLServerException;
import examProjectTheDisciplesOfSkrumm.BE.Interval;
import examProjectTheDisciplesOfSkrumm.BE.Task;
import examProjectTheDisciplesOfSkrumm.GUI.Model.ModelFacade;
import examProjectTheDisciplesOfSkrumm.GUI.Model.TaskModel;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.sql.SQLException;
import java.time.Duration;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleGroup;
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

    private ModelFacade modelfacade;

    private Task currentTask;
    private Interval currentInterval;
    @FXML
    private JFXButton deleteButton;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        try
        {
            modelfacade = new ModelFacade();
        } catch (IOException ex)
        {
            Logger.getLogger(EditIntervalViewController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex)
        {
            Logger.getLogger(EditIntervalViewController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void fillView(Interval interval)
    {
        final ToggleGroup group = new ToggleGroup();

        paid.setToggleGroup(group);
        notPaid.setToggleGroup(group);

        if (interval.getIsPaid() == 0)
        {
            notPaid.setSelected(true);
        } else if (interval.getIsPaid() == 1)
        {
            paid.setSelected(true);
        }

        currentInterval = interval;
        
        creationDate.setValue(interval.getCreationDate());
        startTime.setValue(interval.getStartTime());
        stopTime.setValue(interval.getStopTime());

        currentTask = interval.getTask();
    }

    @FXML
    private void handleSave(ActionEvent event) throws SQLException, SQLServerException
    {
        long intervalTime = Duration.between(startTime.getValue(), stopTime.getValue()).getSeconds();

        int paidOrNot = 0;

        if (paid.isSelected())
        {
            paidOrNot = 1;
        } else if (notPaid.isSelected())
        {
            paidOrNot = 0;
        }

        Interval newInterval = new Interval(currentInterval.getId(), startTime.getValue(), stopTime.getValue(),
                creationDate.getValue(), (int) intervalTime, currentTask, paidOrNot);
        
        
        List<Interval> taskIntervals = currentTask.getIntervals();
        for (Interval taskInterval : taskIntervals)
        {
            if(taskInterval.getId() == newInterval.getId())
            {
                taskInterval.setCreationDate(creationDate.getValue());
                taskInterval.setStartTime(startTime.getValue());
                taskInterval.setStopTime(stopTime.getValue());
                taskInterval.setIntervalTime((int) intervalTime);
                taskInterval.setIsPaid(paidOrNot);
            }
        }
        
        modelfacade.updateInterval(currentInterval, newInterval);

        Stage stage = (Stage) saveButton.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void handleCancel(ActionEvent event)
    {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void handleDelete(ActionEvent event) 
    {
        try
        {
            modelfacade.deleteInterval(currentInterval);
            Stage stage = (Stage) deleteButton.getScene().getWindow();
            stage.close();
        }
        catch(SQLException e)
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("your email or password may be incorect\n" + e);
            alert.setContentText("Please try again");
            alert.showAndWait();
            
        }
        
    }

}
