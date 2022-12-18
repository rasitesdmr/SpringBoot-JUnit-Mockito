package com.example.springbootjunitmockito.service;

import com.example.springbootjunitmockito.exception.BadRequestException;
import com.example.springbootjunitmockito.model.Gender;
import com.example.springbootjunitmockito.model.Student;
import com.example.springbootjunitmockito.repository.StudentRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class StudentServiceImplTest {
    @Mock
    private StudentRepository studentRepository;
    //    private AutoCloseable autoCloseable;
    private StudentServiceImpl studentServiceImpl;

    @BeforeEach
    void setUp() {
        //  autoCloseable = MockitoAnnotations.openMocks(this);
        studentServiceImpl = new StudentServiceImpl(studentRepository);
    }

//    @AfterEach
//    void tearDown() throws Exception {
//        autoCloseable.close();
//    }

    @Test
    void canGetAllStudents() {
        //when
        studentServiceImpl.getAllStudents();
        //then
        verify(studentRepository).findAll();
    }

    @Test
    void canAddStudent() {
        //given
        Student student = new Student(
                "TestName",
                "TestEmail@gmail.com",
                Gender.MALE);
        //when
        studentServiceImpl.addStudent(student);

        //then
        ArgumentCaptor<Student> studentArgumentCaptor = ArgumentCaptor.forClass(Student.class);
        verify(studentRepository).save(studentArgumentCaptor.capture());
        Student capturedStudent = studentArgumentCaptor.getValue();
        assertThat(capturedStudent).isEqualTo(student);
    }

    @Test
    void willThrowWhenEmailsIsTaken() {
        //given
        Student student = new Student(
                "TestName",
                "TestEmail@gmail.com",
                Gender.MALE);

        given(studentRepository.selectExistsEmail(student.getEmail())).willReturn(true);

        //when
        //then
        assertThatThrownBy((() -> studentServiceImpl.addStudent(student)))
                .isInstanceOf(BadRequestException.class)
                .hasMessageContaining("Email" + student.getEmail() + "taken");

        verify(studentRepository,never()).save(any());
    }

    @Test
    @Disabled
    void deleteStudent() {
    }
}