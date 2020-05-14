/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package examProjectTheDisciplesOfSkrumm.DAL;

import com.microsoft.sqlserver.jdbc.SQLServerException;
import examProjectTheDisciplesOfSkrumm.BE.Client;
import examProjectTheDisciplesOfSkrumm.BE.Interval;
import examProjectTheDisciplesOfSkrumm.BE.Project;
import examProjectTheDisciplesOfSkrumm.BE.Task;
import examProjectTheDisciplesOfSkrumm.BE.User;
import examProjectTheDisciplesOfSkrumm.DAL.Interface.TaskDBDAOInterface;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;

/**
 *
 * @author lumby
 */
public class TaskDBDAO implements TaskDBDAOInterface
{

    private final DatabaseConnector dbCon;
    private UserDBDAO userDBDAO;
    private ProjectDBDAO projectDBDAO;

    public TaskDBDAO() throws IOException
    {
        dbCon = new DatabaseConnector();
        userDBDAO = new UserDBDAO();
        projectDBDAO = new ProjectDBDAO();
    }

    @Override
    public List<Task> getAllTasks() throws SQLException
    {
        ArrayList<Task> returntasks = new ArrayList<>();

        try (Connection con = dbCon.getConnection())
        {
            PreparedStatement ps = con.prepareStatement("SELECT * FROM [task]");
            ResultSet rs = ps.executeQuery();

            while (rs.next())
            {
                int id = rs.getInt("id");
                String title = rs.getString("title");
                Project project = projectDBDAO.getProject(new Project(rs.getInt("ProjectID"), title, new Client(id, title, id, id), id));
                int duration = rs.getInt("duration");
                String clientName = project.getClientName();
                LocalDateTime lastUsed = rs.getTimestamp("lastUsed").toLocalDateTime();
                LocalDate creationDate = rs.getDate("creationDate").toLocalDate();
                LocalTime startTime = rs.getTime("startTime").toLocalTime();
                LocalTime stopTime = rs.getTime("stopTime").toLocalTime();

                ArrayList<Interval> intervals = new ArrayList<Interval>();

                String userEmail = rs.getString("userEmail");

                User user = userDBDAO.getUser(new User(userEmail, clientName, clientName, title, false));

                intervals.addAll(getIntervals(new Task(id, title, project, duration, lastUsed, creationDate, startTime, stopTime, user, intervals)));

                returntasks.add(new Task(id, title, project, duration, lastUsed, creationDate, startTime, stopTime, user, intervals));
            }

            return returntasks;

        }
    }
    public boolean taskExist(Task task) throws SQLException
    {
        try (Connection con = dbCon.getConnection())
        {
            PreparedStatement ps = con.prepareStatement("SELECT * FROM [task] WHERE id = ?");
            ps.setInt(1, task.getId());

            ResultSet rs = ps.executeQuery();

            while (rs.next())
            {
                return true;
            }

            return false;
        }
    }

