/*
 * StudentDto.java 2021. 07. 08
 *
 * Copyright 2021 Naver Cloud Corp. All rights Reserved.
 * Naver Business Platform PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package me.potato.simplereactiverestwithmongodb.controllers.dtos;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import me.potato.simplereactiverestwithmongodb.models.Student;

/**
 * @author dongju.paek
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class StudentDto {
	private String       id;
	private String       name;
	private long         studentNumber;
	private String       email;
	private List<String> courses;
	private float        gpa;

	public StudentDto(Student student) {
		this.id            = student.getId();
		this.name          = student.getName();
		this.studentNumber = student.getStudentNumber();
		this.email         = student.getEmail();
		this.courses       = new ArrayList<>(student.getCourses());
		this.gpa           = student.getGpa();
	}

	public Student toStudent() {
		var aStudent = new Student();
		aStudent.setName(name);
		aStudent.setStudentNumber(studentNumber);
		aStudent.setEmail(email);
		aStudent.setCourses(new ArrayList<>(this.getCourses()));
		aStudent.setGpa(gpa);
		return aStudent;
	}
}