package com.hospital.quanlybenhvien.repository;

import com.hospital.quanlybenhvien.model.Doctor;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

// DoctorRepository - Lớp truy cập dữ liệu cho bảng doctors
// Quản lý tất cả các thao tác với database liên quan đến bác sĩ
@Repository
// DoctorRepository.java
// - Chức năng: Thao tác cơ sở dữ liệu (CRUD) bảng doctors qua JDBC
public class DoctorRepository {

    private final DataSource dataSource; // Kết nối pool đến database

    public DoctorRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    // Chuyển đổi dòng ResultSet thành object Doctor
    private Doctor mapRow(ResultSet rs) throws SQLException {
        Doctor d = new Doctor();
        d.setId(rs.getInt("id"));
        d.setFullName(rs.getString("full_name"));
        d.setSpecialty(rs.getString("specialty"));
        d.setPhone(rs.getString("phone"));
        d.setEmail(rs.getString("email"));
        d.setActive(rs.getBoolean("active"));
        return d;
    }

    // Lấy tất cả bác sĩ từ database
    public List<Doctor> findAll() {
        List<Doctor> list = new ArrayList<>();
        String sql = "SELECT * FROM doctors";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                list.add(mapRow(rs));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    // Tìm bác sĩ theo ID
    public Doctor findById(int id) {
        String sql = "SELECT * FROM doctors WHERE id = ?";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return mapRow(rs);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Tìm kiếm bác sĩ theo từ khóa (tên, SĐT, chuyên khoa)
    public List<Doctor> search(String keyword) {
        List<Doctor> list = new ArrayList<>();
        String sql = "SELECT * FROM doctors WHERE full_name LIKE ? OR phone LIKE ? OR specialty LIKE ?";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            String kw = "%" + keyword + "%";
            ps.setString(1, kw);  // Tìm trong full_name
            ps.setString(2, kw);  // Tìm trong phone
            ps.setString(3, kw);  // Tìm trong specialty
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                list.add(mapRow(rs));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    // Thêm bác sĩ mới
    public void save(Doctor d) {
        String sql = "INSERT INTO doctors (full_name, specialty, phone, email, active) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, d.getFullName());
            ps.setString(2, d.getSpecialty());
            ps.setString(3, d.getPhone());
            ps.setString(4, d.getEmail());
            ps.setBoolean(5, d.isActive());
            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Cập nhật thông tin bác sĩ
    public void update(Doctor d) {
        String sql = "UPDATE doctors SET full_name=?, specialty=?, phone=?, email=?, active=? WHERE id=?";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, d.getFullName());
            ps.setString(2, d.getSpecialty());
            ps.setString(3, d.getPhone());
            ps.setString(4, d.getEmail());
            ps.setBoolean(5, d.isActive());
            ps.setInt(6, d.getId());
            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Xóa bác sĩ theo ID
    public void delete(int id) {
        String sql = "DELETE FROM doctors WHERE id = ?";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Đếm tổng số bác sĩ
    public int count() {
        String sql = "SELECT COUNT(*) FROM doctors";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            if (rs.next()) return rs.getInt(1);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    // Đếm số bác sĩ đang làm việc (active = 1)
// Dùng cho dashboard để hiển thị thống kê
    public int countActive() {
        String sql = "SELECT COUNT(*) FROM doctors WHERE active = 1";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            if (rs.next()) return rs.getInt(1);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
}