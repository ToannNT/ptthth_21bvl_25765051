import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;

public class SumClient {
    public static void main(String[] args) {
        try {
            Registry registry = LocateRegistry.getRegistry("localhost", 1099);
            SumInterface stub = (SumInterface) registry.lookup("SumService");

            Scanner sc = new Scanner(System.in);
            System.out.println("=== Bai 2: Sum Client ===");
            System.out.print("Enter a: ");
            int a = sc.nextInt();
            System.out.print("Enter b: ");
            int b = sc.nextInt();

            int result = stub.add(a, b);
            System.out.println("Result from server: " + a + " + " + b + " = " + result);
            sc.close();
        } catch (Exception e) {
            System.err.println("Client error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
