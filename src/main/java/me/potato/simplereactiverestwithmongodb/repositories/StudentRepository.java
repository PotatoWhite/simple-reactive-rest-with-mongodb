/*
 * StudentRespository.java 2021. 07. 08
 *
 * Copyright 2021 Naver Cloud Corp. All rights Reserved.
 * Naver Business Platform PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package me.potato.simplereactiverestwithmongodb.repositories;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import me.potato.simplereactiverestwithmongodb.models.Student;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * @author dongju.paek
 */
public interface StudentRepository extends ReactiveMongoRepository<Student, String> {

	Mono<Student> findByStudentNumber(long studentNumber);

	Mono<Student> findByEmail(String email);

	Flux<Student> findAllByOrderByGpaDesc();

}