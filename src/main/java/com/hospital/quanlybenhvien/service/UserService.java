package com.hospital.quanlybenhvien.service;

import com.hospital.quanlybenhvien.model.User;
import com.hospital.quanlybenhvien.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // Lấy danh sách tài khoản, nếu có từ khóa thì tìm kiếm
    public List<User> searchUsers(String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) {
            return userRepository.findAll();
        }
        return userRepository.search(keyword.trim());
    }

    // Tìm tài khoản theo id
    public User findById(int id) {
        return userRepository.findById(id);
    }

    // Thêm tài khoản mới
    public void addUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }

    // Cập nhật tài khoản
    public void updateUser(User user) {
        User oldUser = userRepository.findById(user.getId());
        if (oldUser == null) {
            return;
        }

        // Nếu không nhập mật khẩu mới thì giữ mật khẩu cũ
        if (user.getPassword() == null || user.getPassword().trim().isEmpty()) {
            user.setPassword(oldUser.getPassword());
        } else {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        }

        userRepository.update(user);
    }

    // Xóa tài khoản
    public void deleteUser(int id) {
        userRepository.delete(id);
    }
}
