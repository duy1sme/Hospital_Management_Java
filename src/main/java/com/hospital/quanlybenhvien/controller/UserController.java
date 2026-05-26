package com.hospital.quanlybenhvien.controller;

import com.hospital.quanlybenhvien.model.User;
import com.hospital.quanlybenhvien.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    // Hiển thị danh sách tài khoản
    @GetMapping
    public String listUsers(@RequestParam(value = "keyword", required = false) String keyword,
                            Model model) {
        model.addAttribute("users", userService.searchUsers(keyword));
        model.addAttribute("keyword", keyword);
        return "users/list";
    }

    // Hiển thị form thêm tài khoản
    @GetMapping("/add")
    public String showAddForm(Model model) {
        User user = new User();
        user.setEnabled(true);
        user.setRole("ROLE_DOCTOR");
        model.addAttribute("user", user);
        model.addAttribute("title", "Thêm tài khoản");
        return "users/form";
    }

    // Xử lý thêm tài khoản
    @PostMapping("/add")
    public String addUser(@ModelAttribute User user) {
        userService.addUser(user);
        return "redirect:/admin/users";
    }

    // Hiển thị form sửa tài khoản
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable int id, Model model) {
        User user = userService.findById(id);
        if (user == null) {
            return "redirect:/admin/users";
        }

        // Không đưa mật khẩu đã mã hóa ra form
        user.setPassword("");
        model.addAttribute("user", user);
        model.addAttribute("title", "Sửa tài khoản");
        return "users/form";
    }

    // Xử lý sửa tài khoản
    @PostMapping("/edit")
    public String updateUser(@ModelAttribute User user) {
        userService.updateUser(user);
        return "redirect:/admin/users";
    }

    // Xóa tài khoản
    @GetMapping("/delete/{id}")
    public String deleteUser(@PathVariable int id) {
        userService.deleteUser(id);
        return "redirect:/admin/users";
    }
}
