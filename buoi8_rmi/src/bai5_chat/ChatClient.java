import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;

public class ChatClient {
    public static void main(String[] args) {
        try {
            Registry registry = LocateRegistry.getRegistry("localhost", 1099);
            ChatInterface stub = (ChatInterface) registry.lookup("ChatService");

            Scanner sc = new Scanner(System.in);
            System.out.println("=== Bai 5: Simple Chat Client ===");
            System.out.println("Type 'exit' to quit.");
            System.out.println("-----------------------------------");

            while (true) {
                System.out.print("You: ");
                String msg = sc.nextLine();
                if (msg.equalsIgnoreCase("exit")) break;

                String reply = stub.sendMessage(msg);
                System.out.println(reply);
            }
            sc.close();
        } catch (Exception e) {
            System.err.println("Client error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
