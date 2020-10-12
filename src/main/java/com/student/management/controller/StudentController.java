package com.student.management.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.student.management.exception.ResourceNotFoundException;
import com.student.management.model.Student;
import com.student.management.repository.StudentRepository;

@RestController
@CrossOrigin(origins = "*")
public class StudentController {
	
	@Autowired
	private StudentRepository studentRepository;
	
	//get all students
	@GetMapping("/students")
	public List<Student> getAllStudents(){
		return studentRepository.findAll();
	}

	@PostMapping("/students")
	public Student createStudent(@RequestBody Student student) {
		return studentRepository.save(student);
	}
	
	@GetMapping("/students/{id}")
	public ResponseEntity<Student> getStudentById(@PathVariable Long id) {
		Student student = studentRepository.findById(id)
				.orElseThrow(()-> new ResourceNotFoundException("Studentdoes not exist with id = " +id));
		return ResponseEntity.ok(student) ;
		
	}
	
	@PutMapping("/students/{id}")
	public ResponseEntity<Student> updateStudent(@PathVariable Long id, @RequestBody Student studentDetail){
		Student student = studentRepository.findById(id)
				.orElseThrow(()-> new ResourceNotFoundException("Studentdoes not exist with id = " +id));
		student.setFirstName(studentDetail.getFirstName());
		student.setLastName(studentDetail.getLastName());
		student.setEmailId(studentDetail.getEmailId());
		student.setMobileNo(studentDetail.getMobileNo());
		Student updatedStudent = studentRepository.save(student);		
		return ResponseEntity.ok(updatedStudent);
	}
	
	@DeleteMapping("/students/{id}")
	public ResponseEntity<Map<String, Boolean>> deleteStudent(@PathVariable Long id){
		Student student = studentRepository.findById(id)
				.orElseThrow(()-> new ResourceNotFoundException("Studentdoes not exist with id = " +id));
		 studentRepository.delete(student);
		 Map<String, Boolean> response = new HashMap<>();
		 response.put("deleted", Boolean.TRUE);
		 return ResponseEntity.ok(response);
	}
}
