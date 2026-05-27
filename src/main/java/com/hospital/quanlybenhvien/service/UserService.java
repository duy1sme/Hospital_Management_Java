package com.hospital.quanlybenhvien.service;

import com.hospital.quanlybenhvien.model.User;
import com.hospital.quanlybenhvien.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

// UserService - Lớp phục vụ cho logic quản lý tài khoản người dùng
// Nằm giữa Controller và Repository - xử lý business logic
// Đặc biệt: mã hóa mật khẩu bằng BCrypt trước khi lưu vào database
// @Service: Đánh dấu đây là service bean của Spring
@Service
// UserService.java
// - Chức năng: Xử lý logic nghiệp vụ và mã hóa mật khẩu tài khoản
public class UserService {

    private final UserRepository userRepository;      // Truy cập database
    private final PasswordEncoder passwordEncoder;    // Mã hóa mật khẩu

    // Constructor injection: Spring sẽ inject cả hai dependencies
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // Tìm kiếm tài khoản
// Nếu keyword rỗng → lấy tất cả
// Nếu có keyword → tìm kiếm theo keyword
    public List<User> searchUsers(String keyword) {
        // Kiểm tra keyword có hợp lệ không
        if (keyword == null || keyword.trim().isEmpty()) {
            return userRepository.findAll();  // Lấy tất cả nếu không có keyword
        }
        return userRepository.search(keyword.trim()); // Tìm kiếm nếu có keyword
    }

    // Tìm tài khoản theo ID
// Dùng khi cần hiển thị chi tiết hoặc form sửa
    public User findById(int id) {
        return userRepository.findById(id);
    }

    // Thêm tài khoản mới
// QUAN TRỌNG: mã hóa mật khẩu bằng BCrypt trước khi lưu
// Mật khẩu không được lưu dạng plaintext vì lý do bảo mật
    public void addUser(User user) {
        // Mã hóa mật khẩu: plaintext → hashed password
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user); // Lưu vào database
    }

    // Cập nhật tài khoản
// Xử lý 2 trường hợp:
// 1. Nếu không nhập mật khẩu mới → giữ mật khẩu cũ
// 2. Nếu nhập mật khẩu mới → mã hóa và cập nhật
    public void updateUser(User user) {
        // Lấy thông tin cũ từ database
        User oldUser = userRepository.findById(user.getId());
        if (oldUser == null) {
            return; // Tài khoản không tồn tại → không cập nhật
        }

        // Kiểm tra mật khẩu mới
        if (user.getPassword() == null || user.getPassword().trim().isEmpty()) {
            // Không đổi mật khẩu → giữ mật khẩu cũ
            user.setPassword(oldUser.getPassword());
        } else {
            // Có mật khẩu mới → mã hóa nó
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        }

        userRepository.update(user); // Cập nhật vào database
    }

    // Xóa tài khoản
    public void deleteUser(int id) {
        userRepository.delete(id);
    }
}
