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
import examProjectTheDisciplesOfSkrumm.GUI.Model.Interface.ModelFacadeInterface;
import examProjectTheDisciplesOfSkrumm.GUI.Model.ModelFacade;
import examProjectTheDisciplesOfSkrumm.enums.UserMode;
import examProjectTheDisciplesOfSkrumm.enums.ViewTypes;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
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
import javafx.scene.control.Label;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTableView;
import javafx.stage.Stage;
import javafx.util.Callback;
import javax.swing.JDialog;
import javax.swing.JOptionPane;

/**
 * FXML Controller class
 *
 * @author Skrumm
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
    
    private int durationtotal;
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
    @FXML
    private JFXButton editTaskBtn;
    @FXML
    private Label HeaderLabel;
    
    
    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        try
        {
            //Getting the model
            modelfacade = ModelFacade.getInstance();
        } 
        catch (Exception ex)
        {
            Logger.getLogger(TaskViewController.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Failed to get an intance of modelfacade" + ex,"ERROR!", JOptionPane.ERROR_MESSAGE);
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
                  return new ReadOnlyStringWrapper(((Interval) dataObj).getFormatedCreationDate());
                } 
                else if (dataObj instanceof Task)
                {
                    return new ReadOnlyStringWrapper(((Task) dataObj).getFormatedCreationDate());
                } 
                else
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
                    return new ReadOnlyStringWrapper(((Interval) dataObj).getFormatedStartTime());
                } else if (dataObj instanceof Task)
                {
                    return new ReadOnlyStringWrapper(((Task) dataObj).getFormatedStartTime());
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
                    return new ReadOnlyStringWrapper(((Interval) dataObj).getFormatedStopTime());
                } else if (dataObj instanceof Task)
                {
                    return new ReadOnlyStringWrapper(((Task) dataObj).getFormatedStopTime());
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
                if (dataObj instanceof Interval)
                {
                    return new ReadOnlyStringWrapper(((Interval) dataObj).paidOrNot());
                   
                } else if(dataObj instanceof Task)
                {
                    return new ReadOnlyStringWrapper(((Task) dataObj).getIsPaidBoolean());
                }
                else 
                {
                    return null;
                }
            }
        });
        
        try 
        {
             datePickerFrom.setValue(LocalDate.now().with(DayOfWeek.MONDAY));
             datePickerTo.setValue(LocalDate.now().with(DayOfWeek.SUNDAY));
             refreshEverything();
            
            if(modelfacade.getisTimerRunning())
            {
                handleTimerUtilIsRunning();
            }
            
        } 
        catch (SQLException ex) 
        {
            Logger.getLogger(TaskViewController.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Failed to contact the database" + ex,"ERROR!", JOptionPane.ERROR_MESSAGE);
        }

        

    }
    
    /**
     * opens the main view
     * @param event
     * @throws IOException 
     */
    @FXML
    private void handleHome(ActionEvent event)
    {
        Stage taskView = (Stage) ((Node) event.getSource()).getScene().getWindow();
        if(modelfacade.getCurrentUserMode().equals(UserMode.ADMIN))
        {
            try 
            {
                modelfacade.setCurrentuser(modelfacade.getCurrentAdmin());
                FXMLLoader loader = modelfacade.getLoader(ViewTypes.ADMINUSER);
                Parent root = loader.load();
                AdminUserViewController Controller = loader.getController();
                
                Stage stage = new Stage();
                stage.setScene(new Scene(root));
                stage.setTitle("Main menu");
                stage.show();
                stage.setMinHeight(523);
                stage.setMinWidth(721);
                taskView.close();
            } 
            catch (IOException ex)
            {
                Logger.getLogger(MainViewController.class.getName()).log(Level.SEVERE, null, ex);
                JOptionPane.showMessageDialog(null, "Wrong input" + ex,"ERROR!", JOptionPane.ERROR_MESSAGE); 
            } 
            catch (Exception ex)
            {
                Logger.getLogger(MainViewController.class.getName()).log(Level.SEVERE, null, ex);
                JOptionPane.showMessageDialog(null, "Wrong view type" + ex,"ERROR!", JOptionPane.ERROR_MESSAGE); 
            }
        }
        else
        {
            try
            {
                FXMLLoader loader = modelfacade.getLoader(ViewTypes.MAIN);
                Parent root = loader.load();
                MainViewController Controller = loader.getController();
                
                Stage stage = new Stage();
                stage.setScene(new Scene(root));
                stage.setTitle("Main menu");
                stage.show();
                stage.setMinHeight(523);
                stage.setMinWidth(721);
                taskView.close();
            } 
            catch (IOException ex)
            {
                Logger.getLogger(MainViewController.class.getName()).log(Level.SEVERE, null, ex);
                JOptionPane.showMessageDialog(null, "Wrong input" + ex,"ERROR!", JOptionPane.ERROR_MESSAGE); 
            } 
            catch (Exception ex)
            {
                Logger.getLogger(MainViewController.class.getName()).log(Level.SEVERE, null, ex);
                JOptionPane.showMessageDialog(null, "Wrong view type" + ex,"ERROR!", JOptionPane.ERROR_MESSAGE); 
            }
        }
    }
    /**
     * The eventhandler responsible for opening the create task window. 
     * @param event
     * @throws IOException 
     */
    @FXML
    private void createTask(ActionEvent event)
    {
        try 
        {
            FXMLLoader loader = modelfacade.getLoader(ViewTypes.CREATETASK);
            Parent root = loader.load();
            CreateTaskController controller = loader.getController();
            
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Create Task");
            stage.setMinHeight(210);
            stage.setMinWidth(364);
            stage.showAndWait();
            try
            {
                refreshEverything();
            }
            catch (SQLException ex)
            {
                Logger.getLogger(MainViewController.class.getName()).log(Level.SEVERE, null, ex);
                JOptionPane.showMessageDialog(null, "Failed to contact the database" + ex,"ERROR!", JOptionPane.ERROR_MESSAGE);
            }
            
        } 
        catch (IOException ex)
        {
            Logger.getLogger(MainViewController.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Wrong input" + ex,"ERROR!", JOptionPane.ERROR_MESSAGE); 
        } 
        catch (Exception ex)
        {
            Logger.getLogger(MainViewController.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Wrong view type" + ex,"ERROR!", JOptionPane.ERROR_MESSAGE); 
        }
        
    }
/**
 * The eventhandler responsible for opening the edittaskwindow
 * @param event
 * @throws IOException 
 */
    @FXML
    private void handleEditTaskAction(ActionEvent event)
    {

        final JDialog dialog = new JDialog();
        dialog.setAlwaysOnTop(true);

        if ((TaskTable.getSelectionModel().getSelectedItem() == null))
        {
            JOptionPane.showMessageDialog(dialog, "Nothing seems to be selected!\nSelect a task to edit before pressing edit!", "ERROR", JOptionPane.ERROR_MESSAGE);
        } 
        else
        {
            TreeItem selectedItem = (TreeItem) (TaskTable.getSelectionModel().getSelectedItem());

            if (selectedItem.getValue() instanceof Task)
            {

                try 
                {

                    System.err.println("its a task!!!!!!!!!!!!!!!!!!!!");
                    System.out.println(selectedItem.getValue());
                    
                    FXMLLoader loader = modelfacade.getLoader(ViewTypes.EDITTASK);
                    Parent root = loader.load();
                    EditTaskController controller = loader.getController();
                    controller.setEditTask((Task) selectedItem.getValue());
                    Stage stage = new Stage();
                    stage.setScene(new Scene(root));
                    stage.setMinHeight(250);
                    stage.setMinWidth(375);
                    stage.setTitle("Edit Task");
                    stage.showAndWait();
                    try
                    {
                        refreshEverything();
                    }
                    catch (SQLException ex)
                    {
                        Logger.getLogger(MainViewController.class.getName()).log(Level.SEVERE, null, ex);
                        JOptionPane.showMessageDialog(null, "Failed to contact the database" + ex,"ERROR!", JOptionPane.ERROR_MESSAGE);
                    }
                } 
                catch (IOException ex)
                {
                    Logger.getLogger(MainViewController.class.getName()).log(Level.SEVERE, null, ex);
                    JOptionPane.showMessageDialog(null, "Wrong input" + ex,"ERROR!", JOptionPane.ERROR_MESSAGE); 
                } 
                catch (Exception ex)
                {
                    Logger.getLogger(MainViewController.class.getName()).log(Level.SEVERE, null, ex);
                    JOptionPane.showMessageDialog(null, "Wrong view type" + ex,"ERROR!", JOptionPane.ERROR_MESSAGE); 
                }
            } 
            else if(selectedItem.getValue() instanceof Interval)
            {
                try
                {
                    FXMLLoader loader = modelfacade.getLoader(ViewTypes.EDITINTERVAL);
                    Parent root = loader.load();
                    EditIntervalViewController controller = loader.getController();
                    controller.fillView((Interval) selectedItem.getValue());
                    Stage stage = new Stage();
                    stage.setScene(new Scene(root));
                    stage.setMinHeight(260);
                    stage.setMinWidth(318);
                    stage.setTitle("Edit Interval");
                    stage.setAlwaysOnTop(true);
                    stage.showAndWait();
                    try {
                        refreshEverything();
                    } catch (SQLException ex)
                    {
                        Logger.getLogger(MainViewController.class.getName()).log(Level.SEVERE, null, ex);
                        JOptionPane.showMessageDialog(null, "Failed to contact the database" + ex,"ERROR!", JOptionPane.ERROR_MESSAGE);
                    }
                } 
                catch (IOException ex)
                {
                    Logger.getLogger(MainViewController.class.getName()).log(Level.SEVERE, null, ex);
                    JOptionPane.showMessageDialog(null, "Wrong input" + ex,"ERROR!", JOptionPane.ERROR_MESSAGE); 
                } 
                catch (Exception ex)
                {
                    Logger.getLogger(MainViewController.class.getName()).log(Level.SEVERE, null, ex);
                    JOptionPane.showMessageDialog(null, "Wrong view type" + ex,"ERROR!", JOptionPane.ERROR_MESSAGE); 
                }
            }
            else
            {
                System.err.println("Error");
                JOptionPane.showMessageDialog(dialog, "Please select a valid task or interval to edit!", "ERROR", JOptionPane.ERROR_MESSAGE);
            }

        }

    }

    /**
     * The eventhandler responsible for starting and stoping the timer. 
     * @param event
     * @throws SQLException 
     */
    @FXML
    private void handleStartTimer(ActionEvent event)
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
                CurrentTaskLabel.setText("Please select a task");
                timeLabel.setText("00:00:00");
                 
                Task currentTask = modelfacade.getTimerutil().getCurrenttask();
                currentTask.setDuration(modelfacade.getTimerutil().getTotalSec());
                LocalDateTime  stopTime = LocalDateTime.now();
                
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
                    modelfacade.setTimerutil(new TimerUtil(null,timeLabel,selectedItem.getValue().getDuration(),selectedItem.getValue(),LocalDateTime.now() ));
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
     * 
     */
    public void RefreshTreeView()
    {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.submit(() -> 
        {
            //Creating the rootNodeTask
            TreeItem<Task> rootNodeTask = modelfacade.getModel(modelfacade.getCurrentuser(), datePickerFrom.getValue(), datePickerTo.getValue());
        
            Platform.runLater( () -> 
            {
                rootNodeTask.setExpanded(true);

                //Set the model for the TreeTableView
                TaskTable.setRoot(rootNodeTask);

                // Make the root node invisible
                 TaskTable.setShowRoot(false);
            });
        });
        
        executor.shutdown();
    }
    /**
     * The eventhandler responsible for setting the datepickers to the current day. 
     * @param event
     * @throws SQLException 
     */
    @FXML
    private void handlecurrentday(ActionEvent event)
    {
        try
        {
            
             datePickerTo.setValue(LocalDate.now().with(DayOfWeek.SUNDAY));
             datePickerFrom.setValue(LocalDate.now().with(DayOfWeek.MONDAY));
             
            refreshEverything();
        } catch (SQLException ex)
        {
            Logger.getLogger(TaskViewController.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Failed to contact the database" + ex,"ERROR!", JOptionPane.ERROR_MESSAGE);
        }
        
    }
    
    /**
     * checks if the day the user is currently looking at is the current date. 
     */
    private void checkForCurrentday()
    {
        LocalDate date1 = datePickerFrom.getValue();
        LocalDate date2 = datePickerTo.getValue();
        if (!(date1.isEqual(LocalDate.now().with(DayOfWeek.MONDAY))) || !(date2.isEqual(LocalDate.now().with(DayOfWeek.SUNDAY))))
        {
            returnToCurrentDayButton.setVisible(true);
            returnToCurrentDayButton.setDisable(false);
        } else if (date1.isEqual(LocalDate.now().with(DayOfWeek.MONDAY)) || date2.isEqual(LocalDate.now().with(DayOfWeek.SUNDAY)))
        {
            returnToCurrentDayButton.setVisible(false);
            returnToCurrentDayButton.setDisable(true);
        }
    }

    /**
     * The eventhandler responsible for deleting the tasks and intervals. 
     * @param event
     * @throws SQLException 
     */
    @FXML
    private void handleDeleteTaskAction(ActionEvent event)
    {
        final JDialog dialog = new JDialog();
        dialog.setAlwaysOnTop(true);

        if ((TaskTable.getSelectionModel().getSelectedItem() == null))
        {
            JOptionPane.showMessageDialog(dialog, "Nothing seems to be selected!\nSelect a task to delete before pressing delete!", "ERROR", JOptionPane.ERROR_MESSAGE);
        } else
        {

            TreeItem selectedTreeItem =(TreeItem) TaskTable.getSelectionModel().getSelectedItem();

            if (selectedTreeItem.getValue() instanceof Task)
            {
                TreeItem<Task> selectedTask = (TreeItem<Task>) selectedTreeItem;

                Task task = selectedTask.getValue();
                int input = JOptionPane.showConfirmDialog(null, "delete task?", "Select an Option...",
                        JOptionPane.YES_NO_OPTION, JOptionPane.ERROR_MESSAGE);

                if (input == JOptionPane.YES_OPTION)
                {
                    try {
                        modelfacade.deleteTask(task);
                        RefreshTreeView();
                    } catch (SQLException ex) 
                    {
                        Logger.getLogger(TaskViewController.class.getName()).log(Level.SEVERE, null, ex);
                        JOptionPane.showMessageDialog(null, "Failed to contact the database" + ex,"ERROR!", JOptionPane.ERROR_MESSAGE);
                    }
                }

            }
            else if(selectedTreeItem.getValue() instanceof Interval)
            {
                TreeItem<Interval> selectedTask = (TreeItem<Interval>) selectedTreeItem;

                Interval interval = selectedTask.getValue();
                int input = JOptionPane.showConfirmDialog(null, "delete interval?", "Select an Option...",
                        JOptionPane.YES_NO_OPTION, JOptionPane.ERROR_MESSAGE);

                if (input == JOptionPane.YES_OPTION)
                {
                    try 
                    {
                        modelfacade.deleteInterval(interval); 
                        RefreshTreeView();
                    } 
                    catch (SQLException ex) 
                    {
                        Logger.getLogger(TaskViewController.class.getName()).log(Level.SEVERE, null, ex);
                        JOptionPane.showMessageDialog(null, "Failed to contact the database" + ex,"ERROR!", JOptionPane.ERROR_MESSAGE);
                    }
                }  
            }
        }
    }
    /**
     * recalculates the total time for the totalTimeLabel. 
     * @throws SQLException 
     */
    private void refreshtotal() throws SQLException
    {
        durationtotal = 0;
        ArrayList<Task> tasks = new ArrayList<>();
        tasks.addAll(modelfacade.getTasks());
        
        for (Task task : tasks) 
        {
            durationtotal = durationtotal + task.getDuration();
            
        }
        
        
        
        totalTimeLabel.setText(modelfacade.convertSecToTimeString(durationtotal));
        
    }
    
    
    /**
     * calls the RefreshTreeView(), refreshtotal(). checkForCurrentday() methods. 
     * @throws SQLException 
     */
    public void refreshEverything() throws SQLException
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


    /**
     * calls the  refreshEverything() method if the FromDatePicker gets used.
     * @param event
     * @throws SQLException 
     */
    @FXML
    private void handleDatepickerFromAction(ActionEvent event) throws SQLException 
    {
        
        
        int result = datePickerFrom.getValue().compareTo(datePickerTo.getValue());
        if (result > 0)
        {
            JOptionPane optionPane = new JOptionPane();
            JDialog dialog = optionPane.createDialog(null, "ERROR");
            optionPane.setMessage("The end date cannot be \n before the from date!");
            optionPane.setMessageType(JOptionPane.ERROR_MESSAGE);
            dialog.setAlwaysOnTop(true);
            dialog.setVisible(true);
            datePickerTo.setValue(LocalDate.now());
            datePickerFrom.setValue(LocalDate.now());
        }
        else
        {
            refreshEverything();
        }
    }

    /**
     * calls the  refreshEverything() method if the ToDatePicker gets used.
     * @param event
     * @throws SQLException 
     */
    @FXML
    private void handleDatepickerToAction(ActionEvent event) throws SQLException 
    {
        int result = datePickerFrom.getValue().compareTo(datePickerTo.getValue());
        if (result > 0)
        {
            JOptionPane optionPane = new JOptionPane();
            JDialog dialog = optionPane.createDialog(null, "ERROR");
            optionPane.setMessage("The end date cannot be \n before the from date!");
            optionPane.setMessageType(JOptionPane.ERROR_MESSAGE);
            dialog.setAlwaysOnTop(true);
            dialog.setVisible(true);
            datePickerTo.setValue(LocalDate.now());
            datePickerFrom.setValue(LocalDate.now());
        }
        else
        {
            refreshEverything();
        }
    }

    /**
     * getter method for the createTaskButton
     * @return createTaskButton
     */
    public JFXButton getCreateTaskButton()
    {
        return createTaskButton;
    }
    /**
     * getter method for the timerButton
     * @return timerButton
     */
    public JFXButton getTimerButton()
    {
        return timerButton;
    }

    /**
     * getter method for the deleteTaskButton
     * @return deleteTask
     */
    public JFXButton getDeleteTask()
    {
        return deleteTask;
    }

    /**
     * getter method for the editTaskBtn
     * @return editTaskBtn
     */
    public JFXButton getEditTaskBtn()
    {
        return editTaskBtn;
    }

    /**
     * getter method for the HeaderLabel
     * @return HeaderLabel
     */
    public Label getHeaderLabel() {
        return HeaderLabel;
    }
    
    
    
    
    
}


