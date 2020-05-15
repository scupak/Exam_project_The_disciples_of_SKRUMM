/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package examProjectTheDisciplesOfSkrumm.DAL;

import com.microsoft.sqlserver.jdbc.SQLServerException;
import examProjectTheDisciplesOfSkrumm.BE.Client;
import examProjectTheDisciplesOfSkrumm.BE.Project;
import examProjectTheDisciplesOfSkrumm.BE.User;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import examProjectTheDisciplesOfSkrumm.DAL.Interface.UserDBDAOInterface;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author lumby
 */
public class UserDBDAO implements UserDBDAOInterface
{

    private final ConnectionPool conPool;

    public UserDBDAO() throws IOException, Exception
    {
        this.conPool = ConnectionPool.getInstance();
    }

    /**
     * gets a list of all the users in the database
     *
     * @return users
     * @throws SQLServerException
     * @throws SQLException
     */
    @Override
    public List<User> getAllUsers() throws SQLServerException, SQLException
    {
        ArrayList<User> users = new ArrayList<>();
        Connection con = conPool.checkOut();

        try
        {
            PreparedStatement ps = con.prepareStatement("SELECT * FROM [user]");
            ResultSet rs = ps.executeQuery();

            while (rs.next())
            {
                String email = rs.getString("email");
                String firstName = rs.getString("firstName");
                String lastName = rs.getString("lastName");
                String password = rs.getString("password");
                int admin = rs.getByte("isAdmin");
                boolean isAdmin;
                if (admin == 1)
                {
                    isAdmin = true;
                } else
                {
                    isAdmin = false;
                }
                users.add(new User(email, firstName, lastName, password, isAdmin));
            }
            return users;
        }
        finally
        {
            conPool.checkIn(con);
        }
    }

    /**
     * checks if a user exist in the database
     *
     * @param user
     * @return boolean
     * @throws SQLException
     */
    @Override
    public boolean userExist(User user) throws SQLException
    {
        Connection con = conPool.checkOut();
        
        try
        {
            PreparedStatement ps = con.prepareStatement("SELECT * FROM [user] WHERE email = ?");
            ps.setString(1, user.getEmail());

            ResultSet rs = ps.executeQuery();

            while (rs.next())
            {
                return true;
            }

            return false;
        }
        finally
        {
            conPool.checkIn(con);
        }
    }

