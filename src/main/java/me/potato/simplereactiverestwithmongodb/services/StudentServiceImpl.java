/*
 * StudentServiceImpl.java 2021. 07. 08
 *
 * Copyright 2021 Naver Cloud Corp. All rights Reserved.
 * Naver Business Platform PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package me.potato.simplereactiverestwithmongodb.services;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import me.potato.simplereactiverestwithmongodb.models.Student;
import me.potato.simplereactiverestwithmongodb.repositories.StudentRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * @author dongju.paek
 */
@RequiredArgsConstructor
@Service
public class StudentServiceImpl implements StudentService {

	private final StudentRepository studentRepository;

	@Override
	public Flux<Student> findAll() {
		return studentRepository.findAll();
	}

	@Override
	public Mono<Student> findByStudentNumber(long studentNumber) {
		return studentRepository.findByStudentNumber(studentNumber);
	}

	@Override
	public Mono<Student> findByEmail(String email) {
		return studentRepository.findByEmail(email);
	}

	@Override
	public Flux<Student> findAllByOrderByGpaDesc() {
		return studentRepository.findAllByOrderByGpaDesc();
	}

	@Override
	public Mono<Student> saveOrUpdateStudent(Student student) {
		return studentRepository.save(student);
	}

	@Override
	public void deleteStudentById(String id) {
		studentRepository.deleteById(id);
	}
}