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
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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

    ModelFacadeInterface modelfacade;

    DateTimeFormatter formatter;
    @FXML
    private JFXButton AdminBtn;
    private GridPane taskGrid;
    @FXML
    private JFXButton taskBtn;

    private boolean adminCheck;
    @FXML
    private JFXButton clientsProjectBtn;
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

    private int sec = 0;
    private int min = 0;
    private int hour = 0;
    private boolean running = false;
    private int totalsec = 0;

    private Label intervalLabel;
    private Label totaltimelabel;

    //private TimerUtil timer;
    private List<JFXButton> buttons = new ArrayList();
    private List<AnchorPane> panes = new ArrayList<>();
    private List<Label> timeLabels = new ArrayList<>();
    private List<Label> totalTimeLabels = new ArrayList<>();

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
    //ExecutorService executorService;
    //TimerUtil timerutil;
    JFXButton previousbutton = null;

    private LocalTime startTime;
    private LocalTime stopTime;
    private int interval;
    private int totalTime;

    private ObservableList<Task> tasks;
    @FXML
    private Label welcomeLabel;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        try
        {
            /*
            ColumnConstraints halfConstraint = new ColumnConstraints(50);
            taskGrid.getColumnConstraints().addAll(halfConstraint,halfConstraint);
             */
            modelfacade = ModelFacade.getInstance();
        } catch (Exception ex)
        {
            Logger.getLogger(MainViewController.class.getName()).log(Level.SEVERE, null, ex);
        }
        try
        {
            welcomeLabel.setText("Welcome" + " " + modelfacade.getCurrentuser().getFirstName());

            panes.add(taskOne);
            panes.add(taskTwo);
            panes.add(taskThree);
            panes.add(taskFour);
            panes.add(taskFive);
            panes.add(taskSix);

            timeLabels.add(timeLabelOne);
            timeLabels.add(timeLabelTwo);
            timeLabels.add(timeLabelThree);
            timeLabels.add(timeLabelFour);
            timeLabels.add(timeLabelFive);
            timeLabels.add(timeLabelSix);

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

            // anchorPane00.setUserData(new Task("title", "projectName", "clientName", 0) );
        } catch (SQLException ex)
        {
            Logger.getLogger(MainViewController.class.getName()).log(Level.SEVERE, null, ex);
        }

        // anchorPane00.setUserData(new Task("title", "projectName", "clientName", 0) );
        formatter = DateTimeFormatter.ofPattern("dd/MM/YYYY");

    }

    public MainViewController()
    {
        adminCheck = false;
    }

    public void setAdminCheck(boolean adminCheck)
    {
        this.adminCheck = adminCheck;
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
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/examProjectTheDisciplesOfSkrumm/GUI/view/AdminView.fxml"));
            Parent root = loader.load();
            AdminViewController controller = loader.getController();

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setMinHeight(492);
            stage.setMinWidth(804);
            stage.setTitle("Admin");
            stage.show();

        } else
        {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Oops");
            alert.setHeaderText("You do not have permision");
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
        stage.setMinWidth(726);
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

    private void fillGrid() throws SQLException
    {
        int anchorPaneNumber = 0;
        tasks = modelfacade.getSixTasks(modelfacade.getCurrentuser());

        for (Task task : tasks)
        {
            overwriteTasks(panes.get(anchorPaneNumber), task);
            anchorPaneNumber++;

        }

        int tasksSize = tasks.size();
        int i = 0;
        int maxAmountOfTasks = 6;

        for (int amountLeft = maxAmountOfTasks - tasksSize; amountLeft > 0; amountLeft--)
        {
            panes.get(tasksSize + i).getChildren().clear();
            i++;
        }

        i = 0;
        anchorPaneNumber = 0;
    }

    private void overwriteTasks(AnchorPane pane, Task task)
    {
        List children = pane.getChildren();
        List<Label> labels = new ArrayList();
        List<JFXButton> buttonChildren = new ArrayList();
        JFXButton playButton = new JFXButton();

        ImageView Play = new ImageView("/examProjectTheDisciplesOfSkrumm/GUI/Icons/Playbutton.png");
        Play.setFitHeight(24);
        Play.setFitWidth(28);

        Image Paid = new Image("/examProjectTheDisciplesOfSkrumm/GUI/Icons/Paid.png");
        Image NotPaid = new Image("/examProjectTheDisciplesOfSkrumm/GUI/Icons/NotPaid.png");
        ImageView imgView;

        for (Object child : children)
        {
            if (child instanceof Label)
            {
                Label label = (Label) child;

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
        }

        System.out.println(children);

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
                label.setText(task.getLastUsed().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")).toString().substring(0, 10));
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

        ImageView Play = new ImageView("/examProjectTheDisciplesOfSkrumm/GUI/Icons/Playbutton.png");
        ImageView Pause = new ImageView("/examProjectTheDisciplesOfSkrumm/GUI/Icons/PauseBtn.png");

        Play.setScaleX(0.3);
        Play.setScaleY(0.3);
        Pause.setScaleX(0.3);
        Pause.setScaleY(0.3);

        int index = 0;

        JFXButton button = (JFXButton) event.getSource();
//        System.out.println(button.getParent().getId());

        for (AnchorPane pane : panes)
        {
            if (button.getParent().getId() == pane.getId())
            {
                index = panes.indexOf(pane);
                intervalLabel = timeLabels.get(index);
                totaltimelabel = totalTimeLabels.get(index);
            }
        }

        handleStart(intervalLabel, totaltimelabel, button, tasks.get(index).getDuration(), tasks.get(index));

        if (!modelfacade.getisTimerRunning())
        {
            /*
            if(previousbutton != null && !button.equals(previousbutton)){
                System.out.println("difrent button");
                ImageView view = ((ImageView)previousbutton.getChildrenUnmodifiable().get(1));
                view.setImage(new Image("/examProjectTheDisciplesOfSkrumm/GUI/Icons/Playbutton.png"));
            }
             */
            if (modelfacade.getTimerutil().getCurrenttask() != null && !tasks.get(index).equals(modelfacade.getTimerutil().getCurrenttask()))
            {
                System.out.println("difrent button");
                ImageView view = ((ImageView) previousbutton.getChildrenUnmodifiable().get(1));
                view.setImage(new Image("/examProjectTheDisciplesOfSkrumm/GUI/Icons/Playbutton.png"));

                stopTime = LocalTime.now();
                currentTask = modelfacade.getTimerutil().getCurrenttask();
                
                String paid = "";
                String paid2 = "";
                int isPaid = currentTask.getIsPaid();
                
                if(isPaid == 0)
                {
                    paid = "not paid";
                    paid2 = "paid";
                }
                else if(isPaid == 1)
                {
                    paid = "paid";
                    paid2 = "not paid";
                }
                
                int input = JOptionPane.showConfirmDialog(null, "This interval is set as " + paid + "," + "\n" + "would you like to change it to " + paid2 + "?", "New interval",
                        JOptionPane.YES_NO_OPTION, JOptionPane.ERROR_MESSAGE);
                
                if (input == JOptionPane.YES_OPTION)
                {
                    if(isPaid == 0)
                    {
                        isPaid = 1;
                    }
                    else if(isPaid == 1)
                    {
                        isPaid = 0;
                    }
                }

                Interval taskInterval = new Interval(modelfacade.getTimerutil().getStartTime(), stopTime, LocalDate.now(), modelfacade.getTimerutil().getTotalIntervalSec(), currentTask, isPaid);

                System.out.println(taskInterval);

                currentTask.setDuration(modelfacade.getTimerutil().getTotalSec());

                modelfacade.newInterval(taskInterval);

            } else
            {

                //totalTimeLabels.get(index).setText(ultimateLabel.getText());
                button.setGraphic(Play);
                button.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
                button.setContentDisplay(ContentDisplay.CENTER);

                stopTime = LocalTime.now();
                currentTask = tasks.get(index);
                
                String paid = "";
                String paid2 = "";
                
                int isPaid = currentTask.getIsPaid();
                
                if(isPaid == 0)
                {
                    paid = "not paid";
                    paid2 = "paid";
                }
                else if(isPaid == 1)
                {
                    paid = "paid";
                    paid2 = "not paid";
                }
                
                int input = JOptionPane.showConfirmDialog(null, "This interval is set as " + paid + "," + "\n" + "would you like to change it to " + paid2 + "?", "New interval",
                        JOptionPane.YES_NO_OPTION, JOptionPane.ERROR_MESSAGE);

                if (input == JOptionPane.YES_OPTION)
                {
                    if(isPaid == 0)
                    {
                        isPaid = 1;
                    }
                    else if(isPaid == 1)
                    {
                        isPaid = 0;
                    }
                }
                
                Interval taskInterval = new Interval(modelfacade.getTimerutil().getStartTime(), stopTime, LocalDate.now(), modelfacade.getTimerutil().getTotalIntervalSec(), currentTask, isPaid);

                System.out.println(taskInterval);

                currentTask.setDuration(modelfacade.getTimerutil().getTotalSec());

                modelfacade.newInterval(taskInterval);
            }

        } else
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

    private synchronized void handleStart(Label intervalLabel, Label totaltimelabel, JFXButton button, int totalsecfortask, Task currenttask)
    {

        //timerutil = new TimerUtil(label,0);
        //System.out.println(timerutil.getTimeLabel() +"timerlaber +++++++++++++++++");
/*
        System.out.println(System.getProperty("java.version"));
        System.out.println(System.getProperty("javafx.runtime.version"));*/
        //System.out.println("start");
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
        stage.show();

        fillGrid();

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

        int input = JOptionPane.showConfirmDialog(null, "Are you sure you want to permanently delete the task?", "Deleting task",
                JOptionPane.YES_NO_OPTION, JOptionPane.ERROR_MESSAGE);

        if (input == JOptionPane.YES_OPTION)
        {
            modelfacade.deleteTask(thisTask);
            tasks.remove(thisTask);
            fillGrid();
        }
    }
}
