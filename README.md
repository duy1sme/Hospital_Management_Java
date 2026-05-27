# 🏥 Hospital Management — Hệ Thống Quản Lý Bệnh Viện

Chào mừng bạn đến với **Hệ Thống Quản Lý Bệnh Viện (Hospital Management)**. Đây là một ứng dụng web hoàn chỉnh, được xây dựng trên nền tảng **Spring Boot** và sử dụng cơ sở dữ liệu MySQL đảm bảo hiệu năng mạnh mẽ cũng như giao diện thân thiện.

Hệ thống được thiết kế tối ưu phục vụ công tác quản lý tài khoản nhân viên, bác sĩ, hồ sơ bệnh nhân, lịch hẹn khám và thống kê tổng quan trong bệnh viện.

---

## 🌟 Tính Năng Nổi Bật

Hệ thống sở hữu các phân hệ chức năng toàn diện và được phân quyền chặt chẽ thông qua Spring Security:

1. **📊 Dashboard Tổng Quan (Trang Chủ)**
   * Thống kê thời gian thực số lượng bệnh nhân đang điều trị.
   * Số lượng bác sĩ thuộc hệ thống.
   * Số lượng bác sĩ đang trực tiếp làm việc.
   * Tổng số lịch hẹn đã lên lịch trong ngày hôm nay.
2. **👤 Quản Lý Bệnh Nhân**
   * Lưu trữ hồ sơ bệnh nhân: Họ tên, ngày sinh, giới tính, số điện thoại, địa chỉ, chẩn đoán ban đầu.
   * Hỗ trợ tìm kiếm nhanh theo Họ tên hoặc Số điện thoại.
   * Đầy đủ chức năng thêm mới, chỉnh sửa thông tin và xóa bệnh nhân.
3. **🩺 Quản Lý Bác Sĩ**
   * Theo dõi thông tin bác sĩ: Tên, chuyên khoa, số điện thoại, email.
   * Quản lý trạng thái hoạt động (Đang làm việc / Nghỉ việc).
4. **📅 Quản Lý Lịch Hẹn Khám (Đặt Lịch)**
   * Tạo lịch hẹn khám linh hoạt kết nối giữa Bệnh nhân và Bác sĩ.
   * Chọn ngày khám, giờ khám và ghi chú triệu chứng cụ thể.
   * Cập nhật trạng thái lịch hẹn theo quy trình khám bệnh: *Chờ xác nhận*, *Đã xác nhận*, *Hoàn thành*, *Đã hủy*.
5. **🔑 Phân Quyền & Bảo Mật (Spring Security)**
   * Xác thực tài khoản đăng nhập/đăng xuất chuyên nghiệp, chống truy cập trái phép.
   * Phân quyền dựa trên vai trò (Role-based access control):
     * **Quản Trị Viên (Admin)**: Toàn quyền truy cập tất cả chức năng, bao gồm quản lý tài khoản hệ thống (`users`).
     * **Bác Sĩ (Doctor)**: Quản lý bệnh nhân, bác sĩ, đặt lịch khám nhưng bị giới hạn truy cập phân hệ Quản lý tài khoản.
6. **🌱 Tự Động Gieo Dữ Liệu (Data Seeding)**
   * Tự động khởi tạo 2 tài khoản mặc định (Admin và Bác sĩ mẫu) ngay lần đầu khởi chạy nếu hệ thống chưa có dữ liệu, giúp dễ dàng kiểm thử.

---

## 🛠️ Công Nghệ Sử Dụng

