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
import examProjectTheDisciplesOfSkrumm.BE.Interval;
import examProjectTheDisciplesOfSkrumm.BE.Task;
import examProjectTheDisciplesOfSkrumm.BLL.Util.TreeTableUtil;
import examProjectTheDisciplesOfSkrumm.GUI.Model.Interface.ModelFacadeInterface;
import examProjectTheDisciplesOfSkrumm.GUI.Model.ModelFacade;
import java.io.IOException;
import java.net.URL;
import java.time.LocalTime;
import java.util.ResourceBundle;
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
    private JFXButton clientProjectsBtn;
    @FXML
    private JFXButton statisticsBtn;
    @FXML
    private JFXButton createTaskButton;
    @FXML
    private TreeTableColumn clientColumn;
    @FXML
    private Label weekNumberLabel;
    @FXML
    private Label WeekdayLabel;
    @FXML
    private Label CurrentTaskLabel;
    @FXML
    private TreeTableColumn TaskColumn;
    @FXML
    private TreeTableColumn ProjectColumn;
    @FXML
    private Label timeLabel;

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
        TaskColumn.setCellValueFactory( new Callback() {
            @Override
            public Object call(Object obj) 
            {
                final Object dataObj = ((TreeTableColumn.CellDataFeatures) obj).getValue().getValue();
                if (dataObj instanceof Task)
                {
                    return new ReadOnlyStringWrapper(((Task) dataObj).getTitle());
                }
                else
                {
                    return null;
                }
                
            }
        });
        
        
        ProjectColumn.setCellValueFactory(new Callback() {
            @Override
            public Object call(Object obj) 
            {
                final Object dataObj = ((TreeTableColumn.CellDataFeatures) obj).getValue().getValue();
                if (dataObj instanceof Task)
                {
                    return new ReadOnlyStringWrapper(((Task) dataObj).getProjectName());
                }
                else
                {
                    return null;
                }
                   
               
            }
        });
        
        
        TimeColumn.setCellValueFactory( new Callback() {
            @Override
            public Object call(Object obj) 
            {
                final Object dataObj = ((TreeTableColumn.CellDataFeatures) obj).getValue().getValue();
                if (dataObj instanceof Task)
                {
                    return new ReadOnlyStringWrapper(String.valueOf(((Task) dataObj).getDuration()));
                }
                else  if (dataObj instanceof Interval)
                {
                    return new ReadOnlyStringWrapper(String.valueOf(((Interval) dataObj).getIntervalTime()));
                }
                else
                {
                    return null;
                }
                
            }
        });
        
         clientColumn.setCellValueFactory(new Callback() {
            @Override
            public Object call(Object obj) 
            {
                final Object dataObj = ((TreeTableColumn.CellDataFeatures) obj).getValue().getValue();
                if (dataObj instanceof Task)
                {
                    return new ReadOnlyStringWrapper(((Task) dataObj).getClientName());
                }
                else
                {
                    return null;
                }
                
            }
         });
         
         startTimeColumn.setCellValueFactory(new Callback() {
            @Override
            public Object call(Object obj) 
            {
                final Object dataObj = ((TreeTableColumn.CellDataFeatures) obj).getValue().getValue();
                if (dataObj instanceof Interval)
                {
                    return new ReadOnlyStringWrapper(((Interval) dataObj).getStartTime().toString());
                }
                else if(dataObj instanceof Task)
                {
                     return new ReadOnlyStringWrapper(((Task) dataObj).getStartTime().toString());
                }
                else
                {
                    return null;
                }
                
            }
         });
         
         stopTimeColumn.setCellValueFactory(new Callback() {
            @Override
            public Object call(Object obj) 
            {
                final Object dataObj = ((TreeTableColumn.CellDataFeatures) obj).getValue().getValue();
                if (dataObj instanceof Interval)
                {
                    return new ReadOnlyStringWrapper(((Interval) dataObj).getStopTime().toString());
                }
                else if(dataObj instanceof Task)
                {
                     return new ReadOnlyStringWrapper(((Task) dataObj).getStopTime().toString());
                }
                else
                {
                    return null;
                }
                
            }
         });
         
         IsPaidColumn.setCellValueFactory(new Callback() {
            @Override
            public Object call(Object obj) {
                final Object dataObj = ((TreeTableColumn.CellDataFeatures) obj).getValue().getValue();
                if (dataObj instanceof Task)
                {
                    return new ReadOnlyStringWrapper(((Task) dataObj).getIsPaidBoolean());
                }
                else
                {
                    return null;
                }
            }
         });
        
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
        stage.setTitle("View Charts");
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
        stage.setTitle("Home view");
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
        stage.setTitle("Clients and Projects");
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
        stage.setTitle("Create Task");
        stage.show();
    }


    private void handleEditTaskAction(ActionEvent event) throws IOException
    {
        
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/examProjectTheDisciplesOfSkrumm/GUI/view/EditTaskView.fxml"));
        Parent root = loader.load();
        EditTaskController controller = loader.getController();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.setTitle("Edit Task");
        stage.show();
        final JDialog dialog = new JDialog();
        dialog.setAlwaysOnTop(true);
        
        if((TaskTable.getSelectionModel().getSelectedItem() == null)){
            JOptionPane.showMessageDialog(dialog, "Nothing seems to be selected!\nSelect a task to edit before pressing edit!", "ERROR", JOptionPane.ERROR_MESSAGE);   
        } 
        
        else if(!(TaskTable.getSelectionModel().getSelectedItem() instanceof Task)){
            JOptionPane.showMessageDialog(dialog, "Please select a valid task to edit!", "ERROR", JOptionPane.ERROR_MESSAGE);
        }
        
        else
        {
           controller.setEditTask((Task) TaskTable.getSelectionModel().getSelectedItem());
        }
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
