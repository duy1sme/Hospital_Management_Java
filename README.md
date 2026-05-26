# 🏥 Quản Lý Bệnh Viện — Hướng Dẫn Cài Đặt

## Yêu cầu cài đặt trước

| Công cụ | Phiên bản | Link tải |
|---|---|---|
| Java JDK | 17 trở lên | https://adoptium.net |
| IntelliJ IDEA | Mới nhất (Community là đủ) | https://www.jetbrains.com/idea |
| MySQL Server | 8.0 trở lên | https://dev.mysql.com/downloads |
| MySQL Workbench | Mới nhất | https://dev.mysql.com/downloads/workbench |
| Git | Mới nhất | https://git-scm.com |

---

## Bước 1 — Clone project về máy

Mở terminal (hoặc Git Bash), chạy lệnh:

```bash
git clone https://github.com/TEN_NHOM/quanlybenhvien.git
```

Sau đó mở IntelliJ IDEA → **Open** → chọn thư mục vừa clone về.

> ⏳ Chờ IntelliJ tải dependencies Maven (lần đầu mất 2-5 phút, cần có mạng).

---

## Bước 2 — Tạo database trong MySQL

Mở **MySQL Workbench**, kết nối vào MySQL server của máy mình, rồi chạy đoạn SQL sau:

```sql
CREATE DATABASE IF NOT EXISTS quanlybenhvien
    CHARACTER SET utf8mb4
    COLLATE utf8mb4_unicode_ci;

USE quanlybenhvien;

CREATE TABLE IF NOT EXISTS users (
    id        INT AUTO_INCREMENT PRIMARY KEY,
    username  VARCHAR(50)  NOT NULL UNIQUE,
    password  VARCHAR(255) NOT NULL,
    full_name VARCHAR(100) NOT NULL,
    role      VARCHAR(30)  NOT NULL,
    enabled   TINYINT(1)   NOT NULL DEFAULT 1
);

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

CREATE TABLE IF NOT EXISTS doctors (
    id          INT AUTO_INCREMENT PRIMARY KEY,
    full_name   VARCHAR(100) NOT NULL,
    specialty   VARCHAR(100),
    phone       VARCHAR(15),
    email       VARCHAR(100),
    active      TINYINT(1) DEFAULT 1
);
```

> ✅ Chạy xong sẽ thấy 3 bảng: **users**, **patients**, **doctors** xuất hiện trong Workbench.

---

## Bước 3 — Sửa mật khẩu database

Mở file:

```
src/main/resources/application.properties
```

Tìm dòng này và **đổi thành mật khẩu MySQL của máy bạn**:

```properties
spring.datasource.password=123456
```

Ví dụ nếu máy bạn không đặt mật khẩu MySQL thì để trống:

```properties
spring.datasource.password=
```

> ⚠️ Không commit file này lên GitHub sau khi sửa mật khẩu.
> Thêm dòng sau vào file `.gitignore` nếu chưa có:
> ```
> src/main/resources/application.properties
> ```

---

## Bước 4 — Chạy ứng dụng

Trong IntelliJ, mở file:

```
src/main/java/com/hospital/quanlybenhvien/QuanlybenhvienApplication.java
```

Bấm nút **▶ Run** (hoặc Shift+F10).

Chờ đến khi console hiện dòng:

```
Started QuanlybenhvienApplication in X.XXX seconds
```

Và dòng:

```
>>> Đã tạo tài khoản mặc định. Mật khẩu: 123456
```

> Dòng thứ hai chỉ hiện lần đầu chạy (khi bảng users còn trống).

---

## Bước 5 — Mở trình duyệt

Vào địa chỉ:

```
http://localhost:8080
```

Sẽ tự chuyển sang trang đăng nhập. Dùng tài khoản mặc định:

| Tài khoản | Mật khẩu | Quyền |
|---|---|---|
| `admin` | `123456` | Quản trị — vào được tất cả |
| `bacsi1` | `123456` | Bác sĩ — không vào được trang Admin |

---

## Cấu trúc thư mục project

