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

### 📂 [buoi5_socket_oi](./buoi5_socket_oi/)
> **Chủ đề:** Socket nâng cao — TCP Multi-thread & UDP Echo (`DatagramSocket`, `DatagramPacket`, `Thread`)

#### 🔌 TCP — Multi-threaded Echo

| File | Mô tả |
|------|-------|
| [`pTcpEchoServer.java`](./buoi5_socket_oi/pTcpEchoServer.java) | TCP Server lắng nghe port **6789**, tạo `t_Processing` thread mỗi khi có client kết nối |
| [`sTcpEchoServer.java`](./buoi5_socket_oi/sTcpEchoServer.java) | Tương tự `pTcpEchoServer` — phiên bản thứ hai của TCP multi-thread server |
| [`t_Processing.java`](./buoi5_socket_oi/t_Processing.java) | Thread xử lý mỗi client — dùng `DataInputStream`/`DataOutputStream`, gửi ký tự `0`→`9`, echo lại phản hồi |
| [`tcpEchoClient.java`](./buoi5_socket_oi/tcpEchoClient.java) | TCP Client kết nối server (IP + port từ args), đọc tên server, gửi `0`→`9` mỗi 2 giây |

#### 📡 UDP — UDP Echo (phần trọng tâm)

| File | Mô tả |
|------|-------|
| [`udpEchoServer.java`](./buoi5_socket_oi/udpEchoServer.java) | UDP Server dùng `DatagramSocket(6789)`, nhận 10 gói tin, in dữ liệu và echo ngược lại client bằng `DatagramPacket` |
| [`udpEchoClient.java`](./buoi5_socket_oi/udpEchoClient.java) | UDP Client gửi `"data | 0"` → `"data | 9"` tới server mỗi 2 giây, nhận và in phản hồi |

---

*Cập nhật lần cuối: 23/09/2026*