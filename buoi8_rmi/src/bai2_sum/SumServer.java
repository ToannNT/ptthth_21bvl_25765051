import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class SumServer {
    public static void main(String[] args) {
        try {
            SumImpl obj = new SumImpl();
            Registry registry = LocateRegistry.createRegistry(1099);
            registry.rebind("SumService", obj);
            System.out.println("=== Bai 2: Sum Server is running on port 1099 ===");
        } catch (Exception e) {
            System.err.println("Server error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
