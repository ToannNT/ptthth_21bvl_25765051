import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class ChatServer {
    public static void main(String[] args) {
        try {
            ChatImpl obj = new ChatImpl();
            Registry registry = LocateRegistry.createRegistry(1099);
            registry.rebind("ChatService", obj);
            System.out.println("=== Bai 5: Chat Server is running on port 1099 ===");
            System.out.println("Waiting for messages...");
        } catch (Exception e) {
            System.err.println("Server error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
