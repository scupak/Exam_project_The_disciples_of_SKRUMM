/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package examProjectTheDisciplesOfSkrumm.DAL.Interface;

import com.microsoft.sqlserver.jdbc.SQLServerException;
import examProjectTheDisciplesOfSkrumm.BE.User;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author lumby
 */
public interface UserDBDAInterface
{
    public List<User> getAllUsers() throws SQLServerException, SQLException;
    
    public boolean userExist(User user) throws SQLException;
    
    public User createUser(User user) throws SQLException;
    
    public User getUser(User user) throws SQLException;
    
    public boolean updateUserPassword(User user) throws SQLServerException, SQLException;
}