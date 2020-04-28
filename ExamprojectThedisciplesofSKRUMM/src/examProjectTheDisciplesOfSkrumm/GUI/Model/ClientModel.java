/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package examProjectTheDisciplesOfSkrumm.GUI.Model;

import examProjectTheDisciplesOfSkrumm.BE.Client;
import examProjectTheDisciplesOfSkrumm.BE.Project;
import examProjectTheDisciplesOfSkrumm.GUI.Model.Interface.ClientModelInterface;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author kacpe
 */
public class ClientModel implements ClientModelInterface
{
    ObservableList<Client> clients;

    public ClientModel() 
    {
        this.clients = FXCollections.observableArrayList();
    }

    public ObservableList<Client> getClients() {
        return clients;
    }

    public void setClients(ObservableList<Client> clients) {
        this.clients = clients;
    }
    
    public void createClient(Client client)
    {
        clients.add(client);
    }
    
}


