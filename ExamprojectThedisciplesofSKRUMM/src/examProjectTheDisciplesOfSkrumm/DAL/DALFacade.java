/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package examProjectTheDisciplesOfSkrumm.DAL;

import com.microsoft.sqlserver.jdbc.SQLServerException;
import examProjectTheDisciplesOfSkrumm.BE.Client;
import examProjectTheDisciplesOfSkrumm.BE.User;
import examProjectTheDisciplesOfSkrumm.DAL.Interface.ClientDBDAOInterface;
import examProjectTheDisciplesOfSkrumm.DAL.Interface.DALFacadeInterface;
import examProjectTheDisciplesOfSkrumm.DAL.Interface.UserDBDAOInterface;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author lumby
 */
public class DALFacade implements DALFacadeInterface
{
    private UserDBDAOInterface userDBDAO;
    private ClientDBDAOInterface clientDBDAO;


    public DALFacade() throws IOException
    {
        userDBDAO = new UserDBDAO();
        clientDBDAO = new ClientDBDAO();
    }
    
    @Override
    public List<User> getAllUsers() throws SQLServerException, SQLException
    {
        return userDBDAO.getAllUsers();
    }

    @Override
    public boolean userExist(User user) throws SQLException
    {
        return userDBDAO.userExist(user);
    }

    @Override
    public User createUser(User user) throws SQLException
    {
        return userDBDAO.createUser(user);
    }

    @Override
    public User getUser(User user) throws SQLException
    {
        return userDBDAO.getUser(user);
    }

    @Override
    public boolean updateUserPassword(User user) throws SQLServerException, SQLException
    {
        return userDBDAO.updateUserPassword(user);
    }

    @Override
    public Client getClient(Client client) throws SQLException
    {
        return clientDBDAO.getClient(client);
    }

    @Override
    public List<Client> getAllClients() throws SQLServerException, SQLException
    {
        return clientDBDAO.getAllClients();
    }

    @Override
    public boolean clientExist(Client client) throws SQLException
    {
        return clientDBDAO.clientExist(client);
    }

    @Override
    public Client createClient(Client client) throws SQLException
    {
        return clientDBDAO.createClient(client);
    }
    
}
