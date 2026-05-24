-- Tạo database
CREATE DATABASE IF NOT EXISTS quanlybenhvien
    CHARACTER SET utf8mb4
    COLLATE utf8mb4_unicode_ci;

USE quanlybenhvien;

-- Bảng tài khoản đăng nhập
CREATE TABLE users (
                       id        INT AUTO_INCREMENT PRIMARY KEY,
                       username  VARCHAR(50)  NOT NULL UNIQUE,
                       password  VARCHAR(255) NOT NULL,
                       full_name VARCHAR(100) NOT NULL,
                       role      VARCHAR(30)  NOT NULL,
                       enabled   TINYINT(1)   NOT NULL DEFAULT 1
);

-- Bảng bệnh nhân
CREATE TABLE patients (
                          id               INT AUTO_INCREMENT PRIMARY KEY,
                          full_name        VARCHAR(100) NOT NULL,
                          date_of_birth    DATE,
                          gender           VARCHAR(10),
                          phone            VARCHAR(15),
                          address          VARCHAR(255),
                          diagnosis        VARCHAR(255),
                          created_at       DATETIME DEFAULT CURRENT_TIMESTAMP
);

-- Bảng bác sĩ
CREATE TABLE doctors (
                         id          INT AUTO_INCREMENT PRIMARY KEY,
                         full_name   VARCHAR(100) NOT NULL,
                         specialty   VARCHAR(100),
                         phone       VARCHAR(15),
                         email       VARCHAR(100),
                         active      TINYINT(1) DEFAULT 1
);

-- Tài khoản mẫu (mật khẩu: 123456 đã mã hóa BCrypt)
INSERT INTO users (username, password, full_name, role) VALUES
                                                            ('admin',   '$2a$10$slYQmyNdgTY18LRESmJ.QOdFONK4TKqFMXfqnLTXFHqDVaGDHiDpe', 'Quản Trị Viên', 'ROLE_ADMIN'),
                                                            ('bacsi1',  '$2a$10$slYQmyNdgTY18LRESmJ.QOdFONK4TKqFMXfqnLTXFHqDVaGDHiDpe', 'BS. Nguyễn Văn An', 'ROLE_DOCTOR');

-- Dữ liệu mẫu bác sĩ
INSERT INTO doctors (full_name, specialty, phone, email) VALUES
                                                             ('BS. Nguyễn Văn An',  'Nội khoa',  '0901111111', 'an@hospital.com'),
                                                             ('BS. Trần Thị Bình',  'Nhi khoa',  '0902222222', 'binh@hospital.com'),
                                                             ('BS. Lê Văn Cường',   'Tim mạch',  '0903333333', 'cuong@hospital.com');

-- Dữ liệu mẫu bệnh nhân
INSERT INTO patients (full_name, date_of_birth, gender, phone, address, diagnosis) VALUES
                                                                                       ('Nguyễn Thị Mai',  '1990-05-10', 'Nữ',  '0911111111', 'Hà Nội', 'Cảm cúm'),
                                                                                       ('Trần Văn Hùng',   '1985-08-20', 'Nam', '0922222222', 'Hà Nội', 'Đau dạ dày'),
                                                                                       ('Lê Thị Lan',      '2000-03-15', 'Nữ',  '0933333333', 'Hà Nội', 'Sốt xuất huyết');