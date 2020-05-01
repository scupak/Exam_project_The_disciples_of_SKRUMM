/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package examProjectTheDisciplesOfSkrumm.BLL.Util;

import com.sun.javafx.tools.packager.Main;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import javafx.application.Platform;
import javafx.scene.control.Label;

/**
 *
 * @author Zanaxdk <https://github.com/zanaxdk>
 */
public class TimerUtil implements Runnable
{
    private int sec = 0;
    private int min = 0;
    private int hour = 0;
    private int intervalsec = 0;
    private int intervalmin = 0; 
    private int intervalhour = 0; 
    private String totalTimeString;
    private String intervalTimeString;
    private boolean isRunning = true;
    private Label totaltimeLabel;
    private Label intervalLabel;
    private int totalSec = 0;
    private int totalIntervalsec = 0;

    public TimerUtil(Label intervalLabel,Label totaltimeLabel, int totalSec) {
        this.totalSec = totalSec;
        while(totalSec >= 3600){
        totalSec  = totalSec - 3600;
        hour++;
        System.out.println("added one to hours...");
        }
        
        while(totalSec >= 60){
        totalSec = totalSec - 60;
        min++;
        System.out.println("added one to min...");
        }
        
        sec = totalSec;
        System.out.println("added rest of seconds to sec...");
        
        totalTimeString = String.format("%02d", hour) + ":" + String.format("%02d", min) + ":" + String.format("%02d", sec);
        System.out.println(totalTimeString);
        
        //while totalSec is over 3600, totalSec minus 3600, and add 1 to hour until it isnt anymore
        //when done, while totalSec is over 60, totalSec minus 60 and add 1 to min until it isnt anymore
        //add rest of seconds to sec
        this.totaltimeLabel = totaltimeLabel;
    }
    

    @Override
    public void run()
    {
        while (!Thread.interrupted()) {
                if (isRunning) {

                    try {
                        Thread.sleep(1000);

                        sec++;
                        intervalsec++;
                        totalSec++;

                        if (sec >= 60) {
                            min++;
                            intervalmin++;
                            intervalsec = 0;
                            sec = 0;
                        }

                        if (min >= 60) {
                            hour++;
                            intervalhour++;
                            min = 0;
                            intervalmin = 0;
                        }

                        totalTimeString = String.format("%02d", hour) + ":" + String.format("%02d", min) + ":" + String.format("%02d", sec);
                        intervalTimeString = String.format("%02d", intervalhour) + ":" + String.format("%02d", intervalmin) + ":" + String.format("%02d", intervalsec);
                        System.out.println("totalTimeString" + "    " +totalTimeString);
                        System.out.println("intervalTimeString" + "    " +intervalTimeString);
                        System.out.println("totalSec" + "    " + totalSec);
                        System.out.println("intervalsec" + "    " + intervalsec);
                        
                            if(totaltimeLabel != null){
                            Platform.runLater (() -> 
                            {   
                             totaltimeLabel.setText(String.format("%02d", hour) + ":" + String.format("%02d", min) + ":" + String.format("%02d", sec));
                            });
                        }
                            if(totaltimeLabel == null){
                            System.out.println("Nonexistant totaltimeLabel is nonexistant, putting relevant data into time String. This is a Null Pointer");
                            }  
                    }
                    catch (InterruptedException e){
                    }

                }
                else{
                    break;
                }

            }
        
        System.err.println("Interrupted" + Thread.currentThread().getName());
    }
    
     public String getTotalTimestring(){
    return  totalTimeString;
    }
     
     public String getintervalTimeString(){
     
         return intervalTimeString;
     
     
     }
            
    public int getTotalsec(){
    return totalSec;
    }

    public Label getTotalTimeLabel() {
        return totaltimeLabel;
    }

    public void setTotalTimeLabel(Label timeLabel) {
        this.totaltimeLabel = timeLabel;
    }

    public boolean isIsRunning() {
        return isRunning;
    }

    public void setIsRunning(boolean isRunning) {
        this.isRunning = isRunning;
    }
    
    
    
    
                
    public static void main(String[] args){
        int totSec = 123456;
        
        //giving label null, as it works with null
        TimerUtil tu = new TimerUtil(null,null, totSec);
        
        ExecutorService executorService = 
            Executors.newFixedThreadPool(1);
        
        executorService.submit(tu);
        
        
        
        //executorService.shutdownNow();
    }
    
}
