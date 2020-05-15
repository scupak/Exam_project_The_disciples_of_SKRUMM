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
import examProjectTheDisciplesOfSkrumm.BLL.Util.TimerUtil;
import java.lang.reflect.InvocationTargetException;
import java.time.LocalDateTime;
import javafx.collections.ObservableList;
import javafx.scene.control.TreeItem;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.concurrent.ExecutorService;
import javafx.scene.chart.XYChart;

/**
 *
 * @author kacpe
 */
public interface TaskModelInterface 
{
    
    public TreeItem<Task> getModel(User user, LocalDate fromdate, LocalDate todate);
    
    public void createTask(Task task);
    
    public ObservableList<Task> getTasks();
    
    public void setTasks(ObservableList<Task> tasks);

    public void newInterval(Interval interval) throws SQLException, SQLServerException;
    
    public ObservableList<Task> getSixTasks(User user) throws SQLException;
    
    public List<Task> getTasksForUser(User user, LocalDate date) throws SQLException;
    
    public ObservableList<Task> getAllTasks() throws SQLException;

    public boolean taskExist(Task task) throws SQLException;

    public Boolean updateTask(Task task) throws SQLException;

    public Task getTask(Task task) throws SQLException;
    
    public String convertSecToTimeString(int totalSec);
    
    public boolean getisTimerRunning();
     
    public void setIsTimerRunning(boolean isTimerRunning);
    
    public TimerUtil getTimerutil();

    public void setTimerutil(TimerUtil timerutil);
  
    public boolean deleteTask(Task task) throws SQLException;
    
    public boolean clearTask(Task task) throws SQLException;
    
    public ExecutorService getExecutorService();

    public void setExecutorService(ExecutorService executorService);
    
    public boolean updateInterval( Interval oldInterval, Interval newInterval) throws SQLServerException, SQLException; 
    
    public boolean deleteInterval(Interval interval) throws SQLException;
    
    public List<Task> getAllTasks4Project(Project project) throws SQLServerException, SQLException;

    public List<Task> getTasksForUserbetween2Dates(User user, LocalDate fromdate, LocalDate todate) throws SQLException;
    
    public int getDurationFromIntervalsbetween2Dates(String userID, int projectID, LocalDate fromdate, LocalDate todate) throws SQLServerException, SQLException;
    
   
    
    
   
    
}
