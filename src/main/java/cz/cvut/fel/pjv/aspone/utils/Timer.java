package cz.cvut.fel.pjv.aspone.utils;

import java.io.Serializable;

/**
 * The type Timer.
 *
 * @author Mukan Atazhanov
 * @project atazhmuk
 * @created 01 /04/2022 - 16:12
 */
public class Timer extends Thread{
    /**
     * The Count.
     */
    int count = 0;
    /**
     * The Exit.
     */
    boolean exit = false;
    boolean interrupted;

    public void run(){
        while(!exit){
            count++;
            try {
                sleep(0,1);
            } catch (InterruptedException e) {
                e.printStackTrace();
                interrupted = true;
            }
            if(interrupted) {
                Thread.currentThread().interrupt();
            }
        }
    }

    /**
     * stops counter
     */
    public void stopThread(){
        exit = true;
    }

    /**
     * Get time int.
     *
     * @return - time required to make move
     */
    public int getTime(){
        stopThread();
        return count;
    }
}
