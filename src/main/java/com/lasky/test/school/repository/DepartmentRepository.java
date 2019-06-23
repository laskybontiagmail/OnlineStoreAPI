package com.lasky.test.school.repository;

import java.util.List;

import com.lasky.test.school.entity.Department;

public interface DepartmentRepository {
	
	Department get(Integer id) throws Exception;
	Department get(String name) throws Exception;
	List<Department> get(Integer pageNumber, Integer pageSize) throws Exception;
	boolean save(Department department) throws Exception;
	boolean delete(Department department) throws Exception;
	
}


