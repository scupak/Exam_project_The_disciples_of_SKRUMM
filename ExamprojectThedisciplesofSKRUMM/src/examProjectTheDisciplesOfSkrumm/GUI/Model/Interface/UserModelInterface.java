/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package examProjectTheDisciplesOfSkrumm.GUI.Model.Interface;

import com.microsoft.sqlserver.jdbc.SQLServerException;
import examProjectTheDisciplesOfSkrumm.BE.User;
import java.sql.SQLException;
import java.util.List;
import javafx.collections.ObservableList;

/**
 *
 * @author lumby
 */
public interface UserModelInterface
{
     public boolean checkUser(User user) throws SQLException;
     
     public String hashPassword(String password);
     
     public User getUser(User user) throws SQLException;
    
     public ObservableList<User> getAllUsers() throws SQLServerException, SQLException;
    
     public boolean userExist(User user) throws SQLException;
    
    public User createUser(User user) throws SQLException;
    
    public User getCurrentuser();
    
    public void setCurrentuser(User currentuser);
    
    public boolean updateUser(User oldUser, User newUser) throws SQLServerException, SQLException;
}
