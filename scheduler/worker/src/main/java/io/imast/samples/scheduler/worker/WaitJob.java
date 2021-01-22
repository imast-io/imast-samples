package io.imast.samples.scheduler.worker;

import io.imast.core.Zdt;
import io.imast.work4j.execution.JobExecutorBase;
import io.imast.work4j.execution.JobExecutorContext;
import io.imast.work4j.execution.JobExecutorException;
import java.time.ZoneId;
import lombok.extern.slf4j.Slf4j;

/**
 * A quartz job that will wait for some time
 * 
 * @author davitp
 */
@Slf4j
public class WaitJob extends JobExecutorBase {
    
    /**
     * Creates new instance based on context
     * 
     * @param context The context of executor
     */
    public WaitJob(JobExecutorContext context){
        super(context);
    }
    
    /**
     * The job implementation based on quartz 
     * 
     * @throws JobExecutorException 
     */
    @Override
    public void execute() throws JobExecutorException {
            
        // get waiter module for this job type
        var waiter = this.context.<WaiterModule>getModuleOr("WAITER", null);
        
        // check if waiter module is there
        if(waiter == null){
            throw new JobExecutorException("A waiter module is required for this job");
        }
        
        // get time to wait (1 sec by default)
        var timeToWait = this.context.getValue("TIME", 1000);
        
        log.info(String.format("Started Wait Job %s at %s. Waiting %s milliseconds...", this.context.getCode(), Zdt.now(ZoneId.systemDefault().toString()), timeToWait));

        // wait for...
        waiter.waitMillis(timeToWait);
        
        log.info(String.format("Completed Wait Job %s (for %s milliseconds) at %s", this.context.getCode(), timeToWait, Zdt.now(ZoneId.systemDefault().toString())));
    }
}
