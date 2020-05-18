
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
        } catch (Exception ex)
        {
            Logger.getLogger(EditIntervalViewController.class.getName()).log(Level.SEVERE, null, ex);
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

    /**
     * Creates a new instance of interval and sends it down to the updateinterval method.
     * @param event
     * @throws SQLException
     * @throws SQLServerException 
     */
    @FXML
    private void handleSave(ActionEvent event) throws SQLException, SQLServerException
    {
        long intervalTime = Duration.between(startTime.getValue(), stopTime.getValue()).getSeconds();
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

            Interval newInterval = new Interval(currentInterval.getId(), startTime.getValue(), stopTime.getValue(),
                    creationDate.getValue(), (int) intervalTime, currentTask, paidOrNot);

            if (currentInterval.getId() == newInterval.getId() && startTime.getValue() == currentInterval.getStartTime()
                    && stopTime.getValue() == currentInterval.getStopTime() && creationDate.getValue() == currentInterval.getCreationDate()
                    && (int) intervalTime == currentInterval.getIntervalTime() && paidOrNot == currentInterval.getIsPaid())
            {
                Stage stage = (Stage) saveButton.getScene().getWindow();
                stage.close();
            } else
            {
                modelfacade.updateInterval(currentInterval, newInterval);

                List<Interval> taskIntervals = currentTask.getIntervals();
                for (Interval taskInterval : taskIntervals)
                {
                    if (taskInterval.getId() == newInterval.getId())
                    {
                        taskInterval.setCreationDate(creationDate.getValue());
                        taskInterval.setStartTime(startTime.getValue());
                        taskInterval.setStopTime(stopTime.getValue());
                        taskInterval.setIntervalTime((int) intervalTime);
                        taskInterval.setIsPaid(paidOrNot);
                    }
                }

                Stage stage = (Stage) saveButton.getScene().getWindow();
                stage.close();
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
        } catch (SQLException e)
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Could delete interval\n" + e);
            alert.setContentText("Please try again");
            alert.showAndWait();

        }

    }

}