| Thành Phần | Công Nghệ / Thư Viện | Mô Tả |
| :--- | :--- | :--- |
| **Ngôn ngữ** | Java 17 | Đảm bảo hiệu năng mạnh mẽ và tính năng hiện đại. |
| **Framework chính** | Spring Boot 4.0.6 | Cung cấp nền tảng ứng dụng nhanh chóng, tối giản cấu hình. |
| **Tương tác Database** | Spring JDBC (Connection, PreparedStatement) | Viết các câu lệnh SQL thuần giúp kiểm soát tối đa hiệu năng và bảo mật SQL Injection. |
| **Cơ sở dữ liệu** | MySQL Server 8.0+ | Lưu trữ dữ liệu an toàn và đáng tin cậy. |
| **Giao diện (Frontend)** | Thymeleaf + HTML5 + Bootstrap 5 | Render giao diện động từ server-side, responsive mượt mà trên mọi thiết bị. |
| **Bảo mật** | Spring Security 6 | Mã hóa mật khẩu bằng BCrypt, bảo vệ các endpoint hệ thống. |
| **Quản lý build** | Maven | Quản lý toàn bộ vòng đời ứng dụng và các thư viện phụ thuộc. |

---

## 📂 Cấu Trúc Thư Mục Dự Án

Thư mục dự án được tổ chức theo chuẩn kiến trúc MVC (Model-View-Controller) kết hợp mô hình Repository-Service:

```text
quanlybenhvien/
├── src/main/java/com/hospital/quanlybenhvien/
│   ├── config/
│   │   ├── SecurityConfig.java         — Cấu hình xác thực Spring Security & phân quyền truy cập
│   │   └── DataSeeder.java             — Tự động tạo dữ liệu tài khoản mẫu khi khởi động lần đầu
│   ├── controller/
│   │   ├── DashboardController.java    — Trang chủ, xử lý thống kê số liệu tổng quan hệ thống
│   │   ├── UserController.java         — Điều hướng và CRUD tài khoản hệ thống (Chỉ dành cho Admin)
│   │   ├── PatientController.java      — Điều hướng và CRUD bệnh nhân
│   │   ├── DoctorController.java       — Điều hướng và CRUD bác sĩ
│   │   └── AppointmentController.java  — Điều hướng và quản lý trạng thái lịch hẹn khám
│   ├── service/
│   │   ├── UserService.java            — Xử lý logic nghiệp vụ và mã hóa tài khoản người dùng
│   │   ├── PatientService.java         — Xử lý logic nghiệp vụ bệnh nhân
│   │   ├── DoctorService.java          — Xử lý logic nghiệp vụ bác sĩ
│   │   └── AppointmentService.java     — Xử lý logic nghiệp vụ đặt lịch khám bệnh
│   ├── repository/
│   │   ├── UserRepository.java         — Thao tác SQL (JDBC) với bảng `users`
│   │   ├── PatientRepository.java      — Thao tác SQL (JDBC) với bảng `patients`
│   │   ├── DoctorRepository.java       — Thao tác SQL (JDBC) với bảng `doctors`
│   │   └── AppointmentRepository.java  — Thao tác SQL (JDBC) kết nối (JOIN) bảng `appointments`
│   └── model/
│       ├── User.java                   — Model chứa thông tin tài khoản người dùng
│       ├── Patient.java                — Model chứa thông tin bệnh nhân
│       ├── Doctor.java                 — Model chứa thông tin bác sĩ
│       └── Appointment.java            — Model chứa thông tin lịch khám bệnh
│
├── src/main/resources/
│   ├── templates/
│   │   ├── login.html                  — Giao diện đăng nhập hệ thống đẹp mắt
│   │   ├── index.html                  — Giao diện Dashboard (Trang chủ chính)
│   │   ├── users/
│   │   │   ├── list.html               — Danh sách quản lý tài khoản người dùng
│   │   │   └── form.html               — Form thêm / cập nhật tài khoản
│   │   ├── patients/
│   │   │   ├── list.html               — Danh sách quản lý hồ sơ bệnh nhân
│   │   │   └── form.html               — Form thêm / cập nhật hồ sơ bệnh nhân
│   │   ├── doctors/
│   │   │   ├── list.html               — Danh sách quản lý bác sĩ
│   │   │   └── form.html               — Form thêm / cập nhật thông tin bác sĩ
│   │   └── appointments/
│   │       ├── list.html               — Danh sách quản lý lịch hẹn khám bệnh
│   │       └── form.html               — Form lên lịch khám bệnh mới
│   └── application.properties          — Tệp cấu hình kết nối database, cổng chạy ứng dụng
│
└── pom.xml                             — Tệp quản lý thư viện Maven của dự án
```

