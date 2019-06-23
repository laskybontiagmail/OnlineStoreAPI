package com.lasky.test.school.service;

import java.util.List;

import javax.persistence.EntityManagerFactory;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.lasky.OnlineStoreAPI.rest.model.CustomResponseBody;
import com.lasky.OnlineStoreAPI.users.service.UserProfilesService;
import com.lasky.system.DatasourceConnection;
import com.lasky.system.EntityManagerHandlerFactory;
import com.lasky.system.EntityManagerWrapper;
import com.lasky.system.GenericService;
import com.lasky.test.school.entity.Department;
import com.lasky.test.school.entity.Student;
import com.lasky.test.school.repository.DepartmentRepository;
import com.lasky.test.school.repository.DepartmentRepositoryImpl;
import com.lasky.utilities.CustomMap;
import com.lasky.utilities.Utility;

@Service
public class DepartmentService extends GenericService {
	private static Logger logger = LogManager.getLogger(DepartmentService.class);
	private Utility utility = Utility.getOnlyInstance();
	
	public ResponseEntity<CustomResponseBody<Department>> getDepartment(Integer id) {
		String methodName = "getDepartment";
		logger.info(methodName + "(" + "id: " + id + ") {");
		
		ResponseEntity<CustomResponseBody<Department>> responseEntity = null;
		CustomResponseBody<Department> responseBody = null;
		Department department = null;
		String responseMessage = null;
		HttpStatus httpStatus = null;
		DatasourceConnection datasourceConnection = DatasourceConnection.getDefault();
		EntityManagerWrapper<DepartmentService> entityManagerWrapper = null;
		EntityManagerFactory entityManagerFactory = null;
		DepartmentRepository departmentRepository = null;
		
		try {
			if (id == null || id < 1) {
				httpStatus = HttpStatus.BAD_REQUEST;
				responseMessage = "Bad Request Department ID required!";
				throw new Exception(responseMessage);
			}
			entityManagerFactory = EntityManagerHandlerFactory
				.factory()
				.getEntityManagerFactory(datasourceConnection);
			if (entityManagerFactory == null) {
				httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
				responseMessage = "Unable to connect to database server! Contact adminstrator or developer!";
				throw new Exception(responseMessage);
			}
			entityManagerWrapper = EntityManagerWrapper.<DepartmentService>createNewInstance(entityManagerFactory ,this);
			if (entityManagerWrapper.getEntityManager() == null) {
				httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
				responseMessage = "Unable to create entity manager! Contact adminstrator or developer!";
				throw new Exception(responseMessage);
			}
			
			departmentRepository = new DepartmentRepositoryImpl(entityManagerWrapper.getEntityManager());
			department = departmentRepository.get(id);
			
			if (department != null ) {
				httpStatus = HttpStatus.OK;
				responseMessage = "Department with ID: " + id + " successfully retrieved!";
				responseBody = new CustomResponseBody<Department>();
				responseBody.setMessage(responseMessage);
				responseBody.setBody(department);
				responseEntity = new ResponseEntity<CustomResponseBody<Department>>(responseBody, httpStatus);
			} else {
				httpStatus = HttpStatus.NOT_FOUND;
				responseMessage = "Department: " + id + " NOT FOUND!";
				throw new Exception(responseMessage);
			}
			
		}  catch (Exception exception) {
			httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
			logger.error("ERROR on " + methodName);
			utility.logFormattedExceptionStackTrace(logger, exception);
		} finally {
			closeEntityManager(entityManagerWrapper.getEntityManager());
			if (responseBody == null) {
				responseBody = new CustomResponseBody<Department>();
				responseBody.setMessage(responseMessage);
			}
			if (responseEntity == null) {
				responseEntity = new ResponseEntity<CustomResponseBody<Department>>(responseBody ,httpStatus);
			}
		}
		
		logger.info("} " + methodName + "()");
		return responseEntity;
	}
	
