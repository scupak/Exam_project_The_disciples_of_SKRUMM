
package examProjectTheDisciplesOfSkrumm.GUI.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTimePicker;
import com.microsoft.sqlserver.jdbc.SQLServerException;
import examProjectTheDisciplesOfSkrumm.BE.Interval;
import examProjectTheDisciplesOfSkrumm.BE.Task;
import examProjectTheDisciplesOfSkrumm.GUI.Model.Interface.ModelFacadeInterface;
import examProjectTheDisciplesOfSkrumm.GUI.Model.ModelFacade;
import examProjectTheDisciplesOfSkrumm.GUI.Model.TaskModel;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.sql.SQLException;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
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
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javax.swing.JDialog;
import javax.swing.JOptionPane;

/**
 * FXML Controller class
 *
 * @author SKRUMM
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

    private ModelFacadeInterface modelfacade;

    private Task currentTask;
    private Interval currentInterval;
    @FXML
    private JFXButton deleteButton;
    @FXML
    private JFXDatePicker stopTimeDate;
    @FXML
    private JFXDatePicker startTimeDate;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        try
        {
            modelfacade = ModelFacade.getInstance();
        } catch (IOException ex)
        {
            Logger.getLogger(EditIntervalViewController.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Failed to get an intance of modelfacade" + ex,"ERROR!", JOptionPane.ERROR_MESSAGE);
        } catch (SQLException ex)
        {
            Logger.getLogger(EditIntervalViewController.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Failed to get an intance of modelfacade" + ex,"ERROR!", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex)
        {
            Logger.getLogger(EditIntervalViewController.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Failed to get an intance of modelfacade" + ex,"ERROR!", JOptionPane.ERROR_MESSAGE);
        }
        paid.selectedColorProperty().set(Color.rgb(67, 90, 154));
        paid.setUnSelectedColor(Color.rgb(67, 90, 154));
        notPaid.selectedColorProperty().set(Color.rgb(67, 90, 154));
        notPaid.setUnSelectedColor(Color.rgb(67, 90, 154));
    }

    /**
     * Sets the information of the interval currently being edited
     * @param interval 
     */
    public void fillView(Interval interval)
    {
        final ToggleGroup group = new ToggleGroup();
        LocalDate startDate = interval.getStartTime().toLocalDate();
        LocalTime start = interval.getStartTime().toLocalTime();
        LocalDate stopDate = interval.getStopTime().toLocalDate();
        LocalTime stop = interval.getStopTime().toLocalTime();
        
        
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
        startTime.setValue(start);
        stopTime.setValue(stop);
        startTimeDate.setValue(startDate);
        stopTimeDate.setValue(stopDate);

        currentTask = interval.getTask();
    }

    /**
     * Creates a new instance of interval and sends it down to the updateinterval method.
     * @param event
     * @throws SQLException
     * @throws SQLServerException 
     */
    @FXML
    private void handleSave(ActionEvent event)
    {
        LocalDateTime start = LocalDateTime.of(startTimeDate.getValue(), startTime.getValue());
        LocalDateTime stop = LocalDateTime.of(stopTimeDate.getValue(), stopTime.getValue());
        
        long intervalTime = Duration.between(start, stop).getSeconds();
        if (intervalTime <= 0)
        {
            JOptionPane optionPane = new JOptionPane();
            JDialog dialog = optionPane.createDialog(null, "ERROR");
            optionPane.setMessage("Stop Time cannot be before" + "\n" + "    or equal to Start Time!");
            optionPane.setMessageType(JOptionPane.ERROR_MESSAGE);
            dialog.setAlwaysOnTop(true);
            dialog.setVisible(true);
        } else
        {
            int paidOrNot = 0;

            if (paid.isSelected())
            {
                paidOrNot = 1;
            } else if (notPaid.isSelected())
            {
                paidOrNot = 0;
            }
            
            Interval newInterval = new Interval(currentInterval.getId(), start, stop,
                    creationDate.getValue(), (int) intervalTime, currentTask, paidOrNot);

            if (currentInterval.getId() == newInterval.getId() && start == currentInterval.getStartTime()
                    && stop == currentInterval.getStopTime() && creationDate.getValue() == currentInterval.getCreationDate()
                    && (int) intervalTime == currentInterval.getIntervalTime() && paidOrNot == currentInterval.getIsPaid())
            {
                Stage stage = (Stage) saveButton.getScene().getWindow();
                stage.close();
            } else
            {
                try
                {
                    modelfacade.updateInterval(currentInterval, newInterval);
                    
                    List<Interval> taskIntervals = currentTask.getIntervals();
                    for (Interval taskInterval : taskIntervals)
                    {
                        if (taskInterval.getId() == newInterval.getId())
                        {
                            taskInterval.setCreationDate(creationDate.getValue());
                            taskInterval.setStartTime(start);
                            taskInterval.setStopTime(stop);
                            taskInterval.setIntervalTime((int) intervalTime);
                            taskInterval.setIsPaid(paidOrNot);
                        }
                    }
                    
                    Stage stage = (Stage) saveButton.getScene().getWindow();
                    stage.close();
                } catch (SQLException ex)
                {
                    Logger.getLogger(EditIntervalViewController.class.getName()).log(Level.SEVERE, null, ex);
                    JOptionPane.showMessageDialog(null, "Failed to contact the database" + ex,"ERROR!", JOptionPane.ERROR_MESSAGE);
                }
            }
        }

    }

    /**
     * Closes the window
     * @param event 
     */
    @FXML
    private void handleCancel(ActionEvent event)
    {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }

    /**
     * Sends the the interval being edited to the delete interval method to be deleted from the database.
     * @param event 
     */
    @FXML
    private void handleDelete(ActionEvent event)
    {
        try
        {
            modelfacade.deleteInterval(currentInterval);
            Stage stage = (Stage) deleteButton.getScene().getWindow();
            stage.close();
        } catch (SQLException ex)
        {
            Logger.getLogger(EditIntervalViewController.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Could not delete interval" + ex,"ERROR!", JOptionPane.ERROR_MESSAGE);
        }

    }

}
