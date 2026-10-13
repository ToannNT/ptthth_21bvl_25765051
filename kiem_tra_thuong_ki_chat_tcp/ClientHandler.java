import java.io.*;
import java.net.*;
import java.util.Map;

/**
 * Xử lý kết nối của mỗi client trên server.
 * Mỗi client được xử lý trong một thread riêng.
 */
public class ClientHandler implements Runnable {
    private Socket socket;
    private BufferedReader in;
    private PrintWriter out;
    private String username;
    private ChatRoom currentRoom;
    private Map<String, ChatRoom> rooms;

    public ClientHandler(Socket socket, Map<String, ChatRoom> rooms) throws IOException {
        this.socket = socket;
        this.rooms = rooms;
        this.in = new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF-8"));
        this.out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(), "UTF-8"), true);
    }

    public String getUsername() {
        return username;
    }

    public void sendMessage(String message) {
        out.println(message);
    }

    @Override
    public void run() {
        try {
            // Bước 1: Nhận tên người dùng
            out.println("Nhập tên của bạn:");
            username = in.readLine();
            if (username == null || username.trim().isEmpty()) {
                username = "Anonymous";
            }
            out.println("Chào mừng " + username + "!");

            // Bước 2: Vòng lặp chính - chọn phòng và chat
            boolean running = true;
            while (running) {
                // Hiển thị danh sách phòng
                showRoomList();
                out.println("Nhập số phòng để vào (hoặc /exit để thoát):");

                String choice = in.readLine();
                if (choice == null) break;
                choice = choice.trim();

                if (choice.equalsIgnoreCase("/exit")) {
                    out.println("Tạm biệt " + username + "!");
                    running = false;
                    break;
                }

                // Chọn phòng
                ChatRoom selectedRoom = selectRoom(choice);
                if (selectedRoom == null) {
                    out.println("Lựa chọn không hợp lệ! Vui lòng thử lại.");
                    continue;
                }

                // Vào phòng và bắt đầu chat
                currentRoom = selectedRoom;
                currentRoom.addClient(this);
                out.println("=== Bạn đã vào phòng: " + currentRoom.getName() + " ===");
                out.println("Gõ tin nhắn để chat. /quit để rời phòng, /exit để thoát.");

                // Vòng lặp chat trong phòng
                String message;
                while ((message = in.readLine()) != null) {
                    message = message.trim();
                    if (message.equalsIgnoreCase("/quit")) {
                        currentRoom.removeClient(this);
                        currentRoom = null;
                        out.println("=== Đã rời phòng. Quay lại danh sách phòng ===");
                        break;
                    } else if (message.equalsIgnoreCase("/exit")) {
                        if (currentRoom != null) {
                            currentRoom.removeClient(this);
                            currentRoom = null;
                        }
                        out.println("Tạm biệt " + username + "!");
                        running = false;
                        break;
                    } else if (!message.isEmpty()) {
                        currentRoom.broadcast("[" + username + "]: " + message, this);
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Client " + username + " mất kết nối.");
        } finally {
            // Dọn dẹp khi client ngắt kết nối
            if (currentRoom != null) {
                currentRoom.removeClient(this);
            }
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println("Client " + username + " đã ngắt kết nối.");
        }
    }

    // Hiển thị danh sách phòng kèm số người online
    private void showRoomList() {
        out.println("\n========= DANH SÁCH PHÒNG CHAT =========");
        int i = 1;
        for (Map.Entry<String, ChatRoom> entry : rooms.entrySet()) {
            ChatRoom room = entry.getValue();
            out.println(i + ". " + room.getName() + " (" + room.getClientCount() + " người)");
            i++;
        }
        out.println("==========================================");
    }

    // Chọn phòng theo số thứ tự
    private ChatRoom selectRoom(String choice) {
        try {
            int index = Integer.parseInt(choice);
            if (index < 1 || index > rooms.size()) return null;
            int i = 1;
            for (ChatRoom room : rooms.values()) {
                if (i == index) return room;
                i++;
            }
        } catch (NumberFormatException e) {
            // Thử tìm theo tên phòng
            for (ChatRoom room : rooms.values()) {
                if (room.getName().equalsIgnoreCase(choice)) {
                    return room;
                }
            }
        }
        return null;
    }
}
