package com.hospital.quanlybenhvien.repository;

import com.hospital.quanlybenhvien.model.Doctor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class DoctorRepository {

    private final JdbcTemplate jdbcTemplate;

    public DoctorRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // Map ResultSet → Patient
    private Doctor mapRow(java.sql.ResultSet rs, int rowNum) throws java.sql.SQLException {
        Doctor p = new Doctor();
        p.setId(rs.getInt("id"));
        p.setFullName(rs.getString("full_name"));
        p.setSpecialty(rs.getString("specialty"));
        p.setPhone(rs.getString("phone"));
        p.setEmail(rs.getString("email"));
        return p;
    }

    public List<Doctor> findAll() {
        String sql = "SELECT * FROM doctors ORDER BY created_at DESC";
        return jdbcTemplate.query(sql, this::mapRow);
    }

    public Doctor findById(int id) {
        String sql = "SELECT * FROM doctors WHERE id = ?";
        List<Doctor> list = jdbcTemplate.query(sql, this::mapRow, id);
        return list.isEmpty() ? null : list.get(0);
    }

    public List<Doctor> search(String keyword) {
        String sql = "SELECT * FROM doctors WHERE full_name LIKE ? OR phone LIKE ?";
        String kw = "%" + keyword + "%";
        return jdbcTemplate.query(sql, this::mapRow, kw, kw);
    }

    public void save(Doctor d) {
        String sql = "INSERT INTO doctors (full_name, specialty, phone, email) VALUES (?, ?, ?, ?)";
        jdbcTemplate.update(sql,
                d.getFullName(), d.getSpecialty(),
                d.getPhone(), d.getEmail());
    }

    public void update(Doctor d) {
        String sql = "UPDATE doctors SET full_name=?, specialty=?, phone=?, email=? WHERE id=?";
        jdbcTemplate.update(sql,
                d.getFullName(), d.getSpecialty(),
                d.getPhone(), d.getEmail());
    }

    public void delete(int id) {
        String sql = "DELETE FROM doctors WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }

    public long count() {
        String sql = "SELECT COUNT(*) FROM doctors";
        Long result = jdbcTemplate.queryForObject(sql, Long.class);
        return result != null ? result : 0;
    }
}