	public ResponseEntity<CustomResponseBody<Department>> getDepartment(String name) {
		String methodName = "getDepartment";
		logger.info(methodName + "(" + "name: " + name + ") {");
		
		ResponseEntity<CustomResponseBody<Department>> responseEntity = null;
		CustomResponseBody<Department> responseBody = null;
		Department department = null;
		String responseMessage = null;
		HttpStatus httpStatus = null;
		DatasourceConnection datasourceConnection = DatasourceConnection.getDefault();
		EntityManagerWrapper<DepartmentService> entityManagerWrapper = null;
		EntityManagerFactory entityManagerFactory = null;
		DepartmentRepository departmentRepository = null;
		
		try {
			if (utility.isEmptyOrNullIgnoreTrailingWhiteSpaces(name)) {
				httpStatus = HttpStatus.BAD_REQUEST;
				responseMessage = "Bad Request Department Name required!";
				throw new Exception(responseMessage);
			}
			entityManagerFactory = EntityManagerHandlerFactory
				.factory()
				.getEntityManagerFactory(datasourceConnection);
			if (entityManagerFactory == null) {
				httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
				responseMessage = "Unable to connect to database server! Contact adminstrator or developer!";
				throw new Exception(responseMessage);
			}
			entityManagerWrapper = EntityManagerWrapper.<DepartmentService>createNewInstance(entityManagerFactory ,this);
			if (entityManagerWrapper.getEntityManager() == null) {
				httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
				responseMessage = "Unable to create entity manager! Contact adminstrator or developer!";
				throw new Exception(responseMessage);
			}
			
			departmentRepository = new DepartmentRepositoryImpl(entityManagerWrapper.getEntityManager());
			department = departmentRepository.get(name);
			
			if (department != null ) {
				httpStatus = HttpStatus.OK;
				responseMessage = "Department with name: " + name + " successfully retrieved!";
				responseBody = new CustomResponseBody<Department>();
				responseBody.setMessage(responseMessage);
				responseBody.setBody(department);
				responseEntity = new ResponseEntity<CustomResponseBody<Department>>(responseBody, httpStatus);
			} else {
				httpStatus = HttpStatus.NOT_FOUND;
				responseMessage = "Department: " + name + " NOT FOUND!";
				throw new Exception(responseMessage);
			}
			
		}  catch (Exception exception) {
			httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
			logger.error("ERROR on " + methodName);
			utility.logFormattedExceptionStackTrace(logger, exception);
		} finally {
			closeEntityManager(entityManagerWrapper.getEntityManager());
			if (responseBody == null) {
				responseBody = new CustomResponseBody<Department>();
				responseBody.setMessage(responseMessage);
			}
			if (responseEntity == null) {
				responseEntity = new ResponseEntity<CustomResponseBody<Department>>(responseBody ,httpStatus);
			}
		}
		
		logger.info("} " + methodName + "()");
		return responseEntity;
	}
	
	public ResponseEntity<CustomResponseBody<List<Department>>> getDepartments(Integer pageNumber, Integer pageSize) {
		String methodName = "getDepartments";
		logger.info(methodName + "("
			+ "pageNumber: " + pageNumber + ", pageSize: " + pageSize
			+ ") {");
		
		ResponseEntity<CustomResponseBody<List<Department>>> responseEntity = null;
		CustomResponseBody<List<Department>> responseBody = null;
		List<Department> departments = null;
		String responseMessage = null;
		HttpStatus httpStatus = null;
		DatasourceConnection datasourceConnection = DatasourceConnection.getDefault();
		EntityManagerWrapper<DepartmentService> entityManagerWrapper = null;
		EntityManagerFactory entityManagerFactory = null;
		DepartmentRepository departmentRepository = null;
		
		//TODO: paging ang page size
		try {
			entityManagerFactory = EntityManagerHandlerFactory
				.factory()
				.getEntityManagerFactory(datasourceConnection);
			if (entityManagerFactory == null) {
				httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
				responseMessage = "Unable to connect to database server! Contact adminstrator or developer!";
				throw new Exception(responseMessage);
			}
			entityManagerWrapper = EntityManagerWrapper.<DepartmentService>createNewInstance(entityManagerFactory ,this);
			if (entityManagerWrapper.getEntityManager() == null) {
				httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
				responseMessage = "Unable to create entity manager! Contact adminstrator or developer!";
				throw new Exception(responseMessage);
			}
			
			departmentRepository = new DepartmentRepositoryImpl(entityManagerWrapper.getEntityManager());
			departments = departmentRepository.get(pageNumber, pageSize);
			
			if (utility.isNotNullAndEmpty(departments)) {
				httpStatus = HttpStatus.OK;
				responseMessage = "Departments successfully retrieved!";
				responseBody = new CustomResponseBody<List<Department>>();
				responseBody.setMessage(responseMessage);
				responseBody.setBody(departments);
				responseEntity = new ResponseEntity<CustomResponseBody<List<Department>>>(responseBody, httpStatus);
			} else {
				httpStatus = HttpStatus.NOT_FOUND;
				responseMessage = "NO Department FOUND!";
				throw new Exception(responseMessage);
			}
			
		}  catch (Exception exception) {
			httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
			logger.error("ERROR on " + methodName);
			utility.logFormattedExceptionStackTrace(logger, exception);
		} finally {
			closeEntityManager(entityManagerWrapper.getEntityManager());
			if (responseBody == null) {
				responseBody = new CustomResponseBody<List<Department>>();
				responseBody.setMessage(responseMessage);
			}
			if (responseEntity == null) {
				responseEntity = new ResponseEntity<CustomResponseBody<List<Department>>>(responseBody ,httpStatus);
			}
		}
		
		logger.info("} " + methodName + "()");
		return responseEntity;
	}
	