    /**
     * creates a user in the dataabase
     *
     * @param user
     * @return user
     * @throws SQLException
     */
    @Override
    public User createUser(User user) throws SQLException
    {
        if (userExist(user))
        {
            return null;
        }
        
        Connection con = conPool.checkOut();

        try
        {
            PreparedStatement ps = con.prepareStatement("INSERT INTO [user]"
                    + "(email, firstName, lastName, password, isAdmin) "
                    + "VALUES (?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, user.getEmail());
            ps.setString(2, user.getFirstName());
            ps.setString(3, user.getLastName());
            ps.setString(4, user.getPassword());

            byte isAdmin;
            if (user.getIsAdmin() == true)
            {
                isAdmin = 1;
            } else
            {
                isAdmin = 0;
            }
            ps.setByte(5, isAdmin);
            ps.executeUpdate();
            

            return user;
        }
        finally
        {
            conPool.checkIn(con);
        }
    }

    /**
     * gets a specific user from the database
     *
     * @param user
     * @return returnStudent
     * @throws SQLException
     */
    @Override
    public User getUser(User user) throws SQLException
    {
        if (!userExist(user))
        {
            return null;
        }

        User returnUser;
        Connection con = conPool.checkOut();
        try
        {
            PreparedStatement ps = con.prepareStatement("SELECT * FROM [user] WHERE email = ?");

            ps.setString(1, user.getEmail());
            ResultSet rs = ps.executeQuery();

            if (rs.next())
            {
                String email = rs.getString("email");
                String firstName = rs.getString("firstName");
                String lastName = rs.getString("lastName");
                String password = rs.getString("password");
                int admin = rs.getByte("isAdmin");
                boolean isAdmin;
                if (admin == 1)
                {
                    isAdmin = true;
                } else
                {
                    isAdmin = false;
                }
                returnUser = new User(email, firstName, lastName, password, isAdmin);
            } else
            {
                return null;
            }
            return returnUser;
        }
        finally
        {
            conPool.checkIn(con);
        }
    }

    /**
     * updates a users password
     *
     * @param user
     * @return boolean
     * @throws SQLServerException
     * @throws SQLException
     */
    @Override
    public boolean updateUserPassword(User user) throws SQLServerException, SQLException
    {
        if (!userExist(user))
        {
            return false;
        }
        
        Connection con = conPool.checkOut();

        try
        {

            PreparedStatement ps = con.prepareStatement("UPDATE [user] SET password = ? WHERE email = ?");

            ps.setString(1, user.getPassword());
            ps.setString(2, user.getEmail());
            int updatedRows = ps.executeUpdate();

            return updatedRows > 0;

        }
        finally
        {
            conPool.checkIn(con);
        }
    }

    public static void main(String[] args) throws IOException, SQLException
    {
        UserDBDAO userDb = null;
        try {
            userDb = new UserDBDAO();
        } catch (Exception ex) {
            Logger.getLogger(UserDBDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
       // User test1 = new User("standard@user.now", "Mads", "Jensen", "nemt", false);
        //User test2 = new User("admin@user.now", "Jakob", "Grumsen", "nemt", true);
        User test67 = new User("standard@user.now", "No", "Yes", "ok", true);
        Project p = new Project(3, "projectName", new Client(1, "ClientName", 0, 0), 0);
        
        userDb.deleteProjectFromUser(test67, p);
        
        //userDb.createUser(test1);
        //userDb.createUser(test2);
       /* userDb.createUser(test67);
        System.out.println("User67 is " + userDb.userExist(test67));
        System.out.println("User67 was " + userDb.getUser(test67));
        //userDb.updateUserPassword(test);
        userDb.updateUser(test67, new User("email@email.email", "firstName", "lastName", "pass", false));
        System.out.println("User67 is now " + userDb.getUser(test67));*/
       
       /*
        for (Project allUserProject : userDb.getAllUserProjects(test67)) {
            
            System.out.println(allUserProject);
            
        }
       */

    }

    @Override
    public boolean updateUser(User oldUser, User newUser) throws SQLServerException, SQLException {
        if (!userExist(oldUser))
        {
            return false;
        }
        
        Connection con = conPool.checkOut();

        try
        {

            PreparedStatement ps = con.prepareStatement("UPDATE [user] SET password = ?, email =?, firstname =?, lastname =?, isadmin =? WHERE email = ?");

            ps.setString(1, newUser.getPassword());
            ps.setString(2, newUser.getEmail());
            ps.setString(3, newUser.getFirstName());
            ps.setString(4, newUser.getLastName());
            
            byte isAdmin;
            if (newUser.getIsAdmin() == true)
            {
                isAdmin = 1;
            } 
            else
            {
                isAdmin = 0;
            }
            
            ps.setByte(5, isAdmin);
            ps.setString(6, oldUser.getEmail());
            
            int updatedRows = ps.executeUpdate();

            return updatedRows > 0;

        }
        finally
        {
            conPool.checkIn(con);
        }
    }

    @Override
    public boolean deleteUser(User user) throws SQLException {
        
        Connection con = conPool.checkOut();
        try
        {

            PreparedStatement ps = con.prepareStatement("DELETE FROM [user] WHERE email = ?");
            ps.setString(1, user.getEmail());
            
            int updatedRows = ps.executeUpdate();
            
            return updatedRows > 0;
            
        }
        finally
        {
            conPool.checkIn(con);
        }
        
    }
     @Override
    public List<Project> getAllUserProjects(User user) throws SQLServerException, SQLException {
        
        if (!userExist(user))
        {
            return null;
        }
        
         
        ArrayList<Project> projects = new ArrayList<>();
        
        Connection con = conPool.checkOut();
        
        try
        {
            PreparedStatement ps = con.prepareStatement("SELECT UserProjectTable.userId, UserProjectTable.projectId, " + 
                    "client.id AS Cid, client.name, client.rate, client.isPaid, " + 
                    "project.id, project.projectName, project.clientID, project.projectrate " + 
                    "FROM UserProjectTable INNER JOIN project ON UserProjectTable.projectId = project.id INNER JOIN client ON project.clientID = client.id WHERE UserProjectTable.userId = ?");
            
            ps.setString(1, user.getEmail());
            ResultSet rs = ps.executeQuery();
            
            while (rs.next())
            {

                Client client = new Client(rs.getInt("Cid"), rs.getString("name"), rs.getInt("rate"), rs.getInt("isPaid"));
                Project project = new Project(rs.getInt("id"), rs.getString("projectName"), client, rs.getInt("projectrate"));
                projects.add(project);
                
            }
        }
        finally
        {
            conPool.checkIn(con);
        }
        return projects;
    }

    @Override
    public boolean addUserToProject(User user, Project project) throws SQLServerException, SQLException {
        Connection con = conPool.checkOut();
        try  
        {
            PreparedStatement ps = con.prepareStatement("INSERT INTO UserProjectTable "
                    + "(userId, projectId) VALUES (?,?)");
            ps.setString(1, user.getEmail());
            ps.setInt(2, project.getId());

            return ps.executeUpdate() > 0;

        } 
        
        catch (SQLServerException ex) 
        {
             throw new SQLServerException("could not add to project!", ex);
             
        }
        
        catch (SQLException ex) 
        {
             throw new SQLException("could not add to project!", ex);
        }
        finally
        {
            conPool.checkIn(con);
        }
    }

    @Override
    public boolean deleteProjectFromUser(User user, Project project) throws SQLServerException, SQLException {
        
        Connection con = conPool.checkOut();
        
        try 
        {
            PreparedStatement ps = con.prepareStatement(
                    "DELETE FROM UserProjectTable WHERE projectId = ? "
                    + "AND userId = ? "); 
            ps.setInt(1, project.getId());
            ps.setString(2, user.getEmail());
            
            int updatedRows = ps.executeUpdate();

            if (updatedRows > 0) return true;

        } 
        catch (SQLServerException ex) {
            throw new SQLServerException("could not clear user from project", ex);
        } 
        catch (SQLException ex) {
            throw new SQLException("could not clear user from project", ex);
        }
        finally
        {
            conPool.checkIn(con);
        }

        return false;
    }
}
