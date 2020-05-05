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
        if (taskExist(task))
        {
            return null;
        }

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
            PreparedStatement ps = con.prepareStatement("DELTE FROM [task] WHERE id = ?");
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
            
            PreparedStatement ps2 = con.prepareStatement("SELECT FROM [interval] WHERE taskId = ?");
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
                ArrayList<Interval> intervals = new ArrayList<Interval>();
                String userEmail = rs.getString("userEmail");
                User user1 = userDBDAO.getUser(new User(userEmail, "22", "22", title, false));
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
                Project project = projectDBDAO.getProject(new Project(rs.getInt("ProjectID"), title, new Client(id, title, id, id), id));
                int duration = rs.getInt("duration");
                LocalDateTime lastUsed = rs.getTimestamp("lastUsed").toLocalDateTime();
                LocalDate creationDate = rs.getDate("creationDate").toLocalDate();
                LocalTime startTime = rs.getTime("startTime").toLocalTime();
                LocalTime stopTime = rs.getTime("stopTime").toLocalTime();
                ArrayList<Interval> intervals = new ArrayList<>();
                String userEmail = rs.getString("userEmail");
                User user1 = userDBDAO.getUser(new User(userEmail, "22", "22", title, false));
                tasks.add(new Task(id, title, project, duration, lastUsed, creationDate, startTime, stopTime, user1, intervals));

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
            String sql = "INSERT INTO [interval] VALUES (?,?,?,?,?)";
            PreparedStatement ps = con.prepareStatement(sql);

            ps.setDate(1, java.sql.Date.valueOf(interval.getCreationDate()));
            ps.setTime(2, java.sql.Time.valueOf(interval.getStartTime()));
            ps.setTime(3, java.sql.Time.valueOf(interval.getStopTime()));
            ps.setInt(4, interval.getIntervalTime());
            ps.setInt(5, interval.getTask().getId());

            ps.executeUpdate();

            String updateLastUsed = "UPDATE [task] SET lastUsed = ?, duration = ? WHERE id = ?";
            PreparedStatement ps2 = con.prepareStatement(updateLastUsed);

            ps2.setTimestamp(1, java.sql.Timestamp.valueOf(LocalDateTime.now()));
            ps2.setInt(2, interval.getTotalTime());
            ps2.setInt(3, interval.getTask().getId());

            ps2.executeUpdate();
        }
    }

    private List<Interval> getIntervals(Task task) throws SQLException
    {
        try (Connection con = dbCon.getConnection())
        {
            List<Interval> intervals = new ArrayList<>();

            PreparedStatement ps = con.prepareStatement("SELECT * FROM [interval] WHERE interval.taskId = ?");

            ps.setInt(1, task.getId());

            ResultSet rs = ps.executeQuery();
            while (rs.next())
            {
                Date date = rs.getDate("creationDate");
                java.sql.Time intervalStartTime = rs.getTime("startTime");
                java.sql.Time intervalStopTime = rs.getTime("stopTime");
                int intervalTime = rs.getInt("intervalTime");

                Interval interval = new Interval(intervalStartTime.toLocalTime(), intervalStopTime.toLocalTime(), date.toLocalDate(), intervalTime,
                        task);

                intervals.add(interval);
            }

            return intervals;
        }
    }

    public static void main(String[] args) throws IOException, SQLException
    {
        TaskDBDAO taskDBDAO = new TaskDBDAO();

        Client client = new Client(1, "why", 0, 0);
        Project project = new Project(1, "reeeeeeee", client, 0);
        User user = taskDBDAO.userDBDAO.getUser(new User("standard@user.now", "kof", "kof", "fok", true));
        ArrayList<Interval> intervals = new ArrayList<>();
        Task task = new Task(3, "rjo", project, 50, LocalDateTime.now(), LocalDate.now(), LocalTime.now(), LocalTime.now(), user, intervals);
        Task task2 = taskDBDAO.getTask(task);

        //taskDBDAO.updateTask(task);
        ArrayList<Task> t = new ArrayList<>();
        t.addAll(taskDBDAO.getTasksForUser(user, LocalDate.of(2020, 05, 04)));

        for (Task task1 : t)
        {
            System.out.println(task1);
        }
        Task task3 = taskDBDAO.getTask(task);
        task3.setDuration(666);
        taskDBDAO.updateTask(task3);
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

}
