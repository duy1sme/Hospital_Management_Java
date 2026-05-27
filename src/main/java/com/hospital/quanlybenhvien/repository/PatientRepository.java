package com.hospital.quanlybenhvien.repository;

import com.hospital.quanlybenhvien.model.Patient;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

// PatientRepository - Lớp truy cập dữ liệu cho bảng patients
// Quản lý tất cả các thao tác CRUD với bệnh nhân
@Repository
// PatientRepository.java
// - Chức năng: Thao tác cơ sở dữ liệu (CRUD) bảng patients qua JDBC
public class PatientRepository {

    // DataSource = nguồn cung cấp kết nối đến MySQL (quản lý connection pool)
    private final DataSource dataSource;

    public PatientRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    // Hàm dùng chung để map ResultSet → Patient object
    private Patient mapRow(ResultSet rs) throws SQLException {
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

    // Lấy tất cả bệnh nhân
// Sắp xếp theo ngày tạo mới nhất trước
    public List<Patient> findAll() {
        List<Patient> list = new ArrayList<>();
        String sql = "SELECT * FROM patients ORDER BY created_at DESC"; // DESC = mới nhất trước

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

    // Tìm bệnh nhân theo ID
// Dùng khi cần hiển thị chi tiết bệnh nhân hoặc sửa thông tin
    public Patient findById(int id) {
        String sql = "SELECT * FROM patients WHERE id = ?";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id); // ? đầu tiên = id
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return mapRow(rs);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Tìm kiếm bệnh nhân theo tên hoặc số điện thoại
// @param keyword: từ khóa tìm kiếm
    public List<Patient> search(String keyword) {
        List<Patient> list = new ArrayList<>();
        String sql = "SELECT * FROM patients WHERE full_name LIKE ? OR phone LIKE ?";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            String kw = "%" + keyword + "%";
            ps.setString(1, kw); // ? đầu tiên = tìm theo tên
            ps.setString(2, kw); // ? thứ hai  = tìm theo số điện thoại
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                list.add(mapRow(rs));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    // Thêm bệnh nhân mới
// created_at sẽ được database tự động ghi bằng CURRENT_TIMESTAMP
    public void save(Patient p) {
        String sql = "INSERT INTO patients (full_name, date_of_birth, gender, phone, address, diagnosis) " +
                "VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, p.getFullName());
            ps.setString(2, p.getDateOfBirth());
            ps.setString(3, p.getGender());
            ps.setString(4, p.getPhone());
            ps.setString(5, p.getAddress());
            ps.setString(6, p.getDiagnosis());
            ps.executeUpdate(); // Thực hiện INSERT

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Cập nhật thông tin bệnh nhân
// Chỉ cập nhật thông tin, không cập nhật ngày tạo
    public void update(Patient p) {
        String sql = "UPDATE patients SET full_name=?, date_of_birth=?, gender=?, " +
                "phone=?, address=?, diagnosis=? WHERE id=?";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, p.getFullName());
            ps.setString(2, p.getDateOfBirth());
            ps.setString(3, p.getGender());
            ps.setString(4, p.getPhone());
            ps.setString(5, p.getAddress());
            ps.setString(6, p.getDiagnosis());
            ps.setInt(7, p.getId());    // WHERE id = ?
            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Xóa bệnh nhân theo ID
// CHỦÍ: Sẽ xóa hết lịch hẹn liên quan nếu có ràng buộc khóa ngoài
    public void delete(int id) {
        String sql = "DELETE FROM patients WHERE id = ?";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Đếm tổng số bệnh nhân
// Dùng cho thống kê trên dashboard
    public int count() {
        String sql = "SELECT COUNT(*) FROM patients";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            if (rs.next()) {
                return rs.getInt(1); // Lấy giá trị COUNT từ cột đầu tiên
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
}