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
import examProjectTheDisciplesOfSkrumm.DAL.DALFacadeFactory;
import examProjectTheDisciplesOfSkrumm.DAL.Interface.DALFacadeInterface;
import examProjectTheDisciplesOfSkrumm.DAL.TaskDBDAO;
import java.io.IOException;
import java.sql.SQLException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.chart.XYChart;

/**
 *
 * @author kacpe
 */
public class TaskManager implements TaskManagerInterface
{
    private DALFacadeInterface dal;
    
    public TaskManager(DALFacadeInterface dal) throws IOException, Exception
    {
        this.dal = dal;
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

    @Override
    public int getDurationFromTasks(Project project) throws SQLServerException, SQLException {
        return dal.getDurationFromTasks(project);
    }

    @Override
    public int getDurationFromIntervalsbetween2Dates(String userID, int projectID, LocalDate fromdate, LocalDate todate) throws SQLServerException, SQLException {
      return  dal.getDurationFromIntervalsbetween2Dates(userID, projectID, fromdate, todate);
    }
    /**
     * 
     * @param userID
     * @param fromdate
     * @param todate
     * @return ProjectBarChartData
     * @throws SQLException 
     */
    @Override
    public XYChart.Series handleProjectBarChartData(String userID,LocalDate fromdate, LocalDate todate) throws SQLException{
        ArrayList<Project> allProject = new ArrayList<>();
        
        /**get all the projects**/
        allProject.addAll(dal.getAllProjects());
        
        
     XYChart.Series data = new XYChart.Series();
        data.setName("Hours spent on projects");
        
        
        for (Project project : allProject) {
            
            System.out.println(project.getProjectName() + getDurationFromIntervalsbetween2Dates(userID, project.getId(), fromdate, todate) );
            /* the group divedes the output of getDurationFromIntervalsbetween2Dates by 3600 to go from seconds to hours */
             data.getData().add(new XYChart.Data(project.getProjectName(),(getDurationFromIntervalsbetween2Dates(userID, project.getId(), fromdate, todate) / 3600.0) ));
            
            
        }
        
        
        return data;
    }
    
    @Override
     public XYChart.Series handleProjectBarChartDataForAdmin(int projectID,LocalDate fromdate, LocalDate todate) throws SQLException{
        ArrayList<User> allUsers = new ArrayList<>();
        
        /**get all the projects**/
        allUsers.addAll(dal.getAllUsers());
        
        
     XYChart.Series data = new XYChart.Series();
        data.setName("Hours this user spent on the project");
        
        
        for (User user : allUsers) {
            
            System.out.println(user.getEmail() + getDurationFromIntervalsbetween2Dates(user.getEmail(), projectID, fromdate, todate) );
            /* the group divedes the output of getDurationFromIntervalsbetween2Dates by 3600 to go from seconds to hours */
             data.getData().add(new XYChart.Data(user.getEmail(),(getDurationFromIntervalsbetween2Dates(user.getEmail(), projectID, fromdate, todate) / 3600.0) ));
            
            
        }
        
        
        return data;
    }
    
    
     public static void main(String[] args) throws Exception
    {
       TaskManager tm = new TaskManager(DALFacadeFactory.CreateDALFacade(DALFacadeFactory.DALFacadeTypes.PRODUCTION));
       
       
        System.out.println(tm.handleProjectBarChartDataForAdmin(1, LocalDate.of(2020, Month.MAY, 1), LocalDate.of(2020, Month.MAY, 15)).getData());
        
        
    }

    
}
