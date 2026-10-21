import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class ContactServer {
    public static void main(String[] args) {
        try {
            ContactImpl obj = new ContactImpl();
            Registry registry = LocateRegistry.createRegistry(1099);
            registry.rebind("ContactService", obj);
            System.out.println("=== Bai 4: Contact Server is running on port 1099 ===");
        } catch (Exception e) {
            System.err.println("Server error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
