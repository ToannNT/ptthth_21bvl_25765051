# Buoi 4 - Socket IO - Thuc Hanh

## Cau truc thu muc

`
thuchanh/
  src/
    network/
      HostInspector.java    <- Bai 4.1
    tcp/
      TcpCommandServer.java <- Bai 4.2 - Server
      TcpCommandClient.java <- Bai 4.2 - Client
  out/                      <- Class files sau bien dich
  README.md
`

## Bien dich

`
javac -d out src/network/HostInspector.java src/tcp/TcpCommandServer.java src/tcp/TcpCommandClient.java
`

---

## Bai 4.1 - Khao sat dia chi mang (HostInspector)

### Lenh chay

`
java -cp out network.HostInspector localhost
java -cp out network.HostInspector example.com
java -cp out network.HostInspector host-khong-ton-tai.invalid
java -cp out network.HostInspector
`

### Ket qua ghi lai

**localhost:**
`
Host: localhost
So dia chi tim duoc: 2
- IP        : 127.0.0.1  [IPv4]
  Canonical : kubernetes.docker.internal
  Loopback  : true   // giao tiep noi bo trong cung may
  Site-local: false

- IP        : 0:0:0:0:0:0:0:1  [IPv6]
  Canonical : 0:0:0:0:0:0:0:1
  Loopback  : true   // giao tiep noi bo trong cung may
  Site-local: false
`

**example.com:**
`
Host: example.com
So dia chi tim duoc: 2
- IP        : 104.20.23.154  [IPv4]
  Loopback  : false
  Site-local: false

- IP        : 172.66.147.243  [IPv4]
  Loopback  : false
  Site-local: false
`

**host-khong-ton-tai.invalid:**
`
Khong phan giai duoc host: host-khong-ton-tai.invalid
`

**Thieu args:**
`
Usage: java network.HostInspector <hostname>
`

### Giai thich Loopback vs Site-local

| Dac diem | Loopback | Site-local |
|---|---|---|
| Vi du | 127.0.0.1, ::1 | 192.168.x.x, 10.x.x.x, 172.16-31.x.x |
| Pham vi | Chi trong mot may | Mang noi bo (LAN/tochuc) |
| Ra Internet? | Khong | Khong |
| Muc dich | Test/debug tren chinh may do | Giao tiep noi bo to chuc |
| isLoopbackAddress() | true | false |
| isSiteLocalAddress() | false | true |

**Loopback**: Dia chi dac biet (127.0.0.1 voi IPv4, ::1 voi IPv6). Goi tin
khong roi khoi card mang vat ly, vong lai ngay tren may. Dung de kiem thu
ung dung tren may localhost.

**Site-local**: Dia chi mang rieng tu (private), khong duoc dinh tuyen tren
Internet cong cong. Dung de giao tiep trong mang noi bo cong ty/truong hoc.

### Bo sung: Nhan dien IPv4 va IPv6

Su dung instanceof de kiem tra kieu:
- Inet4Address -> IPv4 (4 byte, ví du 192.168.1.1)
- Inet6Address -> IPv6 (16 byte, vi du 2001:db8::1)

Luu y: Phan loai lop A/B/C la kien thuc lich su (classful networking, truoc 1993).
Hien nay mang dung CIDR (Classless Inter-Domain Routing) nen khong can phan loai nay.

---

## Bai 4.2 - TCP Client-Server theo giao thuc dong

### Cac lenh ho tro

| Lenh | Phan hoi |
|---|---|
| PING | OK PONG |
| TIME | OK <thoi-gian-hien-tai> |
| UPPER text | OK TEXT |
| QUIT | OK BYE (dong ket noi) |
| bat-ky | ERR UNKNOWN_COMMAND |

### Trinh tu chay

**Terminal 1 (Server):**
`
java -cp out tcp.TcpCommandServer
`

**Terminal 2 (Client):**
`
java -cp out tcp.TcpCommandClient localhost 5000
`

**Trinh tu gui lenh va ket qua mong doi:**
`
PING          -> Server: OK PONG
UPPER xin chao -> Server: OK XIN CHAO
TIME          -> Server: OK 2026-09-16T19:30:00.123456
ABC           -> Server: ERR UNKNOWN_COMMAND
QUIT          -> Server: OK BYE  (client dong, server tiep tuc lang nghe)
`

### Han che cua server tuan tu

Server hien tai phuc vu **mot client tai mot thoi diem**. Neu chay hai client
dong thoi:
- Client 1 ket noi va gui lenh binh thuong.
- Client 2 phai **cho** trong hang doi accept() cua ServerSocket.
- Chi khi Client 1 gui QUIT hoac ngat ket noi, Client 2 moi duoc phuc vu.

**Giai phap**: Su dung Thread hoac ExecutorService de phuc vu nhieu client
dong thoi (se hoc o bai sau).