/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package examProjectTheDisciplesOfSkrumm.DAL;

import com.microsoft.sqlserver.jdbc.SQLServerException;
import examProjectTheDisciplesOfSkrumm.BE.User;
import examProjectTheDisciplesOfSkrumm.DAL.Interface.UserDBDAInterface;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author lumby
 */
public class UserDBDAO implements UserDBDAInterface
{

    private final DatabaseConnector dbCon;

    public UserDBDAO() throws IOException
    {
        dbCon = new DatabaseConnector();
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

        try (Connection con = dbCon.getConnection())
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
                if (admin != 1)
                {
                    isAdmin = false;
                } else
                {
                    isAdmin = true;
                }
                users.add(new User(email, firstName, lastName, password, isAdmin));
            }
            return users;
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
        try (Connection con = dbCon.getConnection())
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

        try (Connection con = dbCon.getConnection())
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
            ResultSet rs = ps.getGeneratedKeys();

            return user;
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
        try (Connection con = dbCon.getConnection())
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

        try (Connection con = dbCon.getConnection())
        {

            PreparedStatement ps = con.prepareStatement("UPDATE [user] SET password = ? WHERE email = ?");

            ps.setString(1, user.getPassword());
            ps.setString(2, user.getEmail());
            int updatedRows = ps.executeUpdate();

            return updatedRows > 0;

        }
    }

    public static void main(String[] args) throws IOException, SQLException
    {
        UserDBDAO userDb = new UserDBDAO();
        User test1 = new User("standard@user.now", "Mads", "Jensen", "nemt", false);
        User test2 = new User("admin@user.now", "Jakob", "Grumsen", "nemt", true);
        userDb.createUser(test1);
        userDb.createUser(test2);
        //userDb.updateUserPassword(test);
       

    }
}
