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
    Task rootTask = new Task("Root", new Project("Root", new Client("Root", 0, 0), 0, 0), 0);
    
    // Main tasks
    Task createNemesis = new Task("Create Nemesis",new Project("Project X", new Client("Umbrella",10, 1), 10, 1), 0);
    Task takeOverWorldTotal = new Task("Take over world",new Project("Project X", new Client("Umbrella",10, 1), 10, 1), 10);
    
    //takeOverWorld secondary task
    Task takeOverWorld1 = new Task("Take over world",new Project("Project X", new Client("Umbrella",10, 1), 10, 1), 4);
    Task takeOverWorld2 = new Task("Take over world",new Project("Project X", new Client("Umbrella",10, 1), 10, 1), 6);
    
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
    Task rootTask = new Task("Root", new Project("Root", new Client("Root",0, 0), 0, 0), 0);
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
