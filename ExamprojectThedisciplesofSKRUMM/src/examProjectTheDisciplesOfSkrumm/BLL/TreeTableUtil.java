/* Got help and inspiration for this class from 
Pomarolli, A. (3. 10 2016). 
Java Code Geeks. 
Hentet fra javafx-treetableview-example: https://examples.javacodegeeks.com/desktop-java/javafx/javafx-treetableview-example/*/
package examProjectTheDisciplesOfSkrumm.BLL;

import examProjectTheDisciplesOfSkrumm.BE.Task;
import javafx.scene.control.TreeItem;

/**
 *This class enables the use of treetables in the program
 * @author kacpe
 */
public class TreeTableUtil 
{
    @SuppressWarnings("unchecked")
    public static TreeItem<Task> getModel()
    {
    
    /* Create all task*/
    // Root node
    Task rootTask = new Task("Root", "Root", "The root node of the tableview if you can se this then something went wrong", 0);
    
    // Main tasks
    Task createNemesis = new Task("Create Nemesis", "Project X", "Making a buff, binbag wearing, tentacle man", 0);
    Task takeOverWorldTotal = new Task("Take over world", "Project X", "In the process of taking the world over", 10);
    
    //takeOverWorld secondary task
    Task takeOverWorld1 = new Task("Take over world", "Project X", "In the process of taking the world over", 4);
    Task takeOverWorld2 = new Task("Take over world", "Project X", "In the process of taking the world over", 6);
    
    //Build nodes
    TreeItem<Task> createNemesisNode = new TreeItem<>(createNemesis);
    
    TreeItem<Task> takeOverWorldTotalNode = new TreeItem<>(takeOverWorldTotal);
    takeOverWorldTotalNode.getChildren().addAll(new TreeItem<>(takeOverWorld1), new TreeItem<>(takeOverWorld2));
    
    //Create the root node and add children
    TreeItem<Task> rootTaskNode = new TreeItem<>(rootTask);
    rootTaskNode.getChildren().addAll(createNemesisNode,takeOverWorldTotalNode );
    return rootTaskNode;
    }
    
    
}
