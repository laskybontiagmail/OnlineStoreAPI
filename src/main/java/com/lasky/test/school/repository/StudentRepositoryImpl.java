package com.lasky.test.school.repository;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.lasky.system.BaseRepository;
import com.lasky.test.school.entity.QStudent;
import com.lasky.test.school.entity.Student;
import com.lasky.utilities.Utility;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;


public class StudentRepositoryImpl extends BaseRepository<Student, Long> implements StudentRepository {
	private static Logger logger = LogManager.getLogger(StudentRepositoryImpl.class);
	private Utility utility = Utility.getOnlyInstance();
	
	
	/**
	 * Constructor.
	 * @param entityManager - entity manager
	 */
	public StudentRepositoryImpl(EntityManager entityManager) {
		super(entityManager, Student.class);
	}
	
	@Override
	public Student get(Integer id) throws Exception {
		Student student = null;
		
		QStudent qStudent = QStudent.student;
		Predicate predicate = qStudent.id.eq(id);
		
		Optional<Student> queryResult = getRepository().findOne(predicate);
		if (queryResult != null && queryResult.isPresent()) {
			student = queryResult.get();
		}
		
		
		return student;
	}

	@Override
	public List<Student> get(Pageable page, Sort sortOrder) throws Exception {
		List<Student> students = null;
		
		if (page != null) {
			Page<Student> pagedResult = this.getRepository().findAll(page);
			students = pagedResult.getContent();
		} else {
			if (sortOrder != null) {
				students = this.getRepository().findAll(sortOrder);
			} else {
				students = this.getRepository().findAll();
			}
		}
		
		return students;
		
	}

}
