package io.imast.samples.scheduler.worker;

import io.imast.core.Lang;

/**
 * A module to inject into a job as needed 
 * 
 * @author davitp
 */
public class WaiterModule {
   
    /**
     * Wait for some time
     * 
     * @param millis Milliseconds to wait
     */
    public void waitMillis(long millis){
        Lang.wait((int)millis);
    }
}
