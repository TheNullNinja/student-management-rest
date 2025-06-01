package com.student.controller;

import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.student.models.StudentResponse;
import com.student.models.StudentResponse.Record;
import com.student.service.StudentService;

@RestController
@RequestMapping("/api/students")
public class StudentController {

	private final StudentService studentService;

	public StudentController(StudentService studentService) {
		this.studentService = studentService;
	}

	@GetMapping(path = "/all", produces = "application/json")
	public ResponseEntity<List<Record>> getAllStudents() {
		try {
			List<StudentResponse.Record> records = studentService.getAllStudents();
			return ResponseEntity.status(200).body(records);
		} catch (SQLException e) {
			return ResponseEntity.status(500).build();

		}

	}

	@PostMapping(path = "/add", produces = "application/json")
	public ResponseEntity<String> addStudents(@RequestParam String firstName, @RequestParam String lastName,
			@RequestParam String email, @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate dob,
			@RequestParam String phoneNumber) {
		try {
			Date sqlDob = Date.valueOf(dob);
			studentService.addStudent(firstName, lastName, email, sqlDob, phoneNumber);
			return ResponseEntity.status(200).body("Student Added Succesfully");
		} catch (Exception e) {
			return ResponseEntity.status(500).build();
		}

	}

	@PutMapping(path = "/update/{Id}", produces = "application/json")
	public ResponseEntity<String> updateStudents(@PathVariable int Id, @RequestParam String firstName,
			@RequestParam String lastName, @RequestParam String email,
			@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate dob, @RequestParam String phoneNumber) {
		try {
			Date sqlDob = Date.valueOf(dob);
			studentService.updateStudent(Id, firstName, lastName, email, sqlDob, phoneNumber);
			return ResponseEntity.status(200).body("Student updated successfully.");
		} catch (IllegalArgumentException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		} catch (Exception e) {
			return ResponseEntity.status(500).body("Failed to update student.");
		}
	}

}
