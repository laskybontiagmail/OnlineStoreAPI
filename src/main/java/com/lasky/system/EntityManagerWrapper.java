package com.lasky.system;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.lasky.utilities.Utility;

import lombok.Getter;


@Getter
public class EntityManagerWrapper<OwnerType> extends EntityManagerCloser {
	private static Logger logger = LogManager.getLogger(EntityManagerWrapper.class);
	private Utility utility = Utility.getOnlyInstance();
	
	private OwnerType entityManagerOwner = null;
	private EntityManager entityManager = null;
	private String ownerName = null;
	
	
	public static <OwnerType> EntityManagerWrapper<OwnerType> createNewInstance(EntityManagerFactory entityManagerFactory
		,OwnerType entityManagerOwner) throws Exception {
		
		return new EntityManagerWrapper<OwnerType>(entityManagerFactory ,entityManagerOwner);
	}
	
	protected EntityManagerWrapper(EntityManagerFactory entityManagerFactory ,OwnerType entityManagerOwner) throws Exception {
		if (entityManagerOwner == null) {
			throw new Exception("Entity Manager Owner cannot be null!");
		}
		
		this.entityManagerOwner = entityManagerOwner;
		this.ownerName = this.entityManagerOwner.getClass().getName();
		if (ownerName == null
			|| ownerName.equals("java.lang.Class")
			|| ownerName.equals("Class")
			|| ownerName.equals("java.lang.Object")
			|| ownerName.equals("Object")) {
			throw new Exception("Invalid Entity Manager Owner Type: " + ownerName);
		}
		
		if (entityManagerFactory != null) {
			this.entityManager = entityManagerFactory.createEntityManager();
		}
	}
	
	
	protected void finalize() throws Throwable {
		logger.info("EntityManagerWrapper.finalize() a.k.a. Java Destructor!");
		super.finalize();
		if (entityManager != null && entityManager.isOpen()) {
			logger.error("An EntityManager owned by " + ownerName + " HAS NOT BEEN CLOSED! "
				+ "Please contact System Administrator to have the code fixed ASAP!");
			
			logger.info("trying to close it now...");
			this.closeEntityManager(entityManager);
		}
	}
	
}
