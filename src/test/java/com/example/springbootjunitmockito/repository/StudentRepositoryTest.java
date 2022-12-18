package com.example.springbootjunitmockito.repository;

import com.example.springbootjunitmockito.model.Gender;
import com.example.springbootjunitmockito.model.Student;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
class StudentRepositoryTest {
    @Autowired
    StudentRepository studentRepository;

    @AfterEach
    void tearDown(){
        studentRepository.deleteAll();
    }

    @Test
    void itShouldCheckIfStudentEmailExists() {
        //given
        String email = "TestEmail@gmail.com";
        Student student = new Student(
                "TestName",
                email,
                Gender.MALE);
        studentRepository.save(student);

        // when
        boolean expected = studentRepository.selectExistsEmail(email);

        // then
        assertThat(expected).isTrue();

    }

    @Test
    void itShouldCheckWhenStudentEmailDoesNotExists() {
        // given
        String email = "TestEmail@gmail.com";

        // when
        boolean expected = studentRepository.selectExistsEmail(email);

        // then
        assertThat(expected).isFalse();

    }


}