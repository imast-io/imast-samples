package io.imast.samples.scheduler.resources;


import io.imast.core.scheduler.JobDefinition;
import io.imast.core.scheduler.JobSchedulerCtl;
import io.imast.core.scheduler.JobStatus;
import io.imast.core.scheduler.exchange.JobMetadataRequest;
import io.imast.core.scheduler.exchange.JobStatusExchangeRequest;
import io.imast.core.scheduler.iterate.JobIteration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * The remote controller for jobs
 * 
 * @author davitp
 */
@RestController
@RequestMapping("/api/v1/jobs")
public class JobsController {
    
    /**
     * The job scheduler controller
     */
    @Autowired
    private JobSchedulerCtl schedulerCtl;
    
    /**
     * Add a job definition to controller
     * 
     * @param definition The job definition to add
     * @return Returns added entity
     */
    @PostMapping(path = "")
    public ResponseEntity<?> postOne(@RequestBody JobDefinition definition){
        return ResponseEntity.of(this.schedulerCtl.addJob(definition));
    }
    
    /**
     * Exchange metadata
     * 
     * @param request The metadata request
     * @return Returns metadata
     */
    @PostMapping(path = "_metadata")
    public ResponseEntity<?> statusExchange(@RequestBody JobMetadataRequest request){
        return ResponseEntity.ok(this.schedulerCtl.getMetadata(request));
    }
    
    /**
     * Exchange status and update info
     * 
     * @param status The status on the requester side
     * @return Returns status update
     */
    @PostMapping(path = "_exchange")
    public ResponseEntity<?> statusExchange(@RequestBody JobStatusExchangeRequest status){
        return ResponseEntity.ok(this.schedulerCtl.statusExchange(status));
    }
    
    /**
     * Create an iteration of job 
     * 
     * @param id The job id
     * @param iteration The job iteration
     * @return Returns saved iteration
     */
    @PostMapping(path = "{id}/_iterations")
    public ResponseEntity<?> iterate(@PathVariable String id, @RequestBody JobIteration iteration){
        return ResponseEntity.of(this.schedulerCtl.addIteration(iteration));
    }
    
    /**
     * Update status of job definition
     * 
     * @param id The ID of job
     * @param status The status to set
     * @return Returns updated job
     */
    @PutMapping(path = "{id}/_status")
    public ResponseEntity<?> markAs(@PathVariable String id, @RequestParam JobStatus status){
        return ResponseEntity.of(this.schedulerCtl.markAs(id, status));
    }
}
