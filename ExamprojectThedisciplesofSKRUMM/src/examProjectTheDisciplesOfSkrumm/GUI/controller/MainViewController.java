/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package examProjectTheDisciplesOfSkrumm.GUI.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import examProjectTheDisciplesOfSkrumm.BE.Client;
import examProjectTheDisciplesOfSkrumm.BE.Project;
import examProjectTheDisciplesOfSkrumm.BE.Task;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
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
public class MainViewController implements Initializable {

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
    @FXML
    private Label timeLabel;
    @FXML
    private JFXButton EditButton;
    @FXML
    private JFXComboBox<?> intervals;
    @FXML
    private JFXButton deleteButton;
    private AnchorPane anchorPane00;

    private int sec = 0;
    private int min = 0;
    private int hour = 0;
    private boolean running = false;
    private Label timeLabe00;
    private int totalsec = 0;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        /*
        ColumnConstraints halfConstraint = new ColumnConstraints(50);
        taskGrid.getColumnConstraints().addAll(halfConstraint,halfConstraint); 
         */
        
        fillGrid();
       // anchorPane00.setUserData(new Task("title", "projectName", "clientName", 0) );
        

        // anchorPane00.setUserData(new Task("title", "projectName", "clientName", 0) );
    }

    public MainViewController() {
        adminCheck = false;
    }

    public void setAdminCheck(boolean adminCheck) {
        this.adminCheck = adminCheck;
    }

    @FXML
    private void handleChartView(ActionEvent event) throws IOException {
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
    private void handleAdminView(ActionEvent event) throws IOException {
        if (adminCheck == true) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/examProjectTheDisciplesOfSkrumm/GUI/view/AdminView.fxml"));
            Parent root = loader.load();
            AdminViewController controller = loader.getController();

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setMinHeight(423);
            stage.setMinWidth(721);
            stage.setTitle("TimeTracker");
            stage.show();

        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Oops");
            alert.setHeaderText("You do not have permision");
            alert.setContentText("It looks like you are not an admin user");
            alert.showAndWait();
        }
    }

    @FXML
    private void handletaskView(ActionEvent event) throws IOException {
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
    private void handleClientProject(ActionEvent event) throws IOException {
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
        ArrayList<Task> tasks = new ArrayList<>();
        ArrayList<AnchorPane> panes = new ArrayList<>();
        
        panes.add(taskOne);
        panes.add(taskTwo);
        panes.add(taskThree);
        panes.add(taskFour);
        panes.add(taskFive);
        panes.add(taskSix);
        
        Task task1 = new Task("Add information to TableView", new Project("Time Taker", new Client("Grumsen Development")), 0, 0);
        Task task2 = new Task("Drink Pepsi Max", new Project("Time Taker", new Client("Grumsen Development")), 0, 1);
        Task task3 = new Task("Write in report", new Project("Time Taker", new Client("Grumsen Development")), 0, 0);
        
        tasks.add(task1);
        tasks.add(task2);
        tasks.add(task3);
        
        for (Task task : tasks)
        {
            fillGrid2(task.getTitle(), task.getClientName(), "24/04/2020", panes.get(anchorPaneNumber), task.getIsPaid());
            anchorPaneNumber++;
        }
        
        int tasksSize = tasks.size();
        int i = 0;
       
        for (int end = 6-tasksSize; end > 0; end--)
        {
            panes.get(tasksSize + i).getChildren().clear();
            i++;
        }
        
        i = 0;
        anchorPaneNumber = 0;
    }
    
    private void fillGrid2(String task, String client, String date, AnchorPane pane, int isPaid)
    {
        List children = pane.getChildren();
        List<Label> labels = new ArrayList();
        
        Image Paid = new Image("/examProjectTheDisciplesOfSkrumm/GUI/Icons/Paid.png");
        Image NotPaid = new Image("/examProjectTheDisciplesOfSkrumm/GUI/Icons/NotPaid.png"); 
        ImageView imgView;
        
        for (Object child : children)
        {
            if(child instanceof Label)
            {
                Label label = (Label) child;
                
                labels.add(label);
            }
            if(child instanceof ImageView)
            {
                imgView = (ImageView) child;
                
                if(isPaid == 1)
                {
                    imgView.setImage(Paid);
                }
                else if(isPaid == 0)
                {
                    imgView.setImage(NotPaid);
                }
            }  
        }
        
        for (Label label : labels)
        {
            if(label.getText().equals("TASK"))
            {
                label.setText(task);
                label.setMaxWidth(Double.MAX_VALUE);
                pane.setLeftAnchor(label, 0.0);
                pane.setRightAnchor(label, 0.0);
                label.setAlignment(Pos.CENTER);
            }
            
            if(label.getText().equals("Client"))
            {
                label.setText(client);
                label.setMaxWidth(Double.MAX_VALUE);
                pane.setLeftAnchor(label, 0.0);
                pane.setRightAnchor(label, 0.0);
                label.setAlignment(Pos.CENTER);
            }
            
            if(label.getText().equals("Date"))
            {
                label.setText(date);
            }
        }
        
        
    

    

}

    private void handleStartTimer(ActionEvent event) {

        System.out.println(System.getProperty("java.version"));
        System.out.println(System.getProperty("javafx.runtime.version"));
        
        System.out.println("start");

        if (running) {

            running = false;

        } else if (!running) {
            running = true;

        }

        new Thread(()
                -> {
            while (true) {

                if (running) {

                    try {
                        Thread.sleep(1000);

                        sec++;
                        totalsec++;

                        if (sec >= 60) {
                            min++;
                            sec = 0;
                        }

                        if (min >= 60) {
                            hour++;
                            min = 0;
                        }

                        String time = String.format("%02d", hour) + ":" + String.format("%02d", min) + ":" + String.format("%02d", sec);
                        System.out.println(time);
                        System.out.println(totalsec);
                        Platform.runLater(()
                                -> {
                            timeLabel.setText(String.format("%02d", hour) + ":" + String.format("%02d", min) + ":" + String.format("%02d", sec));
                        }
                        );

                    } catch (Exception e) {
                    }

                } else {

                    break;

                }

            }

        }).start();

    }

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

    @FXML
    private void handleStartTimer(MouseEvent event)
    {
    }
}
