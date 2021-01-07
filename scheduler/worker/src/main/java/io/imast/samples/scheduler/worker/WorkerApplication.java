package io.imast.samples.scheduler.worker;

import io.imast.core.Lang;
import io.imast.core.scheduler.ClusteringType;
import io.imast.core.scheduler.JobConstants;
import io.imast.core.scheduler.JobFactory;
import io.imast.core.scheduler.WorkerController;
import io.imast.core.scheduler.WorkerControllerConfig;
import io.imast.core.scheduler.WorkerException;
import io.imast.core.scheduler.quartz.DryRunJob;
import java.time.Duration;
/**
 * The client test app
 * 
 * @author davitp
 */
public class WorkerApplication {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args){
        
        // indicate if agent (worker) is acting as supervisor
        // Note: any cluster should have only one supervising instance
        var supervise = "SUPERVISOR".equalsIgnoreCase(System.getenv("IMAST_WORKER_ROLE"));
        
        // get the cluster name
        var cluster = System.getenv("IMAST_WORKER_CLUSTER_NAME");
        
        // the worker name
        var worker = System.getenv("IMAST_WORKER_WORKER_NAME");
                
        var config = WorkerControllerConfig.builder()
                .cluster(cluster)
                .worker(worker)
                .clusteringType(ClusteringType.JDBC)
                .dataSourceUri("jdbc:mysql://mysqlcluster:8810/quartz_scheduler")
                .dataSource("jdbcds")
                .dataSourceUsername("workeruser")
                .dataSourcePassword("workerpassword")
                .jobSyncRate(Duration.ofMinutes(1))
                .parallelism(8L)
                .supervise(supervise)
                .workerSignalRate(Duration.ofMinutes(2))
                .build();
        
        // an instance of job factory
        var jobFactory = new JobFactory();
        
        // add a simple test job
        jobFactory.registerJobClass(JobConstants.DRY_RUN_JOB_TYPE, DryRunJob.class);
        
        // the localhost discovery client (use null in docker environment)
        var discovery = new SimpleDiscoveryClient(null);
        
        // worker channel implementation instance
        var channel = new WorkerChannelImpl(discovery);
        
        var jobManager = new WorkerController(config, jobFactory, channel);
        
        try {
            jobManager.initialize();
            jobManager.execute();
        } catch (WorkerException ex) {
            throw new RuntimeException(ex);
        }
        
        Lang.wait((int)Duration.ofHours(1).toMillis());
    }
}
