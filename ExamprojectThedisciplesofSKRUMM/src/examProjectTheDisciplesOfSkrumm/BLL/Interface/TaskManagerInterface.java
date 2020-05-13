/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package examProjectTheDisciplesOfSkrumm.BLL.Interface;

import com.microsoft.sqlserver.jdbc.SQLServerException;
import examProjectTheDisciplesOfSkrumm.BE.Client;
import examProjectTheDisciplesOfSkrumm.BE.Interval;
import examProjectTheDisciplesOfSkrumm.BE.Project;
import examProjectTheDisciplesOfSkrumm.BE.Task;
import examProjectTheDisciplesOfSkrumm.BE.User;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

/**
 *
 * @author kacpe
 */
public interface TaskManagerInterface 
{
    public void newInterval(Interval interval) throws SQLException;
    
    public List<Task> getSixTasks(User user) throws SQLException;
    
    public List<Task> getTasksForUser(User user, LocalDate date) throws SQLException;
    
    public String convertSecToTimeString(int totalSec);
    
    public List<Task> getAllTasks() throws SQLException;

    public boolean taskExist(Task task) throws SQLException;

    public Task createTask(Task task) throws SQLException;

    public Boolean updateTask(Task task) throws SQLException;

    public Task getTask(Task task) throws SQLException;
    
    public boolean deleteTask(Task task) throws SQLException;
    
    public boolean clearTask(Task task) throws SQLException;
    
    public boolean updateInterval( Interval oldInterval, Interval newInterval) throws SQLServerException, SQLException;
    
    public boolean deleteInterval(Interval interval) throws SQLException;
    
     public List<Task> getTasksForUserbetween2Dates(User user, LocalDate fromdate, LocalDate todate) throws SQLException;
    
    
}
