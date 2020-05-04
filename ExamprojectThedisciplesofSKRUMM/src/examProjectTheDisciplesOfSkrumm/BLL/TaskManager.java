/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package examProjectTheDisciplesOfSkrumm.BLL;

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
    public void newInterval(Interval interval)
    {
        try
        {
            dal.newInterval(interval);
        } catch (SQLException ex)
        {
            Logger.getLogger(TaskManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Override
    public List<Task> getSixTasks(User user) throws SQLException
    {
        return dal.getSixTasks(user);
    }
    
    public List<Task> getTasksForUser(User user, LocalDate date) throws SQLException
    {
        return dal.getTasksForUser(user, date);
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
  
   
    
    
}
