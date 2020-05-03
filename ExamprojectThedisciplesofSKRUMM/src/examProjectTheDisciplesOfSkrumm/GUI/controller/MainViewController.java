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
import java.time.LocalDate;
import java.time.LocalDateTime;
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

    private Label ultimateLabel;


    private TimerUtil timer;
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
    ExecutorService executorService;
    TimerUtil timerutil;
    JFXButton previousbutton = null;
    
    LocalDateTime startTime;
    LocalDateTime stopTime;
    int interval;
    int totalTime;
    
    ObservableList<Task> tasks;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            /*
            ColumnConstraints halfConstraint = new ColumnConstraints(50);
            taskGrid.getColumnConstraints().addAll(halfConstraint,halfConstraint);
            */
            modelfacade = ModelFacade.getInstance();
        } catch (Exception ex) {
            Logger.getLogger(MainViewController.class.getName()).log(Level.SEVERE, null, ex);
        }
        fillGrid();
        // anchorPane00.setUserData(new Task("title", "projectName", "clientName", 0) );

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
        stage.setMinHeight(423);
        stage.setMinWidth(721);
        stage.setTitle("TimeTracker");
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
            stage.setMinHeight(423);
            stage.setMinWidth(721);
            stage.setTitle("TimeTracker");
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
        stage.setMinHeight(423);
        stage.setMinWidth(721);
        stage.setTitle("TimeTracker");
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
        stage.setMinHeight(423);
        stage.setMinWidth(355);
        stage.setTitle("TimeTracker");
        stage.show();
        mainView.close();
    }

    private void fillGrid()
    {
        int anchorPaneNumber = 0;
        tasks = modelfacade.getTasks();
        
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
        
        for (Task task : tasks)
        {
            overwriteTasks(task.getTitle(), task.getClientName(), task.getLastUsed().toString().substring(0, 10), panes.get(anchorPaneNumber), task.getIsPaid());
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

    private void overwriteTasks(String task, String client, String date, AnchorPane pane, int isPaid)
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

                if (isPaid == 1)
                {
                    imgView.setImage(Paid);
                } else if (isPaid == 0)
                {
                    imgView.setImage(NotPaid);
                }
            }

            if (child instanceof JFXButton)
            {
                JFXButton button = (JFXButton) child;
                buttons.add(button);
                
                if(button.getText().equals("Play"))
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
                label.setText(task);
                label.setMaxWidth(Double.MAX_VALUE);
                pane.setLeftAnchor(label, 0.0);
                pane.setRightAnchor(label, 0.0);
                label.setAlignment(Pos.CENTER);
            }

            if (label.getText().equals("Client"))
            {
                label.setText(client);
                label.setMaxWidth(Double.MAX_VALUE);
                pane.setLeftAnchor(label, 0.0);
                pane.setRightAnchor(label, 0.0);
                label.setAlignment(Pos.CENTER);
            }

            if (label.getText().equals("Date"))
            {
                label.setText(date);
            }
        }
    }

    @FXML
    private void handlePlay(ActionEvent event)
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
                ultimateLabel = timeLabels.get(index);
            }
        }
        
        handleStart(ultimateLabel, button,0);
        
        if (!running)
        {
            if(previousbutton != null && !button.equals(previousbutton)){
                System.out.println("difrent button");
                ImageView view = ((ImageView)previousbutton.getChildrenUnmodifiable().get(1));
                view.setImage(new Image("/examProjectTheDisciplesOfSkrumm/GUI/Icons/Playbutton.png"));
            }
            
            totalTimeLabels.get(index).setText(ultimateLabel.getText());
            button.setGraphic(Play);
            button.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
            button.setContentDisplay(ContentDisplay.CENTER);
            startTime = LocalDateTime.now();
            
        }
        
        else
        {
            button.setGraphic(Pause);
            button.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
            button.setContentDisplay(ContentDisplay.CENTER);
            stopTime = LocalDateTime.now();
            currentTask = tasks.get(index);
            Interval interval = new Interval(startTime, stopTime, timerutil.getTotalsec(), currentTask);
            modelfacade.newInterval(interval);
            
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
        stage.setTitle("TimeTracker");
        stage.show();
        mainView.close();
    }

    private synchronized void handleStart(Label label, JFXButton button, int totalsecfortask)
    {
       
        
        //timerutil = new TimerUtil(label,0);
        //System.out.println(timerutil.getTimeLabel() +"timerlaber +++++++++++++++++");
/*
        System.out.println(System.getProperty("java.version"));
        System.out.println(System.getProperty("javafx.runtime.version"));*/

        //System.out.println("start");

        if (running)
        {
            running = false;
            timerutil.setIsRunning(false);
            executorService.shutdownNow();
            System.err.println("stopped");
        } 
        
        else if (!running)
        {
            System.out.println("not running");
            running = true;
            timerutil = new TimerUtil(label,totalsecfortask);
            executorService = Executors.newFixedThreadPool(1);
            executorService.submit(timerutil);
            
        }
    }
    
    
}
