/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package examProjectTheDisciplesOfSkrumm.GUI.Model;

import examProjectTheDisciplesOfSkrumm.BE.Client;
import examProjectTheDisciplesOfSkrumm.BE.Project;
import examProjectTheDisciplesOfSkrumm.BE.Task;
import examProjectTheDisciplesOfSkrumm.BLL.BLLFacade;
import examProjectTheDisciplesOfSkrumm.BLL.Interface.BLLFacadeInterface;
import examProjectTheDisciplesOfSkrumm.GUI.Model.Interface.TaskModelInterface;
import examProjectTheDisciplesOfSkrumm.GUI.controller.TaskViewController;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.TreeItem;

/**
 *
 * @author kacpe
 */
public class TaskModel implements TaskModelInterface
{
    private final BLLFacadeInterface bllfacade;
    private ObservableList<Task> tasks;

    TaskModel() throws IOException
    {
        bllfacade = new BLLFacade();
        tasks = FXCollections.observableArrayList();
    }

    @Override
    public TreeItem<Task> getMockModel() 
    {
        return bllfacade.getMockModel();
    }

    @Override
    public TreeItem<Task> getModel() 
    {
        return bllfacade.getModel(tasks);
    }

    @Override
    public void createTask(Task task) 
    {
        tasks.add(task);
       
        
    }

    public ObservableList<Task> getTasks() {
        return tasks;
    }

    public void setTasks(ObservableList<Task> tasks) {
        this.tasks = tasks;
    }
    
    
    
    
}


