package com.lasky.test.school.entity;

import java.io.Serializable;
import java.util.Date;

import javax.jdo.annotations.Column;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "Students")
@Getter
@Setter
public class Student implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;
	
//	@Column(name = "department_id")
//	private Integer departmentId;
	
	@Column(name = "firstname")
	private String firstname;
	
	@Column(name = "middlename")
	private String middlename;
	
	@Column(name = "lastname")
	private String lastname;
	
	@Column(name = "age")
	private Integer age;
	
	@JsonIgnore
	@ManyToOne(
		// no cascade here since this will be updated from Department point of view
		fetch = FetchType.LAZY
		)
	@JoinColumn(
		name = "department_id"
		,nullable = false
		,referencedColumnName = "id"
		,insertable = true
		,updatable = true
		)
	private Department department;
	
	
}


