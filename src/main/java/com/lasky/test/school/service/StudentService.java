package com.lasky.test.school.service;

import java.util.List;

import javax.persistence.EntityManagerFactory;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.lasky.OnlineStoreAPI.rest.model.CustomResponseBody;
import com.lasky.system.DatasourceConnection;
import com.lasky.system.EntityManagerHandlerFactory;
import com.lasky.system.EntityManagerWrapper;
import com.lasky.system.GenericService;
import com.lasky.test.school.entity.Student;
import com.lasky.test.school.repository.StudentRepository;
import com.lasky.test.school.repository.StudentRepositoryImpl;
import com.lasky.utilities.CustomMap;
import com.lasky.utilities.Utility;

@Service
public class StudentService extends GenericService {
	private static Logger logger = LogManager.getLogger(StudentService.class);
	private Utility utility = Utility.getOnlyInstance();
	
	public ResponseEntity<CustomResponseBody<Student>> getStudent(Integer id) {
		String methodName = "getStudent";
		logger.info(methodName + "(" + "id: " + id + ") {");
		
		ResponseEntity<CustomResponseBody<Student>> responseEntity = null;
		CustomResponseBody<Student> responseBody = null;
		Student department = null;
		String responseMessage = null;
		HttpStatus httpStatus = null;
		DatasourceConnection datasourceConnection = DatasourceConnection.getDefault();
		EntityManagerWrapper<StudentService> entityManagerWrapper = null;
		EntityManagerFactory entityManagerFactory = null;
		StudentRepository studentRepository = null;
		
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
			entityManagerWrapper = EntityManagerWrapper.<StudentService>createNewInstance(entityManagerFactory ,this);
			if (entityManagerWrapper.getEntityManager() == null) {
				httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
				responseMessage = "Unable to create entity manager! Contact adminstrator or developer!";
				throw new Exception(responseMessage);
			}
			
			studentRepository = new StudentRepositoryImpl(entityManagerWrapper.getEntityManager());
			department = studentRepository.get(id);
			
			if (department != null ) {
				httpStatus = HttpStatus.OK;
				responseMessage = "Department with ID: " + id + " successfully retrieved!";
				responseBody = new CustomResponseBody<Student>();
				responseBody.setMessage(responseMessage);
				responseBody.setBody(department);
				responseEntity = new ResponseEntity<CustomResponseBody<Student>>(responseBody, httpStatus);
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
				responseBody = new CustomResponseBody<Student>();
				responseBody.setMessage(responseMessage);
			}
			if (responseEntity == null) {
				responseEntity = new ResponseEntity<CustomResponseBody<Student>>(responseBody ,httpStatus);
			}
		}
		
		logger.info("} " + methodName + "()");
		return responseEntity;
	}
	
	public ResponseEntity<CustomResponseBody<List<Student>>> getStudents(Integer pageNumber, Integer pageSize,
		String sortBy, String sortDirection) {
		
		String methodName = "getStudents";
		logger.info(methodName + "("
			+ "pageNumber: " + pageNumber
			+ "; pageSize: " + pageSize +
			") {");
		
		ResponseEntity<CustomResponseBody<List<Student>>> responseEntity = null;
		CustomResponseBody<List<Student>> responseBody = null;
		List<Student> students = null;
		String responseMessage = null;
		HttpStatus httpStatus = null;
		DatasourceConnection datasourceConnection = DatasourceConnection.getDefault();
		EntityManagerWrapper<StudentService> entityManagerWrapper = null;
		EntityManagerFactory entityManagerFactory = null;
		StudentRepository studentRepository = null;
		Pageable page = null;
		Sort sortOrder = Sort.unsorted(); //TODO: sorting of result
		
		
		try {
			if (!utility.isEmptyOrNullIgnoreTrailingWhiteSpaces(sortBy)
				&& !utility.isEmptyOrNullIgnoreTrailingWhiteSpaces(sortDirection.trim())) {
				
				sortOrder = new Sort(Direction.valueOf(sortDirection), sortBy);
			}
			if (pageNumber != null || pageSize != null) {
				//page = new PageRequest(pageNumber, pageSize, sortOrder);
				page = PageRequest.of(pageNumber, pageSize, sortOrder);
			}
			entityManagerFactory = EntityManagerHandlerFactory
				.factory()
				.getEntityManagerFactory(datasourceConnection);
			if (entityManagerFactory == null) {
				httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
				responseMessage = "Unable to connect to database server! Contact adminstrator or developer!";
				throw new Exception(responseMessage);
			}
			entityManagerWrapper = EntityManagerWrapper.<StudentService>createNewInstance(entityManagerFactory ,this);
			if (entityManagerWrapper.getEntityManager() == null) {
				httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
				responseMessage = "Unable to create entity manager! Contact adminstrator or developer!";
				throw new Exception(responseMessage);
			}
			
			studentRepository = new StudentRepositoryImpl(entityManagerWrapper.getEntityManager());
			students = studentRepository.get(page, sortOrder);
			
			if (utility.<Student>isNotNullAndEmpty(students)) {
				httpStatus = HttpStatus.OK;
				responseMessage = "Students successfully retrieved!";
				responseBody = new CustomResponseBody<List<Student>>();
				responseBody.setMessage(responseMessage);
				responseBody.setBody(students);
				responseEntity = new ResponseEntity<CustomResponseBody<List<Student>>>(responseBody, httpStatus);
			} else {
				httpStatus = HttpStatus.NOT_FOUND;
				responseMessage = "CANNOT FIND any Student!";
				throw new Exception(responseMessage);
			}
			
		}  catch (Exception exception) {
			httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
			logger.error("ERROR on " + methodName);
			utility.logFormattedExceptionStackTrace(logger, exception);
		} finally {
			closeEntityManager(entityManagerWrapper.getEntityManager());
			if (responseBody == null) {
				responseBody = new CustomResponseBody<List<Student>>();
				responseBody.setMessage(responseMessage);
			}
			if (responseEntity == null) {
				responseEntity = new ResponseEntity<CustomResponseBody<List<Student>>>(responseBody ,httpStatus);
			}
		}
		
		logger.info("} " + methodName + "()");
		return responseEntity;
	}
	
	
}





