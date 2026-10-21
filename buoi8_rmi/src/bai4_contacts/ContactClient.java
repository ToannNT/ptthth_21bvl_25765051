import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;

public class ContactClient {
    public static void main(String[] args) {
        try {
            Registry registry = LocateRegistry.getRegistry("localhost", 1099);
            ContactInterface stub = (ContactInterface) registry.lookup("ContactService");

            Scanner sc = new Scanner(System.in);
            System.out.println("=== Bai 4: Contact Manager Client ===");

            boolean running = true;
            while (running) {
                System.out.println("\n1. Add Contact");
                System.out.println("2. Find Contact");
                System.out.println("3. Delete Contact");
                System.out.println("4. Exit");
                System.out.print("Choose: ");
                int choice = sc.nextInt();
                sc.nextLine(); // consume newline

                switch (choice) {
                    case 1:
                        System.out.print("Name: ");
                        String name = sc.nextLine();
                        System.out.print("Phone: ");
                        String phone = sc.nextLine();
                        stub.addContact(name, phone);
                        System.out.println("Contact added successfully!");
                        break;
                    case 2:
                        System.out.print("Name to find: ");
                        String findName = sc.nextLine();
                        System.out.println(stub.findContact(findName));
                        break;
                    case 3:
                        System.out.print("Name to delete: ");
                        String delName = sc.nextLine();
                        boolean deleted = stub.deleteContact(delName);
                        System.out.println(deleted ? "Deleted successfully!" : "Contact not found!");
                        break;
                    case 4:
                        running = false;
                        break;
                    default:
                        System.out.println("Invalid choice!");
                }
            }
            sc.close();
        } catch (Exception e) {
            System.err.println("Client error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
