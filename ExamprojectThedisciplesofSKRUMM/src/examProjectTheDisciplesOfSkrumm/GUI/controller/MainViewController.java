/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package examProjectTheDisciplesOfSkrumm.GUI.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import examProjectTheDisciplesOfSkrumm.BE.Client;
import examProjectTheDisciplesOfSkrumm.BE.Interval;
import examProjectTheDisciplesOfSkrumm.BE.Project;
import examProjectTheDisciplesOfSkrumm.BE.Task;
import examProjectTheDisciplesOfSkrumm.GUI.Model.Interface.ModelFacadeInterface;
import examProjectTheDisciplesOfSkrumm.GUI.Model.ModelFacade;
import examProjectTheDisciplesOfSkrumm.BLL.Util.TimerUtil;
import examProjectTheDisciplesOfSkrumm.enums.UserMode;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
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
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javax.swing.JOptionPane;

/**
 * FXML Controller class
 *
 * @author Christina
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
    
    private LocalTime startTime;
    private LocalTime stopTime;
    
    private boolean running;
    
    private ModelFacadeInterface modelfacade;
    private DateTimeFormatter formatter;
    @FXML
    private JFXButton taskBtn;
    @FXML
    private JFXButton clientsProjectBtn;
    @FXML
    private JFXButton AdminBtn;
    @FXML
    private ImageView logoImgView;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        try
        {
            modelfacade = ModelFacade.getInstance();
        } 
        catch (Exception ex)
        {
            Logger.getLogger(MainViewController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        try
        {
            welcomeLabel.setText("Welcome" + " " + modelfacade.getCurrentuser().getFirstName());
            
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

        } 
        catch (SQLException ex)
        {
            Logger.getLogger(MainViewController.class.getName()).log(Level.SEVERE, null, ex);
        }

        formatter = DateTimeFormatter.ofPattern("dd/MM/YYYY");
        
        

    }


    @FXML
    private void handleChartView(ActionEvent event) throws IOException
    {
        Stage mainView = (Stage) ((Node) event.getSource()).getScene().getWindow();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/examProjectTheDisciplesOfSkrumm/GUI/view/ChartView.fxml"));
        Parent root = loader.load();
        ChartViewController controller = loader.getController();

        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.setMinHeight(523);
        stage.setMinWidth(721);
        stage.setTitle("Statistics");
        stage.show();
        mainView.close();

    }

    @FXML
    private void handleAdminView(ActionEvent event) throws IOException
    {
        if (modelfacade.getCurrentuser().getIsAdmin() == true)
        {
            modelfacade.setCurrentUserMode(UserMode.ADMIN);
            
            Stage mainView = (Stage) ((Node) event.getSource()).getScene().getWindow();

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/examProjectTheDisciplesOfSkrumm/GUI/view/AdminMainView.fxml"));
            Parent root = loader.load();
            AdminMainViewController controller = loader.getController();

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setMinHeight(525);
            stage.setMinWidth(726);
            stage.setTitle("Admin");
            stage.show();
            mainView.close();

        } else
        {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Oops");
            alert.setHeaderText("You do not have permission");
            alert.setContentText("It looks like you are not an admin user");
            alert.showAndWait();
        }
    }

    @FXML
    private void handletaskView(ActionEvent event) throws IOException
    {
        Stage mainView = (Stage) ((Node) event.getSource()).getScene().getWindow();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/examProjectTheDisciplesOfSkrumm/GUI/view/TaskView.fxml"));
        Parent root = loader.load();
        TaskViewController controller = loader.getController();

        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.setMinHeight(525);
        stage.setMinWidth(943);
        stage.setTitle("Tasks");
        stage.show();
        mainView.close();

    }

    @FXML
    private void handleClientProject(ActionEvent event) throws IOException
    {
        Stage mainView = (Stage) ((Node) event.getSource()).getScene().getWindow();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/examProjectTheDisciplesOfSkrumm/GUI/view/ClientsAndProjects.fxml"));
        Parent root = loader.load();
        ClientsAndProjectsController controller = loader.getController();

        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.setMinHeight(355);
        stage.setMinWidth(529);
        stage.setTitle("Clients and Projects");
        stage.show();
        mainView.close();
    }

    /**
     * This method gets the 6 latest used tasks and displays them in the mainview. 
     * @throws SQLException 
     */
    private void fillGrid() throws SQLException
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
        
    }

    /**
     * Overwrites the labels in the anchorpane with the data from the task.
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
                } 
                else if (task.getIsPaid() == 0)
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
                                FXMLLoader loader = new FXMLLoader(getClass().getResource("/examProjectTheDisciplesOfSkrumm/GUI/view/EditIntervalView.fxml"));
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
                                
                                
                                comboBox.getItems().setAll(intervals);
//                                
//                                comboBox.getSelectionModel().clearSelection();
//                                comboBox.setValue(null);
                                
                                try
                                {
                                    updateMainView();
                                } 
                                catch (SQLException ex)
                                {
                                    Logger.getLogger(MainViewController.class.getName()).log(Level.SEVERE, null, ex);
                                }
                            }
                            //Stage mainView = (Stage) ((Node) event.getSource()).getScene().getWindow();

                        } catch (IOException ex)
                        {
                            Logger.getLogger(MainViewController.class.getName()).log(Level.SEVERE, null, ex);
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
                label.setText(task.getClientName());
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
                label.setText(modelfacade.convertSecToTimeString(task.getDuration()));
            }
        }
    }

    @FXML
    private void handlePlay(ActionEvent event) throws SQLException
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

        for (AnchorPane pane : panes)
        {
            if (button.getParent().getId() == pane.getId())
            {
                index = panes.indexOf(pane);
                intervalLabel = timeLabels.get(index);
                totaltimelabel = totalTimeLabels.get(index);

                for (Node child : pane.getChildren())
                {
                    if (child instanceof JFXComboBox)
                    {
                        combo = (JFXComboBox) child;
                    }
                }
            }
        }

        handleStart(intervalLabel, totaltimelabel, button, tasks.get(index).getDuration(), tasks.get(index));

        if (!modelfacade.getisTimerRunning())
        {
            if (modelfacade.getTimerutil().getCurrenttask() != null && !tasks.get(index).equals(modelfacade.getTimerutil().getCurrenttask()))
            {
                System.out.println("different button");
                ImageView view = ((ImageView) previousbutton.getChildrenUnmodifiable().get(1));
                view.setImage(new Image("/examProjectTheDisciplesOfSkrumm/GUI/Icons/Playbutton.png"));

                
                stopTime = LocalTime.now();
                currentTask = modelfacade.getTimerutil().getCurrenttask();

                String paid = "";
                String paid2 = "";
                int isPaid = currentTask.getIsPaid();

                if (isPaid == 0)
                {
                    paid = "not paid";
                    paid2 = "paid";
                } else if (isPaid == 1)
                {
                    paid = "paid";
                    paid2 = "not paid";
                }

                int input = JOptionPane.showConfirmDialog(null, "This interval is set as " + paid + "," + "\n" + "would you like to change it to " + paid2 + "?", "New interval",
                        JOptionPane.YES_NO_OPTION, JOptionPane.ERROR_MESSAGE);

                if (input == JOptionPane.YES_OPTION)
                {
                    if (isPaid == 0)
                    {
                        isPaid = 1;
                    } else if (isPaid == 1)
                    {
                        isPaid = 0;
                    }
                }

                Interval taskInterval = new Interval(0, modelfacade.getTimerutil().getStartTime().withNano(0), stopTime.withNano(0), LocalDate.now(), modelfacade.getTimerutil().getTotalIntervalSec(), currentTask, isPaid);

                combo.getItems().add(taskInterval);
                combo.getItems().sort(Comparator
                        .comparing(Interval::getCreationDate)
                        .thenComparing(Interval::getStartTime).reversed());

                System.out.println(taskInterval);

                currentTask.setDuration(modelfacade.getTimerutil().getTotalSec());

                if (currentTask.getIntervals().isEmpty())
                {
                    currentTask.setStartTime(modelfacade.getTimerutil().getStartTime());
                }
                currentTask.setStopTime(stopTime);
                modelfacade.updateTask(currentTask);

                modelfacade.newInterval(taskInterval);

            } 
            else
            {
                button.setGraphic(Play);
                button.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
                button.setContentDisplay(ContentDisplay.CENTER);

                stopTime = LocalTime.now();
                currentTask = tasks.get(index);

                //Making sure paid or unpaid is placed in the right spots in the popup
                String paid = "";
                String paid2 = "";

                int isPaid = currentTask.getIsPaid();

                if (isPaid == 0)
                {
                    paid = "not paid";
                    paid2 = "paid";
                } 
                else if (isPaid == 1)
                {
                    paid = "paid";
                    paid2 = "not paid";
                }

                //Popup to change interval paid/unpaid
                int input = JOptionPane.showConfirmDialog(null, "This interval is set as " + paid + "," + "\n" + "would you like to change it to " + paid2 + "?", "New interval",
                        JOptionPane.YES_NO_OPTION, JOptionPane.ERROR_MESSAGE);

                if (input == JOptionPane.YES_OPTION)
                {
                    if (isPaid == 0)
                    {
                        isPaid = 1;
                    } 
                    else if (isPaid == 1)
                    {
                        isPaid = 0;
                    }
                }
                
                Interval taskInterval = new Interval(0, modelfacade.getTimerutil().getStartTime().withNano(0), stopTime.withNano(0), LocalDate.now(), modelfacade.getTimerutil().getTotalIntervalSec(), currentTask, isPaid);
                
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

        } 
        else
        {
            button.setGraphic(Pause);
            button.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
            button.setContentDisplay(ContentDisplay.CENTER);
            startTime = LocalTime.now();
            System.out.println(startTime);
        }
        
        previousbutton = button;
    }

    @FXML
    private void handleLogOut(ActionEvent event) throws IOException
    {
         Stage mainView = (Stage) ((Node) event.getSource()).getScene().getWindow();
        
        if(modelfacade.getCurrentUserMode() == UserMode.ADMIN)
        {
         mainView.close();
        } else{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/examProjectTheDisciplesOfSkrumm/GUI/view/LoginView.fxml"));
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
    }

    private synchronized void handleStart(Label intervalLabel, Label totaltimelabel, JFXButton button, int totalsecfortask, Task currenttask)
    {
        if (modelfacade.getisTimerRunning())
        {
            modelfacade.setIsTimerRunning(false);
            running = false;
            modelfacade.getTimerutil().setIsRunning(false);
            modelfacade.getExecutorService().shutdownNow();
            System.err.println("stopped");
        } 
        else if (!modelfacade.getisTimerRunning())
        {
            System.out.println("not running");
            running = true;
            modelfacade.setIsTimerRunning(true);
            modelfacade.setTimerutil(new TimerUtil(intervalLabel, totaltimelabel, totalsecfortask, currenttask, LocalTime.now()));
            modelfacade.setExecutorService(Executors.newFixedThreadPool(1));
            modelfacade.getExecutorService().submit(modelfacade.getTimerutil());

        }
    }

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

    @FXML
    private void handleEditTask(ActionEvent event) throws IOException, SQLException
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

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/examProjectTheDisciplesOfSkrumm/GUI/view/EditTaskView.fxml"));
        Parent root = loader.load();
        EditTaskController controller = loader.getController();
        controller.setEditTask(thisTask);
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.setTitle("Edit Task");
        stage.showAndWait();

        updateMainView();
    }

    @FXML
    private void handleDeleteTask(ActionEvent event) throws SQLException
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
            modelfacade.deleteTask(thisTask);
            tasks.remove(thisTask);
            
            updateMainView();
        }
    }
    
    /**
     * Method to update the MainView.
     * It first resets all the labels, and then overwrites them with new data.
     * @throws SQLException 
     */
    private void updateMainView() throws SQLException
    {
        List<Label> mainViewLabels = new ArrayList<>();
        
        for (AnchorPane pane : panes)
        {
            List children = pane.getChildren();
            
            for (Object child : children)
            {
                if(child instanceof Label)
                {
                    Label label = (Label) child;
                    
                    mainViewLabels.add(label);
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
}
