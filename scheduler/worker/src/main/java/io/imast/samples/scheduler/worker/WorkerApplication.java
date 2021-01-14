package io.imast.samples.scheduler.worker;

import io.imast.core.Lang;
import io.imast.work4j.worker.ClusteringType;
import io.imast.work4j.worker.WorkerConfiguration;
import io.imast.work4j.worker.WorkerException;
import io.imast.work4j.worker.controller.WorkerControllerBuilder;
import io.vavr.control.Try;
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
        
        var local = false;
        
        // indicate if agent (worker) is acting as supervisor
        // Note: any cluster should have only one supervising instance
        var supervise = "SUPERVISOR".equalsIgnoreCase(System.getenv("IMAST_WORKER_ROLE"));
        
        Long pollRate = 0L;
        
        if(supervise){
            pollRate = Duration.ofMinutes(1).toMillis();
        }
        
        String env = null;
        String mysqlhost = "mysqlcluster";
        
        if(local){
            pollRate = Duration.ofMinutes(1).toMillis();
            env = "localhost";
            mysqlhost = "localhost";
        }
        
        // get the cluster name
        var cluster = System.getenv("IMAST_WORKER_CLUSTER_NAME");
        
        // the worker name
        var worker = System.getenv("IMAST_WORKER_WORKER_NAME");
                
        var config = WorkerConfiguration.builder()
                .cluster(cluster)
                .worker(worker)
                .clusteringType(ClusteringType.JDBC)
                .dataSourceUri(String.format("jdbc:mysql://%s:8810/quartz_scheduler", mysqlhost))
                .dataSource("jdbcds")
                .dataSourceUsername("workeruser")
                .dataSourcePassword("workerpassword")
                .pollingRate(pollRate)
                .parallelism(8L)
                .workerRegistrationTries(10)
                .heartbeatRate(Duration.ofMinutes(2).toMillis())
                .build();
        
        // the localhost discovery client (use null in docker environment)
        var discovery = new SimpleDiscoveryClient(env);
        
        // worker channel implementation instance
        var channel = new WorkerChannelImpl(discovery);
        
        var workerController = Try.of(() -> WorkerControllerBuilder
                .builder(config)
                .withChannel(channel)
                .withJob("WAIT_JOB_TYPE", WaitJob.class)
                .withModule("WAIT_JOB_TYPE", "WAITER", new WaiterModule())
                .build()).getOrNull();
        
        try {
        
            workerController.initialize();
            workerController.start();
            
        } catch (WorkerException ex) {
            throw new RuntimeException(ex);
        }
        
        Lang.wait((int)Duration.ofHours(1).toMillis());
    }
}
