# Chat Nhóm qua TCP - Kiểm Tra Thường Kì

## Mô tả
Chương trình chat nhóm qua giao thức TCP. Client kết nối đến Server, chọn phòng chat và gửi/nhận tin nhắn với các client khác trong cùng phòng.

## Cấu trúc file

| File | Mô tả |
|------|-------|
| `Server.java` | Server chính, khởi tạo 3 phòng chat, lắng nghe kết nối |
| `ChatRoom.java` | Lớp phòng chat, quản lý client và broadcast tin nhắn |
| `ClientHandler.java` | Xử lý từng client trong thread riêng |
| `Client.java` | Client kết nối server, gửi/nhận tin nhắn |

## Hướng dẫn chạy

### Bước 1: Compile tất cả file
```bash
cd kiem_tra_thuong_ki_chat_tcp
javac -encoding UTF-8 *.java
```

### Bước 2: Chạy Server (terminal 1)
```bash
java Server
```

### Bước 3: Chạy Client (mở thêm terminal khác)
```bash
java Client
```
> Mở nhiều terminal để tạo nhiều client chat cùng lúc.

## Luồng hoạt động

1. Client nhập **tên** khi kết nối
2. Server hiển thị **danh sách 3 phòng**: HocTap, GiaiTri, TanGau
3. Client chọn **số thứ tự phòng** để vào
4. Gõ tin nhắn → gửi đến tất cả client **cùng phòng**
5. `/quit` → rời phòng, quay lại danh sách phòng
6. `/exit` → ngắt kết nối hoàn toàn

## Giải thích code

### Server.java
- Tạo `ServerSocket` trên port `12345`
- Khởi tạo 3 phòng chat bằng `LinkedHashMap` (giữ thứ tự)
- Mỗi client kết nối → tạo `ClientHandler` chạy trong thread mới

### ChatRoom.java
- Dùng `CopyOnWriteArrayList` để lưu danh sách client (thread-safe)
- `addClient()`: thêm client + thông báo cho phòng
- `removeClient()`: xóa client + thông báo cho phòng
- `broadcast()`: gửi tin nhắn đến tất cả client trong phòng (trừ người gửi)

### ClientHandler.java
- Mỗi client chạy trong 1 thread riêng (`implements Runnable`)
- Vòng lặp ngoài: hiển thị danh sách phòng, chờ client chọn
- Vòng lặp trong: nhận tin nhắn, broadcast trong phòng
- Xử lý `/quit` (rời phòng) và `/exit` (ngắt kết nối)

### Client.java
- Kết nối đến server qua `Socket`
- **Thread nhận tin**: chạy riêng, liên tục đọc tin nhắn từ server và in ra console
- **Thread chính**: đọc input từ bàn phím và gửi lên server
