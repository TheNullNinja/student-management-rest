package com.student.datasource;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.student.models.StudentResponse;
import com.student.models.StudentResponse.Record;

@Component
public class StudentConnectionUtil {

	private final JdbcTemplate jdbcTemplate;

	// Constructor based template injection
	public StudentConnectionUtil(@Qualifier("studentJdbcTemplate") JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	public List<Record> fetchStudentsDetail() throws SQLException {

		String sql = "SELECT * FROM students_details";
		List<StudentResponse.Record> records = jdbcTemplate.query(sql, new StudentRowMapper());
		System.out.println("Records = " + records.toString());
		return records;

	}

	public void insertStudentDetails(String firstName, String lastName, String email, Date dob,
			String phoneNumber) throws SQLException {

		String sql = "INSERT INTO students_details (first_name, last_name, email, dob, phone_number) VALUES (?, ?, ?, ?, ?)";

		jdbcTemplate.update(sql, firstName, lastName, email, dob, phoneNumber);

	}

	public void updateStudentDetails(int studentID, String firstName, String lastName, String email, Date dob,
			String phoneNumber) {

		StringBuilder sql = new StringBuilder("UPDATE students_details SET ");
		List<Object> params = new ArrayList<>();

		// Map columns to values
		Map<String, Object> fieldsToUpdate = Map.of("first_name", firstName, "last_name", lastName, "email", email,
				"dob", dob, "phone_number", phoneNumber);

		boolean firstField = true;

		for (Map.Entry<String, Object> entry : fieldsToUpdate.entrySet()) {
			if (entry.getValue() != null
					&& !(entry.getValue() instanceof String && ((String) entry.getValue()).isEmpty())) {
				if (!firstField) {
					sql.append(", ");
				}
				sql.append(entry.getKey()).append(" = ?");
				params.add(entry.getValue());
				firstField = false;
			}
		}

		if (params.isEmpty()) {
			throw new IllegalArgumentException("No fields to update!");
		}

		sql.append(" WHERE student_id = ?");
		params.add(studentID);

		jdbcTemplate.update(sql.toString(), params.toArray());
	}

	private static class StudentRowMapper implements RowMapper<Record> {
		@Override
		public Record mapRow(ResultSet rs, int rowNum) throws SQLException {
			Record record = new Record();
			record.setRollNumber(rs.getInt("student_id"));
			record.setFirstName(rs.getString("first_name"));
			record.setLastName(rs.getString("last_name"));
			record.setEmail(rs.getString("email"));
			record.setDob(rs.getDate("dob"));
			record.setPhoneNumber(rs.getString("phone_number"));
			return record;
		}
	}

}
