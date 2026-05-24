package com.hospital.quanlybenhvien.repository;

import com.hospital.quanlybenhvien.model.Patient;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class PatientRepository {

    private final JdbcTemplate jdbcTemplate;

    public PatientRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // Map ResultSet → Patient
    private Patient mapRow(java.sql.ResultSet rs, int rowNum) throws java.sql.SQLException {
        Patient p = new Patient();
        p.setId(rs.getInt("id"));
        p.setFullName(rs.getString("full_name"));
        p.setDateOfBirth(rs.getString("date_of_birth"));
        p.setGender(rs.getString("gender"));
        p.setPhone(rs.getString("phone"));
        p.setAddress(rs.getString("address"));
        p.setDiagnosis(rs.getString("diagnosis"));
        p.setCreatedAt(rs.getString("created_at"));
        return p;
    }

    public List<Patient> findAll() {
        String sql = "SELECT * FROM patients ORDER BY created_at DESC";
        return jdbcTemplate.query(sql, this::mapRow);
    }

    public Patient findById(int id) {
        String sql = "SELECT * FROM patients WHERE id = ?";
        List<Patient> list = jdbcTemplate.query(sql, this::mapRow, id);
        return list.isEmpty() ? null : list.get(0);
    }

    public List<Patient> search(String keyword) {
        String sql = "SELECT * FROM patients WHERE full_name LIKE ? OR phone LIKE ?";
        String kw = "%" + keyword + "%";
        return jdbcTemplate.query(sql, this::mapRow, kw, kw);
    }

    public void save(Patient p) {
        String sql = "INSERT INTO patients (full_name, date_of_birth, gender, phone, address, diagnosis) VALUES (?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql,
                p.getFullName(), p.getDateOfBirth(), p.getGender(),
                p.getPhone(), p.getAddress(), p.getDiagnosis());
    }

    public void update(Patient p) {
        String sql = "UPDATE patients SET full_name=?, date_of_birth=?, gender=?, phone=?, address=?, diagnosis=? WHERE id=?";
        jdbcTemplate.update(sql,
                p.getFullName(), p.getDateOfBirth(), p.getGender(),
                p.getPhone(), p.getAddress(), p.getDiagnosis(), p.getId());
    }

    public void delete(int id) {
        String sql = "DELETE FROM patients WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }

    public long count() {
        String sql = "SELECT COUNT(*) FROM patients";
        Long result = jdbcTemplate.queryForObject(sql, Long.class);
        return result != null ? result : 0;
    }
}