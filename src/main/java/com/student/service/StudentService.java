package com.student.service;

import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

import org.springframework.stereotype.Service;

import com.student.datasource.StudentConnectionUtil;
import com.student.models.StudentResponse;

@Service
public class StudentService {
	
	private final StudentConnectionUtil studentConnectionUtil;
	
	public StudentService(StudentConnectionUtil studentConnectionUtil) {
		this.studentConnectionUtil = studentConnectionUtil;
	}
	
	public List<StudentResponse.Record> getAllStudents() throws SQLException {
        return studentConnectionUtil.fetchStudentsDetail();
    }

    public void addStudent(String firstName, String lastName, String email, Date dob, String phoneNumber) throws SQLException {
        studentConnectionUtil.insertStudentDetails(firstName, lastName, email, dob, phoneNumber);
    }

    public void updateStudent(int studentID, String firstName, String lastName, String email, Date dob, String phoneNumber) {
        studentConnectionUtil.updateStudentDetails(studentID, firstName, lastName, email, dob, phoneNumber);
    }

}
