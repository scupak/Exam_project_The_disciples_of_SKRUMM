/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package examProjectTheDisciplesOfSkrumm.GUI.Model.Interface;

import examProjectTheDisciplesOfSkrumm.BE.User;
import java.sql.SQLException;

/**
 *
 * @author lumby
 */
public interface UserModelInterface
{
     public boolean checkUser(User user) throws SQLException;
     
     public String hashPassword(String password);
}