	public ResponseEntity<CustomResponseBody<Boolean>> addDepartment(Department department) {
		String methodName = "addDepartment";
		logger.info(methodName + "("
			+ "department: " + utility.toJsonString(department)
			+ ") {");
		
		ResponseEntity<CustomResponseBody<Boolean>> responseEntity = null;
		CustomResponseBody<Boolean> responseBody = null;
		
		String responseMessage = null;
		HttpStatus httpStatus = null;
		DatasourceConnection datasourceConnection = DatasourceConnection.getDefault();
		EntityManagerWrapper<DepartmentService> entityManagerWrapper = null;
		EntityManagerFactory entityManagerFactory = null;
		DepartmentRepository departmentRepository = null;
		
		try {
			entityManagerFactory = EntityManagerHandlerFactory
				.factory()
				.getEntityManagerFactory(datasourceConnection);
			if (entityManagerFactory == null) {
				httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
				responseMessage = "Unable to connect to database server! Contact adminstrator or developer.";
				throw new Exception(responseMessage);
			}
			entityManagerWrapper = EntityManagerWrapper.<DepartmentService>createNewInstance(entityManagerFactory ,this);
			if (entityManagerWrapper.getEntityManager() == null) {
				httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
				responseMessage = "Unable to create entity manager! Contact adminstrator or developer.";
				throw new Exception(responseMessage);
			}
			if (department == null) {
				httpStatus = HttpStatus.BAD_REQUEST;
				responseMessage = "Bad Request! proper Department required!";
				throw new Exception(responseMessage);
			}
			//TODO: check if department already exists or new department object is valid for adding
			
			departmentRepository = new DepartmentRepositoryImpl(entityManagerWrapper.getEntityManager());
			
//			// Set departments for Student objects so that the foreign key won't be null
//			Student student = null;
//			for (int index = 0; index < department.getStudents().size(); index++) {
//				student = department.getStudents().get(index);
//				student.setDepartment(department);
//			}
			
			if (departmentRepository.save(department)) {
				httpStatus = HttpStatus.OK;
				responseMessage = "Successfully added Department!";
				responseBody = new CustomResponseBody<Boolean>();
				responseBody.setMessage(responseMessage);
				responseBody.setBody(Boolean.TRUE);
				responseEntity = new ResponseEntity<CustomResponseBody<Boolean>>(responseBody ,httpStatus);
			} else {
				httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
				responseMessage = "Department cannot be saved! Contact adminstrator or developer.";
				throw new Exception(responseMessage);
			}
			
		}  catch (Exception exception) {
			httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
			logger.error("ERROR on " + methodName);
			utility.logFormattedExceptionStackTrace(logger, exception);
		} finally {
			closeEntityManager(entityManagerWrapper.getEntityManager());
			if (responseBody == null) {
				responseBody = new CustomResponseBody<Boolean>();
				responseBody.setMessage(responseMessage);
				responseBody.setBody(Boolean.FALSE);
			}
			if (responseEntity == null) {
				responseEntity = new ResponseEntity<CustomResponseBody<Boolean>>(responseBody ,httpStatus);
			}
		}
		
		logger.info("} " + methodName + "()");
		return responseEntity;
	}
	
