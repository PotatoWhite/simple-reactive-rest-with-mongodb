/*
 * StudentRestController.java 2021. 07. 08
 *
 * Copyright 2021 Naver Cloud Corp. All rights Reserved.
 * Naver Business Platform PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package me.potato.simplereactiverestwithmongodb.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.mongodb.DuplicateKeyException;

import lombok.RequiredArgsConstructor;
import me.potato.simplereactiverestwithmongodb.controllers.dtos.StudentDto;
import me.potato.simplereactiverestwithmongodb.models.Student;
import me.potato.simplereactiverestwithmongodb.services.StudentService;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * @author dongju.paek
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/students")
public class StudentRestController {
	private final StudentService studentService;

	@GetMapping()
	public Flux<StudentDto> getAllStudents() {
		return studentService.findAll().map(StudentDto::new);
	}

	@GetMapping("/byStudentNumber/{studentNumber}")
	public Mono<ServerResponse> getStudentByStudentNumber(@PathVariable Long studentNumber) {
		return studentService.findByStudentNumber(studentNumber)
			.flatMap(student -> ServerResponse.status(HttpStatus.OK).bodyValue(new StudentDto(student)))
			.onErrorResume(err -> ServerResponse.status(HttpStatus.NOT_FOUND).bodyValue(err.getMessage()));
	}

	@GetMapping("/byEmail/{email}")
	public Mono<ServerResponse> getStudentByEmail(@PathVariable String email) {
		return studentService.findByEmail(email)
			.flatMap(student -> ServerResponse.status(HttpStatus.OK).bodyValue(new StudentDto(student)))
			.onErrorResume(error -> ServerResponse.status(HttpStatus.NOT_FOUND).bodyValue(error.getMessage()));
	}

	@GetMapping("/orderByGpa")
	public Flux<StudentDto> findAllbyGpaDesc() {
		return studentService.findAllByOrderByGpaDesc().map(StudentDto::new);
	}

	@PostMapping("/save")
	public Mono<ServerResponse> saveOrUpdateStudent(@RequestBody StudentDto student) {
		return studentService.saveOrUpdateStudent(student.toStudent())
			.flatMap(saved -> ServerResponse.status(HttpStatus.CREATED).bodyValue(new StudentDto(saved)))
			.onErrorResume(DuplicateKeyException.class, t -> ServerResponse.status(HttpStatus.BAD_REQUEST).bodyValue(t.getMessage()));
	}

	@DeleteMapping("/delete/{studentNumber}")
	public Mono<Student> delete(@PathVariable Long studentNumber) {
		return studentService.findByStudentNumber(studentNumber)
			.doOnSuccess(student -> ServerResponse.status(HttpStatus.NO_CONTENT).bodyValue(student))
			.doOnTerminate(() -> ServerResponse.status(HttpStatus.NO_CONTENT).build());
	}
}