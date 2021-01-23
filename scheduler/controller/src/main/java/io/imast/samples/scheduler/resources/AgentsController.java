package io.imast.samples.scheduler.resources;

import io.imast.work4j.controller.SchedulerController;
import io.imast.work4j.model.agent.AgentDefinition;
import io.imast.work4j.model.agent.AgentHealth;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * The agent definitions controller
 * 
 * @author davitp
 */
@RestController
@RequestMapping("/api/v1/scheduler/agents")
public class AgentsController {
    
    /**
     * The scheduler controller
     */
    @Autowired
    private SchedulerController schedulerCtl;
    
    /**
     * Get all agent definitions
     * 
     * @return Returns all agents
     */
    @GetMapping(path = "")
    public ResponseEntity<?> get(){
        return ResponseEntity.ok(this.schedulerCtl.getAgents());
    }
    
    /**
     * Get agent definition by id
     * 
     * @param id The code of agent
     * @return Returns agent
     */
    @GetMapping(path = "{id}")
    public ResponseEntity<?> getSingle(@PathVariable String id){
        return ResponseEntity.of(this.schedulerCtl.getAgent(id));
    }
    
    /**
     * Create and store agent
     * 
     * @param agent The agent to register
     * @return Returns saved agent
     */
    @PostMapping(path = "")
    public ResponseEntity<?> postAgent(@RequestBody AgentDefinition agent){
        return ResponseEntity.of(this.schedulerCtl.registration(agent));
    }
    
    /**
     * Update agent health
     * 
     * @param id The agent id
     * @param health The health to update
     * @return Returns saved signal
     */
    @PutMapping(path = "{id}/health")
    public ResponseEntity<?> putHealth(@PathVariable String id, @RequestBody AgentHealth health){
        return ResponseEntity.of(this.schedulerCtl.heartbeat(id, health));
    }
    
    /**
     * Delete agent by id
     * 
     * @param id The id of agent to delete
     * @return Returns deleted agent if available
     */
    @DeleteMapping(path = "{id}")
    public ResponseEntity<?> deleteAgent(@PathVariable String id){
        return ResponseEntity.of(this.schedulerCtl.deleteAgent(id));
    }
}
