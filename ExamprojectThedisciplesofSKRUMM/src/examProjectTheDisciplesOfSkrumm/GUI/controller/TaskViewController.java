/* Got help and inspiration for this class from 
Pomarolli, A. (3. 10 2016). 
Java Code Geeks. 
Hentet fra javafx-treetableview-example: https://examples.javacodegeeks.com/desktop-java/javafx/javafx-treetableview-example/
Also got help and inspiration from James_D. (7. 3 2014).
set-two-root-nodes-for-treeview.
Hentet fra stackoverflow.com: https://stackoverflow.com/questions/22260032/set-two-root-nodes-for-treeview/22260167*/
package examProjectTheDisciplesOfSkrumm.GUI.controller;

import com.jfoenix.controls.JFXButton;
import examProjectTheDisciplesOfSkrumm.BE.Task;
import examProjectTheDisciplesOfSkrumm.BLL.Util.TreeTableUtil;
import examProjectTheDisciplesOfSkrumm.GUI.Model.Interface.ModelFacadeInterface;
import examProjectTheDisciplesOfSkrumm.GUI.Model.ModelFacade;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
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

/**
 * FXML Controller class
 *
 * @author kacpe
 */
public class TaskViewController implements Initializable 
{
    ModelFacadeInterface modelfacade;
    @FXML
    private TreeTableView<Task> TaskTable;
    @FXML
    private TreeTableColumn<Task, Integer> TimeColumn;
    @FXML
    private JFXButton homeBtn;
    @FXML
    private JFXButton clientProjectsBtn;
    @FXML
    private JFXButton statisticsBtn;
    @FXML
    private JFXButton createTaskButton;
    @FXML
    private TreeTableColumn<Task, String> clientColumn;
    @FXML
    private Label weekNumberLabel;
    @FXML
    private Label WeekdayLabel;
    @FXML
    private Label DateLabel;
    @FXML
    private Label CurrentTaskLabel;
    @FXML
    private TreeTableColumn<Task, String> TaskColumn;
    @FXML
    private TreeTableColumn<Task, String> ProjectColumn;
    @FXML
    private Label timeLabel;

    private int sec = 0;
    private int min = 0;
    private int hour = 0;
    private boolean running = false;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) 
    {
        try {
            //Getting the model
            modelfacade = ModelFacade.getInstance();
        } catch (Exception ex) {
            Logger.getLogger(TaskViewController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        //Making some mock tasks
        
        //Setting cellValue Factories for TreeTableView 
        TaskColumn.setCellValueFactory(new TreeItemPropertyValueFactory<>("title"));
        ProjectColumn.setCellValueFactory(new TreeItemPropertyValueFactory<>("projectName"));
        TimeColumn.setCellValueFactory(new TreeItemPropertyValueFactory<>("duration"));
        clientColumn.setCellValueFactory(new TreeItemPropertyValueFactory<>("clientName"));
        
        //Creating the rootNodeTask
        TreeItem<Task> rootNodeTask = modelfacade.getModel();
        rootNodeTask.setExpanded(true);
        
        //Set the model for the TreeTableView
        TaskTable.setRoot(rootNodeTask);
        
        // Make the root node invisible
        TaskTable.setShowRoot(false);
        
        
        
        
    } 

    @FXML
    private void handlecChartView(ActionEvent event) throws IOException {
        Stage chartView = (Stage) ((Node) event.getSource()).getScene().getWindow();
        
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/examProjectTheDisciplesOfSkrumm/GUI/view/ChartView.fxml"));
        Parent root = loader.load();
        ChartViewController Controller = loader.getController();
        
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.setTitle("TimeTracker");
        stage.show();
        chartView.close();
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
        stage.setTitle("TimeTracker");
        stage.show();
        chartView.close();
    }

    /**
     * 
     * @param event
     * @throws IOException 
     */
    @FXML
    private void handleClientsProject(ActionEvent event) throws IOException
    {
        Stage taskView = (Stage) ((Node) event.getSource()).getScene().getWindow();
        
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/examProjectTheDisciplesOfSkrumm/GUI/view/ClientsAndProjects.fxml"));
        Parent root = loader.load();
        ClientsAndProjectsController Controller = loader.getController();
        
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.setTitle("TimeTracker");
        stage.show();
        taskView.close();
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
        stage.setTitle("TimeTracker");
        stage.show();
    }


    private void handleEditTaskAction(ActionEvent event) throws IOException
    {
      
        
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/examProjectTheDisciplesOfSkrumm/GUI/view/CreateTaskView.fxml"));
        Parent root = loader.load();
        CreateTaskController controller = loader.getController();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.setTitle("TimeTracker");
        stage.show();
    } 
    
    @FXML
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

                        if (sec >= 60) {
                            min++;
                            sec = 0;
                        }

                        if (min >= 60) {
                            hour++;
                            min = 0;
                        }

                        String time = String.format("%02d", hour) + " : " + String.format("%02d", min) + " : " + String.format("%02d", sec);
                        System.out.println(time);
                        Platform.runLater(()
                                -> {
                            timeLabel.setText(String.format("%02d", hour) + " : " + String.format("%02d", min) + " : " + String.format("%02d", sec));
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

    /**
     * Refreshes the tableview
     */
    public void RefreshTreeView() 
    {
        //Creating the rootNodeTask
        TreeItem<Task> rootNodeTask = modelfacade.getModel();
        rootNodeTask.setExpanded(true);
        
        //Set the model for the TreeTableView
        TaskTable.setRoot(rootNodeTask);
        
        // Make the root node invisible
        TaskTable.setShowRoot(false);
    }
    
        
               
    
    
  
    
    
   
}
