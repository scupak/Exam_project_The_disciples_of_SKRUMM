/* Got help and inspiration for this class from 
Pomarolli, A. (3. 10 2016). 
Java Code Geeks. 
Hentet fra javafx-treetableview-example: https://examples.javacodegeeks.com/desktop-java/javafx/javafx-treetableview-example/*/
package examProjectTheDisciplesOfSkrumm.BLL.Util;

import examProjectTheDisciplesOfSkrumm.BE.Client;
import examProjectTheDisciplesOfSkrumm.BE.Interval;
import examProjectTheDisciplesOfSkrumm.BE.Project;
import examProjectTheDisciplesOfSkrumm.BE.Task;
import examProjectTheDisciplesOfSkrumm.BE.User;
import examProjectTheDisciplesOfSkrumm.BLL.Interface.TreeTableUtilInterface;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Comparator;
import javafx.collections.ObservableList;
import javafx.scene.control.TreeItem;

/**
 *This class enables the use of treetables in the program
 * @author SKRUMM
 */
public class TreeTableUtil implements TreeTableUtilInterface
{

    public TreeTableUtil() 
    {
    }
    
    
    /**
     * gets the model as a treeItem
     * @param tasks
     * @return a root treeItem based upon the given list of tasks
     */
    @SuppressWarnings("unchecked")
    @Override
    public TreeItem getModel(ObservableList<Task> tasks)
    {
    
    /* Create all task*/
    // Root node
    Task rootTask = new Task(1, "Root", new Project(1, "Root", new Client(1, "Root", 0, 0), 0), 0,
            LocalDateTime.MIN, LocalDate.MIN, LocalTime.MIN, LocalTime.MIN,
            new User("standard@user.now", "h", "l", "nemt", false), new ArrayList<>());
        ArrayList<TreeItem> treeItems = new ArrayList();
        
    

        for (Task task : tasks) 
        {  
           TreeItem treeitem = new TreeItem(task);
           if(!task.getIntervals().isEmpty())
           {
               ArrayList<Interval> intervals = new ArrayList();
               intervals.addAll(task.getIntervals());
               Comparator<Interval> byDate = Comparator
                .comparing(Interval::getCreationDate)
                .thenComparing(Interval::getStartTime).reversed();
               intervals.sort(byDate);
               
               ArrayList<TreeItem> treeintervals = new ArrayList();
               
               for (Interval interval : intervals) 
               {
                    treeintervals.add(new TreeItem(interval));
               }
               
               treeitem.getChildren().addAll(treeintervals);
           }
           treeItems.add(treeitem);
        }
        
    //Create the root node and add children
    TreeItem rootTaskNode = new TreeItem(rootTask);
    rootTaskNode.getChildren().addAll(treeItems);
    return rootTaskNode;
    }

   
    
    
    
    
    
    
    
}
