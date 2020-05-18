/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package examProjectTheDisciplesOfSkrumm.BLL.Security;

/**
 *
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
