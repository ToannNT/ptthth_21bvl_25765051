import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class BankServer {
    public static void main(String[] args) {
        try {
            // Initial balance = 1,000,000
            BankImpl obj = new BankImpl(1000000.0);
            Registry registry = LocateRegistry.createRegistry(1099);
            registry.rebind("BankService", obj);
            System.out.println("=== Bai 6: Bank Server is running on port 1099 ===");
            System.out.println("Initial balance: 1,000,000.00");
        } catch (Exception e) {
            System.err.println("Server error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
