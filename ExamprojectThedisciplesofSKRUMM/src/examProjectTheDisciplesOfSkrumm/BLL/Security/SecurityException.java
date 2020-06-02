/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package examProjectTheDisciplesOfSkrumm.BLL.Security;

/**
 * this is class was provided from the groups teacher Peter Stegger
 * github: https://github.com/Stegger/SteggersStudentRegistration/tree/master/src/steggersstudentregistration/bll/security
 * @author SKRUMM
 */
public class SecurityException extends RuntimeException
{

    /**
     * exception that occurs when there are errors present in the security of the program
     * @param message 
     */
    public SecurityException(String message)
    {
        super(message);
    }
}
