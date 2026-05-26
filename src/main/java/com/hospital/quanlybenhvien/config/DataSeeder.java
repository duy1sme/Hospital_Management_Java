package com.hospital.quanlybenhvien.config;

import com.hospital.quanlybenhvien.repository.UserRepository;
import com.hospital.quanlybenhvien.model.User;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataSeeder implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public DataSeeder(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) throws Exception {
        // Chỉ tạo nếu chưa có tài khoản nào trong database
        if (userRepository.findByUsername("admin") == null) {

            User admin = new User();
            admin.setUsername("admin");
            admin.setPassword(passwordEncoder.encode("123456")); // mã hóa đúng
            admin.setFullName("Quản Trị Viên");
            admin.setRole("ROLE_ADMIN");
            admin.setEnabled(true);
            userRepository.save(admin);

            User doctor = new User();
            doctor.setUsername("bacsi1");
            doctor.setPassword(passwordEncoder.encode("123456"));
            doctor.setFullName("BS. Nguyễn Văn An");
            doctor.setRole("ROLE_DOCTOR");
            doctor.setEnabled(true);
            userRepository.save(doctor);

            System.out.println(">>> Đã tạo tài khoản mặc định. Mật khẩu: 123456");
        }
    }
}