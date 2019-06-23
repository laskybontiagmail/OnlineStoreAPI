package com.lasky.test.school.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.jdo.annotations.Column;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.PostPersist;
//import javax.persistence.PrePersist;
//import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "Departments")
@Getter
@Setter
public class Department implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;
	
	@Column(name = "name")
	private String name;
	
	//@Getter(AccessLevel.NONE)
	@Setter(AccessLevel.NONE)
	@OneToMany(
		cascade = CascadeType.ALL
		//,fetch = FetchType.LAZY
		,fetch = FetchType.EAGER
		,mappedBy = "department"
		)
	private List<Student> students;
	
	/**
	 * Custom setter to make sure JPA/Hibernate won't encounter error when
	 * saving this entity to the DB. Basically this will make sure that the 
	 * students elements will have this object as its department.
	 * Or else the students' department will be null
	 * and an error "department_id == NULL is not allowed!"
	 * @param students
	 */
	public void setStudents(List<Student> students) {
		this.students = students;
		
		if (this.students != null && !this.students.isEmpty()) {
			for (Student student: this.students) {
				if (student.getDepartment() == null) {
					student.setDepartment(this);
				}
			}
		}
	}
	
	
//	public List<Student> getStudents() {
//		
//		
//		
//		return this.students;
//	}
	
//	@PostPersist
//	public List<Student> loadStudents() {
//		System.out.println("loadStudents().....");
//		
//		
//		return this.students;
//	}
	
}


