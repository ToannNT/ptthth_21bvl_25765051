import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;

public class PrimeClient {
    public static void main(String[] args) {
        try {
            Registry registry = LocateRegistry.getRegistry("localhost", 1099);
            PrimeInterface stub = (PrimeInterface) registry.lookup("PrimeService");

            Scanner sc = new Scanner(System.in);
            System.out.println("=== Bai 3: Prime Check Client ===");
            System.out.print("Enter a number to check: ");
            int n = sc.nextInt();

            boolean result = stub.isPrime(n);
            System.out.println("Result from server: " + n + (result ? " is PRIME" : " is NOT PRIME"));
            sc.close();
        } catch (Exception e) {
            System.err.println("Client error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
