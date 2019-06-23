package com.lasky.test.school.repository;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.lasky.system.BaseRepository;
import com.lasky.test.school.entity.Department;
import com.lasky.test.school.entity.QDepartment;
import com.lasky.utilities.Utility;
import com.querydsl.core.types.Predicate;

public class DepartmentRepositoryImpl extends BaseRepository<Department, Long> implements DepartmentRepository {
	private static Logger logger = LogManager.getLogger(DepartmentRepositoryImpl.class);
	private Utility utility = Utility.getOnlyInstance();
	
	/**
	 * Constructor.
	 * @param entityManager - entity manager
	 */
	public DepartmentRepositoryImpl(EntityManager entityManager) {
		super(entityManager ,Department.class);
	}
	
	@Override
	public Department get(Integer id) throws Exception {
		Department department = null;
		
		if (id != null) {
			QDepartment qDepartment = QDepartment.department;
			Predicate predicate = qDepartment.id.eq(id);
			Optional<Department> result = getRepository().findOne(predicate);
			
			if (result != null && result.isPresent()) {
				department = result.get();
			}
		}
		
		return department;
	}
	
	@Override
	public Department get(String name) throws Exception {
		Department department = null;
		
		if (name != null) {
			QDepartment qDepartment = QDepartment.department;
			Predicate predicate = qDepartment.name.eq(name);
			Optional<Department> result = getRepository().findOne(predicate);
			
			if (result != null && result.isPresent()) {
				department = result.get();
			}
		}
		
		return department;
	}
	
	@Override
	public List<Department> get(Integer pageNumber, Integer pageSize) throws Exception {
		List<Department> departments = null;
		
		//TODO: paging and page size
		
		departments = getRepository().findAll(sortByIdAsc());
		
		return departments;
	}
	
	@Override
	public boolean save(Department department) throws Exception {
		boolean isSaved = false;
		Exception exceptionThrown = null;
		
		if (department != null) {
			try {
				getEntityManager().getTransaction().begin();
				getEntityManager().<Department>merge(department);
				
				isSaved = true;
				
			} catch (Exception exception) {
				utility.logFormattedExceptionStackTrace(logger, exception);
				exceptionThrown = exception;
			} finally {
				try {
					if (isSaved) {
						getEntityManager().getTransaction().commit();
					} else {
						getEntityManager().getTransaction().rollback();
					}
				} catch (Exception subException) {
					utility.logFormattedExceptionStackTrace(logger, subException);
					if (exceptionThrown == null) {
						exceptionThrown = subException;
					}
					isSaved = false; 
				}
			}
		}
		
		if (exceptionThrown != null) {
			throw exceptionThrown;
		}
		
		return isSaved;
	}
	
	@Override
	public boolean delete(Department department) throws Exception {
		boolean isDeleted = false;
		Exception exceptionThrown = null;
		
		if (department != null) {
			try {
				getEntityManager().getTransaction().begin();
				getEntityManager().remove(department);
				
				isDeleted = true;
			} catch (Exception exception) {
				utility.logFormattedExceptionStackTrace(logger, exception);
				exceptionThrown = exception;
			} finally {
				try {
					if (isDeleted) {
						getEntityManager().getTransaction().commit();
					} else {
						getEntityManager().getTransaction().rollback();
					}
				} catch (Exception subException) {
					utility.logFormattedExceptionStackTrace(logger, subException);
					if (exceptionThrown == null) {
						exceptionThrown = subException;
					}
					isDeleted = false; 
				}
			}
		}
		
		if (exceptionThrown != null) {
			throw exceptionThrown;
		}
		
		return isDeleted;
	}
	
}


