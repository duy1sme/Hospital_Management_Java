package com.hospital.quanlybenhvien.controller;

import com.hospital.quanlybenhvien.model.User;
import com.hospital.quanlybenhvien.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

// Quản lý tài khoản — chỉ ADMIN truy cập được (cấu hình trong SecurityConfig)
@Controller
@RequestMapping("/admin/users")
// UserController.java
// - Chức năng: Điều phối các yêu cầu HTTP quản lý tài khoản
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    // GET /admin/users — danh sách tài khoản, hỗ trợ tìm kiếm
    @GetMapping
    public String listUsers(
            @RequestParam(value = "keyword", required = false) String keyword,
            Model model) {
        model.addAttribute("users", userService.searchUsers(keyword));
        model.addAttribute("keyword", keyword);
        return "users/list";
    }

    // GET /admin/users/add — mở form thêm tài khoản mới
    // Đặt giá trị mặc định: enabled=true, role=ROLE_DOCTOR
    @GetMapping("/add")
    public String showAddForm(Model model) {
        User user = new User();
        user.setEnabled(true);
        user.setRole("ROLE_DOCTOR"); // mặc định là bác sĩ, admin có thể đổi
        model.addAttribute("user", user);
        model.addAttribute("title", "Thêm tài khoản");
        return "users/form";
    }

    // POST /admin/users/add — lưu tài khoản mới
    // UserService sẽ tự mã hóa BCrypt trước khi lưu DB
    @PostMapping("/add")
    public String addUser(@ModelAttribute User user) {
        userService.addUser(user);
        return "redirect:/admin/users";
    }

    // GET /admin/users/edit/{id} — mở form sửa
    // Xóa mật khẩu đã hash trước khi đưa ra form (tránh lộ hash)
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable int id, Model model) {
        User user = userService.findById(id);
        if (user == null) return "redirect:/admin/users";

        user.setPassword(""); // không hiện mật khẩu cũ ra form
        model.addAttribute("user", user);
        model.addAttribute("title", "Sửa tài khoản");
        return "users/form";
    }

    // POST /admin/users/edit — lưu chỉnh sửa
    // Nếu ô mật khẩu bỏ trống → UserService giữ nguyên mật khẩu cũ
    @PostMapping("/edit")
    public String updateUser(@ModelAttribute User user) {
        userService.updateUser(user);
        return "redirect:/admin/users";
    }

    // GET /admin/users/delete/{id} — xóa tài khoản
    @GetMapping("/delete/{id}")
    public String deleteUser(@PathVariable int id) {
        userService.deleteUser(id);
        return "redirect:/admin/users";
    }
}