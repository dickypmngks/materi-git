package com.example.bcc.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.bcc.model.Student;

@Repository
public interface StudentRepository extends JpaRepository<Student, String>{

}
