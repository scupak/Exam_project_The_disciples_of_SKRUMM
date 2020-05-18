/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package examProjectTheDisciplesOfSkrumm.GUI.Model;

import com.microsoft.sqlserver.jdbc.SQLServerException;
import examProjectTheDisciplesOfSkrumm.BE.Client;
import examProjectTheDisciplesOfSkrumm.BLL.BLLFacade;
import examProjectTheDisciplesOfSkrumm.BLL.Interface.BLLFacadeInterface;
import examProjectTheDisciplesOfSkrumm.GUI.Model.Interface.ClientModelInterface;
import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author SKRUMM
 */
public class ClientModel implements ClientModelInterface
{
    private final BLLFacadeInterface bllfacade;
    ObservableList<Client> clients;

    public ClientModel() throws IOException, Exception 
    {
        bllfacade = new BLLFacade();
        this.clients = FXCollections.observableArrayList();
    }

    /**
     * gets clients
     * @return list of clients
     */
    @Override
    public synchronized ObservableList<Client> getClients() 
    {   
        clients.clear();
       
        try {
            clients.addAll(bllfacade.getAllClients());
        } catch (SQLException ex) {
            Logger.getLogger(ClientModel.class.getName()).log(Level.SEVERE, null, ex);
        }
        return clients;
    }

    /**
     * sets clients to the given list
     * @param clients 
     */
    public void setClients(ObservableList<Client> clients) {
        this.clients = clients;
    }
    
    /**
     * creates a client
     * @param client 
     */
    @Override
    public void createClient(Client client)
    {
        try {
            bllfacade.createClient(client);
        } catch (SQLException ex) {
            Logger.getLogger(ClientModel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * deletes a client
     * @param client
     * @return true if operation successful, false otherwise
     * @throws SQLException 
     */
    @Override
    public boolean deleteClient(Client client) throws SQLException {
        return bllfacade.deleteClient(client);
    }

    /**
     * updates a client
     * @param client
     * @return @return true if operation successful, false otherwise
     * @throws SQLServerException
     * @throws SQLException 
     */
    @Override
    public boolean updateClient(Client client) throws SQLServerException, SQLException {
        return bllfacade.updateClient(client);
    }
    
}


