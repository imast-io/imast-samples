package io.imast.samples.scheduler.config;

import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import io.imast.work4j.controller.JobSchedulerCtl;
import io.imast.work4j.data.impl.AgentDefinitionMongoRepository;
import io.imast.work4j.data.impl.JobDefinitionMongoRepository;
import io.imast.work4j.data.impl.JobIterationMongoRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

/**
 * The main configuration beans
 * 
 * @author davitp
 */
@Configuration
@Slf4j
public class MainConfiguration {
    
    /**
     * The mongo URI
     */
    @Value("${imast.data.mongo.uri}")
    private String mongoUri;
    
    /**
     * The mongo database
     */
    @Value("${imast.data.mongo.db}")
    private String databaseName;
    
    /**
     * The mongo database for communication
     * 
     * @return Returns mongo database
     */
    @Lazy
    @Bean
    public MongoDatabase mongoDatabase(){
        return MongoClients.create(this.mongoUri).getDatabase(this.databaseName);
    }
    
    /**
     * The scheduler controller bean
     * 
     * @return Returns scheduler controller bean
     */
    @Lazy
    @Bean
    public JobSchedulerCtl schedulerCtl(){
        
        var definitions = new JobDefinitionMongoRepository(this.mongoDatabase());
        var agents = new AgentDefinitionMongoRepository(this.mongoDatabase());
        var iterations = new JobIterationMongoRepository(this.mongoDatabase());
                
        return new JobSchedulerCtl(definitions, iterations, agents).initialize();
    }
}
