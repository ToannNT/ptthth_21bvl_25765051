import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class PrimeServer {
    public static void main(String[] args) {
        try {
            PrimeImpl obj = new PrimeImpl();
            Registry registry = LocateRegistry.createRegistry(1099);
            registry.rebind("PrimeService", obj);
            System.out.println("=== Bai 3: Prime Server is running on port 1099 ===");
        } catch (Exception e) {
            System.err.println("Server error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
