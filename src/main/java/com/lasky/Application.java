package com.lasky;

import javax.annotation.PostConstruct;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;

import com.lasky.NativeLibrary.Sample.SampolLaybrariObject;
import com.lasky.system.daemon.service.CustomThreadPool;
import com.lasky.system.daemon.service.SystemDaemon;
import com.lasky.utilities.Utility;


/**
 * Lasky's Site API
 *
 */
@SpringBootApplication
@EnableAutoConfiguration(exclude = {
		DataSourceAutoConfiguration.class,
		DataSourceTransactionManagerAutoConfiguration.class,
		HibernateJpaAutoConfiguration.class})
public class Application {
	private static Logger logger = LogManager.getLogger(Application.class);
	
	@Autowired
	private CustomThreadPool customThreadPool;
	
	@Autowired
	private SystemDaemon systemDaemon;
	
	
    public static void main(String[] args ) {
        System.out.println("Lasky's Site!");
        
        SampolLaybrariObject sampolObject = new SampolLaybrariObject();
        
        SpringApplication.run(Application.class, args);
    }
    
    @PostConstruct
    public void postConstruct() { 
    	this.registerWorkerThreads();
    }
    
    
    private void registerWorkerThreads() {
    	logger.info("registerWorkerThreads() {");
    	Thread registrationThread = new Thread(new Runnable() {
    		@Override
    		public void run() {
    			boolean registerSystemDaemonDone = false;
    			// Add WorkerThread added flag here
    			//boolean serviceRequestsCacheDone = false;
    			//boolean organisationsCacheDone = false;
    			
    			boolean threadIsActive = true;
    			int counter = 0;
    			
    			while(threadIsActive) {
    				logger.info("registerWorkerThreads thread is still active...");
    				try {
    					if (counter > 120) {
        					throw new Exception("ERROR: registering CrmServiceRequestsCache TIMED OUT!!");
        				}
    					
    					if (customThreadPool != null) {
    						if (systemDaemon != null) {
    							logger.info("registering systemDaemon...");
    							customThreadPool.addWorker(systemDaemon);
    							registerSystemDaemonDone = true;
    						}
    						
    					//	if (crmServiceRequestsCache != null) {
    					//		logger.info("registering CrmServiceRequestsCache...");
    					//		customThreadPool.addWorker(crmServiceRequestsCache);
    					//		serviceRequestsCacheDone = true;
    					//	}
    					//	if (crmOrganisationsCache != null) {
    					//		logger.info("registering CrmOrganisationsCache...");
    					//		customThreadPool.addWorker(crmOrganisationsCache);
    					//		organisationsCacheDone = true;
    					//	}
    					}
    					
    					// Add WorkerThread added flag for checking here
    					//if (serviceRequestsCacheDone
    					//	&& organisationsCacheDone) {
    					//	threadIsActive = false;
    					//}
    					if (registerSystemDaemonDone
    						// add more flags here for every worker
    						) {
    						
    						threadIsActive = false;
    					}
    					Thread.sleep(500);
    				} catch (Exception exception) {
    					Utility.getOnlyInstance().logFormattedExceptionStackTrace(logger, exception);
    					logger.info("registerWorkerThreads is unable to sleep!!!");
    				}
    				
    				counter++;
    			}
    			
    			logger.info("registerWorkerThreads thread ended!");
    		}
    	});
    	registrationThread.start();
    	
    	logger.info("} registerWorkerThreads()");
    }
    
}


