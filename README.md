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

### 📂 [buoi6_tcp_services](./buoi6_tcp_services/)
> **Chủ đề:** TCP Services — Các dịch vụ mạng TCP (Daytime, Echo, HTTP đơn giản)

---

### 📂 [buoi7_jdbc](./buoi7_jdbc/)
> **Chủ đề:** JDBC — Kết nối cơ sở dữ liệu Java với SQLite

---

### 📂 [buoi8_rmi](./buoi8_rmi/)
> **Chủ đề:** Java RMI (Remote Method Invocation) — Gọi phương thức từ xa qua mạng

#### 📖 Tổng quan RMI

Java RMI cho phép một chương trình Java gọi phương thức của đối tượng nằm trên máy tính khác thông qua mạng. Mỗi bài gồm 4 thành phần: **Interface**, **Impl** (triển khai), **Server** (đăng ký RMI registry), **Client** (gọi từ xa).

**Cách chạy:** Mở 2 terminal tại `buoi8_rmi\`
```
run.bat <số_bài> server    ← Terminal 1
run.bat <số_bài> client    ← Terminal 2
```

#### 🗂️ Bài 1 – Hello World (`bai1_hello/`)

| File | Mô tả |
|------|-------|
| [`HelloInterface.java`](./buoi8_rmi/src/bai1_hello/HelloInterface.java) | Remote interface khai báo `sayHello()` |
| [`HelloImpl.java`](./buoi8_rmi/src/bai1_hello/HelloImpl.java) | Triển khai trả về `"Hello, World!"` |
| [`HelloServer.java`](./buoi8_rmi/src/bai1_hello/HelloServer.java) | Đăng ký object lên RMI registry port 1099 |
| [`HelloClient.java`](./buoi8_rmi/src/bai1_hello/HelloClient.java) | Lookup và gọi `sayHello()` từ xa |

#### 🗂️ Bài 2 – Tính Tổng Hai Số (`bai2_sum/`)

| File | Mô tả |
|------|-------|
| [`SumInterface.java`](./buoi8_rmi/src/bai2_sum/SumInterface.java) | Remote interface khai báo `sum(int a, int b)` |
| [`SumImpl.java`](./buoi8_rmi/src/bai2_sum/SumImpl.java) | Triển khai trả về `a + b` |
| [`SumServer.java`](./buoi8_rmi/src/bai2_sum/SumServer.java) | Đăng ký SumService lên registry |
| [`SumClient.java`](./buoi8_rmi/src/bai2_sum/SumClient.java) | Nhập 2 số nguyên, gọi `sum()` từ xa và in kết quả |

#### 🗂️ Bài 3 – Kiểm Tra Số Nguyên Tố (`bai3_prime/`)

| File | Mô tả |
|------|-------|
| [`PrimeInterface.java`](./buoi8_rmi/src/bai3_prime/PrimeInterface.java) | Remote interface khai báo `isPrime(int n)` |
| [`PrimeImpl.java`](./buoi8_rmi/src/bai3_prime/PrimeImpl.java) | Triển khai kiểm tra số nguyên tố, trả về `true/false` |
| [`PrimeServer.java`](./buoi8_rmi/src/bai3_prime/PrimeServer.java) | Đăng ký PrimeService lên registry |
| [`PrimeClient.java`](./buoi8_rmi/src/bai3_prime/PrimeClient.java) | Nhập số `n`, gọi `isPrime()` và in kết quả |

#### 🗂️ Bài 4 – Quản Lý Danh Bạ (`bai4_contacts/`)

| File | Mô tả |
|------|-------|
| [`ContactInterface.java`](./buoi8_rmi/src/bai4_contacts/ContactInterface.java) | Remote interface: `addContact()`, `findContact()`, `deleteContact()` |
| [`ContactImpl.java`](./buoi8_rmi/src/bai4_contacts/ContactImpl.java) | Triển khai quản lý danh bạ bằng `HashMap` trên server |
| [`ContactServer.java`](./buoi8_rmi/src/bai4_contacts/ContactServer.java) | Đăng ký ContactService lên registry |
| [`ContactClient.java`](./buoi8_rmi/src/bai4_contacts/ContactClient.java) | Menu tương tác: Add / Find / Delete liên hệ từ xa |

#### 🗂️ Bài 5 – Chat Đơn Giản (`bai5_chat/`)

| File | Mô tả |
|------|-------|
| [`ChatInterface.java`](./buoi8_rmi/src/bai5_chat/ChatInterface.java) | Remote interface khai báo `sendMessage(String msg)` |
| [`ChatImpl.java`](./buoi8_rmi/src/bai5_chat/ChatImpl.java) | Triển khai tự động phản hồi tin nhắn từ client |
| [`ChatServer.java`](./buoi8_rmi/src/bai5_chat/ChatServer.java) | Đăng ký ChatService lên registry |
| [`ChatClient.java`](./buoi8_rmi/src/bai5_chat/ChatClient.java) | Gửi tin nhắn và hiển thị reply từ server |

#### 🗂️ Bài 6 – Quản Lý Tài Khoản Ngân Hàng (`bai6_bank/`)

| File | Mô tả |
|------|-------|
| [`BankInterface.java`](./buoi8_rmi/src/bai6_bank/BankInterface.java) | Remote interface: `getBalance()`, `deposit()`, `withdraw()` |
| [`BankImpl.java`](./buoi8_rmi/src/bai6_bank/BankImpl.java) | Triển khai quản lý số dư (mặc định 1,000,000 VNĐ) |
| [`BankServer.java`](./buoi8_rmi/src/bai6_bank/BankServer.java) | Đăng ký BankService lên registry |
| [`BankClient.java`](./buoi8_rmi/src/bai6_bank/BankClient.java) | Menu: Check Balance / Deposit / Withdraw |

#### ✅ Kết quả buổi 8

| Bài | Chức năng | Trạng thái |
|-----|-----------|------------|
| 1 | Hello World | ✅ Hoàn thành |
| 2 | Tính tổng 2 số | ✅ Hoàn thành |
| 3 | Kiểm tra số nguyên tố | ✅ Hoàn thành |
| 4 | Quản lý danh bạ | ✅ Hoàn thành |
| 5 | Chat đơn giản | ✅ Hoàn thành |
| 6 | Tài khoản ngân hàng | ✅ Hoàn thành |

---

*Cập nhật lần cuối: 21/10/2026*