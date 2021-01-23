package io.imast.samples.scheduler.resources;

import io.imast.work4j.controller.SchedulerController;
import io.imast.work4j.model.iterate.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * The Scheduler Jobs Controller
 * 
 * @author davitp
 */
@RestController
@RequestMapping("/api/v1/scheduler/iterations")
public class IterationsController {
    
    /**
     * The job scheduler controller
     */
    @Autowired
    private SchedulerController schedulerCtl;
       
    /**
     * Get iterations of job by id
     * 
     * @param jobId The ID of requested job
     * @param status The iteration status
     * @param page The page number
     * @param size The page size
     * @return Returns found job iterations
     */
    @GetMapping(path = "", params = { "jobId", "status", "page", "size" })
    public ResponseEntity<?> getIterations(@RequestParam String jobId, @RequestParam(required = false) IterationStatus status, @RequestParam int page, @RequestParam int size){
        return ResponseEntity.ok(this.schedulerCtl.getIterations(jobId, status, page, size));
    }
    
    /**
     * Get iterations of job 
     * 
     * @param status The iteration status
     * @param page The page number
     * @param size The page size
     * @return Returns found job iterations
     */
    @GetMapping(path = "", params = { "status", "page", "size" })
    public ResponseEntity<?> getIterations(@RequestParam(required = false) IterationStatus status, @RequestParam int page, @RequestParam int size){
        return ResponseEntity.ok(this.schedulerCtl.getIterations(null, status, page, size));
    }
    
    /**
     * Create an iteration of job 
     * 
     * @param iteration The job iteration
     * @return Returns saved iteration
     */
    @PostMapping(path = "")
    public ResponseEntity<?> postIteration(@RequestBody JobIteration iteration){
        return ResponseEntity.of(this.schedulerCtl.iterate(iteration));
    }
}
