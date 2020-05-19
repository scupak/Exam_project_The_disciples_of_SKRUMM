
package examProjectTheDisciplesOfSkrumm.GUI.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import examProjectTheDisciplesOfSkrumm.BE.Interval;
import examProjectTheDisciplesOfSkrumm.BE.Task;
import examProjectTheDisciplesOfSkrumm.GUI.Model.Interface.ModelFacadeInterface;
import examProjectTheDisciplesOfSkrumm.GUI.Model.ModelFacade;
import examProjectTheDisciplesOfSkrumm.BLL.Util.TimerUtil;
import examProjectTheDisciplesOfSkrumm.enums.UserMode;
import examProjectTheDisciplesOfSkrumm.enums.ViewTypes;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javax.swing.JOptionPane;

/**
 * FXML Controller class
 *
 * @author Skrumm
 */
public class MainViewController implements Initializable
{

    @FXML
    private Label welcomeLabel;

    //AnchorPanes inside the GridPane
    @FXML
    private AnchorPane taskOne;
    @FXML
    private AnchorPane taskThree;
    @FXML
    private AnchorPane taskFive;
    @FXML
    private AnchorPane taskFour;
    @FXML
    private AnchorPane taskSix;
    @FXML
    private AnchorPane taskTwo;

    //Interval counter labels
    @FXML
    private Label timeLabelOne;
    @FXML
    private Label timeLabelTwo;
    @FXML
    private Label timeLabelThree;
    @FXML
    private Label timeLabelFour;
    @FXML
    private Label timeLabelFive;
    @FXML
    private Label timeLabelSix;

    //Total time counter labels
    @FXML
    private Label totalTimeOne;
    @FXML
    private Label totalTimeTwo;
    @FXML
    private Label totalTimeThree;
    @FXML
    private Label totalTimeFour;
    @FXML
    private Label totalTimeFive;
    @FXML
    private Label totalTimeSix;

    private List<JFXButton> buttons = new ArrayList();
    private List<AnchorPane> panes = new ArrayList<>();
    private List<Label> timeLabels = new ArrayList<>();
    private List<Label> totalTimeLabels = new ArrayList<>();
    private List<Label> labels;
    private List<String> labelNames = new ArrayList<>();
    private ObservableList<Task> tasks;

    private Label intervalLabel;
    private Label totaltimelabel;
    private JFXButton previousbutton = null;

    private LocalDateTime startTime;
    private LocalDateTime stopTime;

    private boolean running;

    private ModelFacadeInterface modelfacade;
    private DateTimeFormatter formatter;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        try
        {
            modelfacade = ModelFacade.getInstance();
        } catch (Exception ex)
        {
            Logger.getLogger(MainViewController.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Failed to get an intance of modelfacade" + ex,"ERROR!", JOptionPane.ERROR_MESSAGE); 
        }

        welcomeLabel.setText("Welcome" + " " + modelfacade.getCurrentuser().getFirstName());
        welcomeLabel.setMaxWidth(Double.MAX_VALUE);
        welcomeLabel.setAlignment(Pos.CENTER);
        //Adding the anchorpanes to the panes list
        panes.add(taskOne);
        panes.add(taskTwo);
        panes.add(taskThree);
        panes.add(taskFour);
        panes.add(taskFive);
        panes.add(taskSix);
        //Adding the interval time labels to the timeLabels list
        timeLabels.add(timeLabelOne);
        timeLabels.add(timeLabelTwo);
        timeLabels.add(timeLabelThree);
        timeLabels.add(timeLabelFour);
        timeLabels.add(timeLabelFive);
        timeLabels.add(timeLabelSix);
        //Adding the total time labels to the totalTimeLabels list
        totalTimeLabels.add(totalTimeOne);
        totalTimeLabels.add(totalTimeTwo);
        totalTimeLabels.add(totalTimeThree);
        totalTimeLabels.add(totalTimeFour);
        totalTimeLabels.add(totalTimeFive);
        totalTimeLabels.add(totalTimeSix);
        fillGrid();
        if (modelfacade.getisTimerRunning())
        {
            
            handleTimerUtilIsRunning();
             
        }

        formatter = DateTimeFormatter.ofPattern("dd/MM/YYYY");

    }

