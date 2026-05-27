package com.hospital.quanlybenhvien.model;

// User.java
// - Chức năng: Đại diện cho thông tin tài khoản người dùng
public class User {

    private int id;              // Mã số tài khoản (Primary Key)
    private String username;     // Tên đăng nhập (dùng khi login)
    private String password;     // Mật khẩu đã mã hóa (không lưu plaintext)
    private String fullName;     // Họ tên đầy đủ của người dùng
    private String role;         // Quyền hạn: ROLE_ADMIN (quản trị) hoặc ROLE_DOCTOR (bác sĩ)
    private boolean enabled;     // Trạng thái: true=hoạt động, false=bị khóa

    // Constructor không tham số (no-arg constructor)
    // Dùng khi Spring Data JPA hoặc Thymeleaf khởi tạo object từ database hoặc form
    public User() {}

    // ===== GETTERS VÀ SETTERS =====
    // Getter: phương thức lấy giá trị | Setter: phương thức gán giá trị
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }

    // Dùng "is" thay vì "get" cho boolean
    public boolean isEnabled() { return enabled; }
    public void setEnabled(boolean enabled) { this.enabled = enabled; }
}