	public ResponseEntity<CustomResponseBody<Boolean>> updateDepartment(Department department) {
		String methodName = "updateDepartment";
		logger.info(methodName + "("
			+ "department: " + utility.toJsonString(department)
			+ ") {");
		
		ResponseEntity<CustomResponseBody<Boolean>> responseEntity = null;
		CustomResponseBody<Boolean> responseBody = null;
		
		String responseMessage = null;
		HttpStatus httpStatus = null;
		DatasourceConnection datasourceConnection = DatasourceConnection.getDefault();
		EntityManagerWrapper<DepartmentService> entityManagerWrapper = null;
		EntityManagerFactory entityManagerFactory = null;
		DepartmentRepository departmentRepository = null;
		Department fetchedDepartment = null;
		
		try {
			entityManagerFactory = EntityManagerHandlerFactory
				.factory()
				.getEntityManagerFactory(datasourceConnection);
			if (entityManagerFactory == null) {
				httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
				responseMessage = "Unable to connect to database server! Contact adminstrator or developer.";
				throw new Exception(responseMessage);
			}
			entityManagerWrapper = EntityManagerWrapper.<DepartmentService>createNewInstance(entityManagerFactory ,this);
			if (entityManagerWrapper.getEntityManager() == null) {
				httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
				responseMessage = "Unable to create entity manager! Contact adminstrator or developer.";
				throw new Exception(responseMessage);
			}
			if (department == null || department.getId() == null) {
				httpStatus = HttpStatus.BAD_REQUEST;
				responseMessage = "Bad Request! proper Department required!";
				throw new Exception(responseMessage);
			}
			
			departmentRepository = new DepartmentRepositoryImpl(entityManagerWrapper.getEntityManager());
			
			// fetch the record to be updated from the DB
			fetchedDepartment = departmentRepository.get(department.getId());
			
			// update records
			if (!simpleUpdateDepartment(department, fetchedDepartment)) {
				httpStatus = HttpStatus.BAD_REQUEST;
				responseMessage = "Bad Request! Kind of update is not supported!";
				throw new Exception(responseMessage);
			}
			
			// save updates to the DB
			if (departmentRepository.save(department)) {
				httpStatus = HttpStatus.OK;
				responseMessage = "Successfully updated Department!";
				responseBody = new CustomResponseBody<Boolean>();
				responseBody.setMessage(responseMessage);
				responseBody.setBody(Boolean.TRUE);
				responseEntity = new ResponseEntity<CustomResponseBody<Boolean>>(responseBody ,httpStatus);
			} else {
				httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
				responseMessage = "Department cannot be saved! Contact adminstrator or developer.";
				throw new Exception(responseMessage);
			}
			
		}  catch (Exception exception) {
			httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
			logger.error("ERROR on " + methodName);
			utility.logFormattedExceptionStackTrace(logger, exception);
		} finally {
			closeEntityManager(entityManagerWrapper.getEntityManager());
			if (responseBody == null) {
				responseBody = new CustomResponseBody<Boolean>();
				responseBody.setMessage(responseMessage);
				responseBody.setBody(Boolean.FALSE);
			}
			if (responseEntity == null) {
				responseEntity = new ResponseEntity<CustomResponseBody<Boolean>>(responseBody ,httpStatus);
			}
		}
		
		logger.info("} " + methodName + "()");
		return responseEntity;
	}
	
