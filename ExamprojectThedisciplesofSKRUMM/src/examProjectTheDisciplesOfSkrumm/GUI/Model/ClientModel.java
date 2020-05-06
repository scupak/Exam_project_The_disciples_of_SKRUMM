/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package examProjectTheDisciplesOfSkrumm.GUI.Model;

import examProjectTheDisciplesOfSkrumm.BE.Client;
import examProjectTheDisciplesOfSkrumm.BE.Project;
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
 * @author kacpe
 */
public class ClientModel implements ClientModelInterface
{
    private final BLLFacadeInterface bllfacade;
    ObservableList<Client> clients;

    public ClientModel() throws IOException 
    {
        bllfacade = new BLLFacade();
        this.clients = FXCollections.observableArrayList();
    }

    @Override
    public ObservableList<Client> getClients() 
    {   
        clients.clear();
       
        try {
            clients.addAll(bllfacade.getAllClients());
        } catch (SQLException ex) {
            Logger.getLogger(ClientModel.class.getName()).log(Level.SEVERE, null, ex);
        }
        return clients;
    }

    public void setClients(ObservableList<Client> clients) {
        this.clients = clients;
    }
    
    @Override
    public void createClient(Client client)
    {
        try {
            bllfacade.createClient(client);
        } catch (SQLException ex) {
            Logger.getLogger(ClientModel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public boolean deleteClient(Client client) throws SQLException {
        return bllfacade.deleteClient(client);
    }
    
}


