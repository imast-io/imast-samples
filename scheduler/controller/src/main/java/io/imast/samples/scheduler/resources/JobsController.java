package io.imast.samples.scheduler.resources;

import io.imast.work4j.controller.SchedulerController;
import io.imast.work4j.model.JobDefinition;
import io.imast.work4j.model.JobStatus;
import io.imast.work4j.model.exchange.JobMetadataRequest;
import io.imast.work4j.model.exchange.JobStatusExchangeRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
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
@RequestMapping("/api/v1/scheduler/jobs")
public class JobsController {
    
    /**
     * The job scheduler controller
     */
    @Autowired
    private SchedulerController schedulerCtl;
    
    /**
     * Gets all the jobs in the system
     * 
     * @param type The optional type parameter
     * @return Returns set of jobs
     */
    @GetMapping(path = "")
    public ResponseEntity<?> getAll(@RequestParam(required = false) String type){
        return ResponseEntity.ok(this.schedulerCtl.getAllJobs(type));
    }
        
    /**
     * Gets all job definitions by page 
     * 
     * @param page The page
     * @param size The page size
     * @return Returns set of job definitions
     */
    @GetMapping(path = "", params = {"page", "size"})
    public ResponseEntity<?> getPage(@RequestParam Integer page, @RequestParam Integer size){
        return ResponseEntity.ok(this.schedulerCtl.getJobsPage(page, size));
    }
    
    /**
     * Find the job by id
     * 
     * @param id The ID of requested job
     * @return Returns found job
     */
    @GetMapping(path = "{id}")
    public ResponseEntity<?> getById(@PathVariable String id){
        return ResponseEntity.of(this.schedulerCtl.getJob(id));
    }
    
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
    public ResponseEntity<?> metadataExchange(@RequestBody JobMetadataRequest request){
        return ResponseEntity.of(this.schedulerCtl.metadata(request));
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
     * Update status of job definition
     * 
     * @param id The ID of job
     * @param status The status to set
     * @return Returns updated job
     */
    @PutMapping(path = "{id}/status")
    public ResponseEntity<?> markAs(@PathVariable String id, @RequestParam JobStatus status){
        return ResponseEntity.of(this.schedulerCtl.markAs(id, status));
    }
    
    /**
     * Update and store job definition
     * 
     * @param id The ID of job
     * @param job The job to update
     * @return Returns updated job
     */
    @PutMapping(path = "{id}")
    public ResponseEntity<?> put(@PathVariable String id, @RequestBody JobDefinition job){
        return ResponseEntity.of(this.schedulerCtl.updateJob(job));
    }
    
    /**
     * Delete job
     * 
     * @param id The job id to remove
     * @return Returns removed job
     */
    @DeleteMapping(path = "{id}")
    public ResponseEntity<?> delete(@PathVariable String id){
        return ResponseEntity.of(this.schedulerCtl.deleteJob(id));
    }
}