	/**
	 * This will delete the department as well as the students under it.
	 * @param departmentId
	 * @return
	 */
	public ResponseEntity<CustomResponseBody<Boolean>> deleteDepartment(Integer departmentId) {
		String methodName = "updateDepartment";
		logger.info(methodName + "("
			+ "departmentId: " + utility.toJsonString(departmentId)
			+ ") {");
		
		ResponseEntity<CustomResponseBody<Boolean>> responseEntity = null;
		CustomResponseBody<Boolean> responseBody = null;
		
		String responseMessage = null;
		HttpStatus httpStatus = null;
		DatasourceConnection datasourceConnection = DatasourceConnection.getDefault();
		EntityManagerWrapper<DepartmentService> entityManagerWrapper = null;
		EntityManagerFactory entityManagerFactory = null;
		DepartmentRepository departmentRepository = null;
		Department fetchedDepartment = null;
		
		try {
			if (departmentId == null || departmentId < 1) {
				httpStatus = HttpStatus.BAD_REQUEST;
				responseMessage = "Bad Request Department ID required!";
				throw new Exception(responseMessage);
			}
			entityManagerFactory = EntityManagerHandlerFactory
				.factory()
				.getEntityManagerFactory(datasourceConnection);
			if (entityManagerFactory == null) {
				httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
				responseMessage = "Unable to connect to database server! Contact adminstrator or developer.";
				throw new Exception(responseMessage);
			}
			entityManagerWrapper = EntityManagerWrapper.<DepartmentService>createNewInstance(entityManagerFactory ,this);
			if (entityManagerWrapper.getEntityManager() == null) {
				httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
				responseMessage = "Unable to create entity manager! Contact adminstrator or developer.";
				throw new Exception(responseMessage);
			}
			
			departmentRepository = new DepartmentRepositoryImpl(entityManagerWrapper.getEntityManager());
			// fetch the record to be updated from the DB
			fetchedDepartment = departmentRepository.get(departmentId);
			if (fetchedDepartment == null) {
				httpStatus = HttpStatus.BAD_REQUEST;
				responseMessage = "Bad Request! Department: " + departmentId + " could NOT be FOUND!";
				throw new Exception(responseMessage);
			}
			
			// save updates to the DB
			if (departmentRepository.delete(fetchedDepartment)) {
				httpStatus = HttpStatus.OK;
				responseMessage = "Successfully deleted the Department!";
				responseBody = new CustomResponseBody<Boolean>();
				responseBody.setMessage(responseMessage);
				responseBody.setBody(Boolean.TRUE);
				responseEntity = new ResponseEntity<CustomResponseBody<Boolean>>(responseBody ,httpStatus);
			} else {
				httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
				responseMessage = "Department cannot be saved! Contact adminstrator or developer.";
				throw new Exception(responseMessage);
			}
			
		}  catch (Exception exception) {
			httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
			logger.error("ERROR on " + methodName);
			utility.logFormattedExceptionStackTrace(logger, exception);
		} finally {
			closeEntityManager(entityManagerWrapper.getEntityManager());
			if (responseBody == null) {
				responseBody = new CustomResponseBody<Boolean>();
				responseBody.setMessage(responseMessage);
				responseBody.setBody(Boolean.FALSE);
			}
			if (responseEntity == null) {
				responseEntity = new ResponseEntity<CustomResponseBody<Boolean>>(responseBody ,httpStatus);
			}
		}
		
		logger.info("} " + methodName + "()");
		return responseEntity;
	}
	
	/**
	 * Just plain simple update. No adding of student nor deleting. Just updating information.
	 * @param sourceDepartment
	 * @param targetDepartment
	 * @throws Exception
	 */
	private boolean simpleUpdateDepartment(Department sourceDepartment, Department targetDepartment) {
		boolean updateFlag = false;
		
		if (sourceDepartment != null && targetDepartment != null
			&& targetDepartment.getId() != null && sourceDepartment.getId().equals(targetDepartment.getId())) {
			
			CustomMap<Integer, Student> targetStudentsMap = new CustomMap<Integer, Student>();
			
			List<Student> sourceStudents = sourceDepartment.getStudents();
			List<Student> targetStudents = targetDepartment.getStudents();
			
			if (utility.isNotNullAndEmpty(sourceStudents) && utility.isNotNullAndEmpty(targetStudents)) {
				if (sourceStudents.size() == targetStudents.size()) {
					updateFlag = true;
					
					for (Student student: targetStudents) {
						targetStudentsMap.set(student.getId(), student);
					}
					
					for (Student student: sourceStudents) {
						if (targetStudentsMap.get(student.getId()) == null) {
							updateFlag = false;
						}
					}
				}
			} else if (sourceStudents == null && sourceStudents == targetStudents) {
				updateFlag = true;
			}
			
			
			if (updateFlag) {
				Student targetStudent = null;
				targetDepartment.setName(sourceDepartment.getName());
				
				if (!targetStudentsMap.isEmpty()) {
					for (Student sourceStudent: targetStudents) {
						targetStudent = targetStudentsMap.get(sourceStudent.getId());
						targetStudent.setFirstname(sourceStudent.getFirstname());
						targetStudent.setMiddlename(sourceStudent.getMiddlename());
						targetStudent.setLastname(sourceStudent.getLastname());
						targetStudent.setAge(sourceStudent.getAge());
						
//						// this code is not really part of this function
//						// this is more of a caller's re
//						targetStudent.setDepartment(targetDepartment);
					}
				}
			}
		}
		
		return updateFlag;
	}
	
	
}




