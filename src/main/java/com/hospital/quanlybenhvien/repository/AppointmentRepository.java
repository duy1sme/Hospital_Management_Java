package com.hospital.quanlybenhvien.repository;

import com.hospital.quanlybenhvien.model.Appointment;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository

public class AppointmentRepository {

    private final DataSource dataSource;

    public AppointmentRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    private Appointment mapRow(ResultSet rs) throws SQLException {
        Appointment a = new Appointment();
        a.setId(rs.getInt("id"));
        a.setPatientId(rs.getInt("patient_id"));
        a.setPatientName(rs.getString("patient_name"));
        a.setDoctorId(rs.getInt("doctor_id"));
        a.setDoctorName(rs.getString("doctor_name"));
        a.setAppointmentDate(rs.getString("appointment_date"));
        a.setAppointmentTime(rs.getString("appointment_time"));
        a.setStatus(rs.getString("status"));
        a.setNote(rs.getString("note"));
        a.setCreatedAt(rs.getString("created_at"));
        return a;
    }

    public List<Appointment> findAll() {
        List<Appointment> list = new ArrayList<>();

        String sql = """
                SELECT a.*,
                       p.full_name AS patient_name,
                       d.full_name AS doctor_name
                FROM appointments a
                JOIN patients p ON a.patient_id = p.id
                JOIN doctors  d ON a.doctor_id  = d.id
                ORDER BY a.appointment_date DESC, a.appointment_time DESC
                """;

        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) list.add(mapRow(rs));

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

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
            ps.setString(1, kw);
            ps.setString(2, kw);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) list.add(mapRow(rs));

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public void save(Appointment a) {
        String sql = "INSERT INTO appointments (patient_id, doctor_id, appointment_date, appointment_time, status, note) " +
                "VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setObject(1, a.getPatientId());
            ps.setObject(2, a.getDoctorId());
            ps.setString(3, a.getAppointmentDate());
            ps.setString(4, a.getAppointmentTime());
            ps.setString(5, "Chờ xác nhận");
            ps.setString(6, a.getNote());
            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateStatus(int id, String status) {
        String sql = "UPDATE appointments SET status = ? WHERE id = ?";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, status);
            ps.setInt(2, id);
            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

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
