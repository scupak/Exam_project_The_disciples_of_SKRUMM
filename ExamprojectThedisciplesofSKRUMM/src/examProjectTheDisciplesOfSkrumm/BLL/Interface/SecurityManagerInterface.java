/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package examProjectTheDisciplesOfSkrumm.BLL.Interface;

/**
 * this is class was provided from the groups teacher Peter Stegger
 * github: https://github.com/Stegger/SteggersStudentRegistration/tree/master/src/steggersstudentregistration/bll/security
 * @author SKRUMM
 */
public interface SecurityManagerInterface {
    
    /**
     * encrypts a string using hashing
     * @param password
     * @return hexString
     * @throws attendance.automation.BLL.Security.SecurityException 
     */
    public String hashPassword(String password) throws examProjectTheDisciplesOfSkrumm.BLL.Security.SecurityException;
    
}
