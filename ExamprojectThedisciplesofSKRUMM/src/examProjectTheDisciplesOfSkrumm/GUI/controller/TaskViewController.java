/* Got help and inspiration for this class from 

Pomarolli, A. (Set D. 24-04-20) Java Code Geeks. https://examples.javacodegeeks.com/desktop-java/javafx/javafx-treetableview-example/
En guide til hvordan man kan bruge TreeTableViews
 
James_D. (Set D. 24-04-20) StackOverflow. https://stackoverflow.com/questions/22260032/set-two-root-nodes-for-treeview/22260167
En guide til hvordan man kan gemme TreeTableViews
 
Ranga. (Set D. 03-05-20) javanbswing.blogspot.com. http://javanbswing.blogspot.com/2016/06/javafx-treetableview-example-with.html
En guide til hvordan man kan have to forskellige slags objekter i en treeTableView.
 */
package examProjectTheDisciplesOfSkrumm.GUI.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDatePicker;
import examProjectTheDisciplesOfSkrumm.BE.Interval;
import examProjectTheDisciplesOfSkrumm.BE.Task;
import examProjectTheDisciplesOfSkrumm.BLL.Util.TimerUtil;
import examProjectTheDisciplesOfSkrumm.BLL.Util.TreeTableUtil;
import examProjectTheDisciplesOfSkrumm.GUI.Model.Interface.ModelFacadeInterface;
import examProjectTheDisciplesOfSkrumm.GUI.Model.ModelFacade;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.WeekFields;
import java.util.ArrayList;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableCell;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTableView;
import javafx.scene.control.cell.TreeItemPropertyValueFactory;
import javafx.stage.Stage;
import javafx.util.Callback;
import javax.swing.JDialog;
import javax.swing.JOptionPane;

/**
 * FXML Controller class
 *
 * @author kacpe
 */
public class TaskViewController implements Initializable
{

    ModelFacadeInterface modelfacade;
    @FXML
    private TreeTableView TaskTable;
    @FXML
    private TreeTableColumn TimeColumn;
    @FXML
    private JFXButton homeBtn;
    @FXML
    private JFXButton createTaskButton;
    @FXML
    private JFXButton timerButton;
    @FXML
    private TreeTableColumn clientColumn;
    private Label weekNumberLabel;
    private Label WeekdayLabel;
    @FXML
    private Label CurrentTaskLabel;
    @FXML
    private TreeTableColumn TaskColumn;
    @FXML
    private TreeTableColumn ProjectColumn;
    @FXML
    private Label timeLabel;

    boolean isCurrentDay;

    int weekOfYear;
    private int sec = 0;
    private int min = 0;
    private int hour = 0;
    private boolean running = false;
    @FXML
    private TreeTableColumn startTimeColumn;
    @FXML
    private TreeTableColumn IsPaidColumn;
    @FXML
    private TreeTableColumn stopTimeColumn;
    @FXML
    private JFXButton returnToCurrentDayButton;
    @FXML
    private JFXButton deleteTask;
    
