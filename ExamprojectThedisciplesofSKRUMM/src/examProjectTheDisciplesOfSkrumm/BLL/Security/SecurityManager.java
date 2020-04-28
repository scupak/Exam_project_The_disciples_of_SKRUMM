/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package examProjectTheDisciplesOfSkrumm.BLL.Security;

import examProjectTheDisciplesOfSkrumm.BLL.Interface.SecurityManagerInterface;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 *
 * @author lumby
 */
public class SecurityManager implements SecurityManagerInterface
{

    /**
     * encrypts a string using hashing
     *
     * @param password
     * @return hexString
     * @throws SecurityException
     */
    @Override
    public String hashPassword(String password) throws SecurityException
    {
        try
        {
            String base = password;
            MessageDigest digest = null;
            digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(base.getBytes(StandardCharsets.UTF_8));
            StringBuffer hexString = new StringBuffer();
            for (int i = 0; i < hash.length; i++)
            {
                String hex = Integer.toHexString(0xff & hash[i]);
                if (hex.length() == 1)
                {
                    hexString.append('0');
                }
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e)
        {
            throw new SecurityException(e.getMessage());
        }
    }

}
