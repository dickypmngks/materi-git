package com.example.bcc.service;

import java.io.IOException;
import java.util.List;

import com.example.bcc.model.Student;

import net.sf.jasperreports.engine.JRException;

public interface StudentService {
	public List<Student> getAllStudent();
	
	public String regisStudent(Student student);
	
	public String updateStudent(Student student);
	
	public String expelStudent(String nim);
	
	public byte[] exportReport()  throws JRException, IOException ;
	
	public byte[] createLetter()  throws JRException, IOException ;
	
	public String kodeUnik(String nim);
	
	public String addPass(String pass);
}
