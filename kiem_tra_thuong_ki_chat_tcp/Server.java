import java.io.*;
import java.net.*;
import java.util.*;

/**
 * Server chat nhóm qua TCP.
 * Quản lý các phòng chat và kết nối từ client.
 */
public class Server {
    private static final int PORT = 12345;
    private static Map<String, ChatRoom> rooms = new LinkedHashMap<>();

    public static void main(String[] args) {
        // Khởi tạo các phòng chat cố định
        rooms.put("HocTap", new ChatRoom("HocTap"));
        rooms.put("GiaiTri", new ChatRoom("GiaiTri"));
        rooms.put("TanGau", new ChatRoom("TanGau"));

        System.out.println("=== SERVER CHAT NHÓM TCP ===");
        System.out.println("Server đang chạy trên port " + PORT);
        System.out.println("Các phòng: HocTap, GiaiTri, TanGau");
        System.out.println("Đang chờ kết nối...\n");

        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Client mới kết nối: " + clientSocket.getInetAddress());

                ClientHandler handler = new ClientHandler(clientSocket, rooms);
                Thread thread = new Thread(handler);
                thread.start();
            }
        } catch (IOException e) {
            System.out.println("Lỗi server: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
