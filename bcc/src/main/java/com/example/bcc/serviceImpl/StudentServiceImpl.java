package com.example.bcc.serviceImpl;

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import org.springframework.stereotype.Service;

import com.example.bcc.model.Student;
import com.example.bcc.repository.StudentRepository;
import com.example.bcc.service.StudentService;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

@Service
public class StudentServiceImpl implements StudentService {
	
	@Autowired
	StudentRepository studentRepo;

	@Override
	public List<Student> getAllStudent() {
		return studentRepo.findAll();
	}

	@Override
	public String regisStudent(Student student) {
		String result = "Register Student Failed";

		if (student != null) {
			studentRepo.save(student);
			result = "Register Student Success";
		}

		return result;
	}

	@Override
	public String updateStudent(Student student) {
		String result = "Update Student Failed";

		Optional<Student> extStudent = studentRepo.findById(student.getNim());

		if (extStudent.isPresent()) {
		
			studentRepo.save(student);
			result = "Update Student Success";
		}

		return result;
	}

	@Override
	public String expelStudent(String nim) {
		String result = "Delete Student Failed";

		Optional<Student> extStudent = studentRepo.findById(nim);

		if (extStudent.isPresent()) {
			studentRepo.deleteById(nim);
			result = "Delete Student Success";
		}

		return result;
	}

	@Override
	public byte[] exportReport() throws JRException, IOException {
	InputStream filePath = new ClassPathResource("/template/Mahasiswa.jrxml").getInputStream();	
		
		List<Student> dsStudent = getAllStudent();
		
		JRBeanCollectionDataSource ds = new JRBeanCollectionDataSource(dsStudent);
		
		JasperReport report = JasperCompileManager.compileReport(filePath);
		
		JasperPrint print = JasperFillManager.fillReport(report, null,ds);
		
		byte[]  byteArr = JasperExportManager.exportReportToPdf(print);
		
		return byteArr;
		
	}

	@Override
	public byte[] createLetter() throws JRException, IOException {
		InputStream filePath = new ClassPathResource("/template/surat.jrxml").getInputStream();	
		
		List<Student> dsStudent = getAllStudent();
		
		JRBeanCollectionDataSource ds = new JRBeanCollectionDataSource(dsStudent);
		
		JasperReport report = JasperCompileManager.compileReport(filePath);
		
		JasperPrint print = JasperFillManager.fillReport(report, null,ds);
		
		byte[]  byteArr = JasperExportManager.exportReportToPdf(print);
		
		return byteArr;
	}

	@Override
	public String kodeUnik(String nim) {
		String result=nim.substring(0,3);
		
		Date date = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("ddMMyy");
		String strDate = formatter.format(date);
		result += "/"+strDate;
		
		int nilai = (int)(Math.random()*1001);
		String inRandom = String.format ("%04d",nilai);
		

		 result += "/"+inRandom;
		return result;
	}

	@Override
	public String addPass(String pass) {
		String result = "Add Pass Failed";
		if(pass != null) {
			BCryptPasswordEncoder passEncoder = new BCryptPasswordEncoder();
			result = passEncoder.encode(pass);
		}
		return result;
	}
}
