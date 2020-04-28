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
public class Task 
{
    private String title;
    private Project project;
    private int time;
    private String projectName;
    private String clientName;
    private int isPaid;

    public Task(String title, Project project, int time) {
        this.title = title;
        this.project = project;
        this.time = time;
        this.projectName = project.getProjectName();
        this.clientName = project.getClient().getClientName();
    }
    
    public Task(String title, Project project, int time, int isPaid) 
    {
        this.title = title;
        this.project = project;
        this.time = time;
        this.projectName = project.getProjectName();
        this.clientName = project.getClient().getClientName();
        this.isPaid = isPaid;
    }

    public int getIsPaid()
    {
        return isPaid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
        this.projectName = project.getProjectName();
        this.clientName = project.getClient().getClientName();
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public String getProjectName() {
        return projectName;
    }

    public String getClientName() {
        return clientName;
    }

    
    

    
    

    
    
    
    

   

  

   
    
    
    
}
