# 📘 Phát Triển Hệ Thống Tích Hợp

## 📋 Thông Tin Môn Học

| Thông tin | Chi tiết |
|-----------|----------|
| **Tên môn** | Phát Triển Hệ Thống Tích Hợp |
| **Mã môn** | DHCNTT21BVL - 428801447201 |
| **Giảng viên** | Trần Thị Minh Khoa |

## 👤 Thông Tin Sinh Viên

| Thông tin | Chi tiết |
|-----------|----------|
| **STT** | 60 |
| **MSSV** | 25765051 |
| **Họ và tên** | Nguyễn Thanh Toàn |

---

## 📁 Danh Sách Buổi Học

### 📂 [buoi03_stream_inputstream_outputstream](./buoi03_stream_inputstream_outputstream/)
> **Chủ đề:** STREAM (Dòng chảy) — `java.io.InputStream` & `java.io.OutputStream`

| File | Mô tả |
|------|-------|
| [`InStream1.java`](./buoi03_stream_inputstream_outputstream/InStream1.java) | Ví dụ 1a — Đọc từng byte từ `System.in`, in ký tự tương ứng, thoát khi gặp `q` hoặc EOF |
| [`InStream2b.java`](./buoi03_stream_inputstream_outputstream/InStream2b.java) | Ví dụ 2 — Đọc non-blocking dùng `available()`, in dấu `.` khi chờ dữ liệu |
| [`ReadLineDemo.java`](./buoi03_stream_inputstream_outputstream/ReadLineDemo.java) | Ví dụ 3 — Đọc từng dòng văn bản dùng `BufferedReader` bọc `InputStreamReader` |

#### 🔬 Thực hành

| File | Bài | Mô tả |
|------|-----|-------|
| [`thuchanh/ConsoleReaderDemo.java`](./buoi03_stream_inputstream_outputstream/thuchanh/ConsoleReaderDemo.java) | TH1 | Đọc văn bản từ bàn phím, đếm số dòng, thoát khi nhập `q` |
| [`thuchanh/TextFileDemo.java`](./buoi03_stream_inputstream_outputstream/thuchanh/TextFileDemo.java) | TH2 | Ghi và đọc tệp văn bản UTF-8 với `BufferedWriter` / `BufferedReader` |
| [`thuchanh/BinaryFileCopy.java`](./buoi03_stream_inputstream_outputstream/thuchanh/BinaryFileCopy.java) | TH3 | Sao chép tệp nhị phân (ảnh) bằng byte stream có bộ đệm 8KB |
| [`thuchanh/Product.java`](./buoi03_stream_inputstream_outputstream/thuchanh/Product.java) | TH4 | Lớp `Product` — validate, tính giá trị tồn kho |
| [`thuchanh/ProductCsvApp.java`](./buoi03_stream_inputstream_outputstream/thuchanh/ProductCsvApp.java) | TH4 | Đọc `products.csv`, in danh sách, ghi báo cáo `report.txt` |

---

### 📂 [buoi4_socket_io](./buoi4_socket_io/)
> **Chủ đề:** Socket I/O — TCP Echo Server & Client (`java.net.ServerSocket`, `java.net.Socket`)

| File | Mô tả |
|------|-------|
| [`TCPEchoServer.java`](./buoi4_socket_io/TCPEchoServer.java) | Server lắng nghe port **6789**, nhận từng byte từ client và echo lại |
| [`TCPEchoClient.java`](./buoi4_socket_io/TCPEchoClient.java) | Client kết nối `127.0.0.1:6789`, gửi ký tự `0`→`9` mỗi 2 giây và in phản hồi |

---

*Cập nhật lần cuối: 15/09/2026*