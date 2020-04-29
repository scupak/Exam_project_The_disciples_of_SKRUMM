/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package examProjectTheDisciplesOfSkrumm.GUI.Model;

import examProjectTheDisciplesOfSkrumm.BE.User;
import examProjectTheDisciplesOfSkrumm.BLL.BLLFacade;
import examProjectTheDisciplesOfSkrumm.BLL.Interface.BLLFacadeInterface;
import examProjectTheDisciplesOfSkrumm.GUI.Model.Interface.UserModelInterface;
import java.io.IOException;
import java.sql.SQLException;

/**
 *
 * @author lumby
 */
public class UserModel implements UserModelInterface
{
     private final BLLFacadeInterface bllFacade;
     
     public UserModel() throws IOException
     {
         bllFacade = new BLLFacade();
     }
     
     public boolean checkUser(User user) throws SQLException
     {
        user.setIsAdmin(bllFacade.getUser(user).getIsAdmin());
         return bllFacade.checkUser(user);
     }
     
     public String hashPassword(String password)
     {
         return bllFacade.hashPassword(password);
     }
}
