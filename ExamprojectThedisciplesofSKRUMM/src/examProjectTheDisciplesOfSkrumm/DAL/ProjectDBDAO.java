/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package examProjectTheDisciplesOfSkrumm.DAL;

import com.microsoft.sqlserver.jdbc.SQLServerException;
import examProjectTheDisciplesOfSkrumm.BE.Client;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import examProjectTheDisciplesOfSkrumm.BE.Project;
import examProjectTheDisciplesOfSkrumm.BE.User;
import examProjectTheDisciplesOfSkrumm.DAL.Interface.ProjectDBDAOInterface;
import java.io.IOException;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

/**
 *
 * @author kacpe
 */
public class ProjectDBDAO implements ProjectDBDAOInterface
{
    private final DatabaseConnector dbcon;
    private ClientDBDAO clientdb;

    public ProjectDBDAO() throws IOException {
        dbcon = new DatabaseConnector();
        clientdb = new ClientDBDAO();
    }
    
    //select UserProjectTable.userId, UserProjectTable.projectId, project.id, project.projectName, project.clientID, project.projectrate from UserProjectTable inner join project on UserProjectTable.projectId = project.id where UserProjectTable.userId = 'standard@user.now'

    
    @Override
    public List<Project> getAllProjects() throws SQLServerException, SQLException
    {
        ArrayList<Project> projects = new ArrayList<>();
        
        try (Connection con = dbcon.getConnection())
        {
            PreparedStatement ps = con.prepareStatement("SELECT e.id, e.projectName, e.clientID, e.projectrate, d.id AS Cid, d.name, d.rate, d.isPaid \n" +
                    "FROM project e \n" +
                     "JOIN client d ON e.clientID = d.id");
            ResultSet rs = ps.executeQuery();
            
            while (rs.next())
            {
                int id = rs.getInt("id");
                String projectName = rs.getString("projectName").trim();
                Client client = new Client(rs.getInt("Cid"), rs.getString("name"), rs.getInt("rate"), rs.getInt("isPaid"));
                int projectrate = rs.getInt("projectrate");
                projects.add(new Project(id, projectName, client, projectrate));
                
            }
        }
        return projects;
    }

    @Override
    public boolean projectExist(Project project) throws SQLException 
    {
         try (Connection con = dbcon.getConnection())
        {
            PreparedStatement ps = con.prepareStatement("SELECT * FROM project WHERE id = ?");
            ps.setInt(1, project.getId());
            
            ResultSet rs = ps.executeQuery();
            
            while (rs.next())
            {
                return true;
                
            }
            return false;
        }
       
    }

    @Override
    public Project createProject(Project project) throws SQLException 
    {
        
        try (Connection con = dbcon.getConnection())
        {
            PreparedStatement ps = con.prepareStatement("INSERT INTO [project]"
                    + "(projectName, clientID, projectrate )"
                    + "VALUES (?,?,?)", Statement.RETURN_GENERATED_KEYS);
            
            ps.setString(1, project.getProjectName());
            ps.setInt(2, project.getClient().getId());
            ps.setInt(3, project.getProjectRate());
            ps.executeUpdate();
            
            ResultSet rs = ps.getGeneratedKeys();
            
            if (rs.next())
            {
                project.setId((int) rs.getLong(1));
            }else
            {
                return null;
            }
            System.out.println(project.getClientName() + " Assigned to " + project.getProjectName());
            JOptionPane.showMessageDialog(null, project.getProjectName() + " has been created!");
            return project;
        }
        
    }

    @Override
    public Project getProject(Project project) throws SQLException 
    {
            if (!projectExist(project))
            {
                return null;
            }
            
        
        Client steve = new Client(1, "Steve", 0, 0);
        Project returnproject = null;
         try (Connection con = dbcon.getConnection())
        {
            PreparedStatement ps = con.prepareStatement("SELECT e.id, e.projectName, e.clientID, e.projectrate, d.id AS Cid, d.name, d.rate, d.isPaid \n" +
        "FROM project e \n" +
        "JOIN client d ON e.clientID = d.id\n" +
        "WHERE e.id = ?"); 
            ps.setInt(1, project.getId());
            
            ResultSet rs = ps.executeQuery();
            
            while (rs.next())
            {
                int id = rs.getInt("id");
                String projectName = rs.getString("projectName").trim();
                Client client = new Client(rs.getInt("Cid"), rs.getString("name"), rs.getInt("rate"), rs.getInt("isPaid"));
                int projectrate = rs.getInt("projectrate");
                returnproject = new Project(id, projectName, client, projectrate);
                
            }
            return returnproject;
        }
        
    }
    
