package com.hospital.quanlybenhvien;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

// Ứng dụng quản lý bệnh viện - Điểm khởi động chính
// @SpringBootApplication: Đánh dấu đây là ứng dụng Spring Boot
// Khi chạy, Spring Boot sẽ:
// - Quét tất cả @Component, @Service, @Repository, @Controller
// - Cấu hình database tự động từ application.properties
// - Khởi động máy chủ Tomcat trên cổng 8080
@SpringBootApplication
// QuanlybenhvienApplication.java
// - Chức năng: Khởi chạy ứng dụng Spring Boot chính
public class QuanlybenhvienApplication {

	// Main method: Điểm vào của ứng dụng Java
	// args: các tham số dòng lệnh truyền vào
	public static void main(String[] args) {
		// SpringApplication.run(): Khởi động toàn bộ ứng dụng Spring Boot
		SpringApplication.run(QuanlybenhvienApplication.class, args);
	}

}
