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
import java.net.URL;
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

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        // TODO
    }    
    
    public void fillView(Interval interval)
    {
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
    }

    @FXML
    private void handleSave(ActionEvent event)
    {
        
    }

    @FXML
    private void handleCancel(ActionEvent event)
    {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }
    
}