    public Task createTask(Task task) throws SQLException
    {
        

        try (Connection con = dbCon.getConnection())
        {
            PreparedStatement ps = con.prepareStatement("INSERT INTO [task]"
                    + "(title, projectID, lastUsed, CreationDate, startTime, stopTime, duration, userEmail)"
                    + "VALUES (?,?,?,?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);

            ps.setString(1, task.getTitle());
            ps.setInt(2, task.getProject().getId());
            ps.setTimestamp(3, java.sql.Timestamp.valueOf(task.getLastUsed()));
            ps.setDate(4, java.sql.Date.valueOf(task.getCreationDate()));
            ps.setTime(5, java.sql.Time.valueOf(task.getStartTime()));
            ps.setTime(6, java.sql.Time.valueOf(task.getStopTime()));
            ps.setInt(7, task.getDuration());
            ps.setString(8, task.getUserEmail());
            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();

            if (rs.next())
            {
                task.setId((int) rs.getLong(1));
            } else
            {
                return null;
            }

            return task;
        }

    }
    
    public boolean deleteTask(Task task) throws SQLException
    {
        try(Connection con = dbCon.getConnection())
        {
            if(clearTask(task))
            {
            PreparedStatement ps = con.prepareStatement("DELETE FROM [task] WHERE id = ?");
            ps.setInt(1, task.getId());
            
            int updatedRows = ps.executeUpdate();
            
            return updatedRows > 0;
            }
            
        }
        
        return false;
    }
    
    public boolean clearTask(Task task) throws SQLException
    {
        try(Connection con = dbCon.getConnection())
        {
            PreparedStatement ps = con.prepareStatement("DELETE FROM [interval] WHERE taskId = ?");
            ps.setInt(1, task.getId());
            ps.executeUpdate();
            
            PreparedStatement ps2 = con.prepareStatement("SELECT * FROM [interval] WHERE taskId = ?");
            ps2.setInt(1, task.getId());
            ResultSet rs = ps2.executeQuery();
            
            while(rs.next())
            {
                return false;
            }
            
            return true;
        }
    }

    @Override
    public Boolean updateTask(Task task) throws SQLException
    {
        if (!taskExist(task))
        {
            return null;
        }

        System.err.println(task);

        try (Connection con = dbCon.getConnection())
        {
            PreparedStatement ps = con.prepareStatement("UPDATE [task] "
                    + "SET title = ?, projectID = ?, lastUsed = ?, creationDate = ?,"
                    + " startTime = ?, stopTime = ?, duration = ?, userEmail = ?"
                    + " WHERE id = ?");
            ps.setString(1, task.getTitle());
            ps.setInt(2, task.getProject().getId());
            ps.setTimestamp(3, java.sql.Timestamp.valueOf(task.getLastUsed()));
            ps.setDate(4, java.sql.Date.valueOf(task.getCreationDate()));
            ps.setTime(5, java.sql.Time.valueOf(task.getStartTime()));
            ps.setTime(6, java.sql.Time.valueOf(task.getStopTime()));
            ps.setInt(7, task.getDuration());
            ps.setString(8, task.getUserEmail());
            ps.setInt(9, task.getId());

            int updatedRows = ps.executeUpdate();

            return updatedRows > 0;

        }

    }

    @Override
    public Task getTask(Task task) throws SQLException
    {
        if (!taskExist(task))
        {
            return null;
        }

        Task returnTask = null;
        ArrayList<Interval> intervals = new ArrayList<>();
        try (Connection con = dbCon.getConnection())
        {
            PreparedStatement ps = con.prepareStatement("SELECT * FROM [task] WHERE id = ?");

            ps.setInt(1, task.getId());
            ResultSet rs = ps.executeQuery();

            if (rs.next())
            {
                String title = rs.getString("title");
                Project project = projectDBDAO.getProject(new Project(rs.getInt("projectID"), title, new Client(0, title, 0, 0), 0));
                int duration = rs.getInt("duration");
                String projectName = project.getProjectName();
                String clientName = project.getClientName();
                LocalDateTime lastUsed = rs.getTimestamp("lastUsed").toLocalDateTime();
                LocalDate creationDate = rs.getDate("creationDate").toLocalDate();
                LocalTime startTime = rs.getTime("startTime").toLocalTime();
                LocalTime stopTime = rs.getTime("stopTime").toLocalTime();
                String userEmail = rs.getString("userEmail");
                intervals = getIntervals(task);
                User user = userDBDAO.getUser(new User(userEmail, clientName, clientName, title, true));

                returnTask = new Task(task.getId(), title, project, duration, lastUsed, creationDate, startTime, stopTime, user, intervals);

            }

        }

        return returnTask;
    }

    @Override
    public List<Task> getSixTasks(User user) throws SQLException
    {
        ArrayList<Task> tasks = new ArrayList<>();

        try (Connection con = dbCon.getConnection())
        {
            PreparedStatement ps = con.prepareStatement("SELECT TOP 6 * FROM [task] WHERE userEmail = ? ORDER BY lastUsed DESC");

            ps.setString(1, user.getEmail());
            ResultSet rs = ps.executeQuery();

            while (rs.next())
            {
                int id = rs.getInt("id");
                String title = rs.getString("title");
                Project project = projectDBDAO.getProject(new Project(rs.getInt("ProjectID"), title, new Client(id, title, id, id), id));
                int duration = rs.getInt("duration");
                LocalDateTime lastUsed = rs.getTimestamp("lastUsed").toLocalDateTime();
                LocalDate creationDate = rs.getDate("creationDate").toLocalDate();
                LocalTime startTime = rs.getTime("startTime").toLocalTime();
                LocalTime stopTime = rs.getTime("stopTime").toLocalTime();
                String userEmail = rs.getString("userEmail");
                User user1 = userDBDAO.getUser(new User(userEmail, "22", "22", title, false));
                
                ArrayList<Interval> intervals = new ArrayList<>();
                
                intervals.addAll(getIntervals(new Task(id, title, project, duration, lastUsed, creationDate, startTime, stopTime, user, intervals)));
                
                tasks.add(new Task(id, title, project, duration, lastUsed, creationDate, startTime, stopTime, user1, intervals));

            }
            if (tasks.isEmpty())
            {
                System.out.println("no tasks for this user");
            }

            return tasks;

        }
    }

    @Override
    public List<Task> getTasksForUser(User user, LocalDate date) throws SQLException
    {
        ArrayList<Task> tasks = new ArrayList<>();
        String sqlString;
        PreparedStatement ps;

        try (Connection con = dbCon.getConnection())
        {

            if (date == null)
            {
                sqlString = "SELECT * FROM [task] WHERE userEmail = ?";
                ps = con.prepareStatement(sqlString);
                ps.setString(1, user.getEmail());

            } else
            {
                sqlString = "SELECT * FROM [task] WHERE userEmail = ? AND "
                        + "cast(CONVERT(varchar(8), lastUsed, 112) AS date) = ?";
                ps = con.prepareStatement(sqlString);
                ps.setString(1, user.getEmail());
                ps.setDate(2, java.sql.Date.valueOf(date));
            }

            ResultSet rs = ps.executeQuery();

            while (rs.next())
            {
                int id = rs.getInt("id");
                String title = rs.getString("title");
                Project project = projectDBDAO.getProject(new Project(rs.getInt("ProjectID"), title, new Client(id, title, id,
                        id), id));
                int duration = rs.getInt("duration");
                LocalDateTime lastUsed = rs.getTimestamp("lastUsed").toLocalDateTime();
                LocalDate creationDate = rs.getDate("creationDate").toLocalDate();
                LocalTime startTime = rs.getTime("startTime").toLocalTime();
                LocalTime stopTime = rs.getTime("stopTime").toLocalTime();
                ArrayList<Interval> intervals = getIntervals(new Task(id, title, project, duration, lastUsed, 
                        creationDate, startTime, stopTime, user));
                String userEmail = rs.getString("userEmail");
                User user1 = userDBDAO.getUser(new User(userEmail, "22", "22", title, false));
                tasks.add(new Task(id, title, project, duration, lastUsed, creationDate, startTime, 
                        stopTime, user1, intervals));

            }

            return tasks;
        }

    }

    @Override
    public void newInterval(Interval interval) throws SQLServerException, SQLException
    {
        //set last used in the task
        //TODO implement transactions.
        try (Connection con = dbCon.getConnection())
        {
            PreparedStatement ps = con.prepareStatement("INSERT INTO [interval] VALUES (?,?,?,?,?,?)");

            ps.setDate(1, java.sql.Date.valueOf(interval.getCreationDate()));
            ps.setTime(2, java.sql.Time.valueOf(interval.getStartTime()));
            ps.setTime(3, java.sql.Time.valueOf(interval.getStopTime()));
            ps.setInt(4, interval.getIntervalTime());
            ps.setInt(5, interval.getTask().getId());
            ps.setInt(6, interval.getIsPaid());
            
            ps.executeUpdate();
            
            
            String updateLastUsed = "UPDATE [task] SET lastUsed = ?, duration = ? WHERE id = ?";
            PreparedStatement ps2 = con.prepareStatement(updateLastUsed);

            ps2.setTimestamp(1, java.sql.Timestamp.valueOf(LocalDateTime.now()));
            ps2.setInt(2, interval.getTask().getDuration());
            ps2.setInt(3, interval.getTask().getId());

            ps2.executeUpdate();
            
        }
    }
    
    @Override
    public boolean updateInterval( Interval oldInterval, Interval newInterval) throws SQLServerException, SQLException
    {
        if(!intervalExist(oldInterval))
        {
            return false;
        }
        
        
        try (Connection con = dbCon.getConnection())
        {
            String sql = "UPDATE [interval] SET creationDate = ?, startTime = ?, stopTime = ?, intervalTime = ?, isPaid = ? WHERE intervalId = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            
            ps.setDate(1, java.sql.Date.valueOf(newInterval.getCreationDate()));
            ps.setTime(2, java.sql.Time.valueOf(newInterval.getStartTime()));
            ps.setTime(3, java.sql.Time.valueOf(newInterval.getStopTime()));
            ps.setInt(4, newInterval.getIntervalTime());
            ps.setInt(5, newInterval.getIsPaid());
            ps.setInt(6, newInterval.getId());
            
            ps.executeUpdate();
            
            String updateLastUsed = "UPDATE [task] SET lastUsed = ?, duration = ? WHERE id = ?";
            PreparedStatement ps2 = con.prepareStatement(updateLastUsed);

            ps2.setTimestamp(1, java.sql.Timestamp.valueOf(LocalDateTime.now()));
            ps2.setInt(2, newInterval.getTask().getDuration() - oldInterval.getIntervalTime() + newInterval.getIntervalTime());
            ps2.setInt(3, newInterval.getTask().getId());

            
            
            int updatedRows = ps2.executeUpdate();

            return updatedRows > 0;
        }
    }

    private ArrayList<Interval> getIntervals(Task task) throws SQLException
    {
        try (Connection con = dbCon.getConnection())
        {
            ArrayList<Interval> intervals = new ArrayList<>();

            PreparedStatement ps = con.prepareStatement("SELECT * FROM [interval] WHERE interval.taskId = ?");

            ps.setInt(1, task.getId());

            ResultSet rs = ps.executeQuery();
            while (rs.next())
            {
                Date date = rs.getDate("creationDate");
                java.sql.Time intervalStartTime = rs.getTime("startTime");
                java.sql.Time intervalStopTime = rs.getTime("stopTime");
                int intervalTime = rs.getInt("intervalTime");
                int isPaid = rs.getInt("isPaid");
                int id = rs.getInt("intervalId");

                Interval interval = new Interval(id, intervalStartTime.toLocalTime(), intervalStopTime.toLocalTime(), date.toLocalDate(), intervalTime,
                        task, isPaid);

                intervals.add(interval);
            }

            return intervals;
        }
    }
    
    @Override
    public boolean deleteInterval(Interval interval) throws SQLException 
    {
        if(!intervalExist(interval))
        {
            return false;
        }
        
        try(Connection con = dbCon.getConnection())
        { 
            PreparedStatement ps = con.prepareStatement("DELETE FROM [interval] WHERE intervalId = ?");
            ps.setInt(1, interval.getId());
            
            int updatedRows = ps.executeUpdate();
            
            String updateLastUsed = "UPDATE [task] SET lastUsed = ?, duration = ? WHERE id = ?";
            PreparedStatement ps2 = con.prepareStatement(updateLastUsed);

            ps2.setTimestamp(1, java.sql.Timestamp.valueOf(LocalDateTime.now()));
            ps2.setInt(2, interval.getTask().getDuration() - interval.getIntervalTime());
            ps2.setInt(3, interval.getTask().getId());

            ps2.executeUpdate();
            
            return updatedRows > 0;
        }
    }
        
    public boolean intervalExist(Interval interval) throws SQLException 
    {
        try(Connection con = dbCon.getConnection())
        { 
            PreparedStatement ps = con.prepareStatement("SELECT * FROM [interval] WHERE intervalId = ?");
            ps.setInt(1, interval.getId());
            
            ResultSet rs = ps.executeQuery();

            while (rs.next())
            {
                return true;
            }

            return false;
        }  
    }
    
    @Override
    public List<Task> getTasksForUserbetween2Dates(User user, LocalDate fromdate, LocalDate todate) throws SQLException
    {
        ArrayList<Task> tasks = new ArrayList<>();
        String sqlString;
        PreparedStatement ps;

        try (Connection con = dbCon.getConnection())
        {

            
                sqlString = "SELECT DISTINCT  interval.taskId, task.id , task.creationDate,task.duration, task.lastUsed , task.projectID , task.startTime, task.stopTime, task.title, task.userEmail "
                        + "FROM interval "
                        + "INNER JOIN task on interval.taskId = task.id "
                        + "WHERE  task.userEmail = ? AND interval.creationDate >= ? AND interval.creationDate <= ? OR task.creationDate >= ? AND task.creationDate <= ? ";
                ps = con.prepareStatement(sqlString);
                
                ps.setString(1, user.getEmail());
                ps.setDate(2, java.sql.Date.valueOf(fromdate));
                ps.setDate(3, java.sql.Date.valueOf(todate));
                ps.setDate(4, java.sql.Date.valueOf(fromdate));
                ps.setDate(5, java.sql.Date.valueOf(todate));
            

            ResultSet rs = ps.executeQuery();

            while (rs.next())
            {
                int id = rs.getInt("id");
                String title = rs.getString("title");
                Project project = projectDBDAO.getProject(new Project(rs.getInt("ProjectID"), title, new Client(id, title, id,
                        id), id));
                int duration = rs.getInt("duration");
                LocalDateTime lastUsed = rs.getTimestamp("lastUsed").toLocalDateTime();
                LocalDate creationDate = rs.getDate("creationDate").toLocalDate();
                LocalTime startTime = rs.getTime("startTime").toLocalTime();
                LocalTime stopTime = rs.getTime("stopTime").toLocalTime();
                //make the from date to date dal method work for intervals
                ArrayList<Interval> intervals = getIntervalsbetween2Dates(new Task(id, title, project, duration, lastUsed, 
                        creationDate, startTime, stopTime, user),fromdate,todate);
                
                String userEmail = rs.getString("userEmail");
                
                User user1 = userDBDAO.getUser(new User(userEmail, "22", "22", title, false));
                
                tasks.add(new Task(id, title, project, duration, lastUsed, creationDate, startTime, 
                        stopTime, user1, intervals));

            }

            return tasks;
        }

    }
        
        
        //make the from date to date dal method work for intervals
    private ArrayList<Interval> getIntervalsbetween2Dates(Task task, LocalDate fromdate, LocalDate todate) throws SQLException
    {
        try (Connection con = dbCon.getConnection())
        {
            ArrayList<Interval> intervals = new ArrayList<>();

            PreparedStatement ps = con.prepareStatement("SELECT interval.creationDate,interval.intervalId,interval.intervalTime,interval.isPaid, interval.startTime,interval.stopTime,interval.taskId,  task.id , task.userEmail ,task.title "
                                                      + "FROM interval "
                                                      + "INNER JOIN task on interval.taskId = task.id "
                                                      + "WHERE  task.id = ? AND interval.creationDate >= ? AND interval.creationDate <= ? ");

            ps.setInt(1, task.getId());
            ps.setDate(2,  java.sql.Date.valueOf(fromdate));
            ps.setDate(3, java.sql.Date.valueOf(todate));

            ResultSet rs = ps.executeQuery();
            while (rs.next())
            {
                Date date = rs.getDate("creationDate");
                java.sql.Time intervalStartTime = rs.getTime("startTime");
                java.sql.Time intervalStopTime = rs.getTime("stopTime");
                int intervalTime = rs.getInt("intervalTime");
                int isPaid = rs.getInt("isPaid");
                int id = rs.getInt("intervalId");

                Interval interval = new Interval(id, intervalStartTime.toLocalTime(), intervalStopTime.toLocalTime(), date.toLocalDate(), intervalTime,
                        task, isPaid);

                intervals.add(interval);
            }

            return intervals;
        }
    }
        
        
    
    
    
    

    public static void main(String[] args) throws IOException, SQLException
    {
        TaskDBDAO taskDBDAO = new TaskDBDAO();

          User user = new User("standard@user.now", "kok", "kok", "kok", true);
          ArrayList<Interval> intervals = new ArrayList<>();
          Client client = new Client(1, "why", 0, 0);
          Project project = new Project(2, "reeeeeeee", client, 0);
          Task task = new Task(2, "rjo", project, 50, LocalDateTime.now(), LocalDate.now(), LocalTime.now(), LocalTime.now(), user, intervals);
          
          
          System.out.println(taskDBDAO.getDurationFromTasks(project));
          
         // Interval interval = new Interval(15, LocalTime.MIN, LocalTime.MIN, LocalDate.MIN, 0, task, 0);
         // taskDBDAO.deleteInterval(interval);
         
         for (Task tasksForUserbetween2Date : taskDBDAO.getTasksForUserbetween2Dates(user, LocalDate.of(2020, Month.MAY, 1), LocalDate.of(2020, Month.MAY, 14))) {
            
             int i = 1;
             System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++" + " " + tasksForUserbetween2Date);
             
             for (Interval interval : tasksForUserbetween2Date.getIntervals()) {
                 
                 
                 System.out.println( i + "  " + interval + "  " + interval.getTask() );
                 
                 
                 i++;
                 
             }
             
        }
         
         
         
         /*
         int i = 1;
         
         
         
         
         
         
         
         for (Interval intervalsbetween2Date : taskDBDAO.getIntervalsbetween2Dates(task, LocalDate.of(2020, Month.MAY, 3), LocalDate.of(2020, Month.MAY, 6))) {
            
             
             System.out.println(intervalsbetween2Date + " " + i);
             
             i++;
        }
         
         
         
         
         
         
         
          */
//        Client client = new Client(1, "why", 0, 0);
//        Project project = new Project(1, "reeeeeeee", client, 0);
//        User user = taskDBDAO.userDBDAO.getUser(new User("standard@user.now", "kof", "kof", "fok", true));
//        ArrayList<Interval> intervals = new ArrayList<>();
//        Task task = new Task(3, "rjo", project, 50, LocalDateTime.now(), LocalDate.now(), LocalTime.now(), LocalTime.now(), user, intervals);
//        Task task2 = taskDBDAO.getTask(task);

        //taskDBDAO.updateTask(task);
//        ArrayList<Task> t = new ArrayList<>();
//        t.addAll(taskDBDAO.getTasksForUser(user, LocalDate.of(2020, 05, 04)));
//
//        for (Task task1 : t)
//        {
//            System.out.println(task1);
//        }
//        Task task3 = taskDBDAO.getTask(task);
//        task3.setDuration(666);
//        taskDBDAO.updateTask(task3);
        
//        taskDBDAO.deleteTask(task);
//        ArrayList<Task> six = new ArrayList<>();
//        six.addAll(taskDBDAO.getSixTasks(user));
//       
//        for (Task task1 : six)
//        {
//            System.out.println(task1.toString());
//        }

        //ArrayList<Task> six = new ArrayList<>();
        //six.addAll(taskDBDAO.getSixTasks(user));
        //for (Task task1 : six)
        //{
        //System.out.println(task1.toString());
        //}
        //System.out.println(task2);
        // System.out.println(task2);
        //Interval interval = new Interval(LocalTime.now(),LocalTime.now(), 600, 50, task);
        //taskDBDAO.newInterval(interval);
    }

    @Override
    public List<Task> getAllTasks4Project(Project project) throws SQLServerException, SQLException {
                ArrayList<Task> returntasks = new ArrayList<>();

        try (Connection con = dbCon.getConnection())
        {
            PreparedStatement ps = con.prepareStatement("SELECT * FROM [task] WHERE ProjectID = ?");
            ps.setInt(1, project.getId());
            ResultSet rs = ps.executeQuery();

            while (rs.next())
            {
                int id = rs.getInt("id");
                String title = rs.getString("title");
                Project projectoid = projectDBDAO.getProject(new Project(rs.getInt("ProjectID"), title, new Client(id, title, id, id), id));
                int duration = rs.getInt("duration");
                String clientName = project.getClientName();
                LocalDateTime lastUsed = rs.getTimestamp("lastUsed").toLocalDateTime();
                LocalDate creationDate = rs.getDate("creationDate").toLocalDate();
                LocalTime startTime = rs.getTime("startTime").toLocalTime();
                LocalTime stopTime = rs.getTime("stopTime").toLocalTime();

                ArrayList<Interval> intervals = new ArrayList<Interval>();

                String userEmail = rs.getString("userEmail");

                User user = userDBDAO.getUser(new User(userEmail, clientName, clientName, title, false));

                intervals.addAll(getIntervals(new Task(id, title, project, duration, lastUsed, creationDate, startTime, stopTime, user, intervals)));

                returntasks.add(new Task(id, title, project, duration, lastUsed, creationDate, startTime, stopTime, user, intervals));
            }

            return returntasks;

        }
    }

    @Override
    public int getDurationFromTasks(Project project) throws SQLServerException, SQLException {
        int time = 0;
         try (Connection con = dbCon.getConnection())
        {
            PreparedStatement ps = con.prepareStatement("SELECT SUM(task.duration) AS 'sumDuration' FROM [task] WHERE ProjectID = ?");
            ps.setInt(1, project.getId());
            ResultSet rs = ps.executeQuery();

            while (rs.next())
            {
                time = rs.getInt("sumDuration") + time;
            }

            return time;

        }
    }

    

}
