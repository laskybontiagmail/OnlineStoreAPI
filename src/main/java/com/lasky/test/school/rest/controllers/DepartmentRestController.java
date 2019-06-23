package com.lasky.test.school.rest.controllers;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lasky.OnlineStoreAPI.rest.model.CustomResponseBody;
import com.lasky.test.school.entity.Department;
import com.lasky.test.school.service.DepartmentService;
import com.lasky.utilities.Utility;


@RestController
public class DepartmentRestController {
	
	@Autowired
	DepartmentService departmentService;
	
	private static Logger logger = LogManager.getLogger(DepartmentRestController.class);
	private Utility utility = Utility.getOnlyInstance();
	
	
	@RequestMapping(
		value = "/departments/byid/{id}"
		,method = RequestMethod.GET
		,produces = MediaType.APPLICATION_JSON_VALUE
		,consumes = MediaType.APPLICATION_JSON_VALUE
		)
    public ResponseEntity<CustomResponseBody<Department>> getDepartment(
    	@PathVariable(value = "id", required = true) final Integer id
    	) {
		String methodName = "getDepartment";
		logger.info(methodName + "() {");
		
		ResponseEntity<CustomResponseBody<Department>> responseEntity = null;
		responseEntity = departmentService.getDepartment(id);
		
		logger.info("} " + methodName + "()");
		return responseEntity;
	}
	
	@RequestMapping(
		value = "/departments/byname/{name}"
		,method = RequestMethod.GET
		,produces = MediaType.APPLICATION_JSON_VALUE
		,consumes = MediaType.APPLICATION_JSON_VALUE
		)
    public ResponseEntity<CustomResponseBody<Department>> getDepartment(
    	@PathVariable(value = "name", required = true) final String name
    	) {
		String methodName = "getDepartment";
		logger.info(methodName + "() {");
		
		ResponseEntity<CustomResponseBody<Department>> responseEntity = null;
		responseEntity = departmentService.getDepartment(name);
		
		logger.info("} " + methodName + "()");
		return responseEntity;
	}
	
	@RequestMapping(
			value = "/departments"
			,method = RequestMethod.GET
			,produces = MediaType.APPLICATION_JSON_VALUE
			,consumes = MediaType.APPLICATION_JSON_VALUE
			)
	    public ResponseEntity<CustomResponseBody<List<Department>>> getDepartments(
    		@RequestParam(value = "pageNumber", required = false) final Integer pageNumber
    		,@RequestParam(value = "pageSize", required = false) final Integer pageSize
	    	) {
			String methodName = "getDepartment";
			logger.info(methodName + "() {");
			
			ResponseEntity<CustomResponseBody<List<Department>>> responseEntity = null;
			responseEntity = departmentService.getDepartments(pageNumber ,pageSize);
			
			logger.info("} " + methodName + "()");
			return responseEntity;
		}
	
	@RequestMapping(
		value = "/departments"
		,method = RequestMethod.POST
		,produces = MediaType.APPLICATION_JSON_VALUE
		,consumes = MediaType.APPLICATION_JSON_VALUE
		)
    public ResponseEntity<CustomResponseBody<Boolean>> addDepartment(
    	@RequestBody final Department department
    	) {
		
		String methodName = "addDepartment";
		logger.info(methodName + "() {");
		
		ResponseEntity<CustomResponseBody<Boolean>> responseEntity = null;
		
		responseEntity = departmentService.addDepartment(department);
		
		logger.info("} " + methodName + "()");
		return responseEntity;
	}
	
	@RequestMapping(
		value = "/departments"
		,method = RequestMethod.PUT
		,produces = MediaType.APPLICATION_JSON_VALUE
		,consumes = MediaType.APPLICATION_JSON_VALUE
		)
    public ResponseEntity<CustomResponseBody<Boolean>> updateDepartment(
    	@RequestBody final Department department
    	) {
		
		String methodName = "updateDepartment";
		logger.info(methodName + "() {");
		
		ResponseEntity<CustomResponseBody<Boolean>> responseEntity = null;
		
		responseEntity = departmentService.updateDepartment(department);
		
		logger.info("} " + methodName + "()");
		return responseEntity;
	}
	
	@RequestMapping(
		value = "/departments/byid/{id}"
		,method = RequestMethod.DELETE
		,produces = MediaType.APPLICATION_JSON_VALUE
		,consumes = MediaType.APPLICATION_JSON_VALUE
		)
    public ResponseEntity<CustomResponseBody<Boolean>> deleteDepartment(
    	@PathVariable(value = "id", required = true) final Integer id
    	) {
		String methodName = "deleteDepartment";
		logger.info(methodName + "() {");
		
		ResponseEntity<CustomResponseBody<Boolean>> responseEntity = null;
		responseEntity = departmentService.deleteDepartment(id);
		
		logger.info("} " + methodName + "()");
		return responseEntity;
	}
	
}



