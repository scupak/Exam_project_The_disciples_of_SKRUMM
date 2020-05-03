/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package examProjectTheDisciplesOfSkrumm.GUI.Model.Interface;

import com.microsoft.sqlserver.jdbc.SQLServerException;
import examProjectTheDisciplesOfSkrumm.BE.Client;
import examProjectTheDisciplesOfSkrumm.BE.Interval;
import examProjectTheDisciplesOfSkrumm.BE.Project;
import examProjectTheDisciplesOfSkrumm.BE.Task;
import examProjectTheDisciplesOfSkrumm.BE.User;
import java.time.LocalDateTime;
import javafx.collections.ObservableList;
import javafx.scene.control.TreeItem;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author kacpe
 */
public interface TaskModelInterface 
{
    
    public TreeItem<Task> getModel();
    
    public void createTask(Task task);
    
    public ObservableList<Task> getTasks();
    
    public void setTasks(ObservableList<Task> tasks);

    public void newInterval(Interval interval) throws SQLException, SQLServerException;
    
    public ObservableList<Task> getSixTasks(User user) throws SQLException;
    
}
