package com.hospital.quanlybenhvien.repository;

import com.hospital.quanlybenhvien.model.Appointment;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

// AppointmentRepository - Lớp truy cập dữ liệu cho bảng appointments
// Quản lý các thao tác với lịch hẹn khám bệnh
// Sử dụng JOIN để lấy tên bệnh nhân và bác sĩ từ các bảng khác
@Repository
// AppointmentRepository.java
// - Chức năng: Thao tác cơ sở dữ liệu (CRUD) bảng appointments qua JDBC
public class AppointmentRepository {

    private final DataSource dataSource;

    public AppointmentRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    // Chuyển đổi dòng ResultSet (với JOIN) thành object Appointment
// ResultSet bao gồm dữ liệu từ 3 bảng: appointments, patients, doctors
    private Appointment mapRow(ResultSet rs) throws SQLException {
        Appointment a = new Appointment();
        a.setId(rs.getInt("id"));
        a.setPatientId(rs.getInt("patient_id"));
        a.setPatientName(rs.getString("patient_name"));    // Từ JOIN
        a.setDoctorId(rs.getInt("doctor_id"));
        a.setDoctorName(rs.getString("doctor_name"));      // Từ JOIN
        a.setAppointmentDate(rs.getString("appointment_date"));
        a.setAppointmentTime(rs.getString("appointment_time"));
        a.setStatus(rs.getString("status"));
        a.setNote(rs.getString("note"));
        a.setCreatedAt(rs.getString("created_at"));
        return a;
    }

    // Lấy tất cả lịch hẹn
// Sử dụng JOIN để lấy tên bệnh nhân từ bảng patients
// và tên bác sĩ từ bảng doctors
    public List<Appointment> findAll() {
        List<Appointment> list = new ArrayList<>();
        // Multi-line SQL query (text block)
        String sql = """
                SELECT a.*,
                       p.full_name AS patient_name,
                       d.full_name AS doctor_name
                FROM appointments a
                JOIN patients p ON a.patient_id = p.id
                JOIN doctors  d ON a.doctor_id  = d.id
                ORDER BY a.appointment_date DESC, a.appointment_time DESC
                """;
        // Sắp xếp theo ngày và giờ gần nhất trước

        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) list.add(mapRow(rs));

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    // Tìm lịch hẹn theo ID
// Cũng sử dụng JOIN để lấy tên bệnh nhân và bác sĩ
    public Appointment findById(int id) {
        String sql = """
                SELECT a.*,
                       p.full_name AS patient_name,
                       d.full_name AS doctor_name
                FROM appointments a
                JOIN patients p ON a.patient_id = p.id
                JOIN doctors  d ON a.doctor_id  = d.id
                WHERE a.id = ?
                """;

        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return mapRow(rs);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Đếm lịch hẹn hôm nay
// CURDATE(): hàm MySQL lấy ngày hôm nay
// Dùng cho dashboard để hiển thị số lịch hẹn trong ngày
    public int countToday() {
        String sql = "SELECT COUNT(*) FROM appointments WHERE appointment_date = CURDATE()";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            if (rs.next()) return rs.getInt(1);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    // Tìm kiếm lịch hẹn theo từ khóa
// Tìm theo tên bệnh nhân hoặc trạng thái
    public List<Appointment> search(String keyword) {
        List<Appointment> list = new ArrayList<>();
        String sql = """
                SELECT a.*,
                       p.full_name AS patient_name,
                       d.full_name AS doctor_name
                FROM appointments a
                JOIN patients p ON a.patient_id = p.id
                JOIN doctors  d ON a.doctor_id  = d.id
                WHERE p.full_name LIKE ? OR a.status LIKE ?
                ORDER BY a.appointment_date DESC
                """;

        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            String kw = "%" + keyword + "%";
            ps.setString(1, kw);  // Tìm theo tên bệnh nhân
            ps.setString(2, kw);  // Tìm theo trạng thái
            ResultSet rs = ps.executeQuery();
            while (rs.next()) list.add(mapRow(rs));

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    // Thêm lịch hẹn mới
// Trạng thái mặc định là "Chờ xác nhận"
    public void save(Appointment a) {
        String sql = "INSERT INTO appointments (patient_id, doctor_id, appointment_date, appointment_time, status, note) " +
                "VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setObject(1, a.getPatientId());
            ps.setObject(2, a.getDoctorId());
            ps.setString(3, a.getAppointmentDate());
            ps.setString(4, a.getAppointmentTime());
            ps.setString(5, "Chờ xác nhận"); // mặc định khi tạo mới
            ps.setString(6, a.getNote());
            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Cập nhật trạng thái lịch hẹn
// Trạng thái có thể: Chờ xác nhận, Đã xác nhận, Hoàn thành, Đã hủy
    public void updateStatus(int id, String status) {
        String sql = "UPDATE appointments SET status = ? WHERE id = ?";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, status);  // Trạng thái mới
            ps.setInt(2, id);         // Lịch hẹn nào
            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Xóa lịch hẹn theo ID
    public void delete(int id) {
        String sql = "DELETE FROM appointments WHERE id = ?";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}