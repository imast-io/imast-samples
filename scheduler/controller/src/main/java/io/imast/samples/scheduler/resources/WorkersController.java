package io.imast.samples.scheduler.resources;

import io.imast.work4j.controller.SchedulerController;
import io.imast.work4j.model.cluster.WorkerHeartbeat;
import io.imast.work4j.model.cluster.WorkerInput;
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
 * The workers controller
 * 
 * @author davitp
 */
@RestController
@RequestMapping("/api/v1/scheduler/workers")
public class WorkersController {
    
    /**
     * The scheduler controller
     */
    @Autowired
    private SchedulerController schedulerController;
    
    /**
     * Get all workers
     * 
     * @return Returns all workers
     */
    @GetMapping(path = "")
    public ResponseEntity<?> get(){
        return ResponseEntity.ok(this.schedulerController.getAllWorkers());
    }
    
    /**
     * Get worker by id
     * 
     * @param id The code of agent
     * @return Returns agent
     */
    @GetMapping(path = "{id}")
    public ResponseEntity<?> getSingle(@PathVariable String id){
        return ResponseEntity.of(this.schedulerController.getWorkerById(id));
    }
    
    /**
     * Create and store worker instance
     * 
     * @param worker The worker to register
     * @return Returns saved worker
     */
    @PostMapping(path = "")
    public ResponseEntity<?> postOne(@RequestBody WorkerInput worker){
        return ResponseEntity.ok(this.schedulerController.insertWorker(worker));
    }
    
    /**
     * Update worker heartbeat
     * 
     * @param id The worker id
     * @param heartbeat The heartbeat to update
     * @return Returns saved worker
     */
    @PutMapping(path = "{id}")
    public ResponseEntity<?> putHealth(@PathVariable String id, @RequestBody WorkerHeartbeat heartbeat){
        return ResponseEntity.ok(this.schedulerController.updateWorker(id, heartbeat));
    }
    
    /**
     * Delete worker by id
     * 
     * @param id The id of worker to delete
     * @return Returns deleted agent if available
     */
    @DeleteMapping(path = "{id}")
    public ResponseEntity<?> deleteWorkerById(@PathVariable String id){
        return ResponseEntity.of(this.schedulerController.deleteWorkerById(id));
    }
    
    /**
     * Delete worker (idle or all)
     * 
     * @param idleOnly Indicates if only idles should be deleted
     * @param cluster The target cluster
     * @param name The target name
     * @return Returns deleted workers if available
     */
    @DeleteMapping(path = "", params = { "idleOnly", "cluster", "name" })
    public ResponseEntity<?> deleteWorkers(@RequestParam boolean idleOnly, @RequestParam(required = false) String cluster, @RequestParam(required = false) String name){
        return ResponseEntity.ok(idleOnly ? this.schedulerController.deleteIdleWorkers(cluster, name) : this.schedulerController.deleteWorkers(cluster, name));
    }
}
