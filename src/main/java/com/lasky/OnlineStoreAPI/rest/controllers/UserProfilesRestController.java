package com.lasky.OnlineStoreAPI.rest.controllers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.lasky.OnlineStoreAPI.rest.model.CustomResponseBody;
import com.lasky.OnlineStoreAPI.users.entity.UserProfile;
import com.lasky.OnlineStoreAPI.users.service.UserProfilesService;
import com.lasky.utilities.Utility;

@RestController
public class UserProfilesRestController {
	@Autowired
	UserProfilesService userProfilesService;
	
	private static Logger logger = LogManager.getLogger(UserProfilesRestController.class);
	private Utility utility = Utility.getOnlyInstance();
	
	@RequestMapping(
    		value = "/userprofiles/byid/{id}"
			,method = RequestMethod.GET
			,produces = MediaType.APPLICATION_JSON_VALUE
			,consumes = MediaType.APPLICATION_JSON_VALUE
    		)
    public ResponseEntity<CustomResponseBody<UserProfile>> getUserProfile(
    	@PathVariable(value = "id", required = true) final Integer id) {
		
		String methodName = "getUserProfile";
		logger.info(methodName + "() {");
		
		ResponseEntity<CustomResponseBody<UserProfile>> responseEntity = null;
		
		responseEntity = this.userProfilesService.getUserProfile(id);
		
		logger.info("}" + methodName + "()");
		return responseEntity;
	}
	
	@RequestMapping(
    		value = "/userprofiles"
			,method = RequestMethod.POST
			,produces = MediaType.APPLICATION_JSON_VALUE
			,consumes = MediaType.APPLICATION_JSON_VALUE
    		)
    public ResponseEntity<CustomResponseBody<Boolean>> addUserProfile(
    		@RequestBody final UserProfile userProfile) {
		
		String methodName = "addUserProfile";
		logger.info(methodName + "() {");
		
		ResponseEntity<CustomResponseBody<Boolean>> responseEntity = null;
		
		responseEntity = this.userProfilesService.addUserProfile(userProfile);
		
		logger.info("}" + methodName + "()");
		return responseEntity;
	}
	
	@RequestMapping(
    		value = "/userprofiles"
			,method = RequestMethod.PUT
			,produces = MediaType.APPLICATION_JSON_VALUE
			,consumes = MediaType.APPLICATION_JSON_VALUE
    		)
    public ResponseEntity<CustomResponseBody<Boolean>> updateUserProfile(
    		@RequestBody final UserProfile userProfile) {
		
		String methodName = "updateUserProfile";
		logger.info(methodName + "() {");
		
		ResponseEntity<CustomResponseBody<Boolean>> responseEntity = null;
		
		responseEntity = this.userProfilesService.updateUserProfile(userProfile);
		
		logger.info("}" + methodName + "()");
		return responseEntity;
	}
	
	@RequestMapping(
    		value = "/userprofiles/byid/{id}"
			,method = RequestMethod.DELETE
			,produces = MediaType.APPLICATION_JSON_VALUE
			,consumes = MediaType.APPLICATION_JSON_VALUE
    		)
    public ResponseEntity<CustomResponseBody<Boolean>> deleteUserProfile(
    		@PathVariable(value = "id", required = true) final Integer id) {
		
		String methodName = "deleteUserProfile";
		logger.info(methodName + "() {");
		
		ResponseEntity<CustomResponseBody<Boolean>> responseEntity = null;
		
		responseEntity = this.userProfilesService.deleteUserProfile(id);
		
		logger.info("}" + methodName + "()");
		return responseEntity;
	}
	
}