    /**
     * The event handler that activates the ChartView.
     * @param event
     * @throws IOException 
     */
    @FXML
    private void handleChartView(ActionEvent event)
    {
        try
        {
            Stage mainView = (Stage) ((Node) event.getSource()).getScene().getWindow();
            
            FXMLLoader loader = modelfacade.getLoader(ViewTypes.CHART);
            Parent root = loader.load();
            ChartViewController controller = loader.getController();
            
            // controller.getNameLabel().setText(modelfacade.getCurrentuser().getEmail() + " " + "Hours");
            
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setMinHeight(523);
            stage.setMinWidth(721);
            stage.setTitle("Statistics");
            stage.show();
            mainView.close();
        } catch (IOException ex)
        {
            Logger.getLogger(MainViewController.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Wrong input" + ex,"ERROR!", JOptionPane.ERROR_MESSAGE); 
        } catch (Exception ex)
        {
            Logger.getLogger(MainViewController.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Wrong view type" + ex,"ERROR!", JOptionPane.ERROR_MESSAGE); 
        }

    }

    /**
     * The event handler that activates the AdminView. 
     * @param event
     * @throws IOException 
     */
    @FXML
    private void handleAdminView(ActionEvent event) 
    {
        if (modelfacade.getCurrentuser().getIsAdmin() == true)
        {
            
            try 
            {
                modelfacade.setCurrentUserMode(UserMode.ADMIN);
                
                Stage mainView = (Stage) ((Node) event.getSource()).getScene().getWindow();
                
                FXMLLoader loader = modelfacade.getLoader(ViewTypes.ADMINMAIN);
                Parent root = loader.load();
                AdminMainViewController controller = loader.getController();
                
                Stage stage = new Stage();
                stage.setScene(new Scene(root));
                stage.setMinHeight(525);
                stage.setMinWidth(726);
                stage.setTitle("Admin");
                stage.show();
                mainView.close();
            } 
            catch (IOException ex)
            {
                Logger.getLogger(MainViewController.class.getName()).log(Level.SEVERE, null, ex);
                JOptionPane.showMessageDialog(null, "Wrong input" + ex,"ERROR!", JOptionPane.ERROR_MESSAGE); 
            } 
            catch (Exception ex)
            {
                Logger.getLogger(MainViewController.class.getName()).log(Level.SEVERE, null, ex);
                JOptionPane.showMessageDialog(null, "Given wrong view type" + ex,"ERROR!", JOptionPane.ERROR_MESSAGE); 
            }
            

        } else
        {
            JOptionPane.showMessageDialog(null, "Looks like you dont have admin priviliges","Acces denied!", JOptionPane.INFORMATION_MESSAGE); 
        }
    }

    /**
     * The event handler that activates the TaskView. 
     * @param event
     * @throws IOException 
     */
    @FXML
    private void handletaskView(ActionEvent event)
    {
        try
        {
            Stage mainView = (Stage) ((Node) event.getSource()).getScene().getWindow();
            
            FXMLLoader loader = modelfacade.getLoader(ViewTypes.TASK);
            Parent root = loader.load();
            TaskViewController controller = loader.getController();
            
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setMinHeight(525);
            stage.setMinWidth(943);
            stage.setTitle("Tasks");
            stage.show();
            mainView.close();
        } catch (IOException ex)
        {
            Logger.getLogger(MainViewController.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Wrong input" + ex,"ERROR!", JOptionPane.ERROR_MESSAGE); 
        } catch (Exception ex)
        {
            Logger.getLogger(MainViewController.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Given wrong type of view" + ex,"ERROR!", JOptionPane.ERROR_MESSAGE); 
        }

    }
    

    /**
     * The event handler that activates the ClientAndProjectView. 
     * @param event
     * @throws IOException 
     */
    @FXML
    private void handleClientProject(ActionEvent event)
    {
        try
        {
            Stage mainView = (Stage) ((Node) event.getSource()).getScene().getWindow();
            
            FXMLLoader loader = modelfacade.getLoader(ViewTypes.CLIENTSANDPROJECTS);
            Parent root = loader.load();
            ClientsAndProjectsController controller = loader.getController();
            
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setMinHeight(355);
            stage.setMinWidth(529);
            stage.setTitle("Clients and Projects");
            stage.show();
            mainView.close();
        } catch (IOException ex)
        {
            Logger.getLogger(MainViewController.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Wrong input" + ex,"ERROR!", JOptionPane.ERROR_MESSAGE); 
        } catch (Exception ex)
        {
            Logger.getLogger(MainViewController.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Given wrong type of view" + ex,"ERROR!", JOptionPane.ERROR_MESSAGE); 
        }
    }

    /**
     * This method gets the 6 latest used tasks and displays them in the
     * mainview.
     *
     * @throws SQLException
     */
    private void fillGrid()
    {
        try
        {
            int anchorPaneNumber = 0;
            tasks = modelfacade.getSixTasks(modelfacade.getCurrentuser());
            labelNames = new ArrayList<String>();
            
            for (Task task : tasks)
            {
                overwriteTasks(panes.get(anchorPaneNumber), task);
                anchorPaneNumber++;
            }
            
            int i = 0;
            int maxAmountOfTasks = 6;
            
            //Letting the unused panes be cleared
            for (int amountLeft = maxAmountOfTasks - tasks.size(); amountLeft > 0; amountLeft--)
            {
                panes.get(tasks.size() + i).getChildren().clear();
                i++;
            }
            
            i = 0;
            anchorPaneNumber = 0;
        } catch (SQLException ex)
        {
            Logger.getLogger(MainViewController.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Problem with the database" + ex,"ERROR!", JOptionPane.ERROR_MESSAGE); 
        }

    }

    /**
     * Overwrites the labels in the anchorpane with the data from the task.
     *
     * @param pane
     * @param task
     */
    private void overwriteTasks(AnchorPane pane, Task task)
    {
        labels = new ArrayList<>();

        //Getting and sorting the task's intervals
        ObservableList<Interval> intervals = FXCollections.observableArrayList();
        intervals.setAll(task.getIntervals());

        Comparator<Interval> byDate = Comparator
                .comparing(Interval::getCreationDate)
                .thenComparing(Interval::getStartTime).reversed();

        intervals.sort(byDate);

        //Icon for the play/pause button when the program is first started
        ImageView Play = new ImageView("/examProjectTheDisciplesOfSkrumm/GUI/Icons/Playbutton.png");
        Play.setFitHeight(24);
        Play.setFitWidth(28);

        //Icons for if the task is paid or not
        Image Paid = new Image("/examProjectTheDisciplesOfSkrumm/GUI/Icons/Paid.png");
        Image NotPaid = new Image("/examProjectTheDisciplesOfSkrumm/GUI/Icons/NotPaid.png");
        ImageView imgView;

        List children = pane.getChildren();

        for (Object child : children)
        {
            if (child instanceof Label)
            {
                Label label = (Label) child;

                labelNames.add(label.getText());
                labels.add(label);
            }

            if (child instanceof ImageView)
            {
                imgView = (ImageView) child;

                if (task.getIsPaid() == 1)
                {
                    imgView.setImage(Paid);
                } else if (task.getIsPaid() == 0)
                {
                    imgView.setImage(NotPaid);
                }
            }

            if (child instanceof JFXButton)
            {
                JFXButton button = (JFXButton) child;

                buttons.add(button);

                if (button.getText().equals("Play"))
                {
                    button.setGraphic(Play);
                    button.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
                    button.setContentDisplay(ContentDisplay.CENTER);
                }
            }

            if (child instanceof JFXComboBox)
            {
                JFXComboBox<Interval> comboBox = (JFXComboBox) child;
                comboBox.getItems().setAll(intervals);

                comboBox.setOnAction(new EventHandler<ActionEvent>()
                {
                    @Override
                    public void handle(ActionEvent event)
                    {
                        try
                        {
                            Interval interval = comboBox.getSelectionModel().getSelectedItem();

                            if (interval != null)
                            {
                                FXMLLoader loader = modelfacade.getLoader(ViewTypes.EDITINTERVAL);
                                Parent root = loader.load();
                                EditIntervalViewController controller = loader.getController();
                                controller.fillView(interval);

                                Stage stage = new Stage();
                                stage.setScene(new Scene(root));
                                stage.setMinHeight(260);
                                stage.setMinWidth(318);
                                stage.setTitle("Edit Interval");
                                stage.setAlwaysOnTop(true);
                                stage.showAndWait();

                                updateMainView();
                            }
                            //Stage mainView = (Stage) ((Node) event.getSource()).getScene().getWindow();

                        } 
                        catch (IOException ex)
                        {
                            Logger.getLogger(MainViewController.class.getName()).log(Level.SEVERE, null, ex);
                            JOptionPane.showMessageDialog(null, "Wrong input" + ex,"ERROR!", JOptionPane.ERROR_MESSAGE); 
                        } 
                        catch (Exception ex)
                        {
                            Logger.getLogger(MainViewController.class.getName()).log(Level.SEVERE, null, ex);
                            JOptionPane.showMessageDialog(null, "Given wrong type of view" + ex,"ERROR!", JOptionPane.ERROR_MESSAGE); 
                        }

                    }

                });

            }

        }

        //Getting and setting the different labels to the right information
        for (Label label : labels)
        {
            if (label.getText().equals("TASK"))
            {
                label.setText(task.getTitle());
                label.setMaxWidth(Double.MAX_VALUE);
                pane.setLeftAnchor(label, 0.0);
                pane.setRightAnchor(label, 0.0);
                label.setAlignment(Pos.CENTER);
            }

            if (label.getText().equals("Client"))
            {
                label.setText(task.getProjectName() + "  |  " + task.getClientName());
                label.setMaxWidth(Double.MAX_VALUE);
                pane.setLeftAnchor(label, 0.0);
                pane.setRightAnchor(label, 0.0);
                label.setAlignment(Pos.CENTER);
            }

            if (label.getText().equals("Date"))
            {
                label.setText(task.getCreationDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")).substring(0, 10));
                label.setMaxWidth(Double.MAX_VALUE);
                pane.setLeftAnchor(label, 0.0);
                pane.setRightAnchor(label, 0.0);
                label.setAlignment(Pos.CENTER);
            }

            if (label.getText().equals("TotalTime"))
            {
                if (task.getDuration() <= 0)
                {
                    label.setText("00:00:00");
                } else
                {
                    label.setText(modelfacade.convertSecToTimeString(task.getDuration()));
                }
            }
        }
    }
    
    /**
     * The eventhandler that that performs all the necessary preprocessing before calling the handleStart() method to start the timer. 
     * @param event
     * @throws SQLException 
     */
    @FXML
    private void handlePlay(ActionEvent event)
    {
        Task currentTask;
        int index = 0;

        ImageView Play = new ImageView("/examProjectTheDisciplesOfSkrumm/GUI/Icons/Playbutton.png");
        ImageView Pause = new ImageView("/examProjectTheDisciplesOfSkrumm/GUI/Icons/PauseBtn.png");
        Play.setScaleX(0.3);
        Play.setScaleY(0.3);
        Pause.setScaleX(0.3);
        Pause.setScaleY(0.3);

        JFXComboBox<Interval> combo = new JFXComboBox();
        JFXButton button = (JFXButton) event.getSource();

        //find the correct AnchorPane to start the timer on
        for (AnchorPane pane : panes)
        {
            if (button.getParent().getId() == pane.getId())
            {
                index = panes.indexOf(pane);
                intervalLabel = timeLabels.get(index);
                totaltimelabel = totalTimeLabels.get(index);

                //find the corret JFXComboBox
                for (Node child : pane.getChildren())
                {
                    if (child instanceof JFXComboBox)
                    {
                        combo = (JFXComboBox) child;
                    }
                }
            }
        }

        //set the duration on a task to 0 if it is negative.
        if (tasks.get(index).getDuration() <= 0)
        {
            handleStart(intervalLabel, totaltimelabel, button, 0, tasks.get(index));
        } else
        {
            handleStart(intervalLabel, totaltimelabel, button, tasks.get(index).getDuration(), tasks.get(index));
        }

        
        if (!modelfacade.getisTimerRunning())
        {
            //if the timer is running and the user presses the play button on a different task, set the other play buttons image to Playbutton.png
            if (modelfacade.getTimerutil().getCurrenttask() != null && !tasks.get(index).equals(modelfacade.getTimerutil().getCurrenttask()))
            {
                try 
                {
                    System.out.println("different button");
                    ImageView view = ((ImageView) previousbutton.getChildrenUnmodifiable().get(1));
                    view.setImage(new Image("/examProjectTheDisciplesOfSkrumm/GUI/Icons/Playbutton.png"));
                    
                    stopTime = LocalDateTime.now();
                    currentTask = modelfacade.getTimerutil().getCurrenttask();
                    
                    Interval taskInterval = new Interval(0, modelfacade.getTimerutil().getStartTime().withNano(0), stopTime.withNano(0), LocalDate.now(), modelfacade.getTimerutil().getTotalIntervalSec(), currentTask, currentTask.getIsPaid());
                    
                    combo.getItems().add(taskInterval);
                    combo.getItems().sort(Comparator
                            .comparing(Interval::getCreationDate)
                            .thenComparing(Interval::getStartTime).reversed());
                    
                    System.out.println(taskInterval);
                    
                    currentTask.setDuration(modelfacade.getTimerutil().getTotalSec());
                    
                    //if the currentTask has no intervals set its starttime to the first intervals starttime.
                    if (currentTask.getIntervals().isEmpty())
                    {
                        currentTask.setStartTime(modelfacade.getTimerutil().getStartTime());
                    }
                    currentTask.setStopTime(stopTime);
                    modelfacade.updateTask(currentTask);
                    
                    modelfacade.newInterval(taskInterval);
                } 
                catch (SQLException ex) 
                {
                    Logger.getLogger(MainViewController.class.getName()).log(Level.SEVERE, null, ex);
                    JOptionPane.showMessageDialog(null, "Problem with the database" + ex,"ERROR!", JOptionPane.ERROR_MESSAGE); 
                }

            } else
            {
                try
                {
                    button.setGraphic(Play);
                    button.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
                    button.setContentDisplay(ContentDisplay.CENTER);
                    
                    stopTime = LocalDateTime.now();
                    currentTask = tasks.get(index);
                    
                    Interval taskInterval = new Interval(0, modelfacade.getTimerutil().getStartTime().withNano(0), stopTime.withNano(0), LocalDate.now(), modelfacade.getTimerutil().getTotalIntervalSec(), currentTask, currentTask.getIsPaid());
                    
                    //Sorting the combobox list after both creationfate and starttime
                    combo.getItems().add(taskInterval);
                    combo.getItems().sort(Comparator
                            .comparing(Interval::getCreationDate)
                            .thenComparing(Interval::getStartTime).reversed());
                    
                    currentTask.setDuration(modelfacade.getTimerutil().getTotalSec());
                    
                    if (currentTask.getIntervals().isEmpty())
                    {
                        currentTask.setStartTime(modelfacade.getTimerutil().getStartTime());
                    }
                    currentTask.setStopTime(stopTime);
                    
                    modelfacade.updateTask(currentTask);
                    modelfacade.newInterval(taskInterval);
                } 
                catch (SQLException ex)
                {
                    Logger.getLogger(MainViewController.class.getName()).log(Level.SEVERE, null, ex);
                    JOptionPane.showMessageDialog(null, "Problem with the database" + ex,"ERROR!", JOptionPane.ERROR_MESSAGE); 
                }
            }

        } else
        {
            button.setGraphic(Pause);
            button.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
            button.setContentDisplay(ContentDisplay.CENTER);
            startTime = LocalDateTime.now();
            System.out.println(startTime);
        }

        previousbutton = button;
    }

    /**
     * The eventhandler that activates the login view. 
     * @param event
     * @throws IOException 
     */
    @FXML
    private void handleLogOut(ActionEvent event)
    {
        Stage mainView = (Stage) ((Node) event.getSource()).getScene().getWindow();

        if (modelfacade.getCurrentUserMode() == UserMode.ADMIN)
        {
            mainView.close();
        } else
        {
            try
            {
                FXMLLoader loader = modelfacade.getLoader(ViewTypes.LOGIN);
                Parent root = loader.load();
                LoginViewController controller = loader.getController();
                
                Stage stage = new Stage();
                stage.setScene(new Scene(root));
                stage.setMinHeight(200);
                stage.setMinWidth(300);
                stage.setTitle("Login");
                stage.show();
                mainView.close();
            } 
            catch (IOException ex)
            {
                Logger.getLogger(MainViewController.class.getName()).log(Level.SEVERE, null, ex);
                JOptionPane.showMessageDialog(null, "Wrong input" + ex,"ERROR!", JOptionPane.ERROR_MESSAGE); 
            } 
            catch (Exception ex)
            {
                Logger.getLogger(MainViewController.class.getName()).log(Level.SEVERE, null, ex);
                JOptionPane.showMessageDialog(null, "Given wrong view type" + ex,"ERROR!", JOptionPane.ERROR_MESSAGE); 
            }
        }
    }

    /**
     * the method that start the timer.
     * @param intervalLabel
     * @param totaltimelabel
     * @param button
     * @param totalsecfortask
     * @param currenttask 
     */
    private synchronized void handleStart(Label intervalLabel, Label totaltimelabel, JFXButton button, int totalsecfortask, Task currenttask)
    {
        if (modelfacade.getisTimerRunning())
        {
            modelfacade.setIsTimerRunning(false);
            running = false;
            modelfacade.getTimerutil().setIsRunning(false);
            modelfacade.getExecutorService().shutdownNow();
            System.err.println("stopped");
        } else if (!modelfacade.getisTimerRunning())
        {
            System.out.println("not running");
            running = true;
            modelfacade.setIsTimerRunning(true);
            modelfacade.setTimerutil(new TimerUtil(intervalLabel, totaltimelabel, totalsecfortask, currenttask, LocalDateTime.now()));
            modelfacade.setExecutorService(Executors.newFixedThreadPool(1));
            modelfacade.getExecutorService().submit(modelfacade.getTimerutil());

        }
    }

    /**
     * the method that handles the situation when the timer is already running when this view gets activated. 
     */
    private void handleTimerUtilIsRunning()
    {
        ImageView Pause = new ImageView("/examProjectTheDisciplesOfSkrumm/GUI/Icons/PauseBtn.png");
        Pause.setScaleX(0.3);
        Pause.setScaleY(0.3);

        //find the index for the task that is beaing run by the timerutil
        int taskindex = 0;
        AnchorPane currentpane;

        System.err.println(modelfacade.getTimerutil().getCurrenttask());

        taskindex = tasks.indexOf(modelfacade.getTimerutil().getCurrenttask());

        //check if the running task is one of the tasks in the main view
        if (taskindex != -1)
        {

            System.err.println(tasks.indexOf(modelfacade.getTimerutil().getCurrenttask()) + "  " + " task index");

            //set the current AnchorPane to the one the task is on
            currentpane = panes.get(taskindex);

            //set the current play/pause button
            for (Object child : currentpane.getChildren())
            {

                if (child instanceof JFXButton)
                {
                    JFXButton button = (JFXButton) child;

                    if (button.getText().equals("Play"))
                    {
                        button.setGraphic(Pause);
                        button.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
                        button.setContentDisplay(ContentDisplay.CENTER);
                        previousbutton = button;
                    }
                }
            }

            //set current interval label 
            Label currentIntervalLabel;
            currentIntervalLabel = timeLabels.get(taskindex);
            modelfacade.getTimerutil().setIntervalLabel(currentIntervalLabel);

            //set current totaltime label 
            Label currenttotaltimelabel;
            currenttotaltimelabel = totalTimeLabels.get(taskindex);
            modelfacade.getTimerutil().setTotalTimeLabel(currenttotaltimelabel);

        }

    }

    /**
     * The eventhandler that activates the EditTaskview.
     * @param event
     * @throws IOException
     * @throws SQLException 
     */
    @FXML
    private void handleEditTask(ActionEvent event)
    {
        try
        {
            JFXButton button = (JFXButton) event.getSource();
            int index = 0;
            Task thisTask;
            
            for (AnchorPane pane : panes)
            {
                if (button.getParent().getId() == pane.getId())
                {
                    index = panes.indexOf(pane);
                }
            }
            
            thisTask = tasks.get(index);
            
            FXMLLoader loader = modelfacade.getLoader(ViewTypes.EDITTASK);
            Parent root = loader.load();
            EditTaskController controller = loader.getController();
            controller.setEditTask(thisTask);
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Edit Task");
            stage.showAndWait();
            
            updateMainView();
        }
        catch (IOException ex)
        {
            Logger.getLogger(MainViewController.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Wrong input" + ex,"ERROR!", JOptionPane.ERROR_MESSAGE); 
        } 
        catch (Exception ex)
        {
            Logger.getLogger(MainViewController.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Given wrong view type" + ex,"ERROR!", JOptionPane.ERROR_MESSAGE); 
        }
    }

    /**
     * The eventhandler that deletes a task.
     * @param event
     * @throws SQLException 
     */
    @FXML
    private void handleDeleteTask(ActionEvent event)
    {
        JFXButton button = (JFXButton) event.getSource();
        int index = 0;
        Task thisTask;

        for (AnchorPane pane : panes)
        {
            if (button.getParent().getId() == pane.getId())
            {
                index = panes.indexOf(pane);
            }
        }

        thisTask = tasks.get(index);

        //Popup when trying to delete a task
        int input = JOptionPane.showConfirmDialog(null, "Are you sure you want to permanently delete the task?", "Deleting task",
                JOptionPane.YES_NO_OPTION, JOptionPane.ERROR_MESSAGE);

        if (input == JOptionPane.YES_OPTION)
        {
            try 
            {
                modelfacade.deleteTask(thisTask);
                tasks.remove(thisTask);
                
                updateMainView();
            }
            catch (SQLException ex) 
            {
                Logger.getLogger(MainViewController.class.getName()).log(Level.SEVERE, null, ex);
                JOptionPane.showMessageDialog(null, "Problem with the database" + ex,"ERROR!", JOptionPane.ERROR_MESSAGE); 
            }
        }
    }

    /**
     * Method to update the MainView. It first resets all the labels, and then
     * overwrites them with new data.
     *
     * @throws SQLException
     */
    private void updateMainView()
    {
        List<Label> mainViewLabels = new ArrayList<>();
        ExecutorService executor = Executors.newSingleThreadExecutor();

        executor.submit(() ->
        {
            Platform.runLater(new Runnable()
            {
                @Override
                public void run()
                {
                    for (AnchorPane pane : panes)
                    {
                        List children = pane.getChildren();

                        for (Object child : children)
                        {
                            if (child instanceof Label)
                            {
                                Label label = (Label) child;

                                mainViewLabels.add(label);
                            }

                            if (child instanceof JFXComboBox)
                            {
                                JFXComboBox box = (JFXComboBox) child;
                                box.getSelectionModel().clearSelection();
                                box.setValue(null);
                                box.getItems().removeAll();
                            }
                        }
                    }

                    for (Label mainViewLabel : mainViewLabels)
                    {
                        int index = mainViewLabels.indexOf(mainViewLabel);
                        mainViewLabel.setText(labelNames.get(index));
                    }

                    fillGrid();
                }
            });
        });

        try
        {
            System.out.println("Attempting to shut down executor");
            executor.shutdown();
            executor.awaitTermination(5, TimeUnit.SECONDS);
        } 
        catch(InterruptedException ex)
        {
            System.out.println("Thread got interupted");
            JOptionPane.showMessageDialog(null, "Thread got interupted" + ex,"ERROR!", JOptionPane.ERROR_MESSAGE); 
        }
        finally
        {
            if(!executor.isShutdown())
            {
                executor.shutdownNow();
                System.out.println("Shut down complete");
            }
        }
    }
}
