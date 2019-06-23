package com.lasky.OnlineStoreAPI.users.repository;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.lasky.OnlineStoreAPI.users.entity.QUserProfile;
import com.lasky.OnlineStoreAPI.users.entity.UserProfile;
import com.lasky.system.BaseRepository;
import com.lasky.utilities.Utility;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;

public class UserProfileRepositoryImpl extends BaseRepository<UserProfile ,Long> implements UserProfileRepository {
	private static Logger logger = LogManager.getLogger(UserProfileRepositoryImpl.class);
	private Utility utility = Utility.getOnlyInstance();
	
	
	/**
	 * Constructor.
	 * @param entityManager - entity manager
	 */
	public UserProfileRepositoryImpl(EntityManager entityManager) {
		super(entityManager ,UserProfile.class);
	}
	
	@Override
	public UserProfile get(Integer id) throws Exception {
		UserProfile userProfile = null;
		
		if (id != null) {
			QUserProfile qUserProfile = QUserProfile.userProfile;
			Predicate predicate = qUserProfile.id.eq(id);
			Optional<UserProfile> result = getRepository().findOne(predicate);
			
			if (result != null && result.isPresent()) {
				userProfile = result.get();
			}
		}
		
		return userProfile;
	}
	
	// TODO: paging
	@Override
	public List<UserProfile> getUserProfiles(Integer pageNumber, Integer pageSize) throws Exception {
		List<UserProfile> userProfiles = null;
		
		userProfiles = getRepository().findAll(this.sortByIdAsc());
		
		return userProfiles;
	}

	@Override
	public List<UserProfile> search(String firstname, String middlename, String lastname) throws Exception {
		List<UserProfile> userProfiles = null;
		
		QUserProfile qUserProfile = QUserProfile.userProfile;
		BooleanExpression booleanExpression = qUserProfile.isNotNull();
		
		if (firstname != null) {
			booleanExpression = booleanExpression.and(qUserProfile.firstName.eq(firstname));
		}
		
		if (middlename != null) {
			booleanExpression = booleanExpression.and(qUserProfile.middleName.eq(middlename));
		}
		
		if (lastname != null) {
			booleanExpression = booleanExpression.and(qUserProfile.lastName.eq(lastname));
		}
		
		userProfiles = getRepository().findAll(booleanExpression);
		
		return userProfiles;
	}

	@Override
	public boolean save(UserProfile userProfile) throws Exception {
		boolean isSaved = false;
		Exception exceptionThrown = null;
		
		if (userProfile != null) {
			try {
				getEntityManager().getTransaction().begin();
				getEntityManager().<UserProfile>merge(userProfile);
				
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
	public boolean delete(UserProfile userProfile) throws Exception {
		boolean isDeleted = false;
		Exception exceptionThrown = null;
		
		if (userProfile != null) {
			try {
				getEntityManager().getTransaction().begin();
				getEntityManager().remove(userProfile);
				
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


