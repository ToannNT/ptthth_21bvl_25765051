# JDBC SQLite - Thực Hành

## Mô tả
Project thực hành kết nối Java với SQLite thông qua JDBC.

## Cấu trúc dự án
```
jdbc_sqlite/
├── src/
│   └── App.java        # File source chính
├── bin/                # Compiled .class files
├── lib/
│   └── sqlite-jdbc-3.51.3.0.jar   # JDBC driver
├── CSDL/               # Thư mục chứa database
├── sinhvien.db         # SQLite database
└── README.md
```

## Chức năng
- Kết nối SQLite qua JDBC
- Tạo bảng `sinhvien` (mssv, hoten, nganh)
- Thêm, cập nhật, truy vấn dữ liệu sinh viên

## Cách chạy
1. Đặt `sqlite-jdbc-3.51.3.0.jar` vào thư mục `lib/`
2. Compile:
   ```
   javac -cp lib/sqlite-jdbc-3.51.3.0.jar -d bin src/App.java
   ```
3. Run:
   ```
   java -cp bin;lib/sqlite-jdbc-3.51.3.0.jar App
   ```

## Sinh viên
- MSSV: 25765051
- Lớp: 21BVL

