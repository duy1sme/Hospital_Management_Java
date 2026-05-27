package com.hospital.quanlybenhvien.repository;

import com.hospital.quanlybenhvien.model.User;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

// UserRepository - Lớp truy cập dữ liệu cho bảng users
// Quản lý tất cả các thao tác với database liên quan đến tài khoản người dùng
// Sử dụng JDBC để execute trực tiếp SQL queries
// @Repository: Đánh dấu đây là một repository bean của Spring
@Repository
// UserRepository.java
// - Chức năng: Thao tác cơ sở dữ liệu (CRUD) bảng users qua JDBC
public class UserRepository {

    // DataSource: kết nối pool đến database MySQL (được cấu hình tự động bởi Spring)
    private final DataSource dataSource;

    // Constructor injection: Spring sẽ tự động inject DataSource
    public UserRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    // Phương thức hỗ trợ: Chuyển đổi dòng dữ liệu từ ResultSet thành object User
// @param rs: ResultSet chứa dữ liệu từ database
// @return: object User với các thuộc tính được gán từ ResultSet
// @throws SQLException: nếu xảy ra lỗi khi đọc dữ liệu
    private User mapRow(ResultSet rs) throws SQLException {
        User u = new User();
        u.setId(rs.getInt("id"));
        u.setUsername(rs.getString("username"));
        u.setPassword(rs.getString("password"));
        u.setFullName(rs.getString("full_name"));
        u.setRole(rs.getString("role"));
        u.setEnabled(rs.getBoolean("enabled"));
        return u;
    }

    // Tìm tài khoản theo username
// Dùng cho chức năng đăng nhập và xác thực người dùng
// @param username: tên đăng nhập cần tìm
// @return: object User nếu tìm thấy, hoặc null nếu không tìm thấy
    public User findByUsername(String username) {
        String sql = "SELECT * FROM users WHERE username = ?"; // SQL query với placeholder ?

        try (Connection conn = dataSource.getConnection();          // Lấy kết nối từ pool
             PreparedStatement ps = conn.prepareStatement(sql)) {   // Chuẩn bị statement

            ps.setString(1, username);  // Gán giá trị vào placeholder ? đầu tiên
            ResultSet rs = ps.executeQuery(); // Thực hiện query

            if (rs.next()) {  // Nếu có dữ liệu trả về
                return mapRow(rs); // Chuyển đổi và trả về
            }

        } catch (SQLException e) {
            e.printStackTrace(); // In lỗi ra console
        }
        return null; // Không tìm thấy
    }

    // Lấy tất cả tài khoản từ database
// @return: danh sách tất cả người dùng
    public List<User> findAll() {
        List<User> list = new ArrayList<>();
        String sql = "SELECT * FROM users ORDER BY id"; // Sắp xếp theo ID tăng dần

        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            // Lặp qua tất cả các dòng trong ResultSet
            while (rs.next()) {
                list.add(mapRow(rs)); // Chuyển đổi mỗi dòng thành User object
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list; // Trả về danh sách (có thể rỗng nếu không có dữ liệu)
    }

    // Tìm tài khoản theo ID
// Dùng khi muốn hiển thị form sửa tài khoản
// @param id: ID của tài khoản cần tìm
// @return: object User hoặc null
    public User findById(int id) {
        String sql = "SELECT * FROM users WHERE id = ?";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);  // Gán id vào placeholder
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return mapRow(rs);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Tìm kiếm tài khoản theo từ khóa
// Tìm trong username hoặc họ tên
// @param keyword: từ khóa tìm kiếm
// @return: danh sách các tài khoản phù hợp
    public List<User> search(String keyword) {
        List<User> list = new ArrayList<>();
        // LIKE %keyword% để tìm kiếm chứa từ khóa trong username hoặc full_name
        String sql = "SELECT * FROM users WHERE username LIKE ? OR full_name LIKE ?";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            String kw = "%" + keyword + "%"; // Thêm % trước và sau từ khóa
            ps.setString(1, kw);  // Tìm trong username
            ps.setString(2, kw);  // Tìm trong full_name
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                list.add(mapRow(rs));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    // Thêm tài khoản mới vào database
// @param u: object User chứa thông tin tài khoản mới
    public void save(User u) {
        // INSERT INTO: thêm dòng mới vào bảng users
        String sql = "INSERT INTO users (username, password, full_name, role, enabled) " +
                "VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            // Gán giá trị cho từng placeholder ? theo thứ tự
            ps.setString(1, u.getUsername());
            ps.setString(2, u.getPassword());
            ps.setString(3, u.getFullName());
            ps.setString(4, u.getRole());
            ps.setBoolean(5, u.isEnabled());
            ps.executeUpdate(); // Thực hiện câu lệnh INSERT

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Cập nhật thông tin tài khoản
// @param u: object User chứa ID và thông tin mới cần cập nhật
    public void update(User u) {
        // UPDATE: sửa dữ liệu của dòng có ID = ?
        String sql = "UPDATE users SET username=?, password=?, full_name=?, role=?, enabled=? WHERE id=?";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, u.getUsername());
            ps.setString(2, u.getPassword());
            ps.setString(3, u.getFullName());
            ps.setString(4, u.getRole());
            ps.setBoolean(5, u.isEnabled());
            ps.setInt(6, u.getId()); // WHERE id = ?
            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Xóa tài khoản theo ID
// @param id: ID của tài khoản cần xóa
    public void delete(int id) {
        String sql = "DELETE FROM users WHERE id = ?"; // DELETE: xóa dòng có id = ?

        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Đếm tổng số tài khoản
// @return: số lượng tài khoản
    public long count() {
        String sql = "SELECT COUNT(*) FROM users"; // COUNT(*): đếm tất cả dòng

        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            if (rs.next()) return rs.getLong(1); // Lấy giá trị COUNT từ cột đầu tiên

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0; // Trả về 0 nếu lỗi
    }
}