```
quanlybenhvien/
├── src/main/java/com/hospital/quanlybenhvien/
│   ├── config/
│   │   ├── SecurityConfig.java      — cấu hình đăng nhập, phân quyền
│   │   └── DataSeeder.java          — tự tạo tài khoản mặc định khi khởi động
│   ├── controller/
│   │   ├── HomeController.java      — trang chủ, trang login
│   │   ├── PatientController.java   — CRUD bệnh nhân
│   │   └── DoctorController.java    — CRUD bác sĩ
│   ├── service/
│   │   ├── PatientService.java      — xử lý logic bệnh nhân
│   │   └── DoctorService.java       — xử lý logic bác sĩ
│   ├── repository/
│   │   ├── UserRepository.java      — truy vấn SQL bảng users
│   │   ├── PatientRepository.java   — truy vấn SQL bảng patients
│   │   └── DoctorRepository.java    — truy vấn SQL bảng doctors
│   └── model/
│       ├── User.java                — class chứa thông tin tài khoản
│       ├── Patient.java             — class chứa thông tin bệnh nhân
│       └── Doctor.java              — class chứa thông tin bác sĩ
│
├── src/main/resources/
│   ├── templates/
│   │   ├── login.html               — trang đăng nhập
│   │   ├── index.html               — trang chủ
│   │   ├── patients/
│   │   │   ├── list.html            — danh sách bệnh nhân
│   │   │   └── form.html            — form thêm/sửa bệnh nhân
│   │   └── doctors/
│   │       ├── list.html            — danh sách bác sĩ
│   │       └── form.html            — form thêm/sửa bác sĩ
│   └── application.properties       — cấu hình database, server
│
└── pom.xml                          — danh sách thư viện dùng trong project
```

---

## Quy tắc làm việc nhóm với Git

### Lần đầu nhận việc

```bash
# Vào nhánh dev mới nhất
git checkout dev
git pull origin dev

# Tạo nhánh riêng cho mình
git checkout -b feature/ten-chuc-nang
# Ví dụ: feature/patient-crud, feature/doctor-crud
```

### Làm xong 1 chức năng thì push lên

```bash
git add .
git commit -m "feat: mô tả ngắn chức năng vừa làm"
git push origin feature/ten-chuc-nang
```

### Tạo Pull Request trên GitHub

Vào GitHub → **Pull requests** → **New pull request**
- Base: `dev`
- Compare: `feature/ten-chuc-nang`

Báo trưởng nhóm review và merge.

### Đồng bộ khi người khác merge xong

```bash
git checkout dev
git pull origin dev
git checkout feature/ten-chuc-nang
git merge dev
```

---

## Xử lý lỗi thường gặp

**Lỗi: `Access denied for user 'root'@'localhost'`**
→ Sai mật khẩu MySQL. Kiểm tra lại `spring.datasource.password` trong `application.properties`.

**Lỗi: `Unknown database 'quanlybenhvien'`**
→ Chưa tạo database. Chạy lại đoạn SQL ở Bước 2.

**Lỗi: `Port 8080 was already in use`**
→ Đang có ứng dụng khác chạy cổng 8080. Tắt đi hoặc đổi cổng trong `application.properties`:
```properties
server.port=8081
```

**Lỗi: Maven không tải được thư viện**
→ Kiểm tra mạng. Trong IntelliJ: chuột phải vào `pom.xml` → **Maven** → **Reload project**.

**Đăng nhập xong vẫn ở trang login**
→ Mở tab ẩn danh (Ctrl+Shift+N) thử lại. Nếu được thì browser đang cache session cũ.

---

## Thông tin nhóm

| Thành viên | Mã SV | Phụ trách |
|---|---|---|
| Nguyễn Văn A | SV001 | Trưởng nhóm, Security, CRUD User |
| Nguyễn Văn B | SV002 | CRUD Bệnh nhân |
| Nguyễn Văn C | SV003 | CRUD Bác sĩ |
| Nguyễn Văn D | SV004 | Giao diện, Báo cáo |

---

*Có vấn đề gì nhắn nhóm chat, không tự fix quá 30 phút.*
