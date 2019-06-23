package com.lasky.OnlineStoreAPI.users.repository;

import java.util.List;

import com.lasky.OnlineStoreAPI.users.entity.UserProfile;

public interface UserProfileRepository {
	UserProfile get(Integer id) throws Exception;
	List<UserProfile> getUserProfiles(Integer pageNumber, Integer pageSize) throws Exception;
	List<UserProfile> search(String firstname ,String middlename ,String lastname) throws Exception;
	boolean save(UserProfile userProfile) throws Exception ;
	boolean delete(UserProfile userProfile) throws Exception;
}
