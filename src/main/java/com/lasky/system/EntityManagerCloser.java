package com.lasky.system;

import javax.persistence.EntityManager;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.hibernate.Session;

import com.lasky.utilities.Utility;

public class EntityManagerCloser {
	private static Logger logger = LogManager.getLogger(EntityManagerCloser.class);
	private Utility utility = Utility.getOnlyInstance();
	
//	protected void closeEntityManager(EntityManager entityManager) {
//		if (entityManager != null) {
//			entityManager.clear();
//			entityManager.close();
//		}
//	}
	
	protected void closeEntityManager(EntityManager entityManager) {
		try {
			logger.info("Disconnecting the session");
			Session session = entityManager.unwrap(Session.class);
			session.disconnect();
			
			//entityManager.clear(); // not needed/necessary
			logger.info("Closing entityManager");
			entityManager.close();
			
			logger.info("Connection was successfully CLOSED!");
		} catch (Exception exception) {
			logger.info("Connection was NOT properly CLOSED!");
			utility.logFormattedExceptionStackTrace(logger, exception);
		}
	}
	
}


