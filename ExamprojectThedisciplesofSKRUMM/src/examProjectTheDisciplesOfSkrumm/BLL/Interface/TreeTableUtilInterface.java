/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package examProjectTheDisciplesOfSkrumm.BLL.Interface;

import examProjectTheDisciplesOfSkrumm.BE.Task;
import javafx.collections.ObservableList;
import javafx.scene.control.TreeItem;

/**
 *
 * @author SKRUMM
 */
public interface TreeTableUtilInterface 
{
  
    /**
     * gets the model as a treeItem
     * @param tasks
     * @return a root treeItem based upon the given list of tasks
     */
    public TreeItem getModel(ObservableList<Task> tasks);
    
}
