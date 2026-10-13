import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Lớp đại diện cho một phòng chat.
 * Quản lý danh sách client trong phòng và gửi tin nhắn broadcast.
 */
public class ChatRoom {
    private String name;
    private List<ClientHandler> clients;

    public ChatRoom(String name) {
        this.name = name;
        this.clients = new CopyOnWriteArrayList<>();
    }

    public String getName() {
        return name;
    }

    // Thêm client vào phòng
    public void addClient(ClientHandler client) {
        clients.add(client);
        broadcast("[SERVER] " + client.getUsername() + " đã vào phòng " + name, client);
    }

    // Xóa client khỏi phòng
    public void removeClient(ClientHandler client) {
        clients.remove(client);
        broadcast("[SERVER] " + client.getUsername() + " đã rời phòng " + name, client);
    }

    // Gửi tin nhắn đến tất cả client trong phòng, trừ người gửi
    public void broadcast(String message, ClientHandler sender) {
        for (ClientHandler client : clients) {
            if (client != sender) {
                client.sendMessage(message);
            }
        }
    }

    public int getClientCount() {
        return clients.size();
    }
}
