/* Got help and inspiration for this class from 
Pomarolli, A. (3. 10 2016). 
Java Code Geeks. 
Hentet fra javafx-treetableview-example: https://examples.javacodegeeks.com/desktop-java/javafx/javafx-treetableview-example/*/
package examProjectTheDisciplesOfSkrumm.BLL.Util;

import examProjectTheDisciplesOfSkrumm.BE.Client;
import examProjectTheDisciplesOfSkrumm.BE.Project;
import examProjectTheDisciplesOfSkrumm.BE.Task;
import examProjectTheDisciplesOfSkrumm.BLL.Interface.TreeTableUtilInterface;
import java.util.ArrayList;
import javafx.collections.ObservableList;
import javafx.scene.control.TreeItem;

/**
 *This class enables the use of treetables in the program
 * @author kacpe
 */
public class TreeTableUtil implements TreeTableUtilInterface
{

    public TreeTableUtil() 
    {
    }
    
    @SuppressWarnings("unchecked")
    public TreeItem<Task> getMockModel()
    {
    
    /* Create all task*/
    // Root node
    Task rootTask = new Task(1, "Root", new Project(1, "Root", new Client(1, "Root", 0, 1), 0, 1), 0);
    
    // Main tasks
    Task createNemesis = new Task(2, "Create Nemesis",new Project(2, "Project X", new Client(1, "Umbrella",10, 1), 10, 0), 0);
    Task takeOverWorldTotal = new Task(3, "Take over world",new Project(3, "Project X", new Client(1, "Umbrella",10, 1), 10, 0), 10);
    
    //takeOverWorld secondary task
    Task takeOverWorld1 = new Task(4, "Take over world",new Project(4, "Project X", new Client(1, "Umbrella",10, 0), 10, 0), 4);
    Task takeOverWorld2 = new Task(5, "Take over world",new Project(5, "Project X", new Client(1, "Umbrella",10, 0), 10, 0), 6);
    
    //Build nodes
    TreeItem<Task> createNemesisNode = new TreeItem<>(createNemesis);
    
    TreeItem<Task> takeOverWorldTotalNode = new TreeItem<>(takeOverWorldTotal);
    takeOverWorldTotalNode.getChildren().addAll(new TreeItem<>(takeOverWorld1), new TreeItem<>(takeOverWorld2));
    
    //Create the root node and add children
    TreeItem<Task> rootTaskNode = new TreeItem<>(rootTask);
    rootTaskNode.getChildren().addAll(createNemesisNode,takeOverWorldTotalNode );
    return rootTaskNode;
    }
    
    @SuppressWarnings("unchecked")
    public TreeItem<Task> getModel(ObservableList<Task> tasks)
    {
    
    /* Create all task*/
    // Root node
    Task rootTask = new Task(6, "Root", new Project(6, "Root", new Client(1, "Root",0, 0), 0, 1), 0);
        ArrayList<TreeItem<Task>> treeItems = new ArrayList();
        
    

        for (Task task : tasks) 
        {  
           TreeItem<Task> treeitem = new TreeItem<Task>(task);
           if(!task.getIntervals().isEmpty())
           {
               ArrayList<TreeItem<Task>> intervals = new ArrayList();
               
               for (Task interval : task.getIntervals()) 
               {
                    intervals.add(new TreeItem<Task>(interval));
               }
               treeitem.getChildren().addAll(intervals);
           }
           treeItems.add(treeitem);
        }
        
    //Create the root node and add children
    TreeItem<Task> rootTaskNode = new TreeItem<>(rootTask);
    rootTaskNode.getChildren().addAll(treeItems);
    return rootTaskNode;
    }
    
    
    
    
    
    
    
}