    private long durationtotal;
    @FXML
    private Label totalTimeLabel;
    @FXML
    private JFXDatePicker datePickerFrom;
    @FXML
    private JFXDatePicker datePickerTo;
    @FXML
    private TreeTableColumn CreationDateColumn;
    @FXML
    private TreeTableColumn LastUsedColumn;
    
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        try
        {
            //Getting the model
            modelfacade = ModelFacade.getInstance();
        } catch (Exception ex)
        {
            Logger.getLogger(TaskViewController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        //Setting cellValue Factories for TreeTableView 
        TaskColumn.setCellValueFactory(new Callback()
        {
            @Override
            public Object call(Object obj)
            {
                final Object dataObj = ((TreeTableColumn.CellDataFeatures) obj).getValue().getValue();
                if (dataObj instanceof Task)
                {
                    return new ReadOnlyStringWrapper(((Task) dataObj).getTitle());
                } else
                {
                    return null;
                }

            }
        });

        ProjectColumn.setCellValueFactory(new Callback()
        {
            @Override
            public Object call(Object obj)
            {
                final Object dataObj = ((TreeTableColumn.CellDataFeatures) obj).getValue().getValue();
                if (dataObj instanceof Task)
                {
                    return new ReadOnlyStringWrapper(((Task) dataObj).getProjectName());
                } else
                {
                    return null;
                }

            }
        });

        TimeColumn.setCellValueFactory(new Callback()
        {
            @Override
            public Object call(Object obj)
            {
                final Object dataObj = ((TreeTableColumn.CellDataFeatures) obj).getValue().getValue();
                if (dataObj instanceof Task)
                {
                    return new ReadOnlyStringWrapper(((Task) dataObj).getFormatedDuration());
                } else if (dataObj instanceof Interval)
                {
                    return new ReadOnlyStringWrapper(((Interval) dataObj).getFormatedIntervaltime());
                } else
                {
                    return null;
                }

            }
        });
        
        LastUsedColumn.setCellValueFactory(new Callback() 
        {
            @Override
            public Object call(Object obj) 
            {
                final Object dataObj = ((TreeTableColumn.CellDataFeatures) obj).getValue().getValue();
                if (dataObj instanceof Task)
                {
                    return new ReadOnlyStringWrapper(((Task) dataObj).getFormatedLastUsed());
                } else
                {
                    return null;
                }
            }
        });
        
        CreationDateColumn.setCellValueFactory(new Callback() 
        {
            @Override
            public Object call(Object obj) 
            {
               final Object dataObj = ((TreeTableColumn.CellDataFeatures) obj).getValue().getValue();
                if (dataObj instanceof Interval)
                {
                  return new ReadOnlyStringWrapper(((Interval) dataObj).getCreationDate().toString());
                } else if (dataObj instanceof Task)
                {
                    return new ReadOnlyStringWrapper(((Task) dataObj).getCreationDate().toString());
                } else
                {
                    return null;
                }
            }
        });

        clientColumn.setCellValueFactory(new Callback()
        {
            @Override
            public Object call(Object obj)
            {
                final Object dataObj = ((TreeTableColumn.CellDataFeatures) obj).getValue().getValue();
                if (dataObj instanceof Task)
                {
                    return new ReadOnlyStringWrapper(((Task) dataObj).getClientName());
                } else
                {
                    return null;
                }

            }
        });

        startTimeColumn.setCellValueFactory(new Callback()
        {
            @Override
            public Object call(Object obj)
            {
                final Object dataObj = ((TreeTableColumn.CellDataFeatures) obj).getValue().getValue();
                if (dataObj instanceof Interval)
                {
                    return new ReadOnlyStringWrapper(((Interval) dataObj).getStartTime().toString());
                } else if (dataObj instanceof Task)
                {
                    return new ReadOnlyStringWrapper(((Task) dataObj).getStartTime().toString());
                } else
                {
                    return null;
                }

            }
        });

        stopTimeColumn.setCellValueFactory(new Callback()
        {
            @Override
            public Object call(Object obj)
            {
                final Object dataObj = ((TreeTableColumn.CellDataFeatures) obj).getValue().getValue();
                if (dataObj instanceof Interval)
                {
                    return new ReadOnlyStringWrapper(((Interval) dataObj).getStopTime().toString());
                } else if (dataObj instanceof Task)
                {
                    return new ReadOnlyStringWrapper(((Task) dataObj).getStopTime().toString());
                } else
                {
                    return null;
                }

            }
        });

        IsPaidColumn.setCellValueFactory(new Callback()
        {
            @Override
            public Object call(Object obj)
            {
                final Object dataObj = ((TreeTableColumn.CellDataFeatures) obj).getValue().getValue();
                if (dataObj instanceof Task)
                {
                    return new ReadOnlyStringWrapper(((Task) dataObj).getIsPaidBoolean());
                } else
                {
                    return null;
                }
            }
        });
        
        try 
        {
            datePickerFrom.setValue(LocalDate.now());
            refreshEverything();
            
            if(modelfacade.getisTimerRunning())
            {
                handleTimerUtilIsRunning();
            }
            
        } catch (SQLException ex) 
        {
            Logger.getLogger(TaskViewController.class.getName()).log(Level.SEVERE, null, ex);
        }

        

    }

    @FXML
    private void handleHome(ActionEvent event) throws IOException
    {
        Stage chartView = (Stage) ((Node) event.getSource()).getScene().getWindow();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/examProjectTheDisciplesOfSkrumm/GUI/view/MainView.fxml"));
        Parent root = loader.load();
        MainViewController Controller = loader.getController();

        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.setTitle("Main menu");
        stage.show();
        stage.setMinHeight(523);
        stage.setMinWidth(721);
        chartView.close();
    }

    @FXML
    private void createTask(ActionEvent event) throws IOException
    {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/examProjectTheDisciplesOfSkrumm/GUI/view/CreateTaskView.fxml"));
        Parent root = loader.load();
        CreateTaskController controller = loader.getController();

        controller.settaskViewController(this);

        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.setTitle("Create Task");
        stage.setMinHeight(200);
        stage.setMinWidth(364);
        stage.show();
    }

    @FXML
    private void handleEditTaskAction(ActionEvent event) throws IOException
    {

        final JDialog dialog = new JDialog();
        dialog.setAlwaysOnTop(true);

        if ((TaskTable.getSelectionModel().getSelectedItem() == null))
        {
            JOptionPane.showMessageDialog(dialog, "Nothing seems to be selected!\nSelect a task to edit before pressing edit!", "ERROR", JOptionPane.ERROR_MESSAGE);
        } else
        {

            TreeItem<Task> selectedItem = (TreeItem<Task>) (TaskTable.getSelectionModel().getSelectedItem());

            if (selectedItem.getValue() instanceof Task)
            {

                System.err.println("its a task!!!!!!!!!!!!!!!!!!!!");
                System.out.println(selectedItem.getValue());

                FXMLLoader loader = new FXMLLoader(getClass().getResource("/examProjectTheDisciplesOfSkrumm/GUI/view/EditTaskView.fxml"));
                Parent root = loader.load();
                EditTaskController controller = loader.getController();
                controller.setEditTask((Task) selectedItem.getValue());
                Stage stage = new Stage();
                stage.setScene(new Scene(root));
                stage.setMinHeight(230);
                stage.setMinWidth(364);
                stage.setTitle("Edit Task");
                stage.show();

            } else
            {

                System.err.println("not a task!!!!!!!!!!!!!!!!!!!!");
                JOptionPane.showMessageDialog(dialog, "Please select a valid task to edit!", "ERROR", JOptionPane.ERROR_MESSAGE);

            }

        }

    }

    @FXML
    private void handleStartTimer(ActionEvent event) throws SQLException
    {   final JDialog dialog = new JDialog();
        dialog.setAlwaysOnTop(true);
        
        if (modelfacade.getisTimerRunning())
        {
            try 
            {
                modelfacade.setIsTimerRunning(false);
                modelfacade.getTimerutil().setIsRunning(false);
                modelfacade.getExecutorService().shutdownNow();
                timerButton.setText("start timer");
                 
                Task currentTask = modelfacade.getTimerutil().getCurrenttask();
                currentTask.setDuration(modelfacade.getTimerutil().getTotalSec());
                LocalTime  stopTime = LocalTime.now();
                 
                 
                Interval taskInterval = new Interval(0, modelfacade.getTimerutil().getStartTime(), stopTime, LocalDate.now(), modelfacade.getTimerutil().getTotalIntervalSec(),currentTask, currentTask.getIsPaid());
                
                if(currentTask.getIntervals().isEmpty())
                {
                    currentTask.setStartTime(modelfacade.getTimerutil().getStartTime());
                }
                currentTask.setStopTime(stopTime);
                modelfacade.updateTask(currentTask);
                modelfacade.newInterval(taskInterval);
                refreshEverything();
                 
                 
                 
            } catch (SQLException ex) 
            {
                Logger.getLogger(TaskViewController.class.getName()).log(Level.SEVERE, null, ex);
                JOptionPane.showMessageDialog(dialog, "an SQLException occurred while trying to make a new interval", "ERROR", JOptionPane.ERROR_MESSAGE);
            }
        } 
        else if (!modelfacade.getisTimerRunning())
        {
            if ((TaskTable.getSelectionModel().getSelectedItem() == null))
            {
                JOptionPane.showMessageDialog(dialog, "Nothing seems to be selected!\nSelect a task to edit before pressing edit!", "ERROR", JOptionPane.ERROR_MESSAGE);
            } 
            else
            {
                TreeItem<Task> selectedItem = (TreeItem<Task>) (TaskTable.getSelectionModel().getSelectedItem());

                if (selectedItem.getValue() instanceof Task)
                {
                    System.err.println("its a task!!!!!!!!!!!!!!!!!!!!");
                    System.out.println(selectedItem.getValue());

                    modelfacade.setIsTimerRunning(true);
                    modelfacade.setTimerutil(new TimerUtil(null,timeLabel,selectedItem.getValue().getDuration(),selectedItem.getValue(),LocalTime.now() ));
                    modelfacade.setExecutorService(Executors.newFixedThreadPool(1));
                    modelfacade.getExecutorService().submit(modelfacade.getTimerutil());
                 
                    CurrentTaskLabel.setText(modelfacade.getTimerutil().getCurrenttask().getTitle());
                    timerButton.setText("stop timer");
                } 
                else
                {
                    System.err.println("not a task!!!!!!!!!!!!!!!!!!!!");
                    JOptionPane.showMessageDialog(dialog, "Please select a valid task to edit!", "ERROR", JOptionPane.ERROR_MESSAGE);
                } 
            }
        }
    }

    /**
     * Refreshes the tableview
     */
    public void RefreshTreeView()
    {
        //Creating the rootNodeTask
        TreeItem<Task> rootNodeTask = modelfacade.getModel(modelfacade.getCurrentuser(), datePickerFrom.getValue());
        rootNodeTask.setExpanded(true);

        //Set the model for the TreeTableView
        TaskTable.setRoot(rootNodeTask);

        // Make the root node invisible
        TaskTable.setShowRoot(false);
    }
    
    @FXML
    private void handlecurrentday(ActionEvent event) throws SQLException
    {
        datePickerFrom.setValue(LocalDate.now());
        refreshEverything();
        
    }

    private void checkForCurrentday()
    {
        LocalDate date = datePickerFrom.getValue();
        if (!(date.isEqual(LocalDate.now())))
        {
            returnToCurrentDayButton.setVisible(true);
            returnToCurrentDayButton.setDisable(false);
        } else if (date.isEqual(LocalDate.now()))
        {
            returnToCurrentDayButton.setVisible(false);
            returnToCurrentDayButton.setDisable(true);
        }
    }

    
    @FXML
    private void handleDeleteTaskAction(ActionEvent event) throws SQLException
    {
        final JDialog dialog = new JDialog();
        dialog.setAlwaysOnTop(true);

        if ((TaskTable.getSelectionModel().getSelectedItem() == null))
        {
            JOptionPane.showMessageDialog(dialog, "Nothing seems to be selected!\nSelect a task to delete before pressing delete!", "ERROR", JOptionPane.ERROR_MESSAGE);
        } else
        {

            TreeItem<Task> selectedItem = (TreeItem<Task>) (TaskTable.getSelectionModel().getSelectedItem());

            if (selectedItem.getValue() instanceof Task)
            {

                Task task = selectedItem.getValue();
                int input = JOptionPane.showConfirmDialog(null, "delete task?", "Select an Option...",
                        JOptionPane.YES_NO_OPTION, JOptionPane.ERROR_MESSAGE);

                if (input == JOptionPane.YES_OPTION)
                {
                    modelfacade.deleteTask(task);
                    RefreshTreeView();
                }

            }
        }

    }
    
    private void refreshtotal() throws SQLException
    {
        durationtotal = 0;
        ArrayList<Task> tasks = new ArrayList<>();
        tasks.addAll(modelfacade.getTasks());
        
        for (Task task : tasks) 
        {
            durationtotal = durationtotal + task.getDuration();
            
        }
        
        long hour = TimeUnit.SECONDS.toHours(durationtotal);
        long min = TimeUnit.SECONDS.toMinutes(durationtotal) - TimeUnit.HOURS.toMinutes(hour);
        Long sec = durationtotal - TimeUnit.MINUTES.toSeconds(min);
        
        totalTimeLabel.setText(String.format("%d:%d:%d", hour, min, sec));
        
    }
    
    
    
    private void refreshEverything() throws SQLException
    {
        RefreshTreeView();
        refreshtotal();
        checkForCurrentday();
        
    }

    /**
     * this method gets called if the timer is allready running when you initilize the view. 
     */
    private void handleTimerUtilIsRunning() {
        
        modelfacade.getTimerutil().setTotalTimeLabel(timeLabel);
        CurrentTaskLabel.setText(modelfacade.getTimerutil().getCurrenttask().getTitle());
        timerButton.setText("stop timer"); 
    }


    @FXML
    private void handleDatepickerFromAction(ActionEvent event) throws SQLException 
    {
        refreshEverything();
    }

    @FXML
    private void handleDatepickerToAction(ActionEvent event) throws SQLException 
    {
        refreshEverything();
    }
}


