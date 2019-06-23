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
import com.lasky.test.school.entity.Student;
import com.lasky.test.school.service.StudentService;
import com.lasky.utilities.Utility;


@RestController
public class StudentRestController {
	
	@Autowired
	StudentService studentService;
	
	private static Logger logger = LogManager.getLogger(StudentRestController.class);
	private Utility utility = Utility.getOnlyInstance();
	
	
	@RequestMapping(
		value = "/students/byid/{id}"
		,method = RequestMethod.GET
		,produces = MediaType.APPLICATION_JSON_VALUE
		,consumes = MediaType.APPLICATION_JSON_VALUE
		)
    public ResponseEntity<CustomResponseBody<Student>> getStudent(
    	@PathVariable(value = "id", required = true) final Integer id
    	) {
		String methodName = "getStudent";
		logger.info(methodName + "() {");
		
		ResponseEntity<CustomResponseBody<Student>> responseEntity = null;
		responseEntity = studentService.getStudent(id);
		
		logger.info("} " + methodName + "()");
		return responseEntity;
	}
	
	@RequestMapping(
		value = "/students"
		,method = RequestMethod.GET
		,produces = MediaType.APPLICATION_JSON_VALUE
		,consumes = MediaType.APPLICATION_JSON_VALUE
		)
    public ResponseEntity<CustomResponseBody<List<Student>>> getStudents(
    	@RequestParam(value = "pageNumber", required = false) final Integer pageNumber
		,@RequestParam(value = "pageSize", required = false) final Integer pageSize
		,@RequestParam(value = "sortBy", required = false) final String sortBy
		,@RequestParam(value = "sortDirection", required = false) final String sortDirection
    	) {
		String methodName = "getStudents";
		logger.info(methodName + "() {");
		
		ResponseEntity<CustomResponseBody<List<Student>>> responseEntity = null;
		responseEntity = studentService.getStudents(pageNumber, pageSize, sortBy, sortDirection);
		
		logger.info("} " + methodName + "()");
		return responseEntity;
	}
	
}



