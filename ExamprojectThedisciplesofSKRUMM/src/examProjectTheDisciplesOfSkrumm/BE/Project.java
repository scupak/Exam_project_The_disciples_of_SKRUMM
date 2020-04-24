/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package examProjectTheDisciplesOfSkrumm.BE;

/**
 *
 * @author kacpe
 */
public class Project 
{
    private String projectName;
    private Client client;
    private String clientName;

    public Project(String projectName, Client client) {
        this.projectName = projectName;
        this.client = client;
        this.clientName = client.getClientName();
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
        this.clientName = client.getClientName();
    }

    public String getClientName() {
        return clientName;
    }

    
    
    
    
    
            
            
            
    
}
