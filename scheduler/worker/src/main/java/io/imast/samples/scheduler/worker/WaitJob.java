package io.imast.samples.scheduler.worker;

import io.imast.core.Zdt;
import io.imast.core.scheduler.quartz.BaseQuartzJob;
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
        
        // get job definition
        var definition = this.getJobDefinition(arg0);
        
        // make sure there is definition
        if(definition == null){
            throw new JobExecutionException("No Job Definition Found");
        }
        
        // get waiter module for this job type
        var waiter = this.<WaiterModule>getContextModule("WAITER", arg0);
        
        // check if waiter module is there
        if(waiter == null){
            throw new JobExecutionException("A waiter module is required for this job");
        }
        
        // get code from job 
        var code = definition.getCode();
        
        // the job data 
        var data = definition.getJobData().getData();
        
        // get time to wait (1 sec by default)
        var timeToWait = (int)data.getOrDefault("TIME", 1000);
        
        log.info(String.format("Started Wait Job %s at %s. Waiting %s milliseconds...", code, Zdt.now(ZoneId.systemDefault().toString()), timeToWait));

        // wait for...
        waiter.waitMillis(timeToWait);
        
        log.info(String.format("Completed Wait Job %s (for %s milliseconds) at %s", code, timeToWait, Zdt.now(ZoneId.systemDefault().toString())));
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
