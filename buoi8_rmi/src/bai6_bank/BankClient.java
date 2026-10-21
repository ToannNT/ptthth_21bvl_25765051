import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;

public class BankClient {
    public static void main(String[] args) {
        try {
            Registry registry = LocateRegistry.getRegistry("localhost", 1099);
            BankInterface stub = (BankInterface) registry.lookup("BankService");

            Scanner sc = new Scanner(System.in);
            System.out.println("=== Bai 6: Bank Client ===");

            boolean running = true;
            while (running) {
                System.out.println("\n1. Check Balance");
                System.out.println("2. Deposit");
                System.out.println("3. Withdraw");
                System.out.println("4. Exit");
                System.out.print("Choose: ");
                int choice = sc.nextInt();

                switch (choice) {
                    case 1:
                        double bal = stub.getBalance();
                        System.out.printf("Current Balance: %.2f%n", bal);
                        break;
                    case 2:
                        System.out.print("Deposit amount: ");
                        double dep = sc.nextDouble();
                        stub.deposit(dep);
                        System.out.printf("Deposited %.2f successfully!%n", dep);
                        break;
                    case 3:
                        System.out.print("Withdraw amount: ");
                        double wd = sc.nextDouble();
                        boolean success = stub.withdraw(wd);
                        System.out.println(success ? "Withdrawal successful!" : "Withdrawal failed! Insufficient funds.");
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