---

## ⚙️ Hướng Dẫn Cài Đặt Chi Tiết

Vui lòng làm theo các bước tuần tự dưới đây để cài đặt và khởi chạy dự án trên máy cá nhân:

### Bước 1 — Chuẩn bị môi trường cài đặt
Đảm bảo máy tính của bạn đã được cài đặt các công cụ sau:
* **Java JDK 17** trở lên (Khuyến nghị dùng Eclipse Temurin).
* **MySQL Server 8.0+** & **MySQL Workbench**.
* **IntelliJ IDEA** (Bản Community hoặc Ultimate đều tốt) hoặc **Eclipse**.
* **Git** (để clone và làm việc nhóm).

### Bước 2 — Clone dự án về máy
Mở Terminal / Git Bash và thực thi câu lệnh sau để tải mã nguồn:
```bash
git clone https://github.com/duy1sme/Hospital_Management_Java.git
```
Sau khi tải xong, hãy mở IntelliJ IDEA, nhấn **Open** và dẫn tới thư mục dự án vừa tải về. Đợi từ 2 đến 3 phút để hệ thống tải toàn bộ thư viện từ Maven repository.

### Bước 3 — Tạo Cơ Sở Dữ Liệu MySQL
Khởi động **MySQL Workbench**, đăng nhập bằng tài khoản admin của bạn và chạy đoạn script SQL dưới đây để tạo cơ sở dữ liệu và 4 bảng liên quan:

```sql
-- Tạo Database hỗ trợ Unicode tiếng Việt đầy đủ
CREATE DATABASE IF NOT EXISTS quanlybenhvien
    CHARACTER SET utf8mb4
    COLLATE utf8mb4_unicode_ci;

USE quanlybenhvien;

-- 1. Bảng lưu trữ thông tin Tài khoản người dùng (users)
CREATE TABLE IF NOT EXISTS users (
    id        INT AUTO_INCREMENT PRIMARY KEY,
    username  VARCHAR(50)  NOT NULL UNIQUE,
    password  VARCHAR(255) NOT NULL,
    full_name VARCHAR(100) NOT NULL,
    role      VARCHAR(30)  NOT NULL,
    enabled   TINYINT(1)   NOT NULL DEFAULT 1
);

-- 2. Bảng lưu trữ thông tin Bác sĩ (doctors)
CREATE TABLE IF NOT EXISTS doctors (
    id          INT AUTO_INCREMENT PRIMARY KEY,
    full_name   VARCHAR(100) NOT NULL,
    specialty   VARCHAR(100),
    phone       VARCHAR(15),
    email       VARCHAR(100),
    active      TINYINT(1) DEFAULT 1
);

-- 3. Bảng lưu trữ thông tin Bệnh nhân (patients)
CREATE TABLE IF NOT EXISTS patients (
    id               INT AUTO_INCREMENT PRIMARY KEY,
    full_name        VARCHAR(100) NOT NULL,
    date_of_birth    DATE,
    gender           VARCHAR(10),
    phone            VARCHAR(15),
    address          VARCHAR(255),
    diagnosis        VARCHAR(255),
    created_at       DATETIME DEFAULT CURRENT_TIMESTAMP
);

-- 4. Bảng lưu trữ thông tin Lịch Hẹn khám bệnh (appointments)
CREATE TABLE IF NOT EXISTS appointments (
    id                INT AUTO_INCREMENT PRIMARY KEY,
    patient_id        INT NOT NULL,
    doctor_id         INT NOT NULL,
    appointment_date  DATE NOT NULL,
    appointment_time  VARCHAR(10) NOT NULL,
    status            VARCHAR(50) NOT NULL DEFAULT 'Chờ xác nhận',
    note              TEXT,
    created_at        DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (patient_id) REFERENCES patients(id) ON DELETE CASCADE,
    FOREIGN KEY (doctor_id) REFERENCES doctors(id) ON DELETE CASCADE
);
```

