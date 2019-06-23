package com.lasky.OnlineStoreAPI.users.service;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManagerFactory;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.lasky.OnlineStoreAPI.rest.model.CustomResponseBody;
import com.lasky.OnlineStoreAPI.users.entity.UserProfile;
import com.lasky.OnlineStoreAPI.users.repository.UserProfileRepository;
import com.lasky.OnlineStoreAPI.users.repository.UserProfileRepositoryImpl;
import com.lasky.system.DatasourceConnection;
import com.lasky.system.EntityManagerHandlerFactory;
import com.lasky.system.EntityManagerWrapper;
import com.lasky.system.GenericService;
import com.lasky.utilities.Utility;


@Service
public class UserProfilesService extends GenericService {
	private static Logger logger = LogManager.getLogger(UserProfilesService.class);
	private Utility utility = Utility.getOnlyInstance();
	
	public ResponseEntity<CustomResponseBody<UserProfile>> getUserProfile(Integer id) {
		String methodName = "getUserProfile";
		logger.info(methodName + "("
			+ "id: " + id
			+ ") {");
		ResponseEntity<CustomResponseBody<UserProfile>> responseEntity = null;
		CustomResponseBody<UserProfile> responseBody = null;
		UserProfile userProfile = null;
		
		String responseMessage = null;
		HttpStatus httpStatus = null;
		DatasourceConnection datasourceConnection = DatasourceConnection.getDefault();
		EntityManagerWrapper<UserProfilesService> entityManagerWrapper = null;
		EntityManagerFactory entityManagerFactory = null;
		UserProfileRepository userProfileRepository = null;
		
		try {
			if (id == null || id < 1) {
				httpStatus = HttpStatus.BAD_REQUEST;
				responseMessage = "Bad Request UserProfile ID required!";
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
			entityManagerWrapper = EntityManagerWrapper.<UserProfilesService>createNewInstance(entityManagerFactory ,this);
			if (entityManagerWrapper.getEntityManager() == null) {
				httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
				responseMessage = "Unable to create entity manager! Contact adminstrator or developer!";
				throw new Exception(responseMessage);
			}
			
			userProfileRepository = new UserProfileRepositoryImpl(entityManagerWrapper.getEntityManager());
			userProfile = userProfileRepository.get(id);
			
			if (userProfile != null) {
				httpStatus = HttpStatus.OK;
				responseMessage = "User Profile with ID: " + id + " successfully retrieved!";
				responseBody = new CustomResponseBody<UserProfile>();
				responseBody.setMessage(responseMessage);
				responseBody.setBody(userProfile);
				responseEntity = new ResponseEntity<CustomResponseBody<UserProfile>>(responseBody ,httpStatus);
			} else {
				httpStatus = HttpStatus.NOT_FOUND;
				responseMessage = "User: " + id + " NOT FOUND!";
				throw new Exception(responseMessage);
			}
			
		} catch (Exception exception) {
			httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
			logger.error("ERROR on " + methodName);
			utility.logFormattedExceptionStackTrace(logger, exception);
		} finally {
			logger.info("Before closing... is entityManager.isOpen(): " + entityManagerWrapper.getEntityManager().isOpen());
			//closeEntityManager(entityManagerWrapper.getEntityManager());
			if (responseBody == null) {
				responseBody = new CustomResponseBody<UserProfile>();
				responseBody.setMessage(responseMessage);
			}
			if (responseEntity == null) {
				responseEntity = new ResponseEntity<CustomResponseBody<UserProfile>>(responseBody ,httpStatus);
			}
		}
		
		logger.info("After closing... is entityManager.isOpen(): " + entityManagerWrapper.getEntityManager().isOpen());
		logger.info("}" + methodName + "()");
		return responseEntity;
	}
	
	public ResponseEntity<CustomResponseBody<List<UserProfile>>> searchUserProfile(String firstname
			,String middlename ,String lastname) {
		String methodName = "searchUserProfile";
		logger.info(methodName + "("
			+ "firstname: " + firstname
			+ ",middlename: " + middlename
			+ ",lastname: " + lastname
			+ ") {");
		ResponseEntity<CustomResponseBody<List<UserProfile>>> responseEntity = null;
		CustomResponseBody<List<UserProfile>> responseBody = null;
		List<UserProfile> userProfiles = null;
		
		String responseMessage = null;
		HttpStatus httpStatus = null;
		DatasourceConnection datasourceConnection = DatasourceConnection.getDefault();
		EntityManagerWrapper entityManagerWrapper = null;
		EntityManagerFactory entityManagerFactory = null;
		UserProfileRepository userProfileRepository = null;
		
		try {
			entityManagerFactory = EntityManagerHandlerFactory
					.factory()
					.getEntityManagerFactory(datasourceConnection);
			if (entityManagerFactory == null) {
				httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
				responseMessage = "Unable to connect to database server! Contact adminstrator or developer!";
				throw new Exception(responseMessage);
			}
			entityManagerWrapper = EntityManagerWrapper.createNewInstance(entityManagerFactory ,this);
			if (entityManagerWrapper.getEntityManager() == null) {
				httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
				responseMessage = "Unable to create entity manager! Contact adminstrator or developer!";
				throw new Exception(responseMessage);
			}
			
			userProfileRepository = new UserProfileRepositoryImpl(entityManagerWrapper.getEntityManager());
			userProfiles = userProfileRepository.search(firstname ,middlename ,lastname);
			
			if (utility.isNotNullAndEmpty(userProfiles)) {
				httpStatus = HttpStatus.OK;
				responseMessage = "Successfully retrieved User Profiles!";
				responseBody = new CustomResponseBody<List<UserProfile>>();
				responseBody.setMessage(responseMessage);
				responseBody.setBody(userProfiles);
				responseEntity = new ResponseEntity<CustomResponseBody<List<UserProfile>>>(responseBody ,httpStatus);
			} else {
				httpStatus = HttpStatus.NOT_FOUND;
				responseMessage = "NO User Profile FOUND!";
				throw new Exception(responseMessage);
			}
			
		} catch (Exception exception) {
			httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
			logger.error("ERROR on " + methodName);
			utility.logFormattedExceptionStackTrace(logger, exception);
		} finally {
			closeEntityManager(entityManagerWrapper.getEntityManager());
			if (responseBody == null) {
				responseBody = new CustomResponseBody<List<UserProfile>>();
				responseBody.setMessage(responseMessage);
			}
			if (responseEntity == null) {
				responseEntity = new ResponseEntity<CustomResponseBody<List<UserProfile>>>(responseBody ,httpStatus);
			}
		}
		
		logger.info("}" + methodName + "()");
		return responseEntity;
	}
	
	/**
	 * Get some number of UserProfiles if no parameter given
	 * this will return all of the records
	 * @return
	 */
	// TODO: Paging
	public ResponseEntity<CustomResponseBody<List<UserProfile>>> getManyUserProfiles(
		Integer pageNumber ,Integer pageSize) {
		String methodName = "getManyUserProfiles";
		logger.info(methodName + "("
			+ "pageNumber: " + pageNumber
			+ "pageSize: " + pageSize
			+ ") {");
		
		ResponseEntity<CustomResponseBody<List<UserProfile>>> responseEntity = null;
		CustomResponseBody<List<UserProfile>> responseBody = null;
		List<UserProfile> userProfiles = null;
		
		String responseMessage = null;
		HttpStatus httpStatus = null;
		DatasourceConnection datasourceConnection = DatasourceConnection.getDefault();
		EntityManagerWrapper<UserProfilesService> entityManagerWrapper = null;
		EntityManagerFactory entityManagerFactory = null;
		UserProfileRepository userProfileRepository = null;
		
		try {
			entityManagerFactory = EntityManagerHandlerFactory
					.factory()
					.getEntityManagerFactory(datasourceConnection);
			if (entityManagerFactory == null) {
				httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
				responseMessage = "Unable to connect to database server! Contact adminstrator or developer!";
				throw new Exception(responseMessage);
			}
			entityManagerWrapper = EntityManagerWrapper.<UserProfilesService>createNewInstance(entityManagerFactory ,this);
			if (entityManagerWrapper.getEntityManager() == null) {
				httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
				responseMessage = "Unable to create entity manager! Contact adminstrator or developer!";
				throw new Exception(responseMessage);
			}
			
			userProfileRepository = new UserProfileRepositoryImpl(entityManagerWrapper.getEntityManager());
			userProfiles = userProfileRepository.getUserProfiles(pageNumber, pageSize);
			
//			todo todo Test..
			
			if (utility.isNotNullAndEmpty(userProfiles)) {
				httpStatus = HttpStatus.OK;
				responseMessage = "Successfully retrieved User Profiles!";
				responseBody = new CustomResponseBody<List<UserProfile>>();
				responseBody.setMessage(responseMessage);
				responseBody.setBody(userProfiles);
				responseEntity = new ResponseEntity<CustomResponseBody<List<UserProfile>>>(responseBody ,httpStatus);
			} else {
				httpStatus = HttpStatus.NOT_FOUND;
				responseMessage = "NO User Profile FOUND!";
				throw new Exception(responseMessage);
			}
			
		} catch (Exception exception) {
			httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
			logger.error("ERROR on " + methodName);
			utility.logFormattedExceptionStackTrace(logger, exception);
		} finally {
			logger.info("Before closing... is entityManager.isOpen(): " + entityManagerWrapper.getEntityManager().isOpen());
			closeEntityManager(entityManagerWrapper.getEntityManager());
			if (responseBody == null) {
				responseBody = new CustomResponseBody<List<UserProfile>>();
				responseBody.setMessage(responseMessage);
			}
			if (responseEntity == null) {
				responseEntity = new ResponseEntity<CustomResponseBody<List<UserProfile>>>(responseBody ,httpStatus);
			}
		}
		
		logger.info("After closing... is entityManager.isOpen(): " + entityManagerWrapper.getEntityManager().isOpen());
		
		logger.info("}" + methodName + "()");
		return responseEntity;
	}
	
	public ResponseEntity<CustomResponseBody<Boolean>> addUserProfile(UserProfile userProfile) {
		String methodName = "addUserProfile";
		logger.info(methodName + "("
			+ "userProfile: " + utility.toJsonString(userProfile)
			+ ") {");
		ResponseEntity<CustomResponseBody<Boolean>> responseEntity = null;
		CustomResponseBody<Boolean> responseBody = null;
		
		String responseMessage = null;
		HttpStatus httpStatus = null;
		DatasourceConnection datasourceConnection = DatasourceConnection.getDefault();
		EntityManagerWrapper<UserProfilesService> entityManagerWrapper = null;
		EntityManagerFactory entityManagerFactory = null;
		UserProfileRepository userProfileRepository = null;
		
		try {
			entityManagerFactory = EntityManagerHandlerFactory
					.factory()
					.getEntityManagerFactory(datasourceConnection);
			if (entityManagerFactory == null) {
				httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
				responseMessage = "Unable to connect to database server! Contact adminstrator or developer.";
				throw new Exception(responseMessage);
			}
			entityManagerWrapper = EntityManagerWrapper.<UserProfilesService>createNewInstance(entityManagerFactory ,this);
			if (entityManagerWrapper.getEntityManager() == null) {
				httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
				responseMessage = "Unable to create entity manager! Contact adminstrator or developer.";
				throw new Exception(responseMessage);
			}
			if (userProfile == null || isUserProfileValid(userProfile)) {
				httpStatus = HttpStatus.BAD_REQUEST;
				responseMessage = "Bad Request! proper UserProfile required!";
				throw new Exception(responseMessage);
			}
			if (this.doesUserProfileExists(userProfile)) {
				httpStatus = HttpStatus.BAD_REQUEST;
				responseMessage = "UserProfile already exists!";
				throw new Exception(responseMessage);
			}
			
			userProfileRepository = new UserProfileRepositoryImpl(entityManagerWrapper.getEntityManager());
			
			Date timeNow = new Date();
			userProfile.setId(null);
			userProfile.setCreated(timeNow);
			userProfile.setModified(timeNow);
			
			if (userProfileRepository.save(userProfile)) {
				httpStatus = HttpStatus.OK;
				responseMessage = "Successfully added User Profile!";
				responseBody = new CustomResponseBody<Boolean>();
				responseBody.setMessage(responseMessage);
				responseBody.setBody(Boolean.TRUE);
				responseEntity = new ResponseEntity<CustomResponseBody<Boolean>>(responseBody ,httpStatus);
			} else {
				httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
				responseMessage = "User profile cannot be saved! Contact adminstrator or developer.";
				throw new Exception(responseMessage);
			}
			
		} catch (Exception exception) {
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
		
		logger.info("}" + methodName + "()");
		return responseEntity;
	}
	
	public ResponseEntity<CustomResponseBody<Boolean>> updateUserProfile(UserProfile userProfile) {
		String methodName = "updateUserProfile";
		logger.info(methodName + "("
			+ "userProfile: " + utility.toJsonString(userProfile)
			+ ") {");
		ResponseEntity<CustomResponseBody<Boolean>> responseEntity = null;
		CustomResponseBody<Boolean> responseBody = null;
		
		String responseMessage = null;
		HttpStatus httpStatus = null;
		DatasourceConnection datasourceConnection = DatasourceConnection.getDefault();
		EntityManagerWrapper<UserProfilesService> entityManagerWrapper = null;
		EntityManagerFactory entityManagerFactory = null;
		UserProfileRepository userProfileRepository = null;
		UserProfile fetchedUserProfile = null;
		
		try {
			entityManagerFactory = EntityManagerHandlerFactory
					.factory()
					.getEntityManagerFactory(datasourceConnection);
			if (entityManagerFactory == null) {
				httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
				responseMessage = "Unable to connect to database server! Contact adminstrator or developer.";
				throw new Exception(responseMessage);
			}
			entityManagerWrapper = EntityManagerWrapper.<UserProfilesService>createNewInstance(entityManagerFactory ,this);
			if (entityManagerWrapper.getEntityManager() == null) {
				httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
				responseMessage = "Unable to create entity manager! Contact adminstrator or developer.";
				throw new Exception(responseMessage);
			}
			if (userProfile == null || isUserProfileValid(userProfile)) {
				httpStatus = HttpStatus.BAD_REQUEST;
				responseMessage = "Bad Request! proper UserProfile required!";
				throw new Exception(responseMessage);
			}
			fetchedUserProfile = this.fetchUserProfileById(userProfile.getId());
			if (fetchedUserProfile == null) {
				httpStatus = HttpStatus.BAD_REQUEST;
				responseMessage = "UserProfile: " + userProfile.getId() + " doesn't exists!";
				throw new Exception(responseMessage);
			}
			if (this.doesUserProfileExists(userProfile)) {
				httpStatus = HttpStatus.BAD_REQUEST;
				responseMessage = "UserProfile not valid for update!\n"
					+ "Either the name combination is taken or there were no changes made.";
				throw new Exception(responseMessage);
			}
			
			userProfileRepository = new UserProfileRepositoryImpl(entityManagerWrapper.getEntityManager());
			
			this.mergeUserProfile(userProfile, fetchedUserProfile);
			if (userProfileRepository.save(fetchedUserProfile)) {
				httpStatus = HttpStatus.OK;
				responseMessage = "Successfully added User Profile!";
				responseBody = new CustomResponseBody<Boolean>();
				responseBody.setMessage(responseMessage);
				responseBody.setBody(Boolean.TRUE);
				responseEntity = new ResponseEntity<CustomResponseBody<Boolean>>(responseBody ,httpStatus);
			} else {
				httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
				responseMessage = "User profile cannot be saved! Contact adminstrator or developer.";
				throw new Exception(responseMessage);
			}
			
		} catch (Exception exception) {
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
		
		logger.info("}" + methodName + "()");
		return responseEntity;
	}
	
	public ResponseEntity<CustomResponseBody<Boolean>> deleteUserProfile(Integer id) {
		String methodName = "deleteUserProfile";
		logger.info(methodName + "(id: " + id + ") {");
		ResponseEntity<CustomResponseBody<Boolean>> responseEntity = null;
		CustomResponseBody<Boolean> responseBody = null;
		
		String responseMessage = null;
		HttpStatus httpStatus = null;
		DatasourceConnection datasourceConnection = DatasourceConnection.getDefault();
		EntityManagerWrapper<UserProfilesService> entityManagerWrapper = null;
		EntityManagerFactory entityManagerFactory = null;
		UserProfileRepository userProfileRepository = null;
		UserProfile fetchedUserProfile = null;
		
		try {
			entityManagerFactory = EntityManagerHandlerFactory
					.factory()
					.getEntityManagerFactory(datasourceConnection);
			if (entityManagerFactory == null) {
				httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
				responseMessage = "Unable to connect to database server! Contact adminstrator or developer.";
				throw new Exception(responseMessage);
			}
			entityManagerWrapper = EntityManagerWrapper.<UserProfilesService>createNewInstance(entityManagerFactory ,this);
			if (entityManagerWrapper.getEntityManager() == null) {
				httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
				responseMessage = "Unable to create entity manager! Contact adminstrator or developer.";
				throw new Exception(responseMessage);
			}
			
			// the line of code below will result to detached error
			// since the entity manager used to fetched this record was already closed
			// the fetchedUserProfile should be fetched using the entity manager in this scope (unclosed  yet)
			fetchedUserProfile = this.fetchUserProfileById(id);
			
			if (fetchedUserProfile == null) {
				httpStatus = HttpStatus.BAD_REQUEST;
				responseMessage = "UserProfile: " + id + " doesn't exists!";
				throw new Exception(responseMessage);
			}
			
			userProfileRepository = new UserProfileRepositoryImpl(entityManagerWrapper.getEntityManager());
			
			if (userProfileRepository.delete(fetchedUserProfile)) {
				httpStatus = HttpStatus.OK;
				responseMessage = "Successfully deleted User Profile!";
				responseBody = new CustomResponseBody<Boolean>();
				responseBody.setMessage(responseMessage);
				responseBody.setBody(Boolean.TRUE);
				responseEntity = new ResponseEntity<CustomResponseBody<Boolean>>(responseBody ,httpStatus);
			} else {
				httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
				responseMessage = "User profile cannot be deleted! Contact adminstrator or developer.";
				throw new Exception(responseMessage);
			}
			
		} catch (Exception exception) {
			httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
			logger.error("ERROR on " + methodName);
			utility.logFormattedExceptionStackTrace(logger, exception);
		} finally {
			logger.info("Before closing... is entityManager.isOpen(): " + entityManagerWrapper.getEntityManager().isOpen());
			
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
		
		
		logger.info("After closing... is entityManager.isOpen(): " + entityManagerWrapper.getEntityManager().isOpen());
		
		
		logger.info("}" + methodName + "()");
		return responseEntity;
	}
	
	private boolean isUserProfileValid(UserProfile userProfile) {
		boolean isValid = true;
		
		if (userProfile == null) {
			isValid = false;
		} else if (utility.isEmptyOrNullIgnoreTrailingWhiteSpaces(userProfile.getFirstName())
			&& utility.isEmptyOrNullIgnoreTrailingWhiteSpaces(userProfile.getMiddleName())
			&& utility.isEmptyOrNullIgnoreTrailingWhiteSpaces(userProfile.getLastName())) {
			isValid = false;
		}
		
		return isValid;
	}
	
	private boolean doesUserProfileExists(UserProfile userProfile) throws Exception {
		boolean doesExist = false;
		
		ResponseEntity<CustomResponseBody<List<UserProfile>>> responseEntity = this.searchUserProfile(
				userProfile.getFirstName() ,userProfile.getMiddleName() ,userProfile.getLastName());
		
		if (responseEntity != null && responseEntity.getStatusCodeValue() == HttpStatus.OK.value()
			&& responseEntity.hasBody()) {
			List<UserProfile> userProfiles = responseEntity.getBody().getBody();
			if (utility.isNotNullAndEmpty(userProfiles)) {
				doesExist = true;
			}
		}
		
		return doesExist;
	}
	
	private UserProfile fetchUserProfileById(Integer id) throws Exception {
		UserProfile userProfile = null;
		
		ResponseEntity<CustomResponseBody<UserProfile>> responseEntity = this.getUserProfile(id);
		if (responseEntity != null && responseEntity.getStatusCodeValue() == HttpStatus.OK.value()
			&& responseEntity.hasBody()) {
			userProfile = responseEntity.getBody().getBody();
		}
		
		return userProfile;
	}
	
	private void mergeUserProfile(UserProfile source ,UserProfile destination) throws Exception {
		destination.setFirstName(source.getFirstName());
		destination.setMiddleName(source.getMiddleName());
		destination.setLastName(source.getLastName());
		destination.setModified(new Date());
	}
	
}



