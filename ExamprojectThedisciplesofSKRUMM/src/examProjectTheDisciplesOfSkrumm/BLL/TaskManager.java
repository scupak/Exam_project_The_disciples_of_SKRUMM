/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package examProjectTheDisciplesOfSkrumm.BLL;

import com.microsoft.sqlserver.jdbc.SQLServerException;
import examProjectTheDisciplesOfSkrumm.BE.Client;
import examProjectTheDisciplesOfSkrumm.BE.Interval;
import examProjectTheDisciplesOfSkrumm.BE.Project;
import examProjectTheDisciplesOfSkrumm.BE.User;
import examProjectTheDisciplesOfSkrumm.BE.Task;
import examProjectTheDisciplesOfSkrumm.BLL.Interface.TaskManagerInterface;
import examProjectTheDisciplesOfSkrumm.DAL.DALFacade;
import examProjectTheDisciplesOfSkrumm.DAL.TaskDBDAO;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author kacpe
 */
public class TaskManager implements TaskManagerInterface
{
    private DALFacade dal;
    
    public TaskManager() throws IOException
    {
        dal = new DALFacade();
    }
    
    @Override
    public void newInterval(Interval interval) throws SQLException
    {
        dal.newInterval(interval);
    }
    
    @Override
    public boolean updateInterval( Interval oldInterval, Interval newInterval) throws SQLServerException, SQLException
    {
        return dal.updateInterval(oldInterval, newInterval);
    }
    
    @Override
    public List<Task> getSixTasks(User user) throws SQLException
    {
        return dal.getSixTasks(user);
    }
    
    @Override
    public List<Task> getTasksForUser(User user, LocalDate date) throws SQLException
    {
        return dal.getTasksForUser(user, date);
    }
    
    @Override
    public String convertSecToTimeString(int totalSec){
        int hour = 0;
        int min = 0; 
        int sec = 0;
        
    
         while(totalSec >= 3600){
        totalSec  = totalSec - 3600;
        hour++;
        System.out.println("added one to hours...");
        }
        
        while(totalSec >= 60){
        totalSec = totalSec - 60;
        min++;
        System.out.println("added one to min...");
        }
        
        sec = totalSec;
        System.out.println("added rest of seconds to sec...");
        
        return(String.format("%02d", hour) + ":" + String.format("%02d", min) + ":" + String.format("%02d", sec));
    }
    
    public static void main(String[] args)
    {
        DALFacade dal;                
    }

    @Override
    public List<Task> getAllTasks() throws SQLException
    {
       return dal.getAllTasks();
    }

    @Override
    public boolean taskExist(Task task) throws SQLException
    {
        return dal.taskExist(task);
    }

    @Override
    public Task createTask(Task task) throws SQLException
    {
       return dal.createTask(task);
    }

    @Override
    public Boolean updateTask(Task task) throws SQLException
    {
       return dal.updateTask(task);
    }

    @Override
    public Task getTask(Task task) throws SQLException
    {
        return dal.getTask(task);
    }

    @Override
    public boolean deleteTask(Task task) throws SQLException
    {
        return dal.deleteTask(task);
    }

    @Override
    public boolean clearTask(Task task) throws SQLException
    {
        return dal.clearTask(task);
    }

    @Override
    public boolean deleteInterval(Interval interval) throws SQLException 
    {
       return dal.deleteInterval(interval);
    }

    @Override
    public List<Task> getAllTasks4Project(Project project) throws SQLServerException, SQLException {
        return dal.getAllTasks4Project(project);
    }

    public List<Task> getTasksForUserbetween2Dates(User user, LocalDate fromdate, LocalDate todate) throws SQLException {
       return dal.getTasksForUserbetween2Dates(user, fromdate, todate);
    }   
}
