package io.imast.samples.scheduler.worker;

import io.imast.core.Zdt;
import io.imast.work4j.worker.job.BaseQuartzJob;
import java.time.ZoneId;
import lombok.extern.slf4j.Slf4j;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 * A quartz job that will wait for some time
 * 
 * @author davitp
 */
@Slf4j
public class WaitJob extends BaseQuartzJob {
    
    /**
     * The job implementation based on quartz 
     * 
     * @param arg0 The job execution context
     * @throws JobExecutionException 
     */
    private void executeImpl(JobExecutionContext arg0) throws JobExecutionException {
            
        // get waiter module for this job type
        var waiter = this.<WaiterModule>getContextModule("WAITER", arg0);
        
        // check if waiter module is there
        if(waiter == null){
            throw new JobExecutionException("A waiter module is required for this job");
        }
        
        // get time to wait (1 sec by default)
        var timeToWait = (int)this.getDataValue(arg0, "TIME", 1000);
        
        log.info(String.format("Started Wait Job %s at %s. Waiting %s milliseconds...", this.getCode(arg0), Zdt.now(ZoneId.systemDefault().toString()), timeToWait));

        // wait for...
        waiter.waitMillis(timeToWait);
        
        log.info(String.format("Completed Wait Job %s (for %s milliseconds) at %s", this.getCode(arg0), timeToWait, Zdt.now(ZoneId.systemDefault().toString())));
    }
    
    /**
     * The job implementation based on quartz 
     * 
     * @param arg0 The job execution context
     * @throws JobExecutionException 
     */
    @Override
    public void execute(JobExecutionContext arg0) throws JobExecutionException {
        this.executeImpl(arg0);
    } 
}
