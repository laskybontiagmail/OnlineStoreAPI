package com.lasky.test.school.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.lasky.test.school.entity.Student;


public interface StudentRepository {
	
	Student get(Integer id) throws Exception;
	List<Student> get(Pageable page, Sort sortOrder) throws Exception;

}
