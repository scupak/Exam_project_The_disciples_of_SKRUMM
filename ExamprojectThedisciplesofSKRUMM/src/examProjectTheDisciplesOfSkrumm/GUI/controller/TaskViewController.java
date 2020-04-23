/* Got help and inspiration for this class from 
Pomarolli, A. (3. 10 2016). 
Java Code Geeks. 
Hentet fra javafx-treetableview-example: https://examples.javacodegeeks.com/desktop-java/javafx/javafx-treetableview-example/
Also got help and inspiration from James_D. (7. 3 2014).
set-two-root-nodes-for-treeview.
Hentet fra stackoverflow.com: https://stackoverflow.com/questions/22260032/set-two-root-nodes-for-treeview/22260167*/
package examProjectTheDisciplesOfSkrumm.GUI.controller;

import examProjectTheDisciplesOfSkrumm.BE.Task;
import examProjectTheDisciplesOfSkrumm.BLL.TreeTableUtil;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTableView;
import javafx.scene.control.cell.TreeItemPropertyValueFactory;

/**
 * FXML Controller class
 *
 * @author kacpe
 */
public class TaskViewController implements Initializable {

    @FXML
    private TreeTableView<Task> TaskTable;
    @FXML
    private TreeTableColumn<Task, String> TaskColumn;
    @FXML
    private TreeTableColumn<Task, String> ProjectColumn;
    @FXML
    private TreeTableColumn<Task, Integer> TimeColumn;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) 
    {
        //Setting cellValue Factories for TreeTableView 
        TaskColumn.setCellValueFactory(new TreeItemPropertyValueFactory<>("title"));
        ProjectColumn.setCellValueFactory(new TreeItemPropertyValueFactory<>("projectName"));
        TimeColumn.setCellValueFactory(new TreeItemPropertyValueFactory<>("time"));
        
        //Creating the rootNodeTask
        TreeItem<Task> rootNodeTask = TreeTableUtil.getModel();
        rootNodeTask.setExpanded(true);
        
        //Set the model for the TreeTableView
        TaskTable.setRoot(rootNodeTask);
        
        // Make the root node invisible
        TaskTable.setShowRoot(false);
        
        
        
        
    } 

    @FXML
    private void handlecChartView(ActionEvent event) {
    }
  
}
