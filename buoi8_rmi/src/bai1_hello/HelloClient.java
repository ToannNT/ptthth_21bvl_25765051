import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class HelloClient {
    public static void main(String[] args) {
        try {
            Registry registry = LocateRegistry.getRegistry("localhost", 1099);
            HelloInterface stub = (HelloInterface) registry.lookup("HelloService");
            String result = stub.sayHello();
            System.out.println("=== Bai 1: Hello Client ===");
            System.out.println("Response from server: " + result);
        } catch (Exception e) {
            System.err.println("Client error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
