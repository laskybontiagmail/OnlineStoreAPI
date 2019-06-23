package com.lasky.system;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import org.hibernate.Hibernate;
import org.hibernate.collection.spi.PersistentCollection;
import org.hibernate.proxy.HibernateProxy;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.JpaMetamodelEntityInformation;

//Contrary to spring-data-jpa-1.11.5.RELEASE, this has been renamed in spring-data-jpa-2.0.8.RELEASE version
import org.springframework.data.jpa.repository.support.QuerydslJpaRepository;

import com.lasky.utilities.ApplicationProperty;

/**
 * Base class for Repository services
 */
public class BaseRepository<T, I extends Serializable> {
	private transient QuerydslJpaRepository<T, I> repository;
	private EntityManager entityManager;
	private transient int batchSize;
	
	/**
	 * Constructor.
	 * @param entityManager - entity manager
	 */
	public BaseRepository(EntityManager entityManager, Class<T> domainClass) {
		initialise(entityManager);
		
		JpaEntityInformation<T, I> entity = new JpaMetamodelEntityInformation<T, I>(domainClass,
				getEntityManager().getMetamodel());
		setRepository(new QuerydslJpaRepository<T, I>(entity, getEntityManager()));
		
	}

	/**
	 * Constructor.
	 * @param entityManager - entity manager
	 */
	public BaseRepository(EntityManager entityManager) {
		initialise(entityManager);
	}
	
	/**
	 * Initialise
	 */
	private void initialise(EntityManager entityManager) {
		setEntityManager(entityManager);
		setBatchSize(
				Integer.valueOf(ApplicationProperty.get("org.hibernate.cfg.Environment.DEFAULT_BATCH_FETCH_SIZE")));

	}

	public EntityManager getEntityManager() {
		return entityManager;
	}

	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	public QuerydslJpaRepository<T, I> getRepository() {
		return repository;
	}

	public void setRepository(QuerydslJpaRepository<T, I> repository) {
		this.repository = repository;
	}
	
	/**
	 * Checks if obj is an instance of either HibernateProxy or PersistentCollection e.g. javassist
	 * @param object
	 * @return
	 */
	public static <Type> boolean isLazyLoaded(Type object) {
		boolean result = false;
		
		if (object != null && (object instanceof HibernateProxy || object instanceof PersistentCollection)) {
			result = true;
		}
		
		return result;
	}
	
	/**
	 * Converts a proxy object to its original object type (form)
	 * @param source - the object that was queried but in proxy form
	 * @return Type - the type of the desired object
	 * 				  else null if the source object is not a proxy object
	 */
	@SuppressWarnings("unchecked")
	public <SourceType, DestinationType> DestinationType loadLazyLoadedObject(SourceType source, Class<DestinationType> destinationType) {
		DestinationType destination = null;
		
		if (source != null && BaseRepository.isLazyLoaded(source)) {
			Hibernate.initialize((HibernateProxy) source);
			destination = (DestinationType) ((HibernateProxy) source).getHibernateLazyInitializer().getImplementation();
		} else if (source != null && destinationType.isInstance(source)) {
			destination = (DestinationType) source;
		}
		
		return destination;
	}
	
	/**
	 * Converts a list of proxy objects to a list of objects
	 * in their original object type (form)
	 * @param source - the object that was queried but in proxy form
	 * @return Type - the type of the desired object
	 * 				  else null if the source object is not a proxy object
	 */
	public <SourceType, DestinationType> List<DestinationType> loadLazyLoadedObjectsList(
			List<SourceType> sourceList, Class<DestinationType> destinationType) {
		List<DestinationType> destinationList = null;
		
		if (sourceList != null) {
			destinationList = new ArrayList<DestinationType>();
			for (Object source : sourceList) {
				destinationList.add(loadLazyLoadedObject(source, destinationType));
			}
		}
		
		return destinationList;
	}

	public int getBatchSize() {
		return batchSize;
	}

	public void setBatchSize(int batchSize) {
		this.batchSize = batchSize;
	}
	
	/**
	 * Make batch size
	 * @param count - count
	 */
	protected void makeBatchSize(int count) {
		if (count % batchSize == 0) {
			getEntityManager().flush();
			getEntityManager().clear();
		}
	}
	
	/**
	 * Provides a Sort object for queries.
	 * @return - a Sort object for Sorting results by id in ascending order
	 */
	protected Sort sortByIdAsc() {
        return new Sort(Sort.Direction.ASC, "id");
    }
}