    public List<Project> getProjectsForClient(Client client) throws SQLException
    {
        ArrayList<Project> projects = new ArrayList<>();
        
        if(!clientdb.clientExist(client)) return null;
        
        try(Connection con = dbcon.getConnection())
        {
            PreparedStatement ps = con.prepareStatement("SELECT * FROM [project] where clientId = ?");
            ps.setInt(1, client.getId());
            
            ResultSet rs = ps.executeQuery();
            
            while(rs.next())
            {
                int id = rs.getInt("id");
                String projectName = rs.getString("projectName").trim();
                int projectrate = rs.getInt("projectrate");
                Project p = new Project(id, projectName, client, projectrate);
                projects.add(p);
            }
        }
        
        
        
        return projects;
    }
    
    public static void main(String[] args) throws IOException, SQLException
    {   
       
        ClientDBDAO clientdb = new ClientDBDAO();
        ProjectDBDAO projectDb = new ProjectDBDAO();
        ArrayList<Project> projects = new ArrayList<>();
        Client client = new Client(6, "Dick", 0, 0);
        projects.addAll(projectDb.getProjectsForClient(client));
        
        for (Project project : projects)
        {
            System.out.println(project);   
        }
//         System.out.println("  ");
//         System.out.println("  ");
//         System.out.println("  ");  
//         
//           Client steve = new Client(1, "Steve", 0, 0);
//           Client grumsen = clientdb.getClient(steve);
//          Project projectx = new Project(0, "Victory",grumsen , 99);
//          System.out.println(projectDb.createProject(projectx));
//
//        for (Project project : projects)
//        {
//            System.out.println(project);   
//        }
       

    }

    @Override
    public boolean deleteProject(Project project) throws SQLException 
    {
        try(Connection con = dbcon.getConnection())
        {
            if(clearProject(project))
            {
            PreparedStatement ps = con.prepareStatement("DELETE FROM [project] WHERE id = ?");
            ps.setInt(1, project.getId());
            
            int updatedRows = ps.executeUpdate();
            
            return updatedRows > 0;
            }
            
        }
        
        return false;
        
    }

    @Override
    public boolean clearProject(Project project) throws SQLException 
    {
        try(Connection con = dbcon.getConnection())
        {
            PreparedStatement ps = con.prepareStatement("DELETE FROM [task] WHERE projectID = ?");
            ps.setInt(1, project.getId());
            ps.executeUpdate();
            
            PreparedStatement ps2 = con.prepareStatement("SELECT * FROM [task] WHERE projectID = ?");
            ps2.setInt(1, project.getId());
            ResultSet rs = ps2.executeQuery();
            
            while(rs.next())
            {
                return false;
            }
            
            return true;
        }
    }

    @Override
    public boolean updateProject(Project project) throws SQLServerException, SQLException {
        if (!projectExist(project))
        {
            return false;
        }

        System.err.println(project);

        try (Connection con = dbcon.getConnection())
        {
            PreparedStatement ps = con.prepareStatement("UPDATE [project] "
                    + "SET projectName = ?,"
                    + " projectrate = ?, clientID = ?"
                    + " WHERE id = ?");
            ps.setString(1, project.getProjectName());
            ps.setInt(2, project.getProjectRate());
            ps.setInt(3, project.getClient().getId());
            ps.setInt(4, project.getId());

            int updatedRows = ps.executeUpdate();

            return updatedRows > 0;

        }
    }

    @Override
    public List<Project> getAllUserProjects() throws SQLServerException, SQLException {
        ArrayList<Project> projects = new ArrayList<>();
        
        try (Connection con = dbcon.getConnection())
        {
            PreparedStatement ps = con.prepareStatement("SELECT UserProjectTable.userId, UserProjectTable.projectId, " + 
                    "client.id AS Cid, client.name, client.rate, client.isPaid, " + 
                    "project.id, project.projectName, project.clientID, project.projectrate " + 
                    "FROM UserProjectTable INNER JOIN project ON UserProjectTable.projectId = project.id INNER JOIN client ON project.clientID = client.id WHERE UserProjectTable.userId = ?");
            ResultSet rs = ps.executeQuery();
            
            while (rs.next())
            {

                Client client = new Client(rs.getInt("Cid"), rs.getString("name"), rs.getInt("rate"), rs.getInt("isPaid"));
                Project project = new Project(rs.getInt("id"), rs.getString("projectName"), client, rs.getInt("projectrate"));
                projects.add(project);
                
            }
        }
        return projects;
    }
    
}