### Bước 4 — Cấu hình mật khẩu kết nối Database
Mở tệp tin `src/main/resources/application.properties` trong IDE của bạn:
```properties
spring.datasource.username=root
spring.datasource.password=duy31082006
```
> ⚠️ **Lưu ý**: Hãy thay đổi giá trị của `spring.datasource.password` khớp với mật khẩu MySQL Server cục bộ trên máy của bạn.

### Bước 5 — Khởi chạy ứng dụng
1. Tìm tệp tin chính: `src/main/java/com/hospital/quanlybenhvien/QuanlybenhvienApplication.java`.
2. Chuột phải vào tệp tin và chọn **Run 'QuanlybenhvienApplication.main()'** (hoặc nhấn tổ hợp phím `Shift + F10`).
3. Chờ tới khi Console xuất hiện thông báo:
   ```text
   Started QuanlybenhvienApplication in X.XXX seconds
   >>> Đã tạo tài khoản mặc định. Mật khẩu: 123456
   ```

### Bước 6 — Truy cập hệ thống
Mở trình duyệt web ưa thích của bạn (Chrome, Edge, Firefox) và truy cập địa chỉ:
```text
http://localhost:8080
```
Sử dụng các tài khoản kiểm thử mặc định được tự động gieo sẵn trong hệ thống:

| Tên Đăng Nhập | Mật Khẩu | Quyền Truy Cập (Role) | Chức Năng |
| :--- | :--- | :--- | :--- |
| `admin` | `123456` | **Quản Trị Viên (Admin)** | Xem Dashboard, Quản lý bệnh nhân, bác sĩ, lịch hẹn và quản lý tài khoản hệ thống |
| `bacsi1` | `123456` | **Bác Sĩ (Doctor)** | Xem Dashboard, Quản lý bệnh nhân, bác sĩ, đặt lịch khám (không truy cập được Quản lý tài khoản) |

---

## 🪵 Xử Lý Các Sự Cố Thường Gặp (Troubleshooting)

* **Lỗi `Access denied for user 'root'@'localhost'`**:
  * *Nguyên nhân*: Mật khẩu MySQL nhập vào cấu hình trong `application.properties` bị sai.
  * *Cách sửa*: Sửa lại dòng `spring.datasource.password` cho đúng với mật khẩu máy tính của bạn và khởi động lại.
* **Lỗi `Unknown database 'quanlybenhvien'`**:
  * *Nguyên nhân*: Bạn chưa tạo cơ sở dữ liệu MySQL.
  * *Cách sửa*: Hãy thực hiện đầy đủ câu lệnh tạo database ở **Bước 3** trong MySQL Workbench.
* **Lỗi `Web server failed to start. Port 8080 was already in use.`**:
  * *Nguyên nhân*: Cổng 8080 đang bị ứng dụng khác chiếm dụng (ví dụ Tomcat khác, Docker hoặc các dịch vụ hệ thống).
  * *Cách sửa*: Mở tệp `application.properties` và thay đổi cổng bằng cách thêm cấu hình: `server.port=8081` (hoặc bất kỳ cổng trống nào khác).
* **Lỗi Maven không tải được thư viện**:
  * *Nguyên nhân*: Lỗi mạng hoặc IDE chưa nhận diện đúng Maven.
  * *Cách sửa*: Click chuột phải vào dự án hoặc tệp `pom.xml` -> Chọn **Maven** -> Chọn **Reload Project**.