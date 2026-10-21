import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class BankImpl extends UnicastRemoteObject implements BankInterface {

    private double balance;

    public BankImpl(double initialBalance) throws RemoteException {
        super();
        this.balance = initialBalance;
    }

    @Override
    public double getBalance() throws RemoteException {
        return balance;
    }

    @Override
    public void deposit(double amount) throws RemoteException {
        if (amount > 0) {
            balance += amount;
            System.out.printf("[Server] Deposited: %.2f | New balance: %.2f%n", amount, balance);
        }
    }

    @Override
    public boolean withdraw(double amount) throws RemoteException {
        if (amount > 0 && balance >= amount) {
            balance -= amount;
            System.out.printf("[Server] Withdrawn: %.2f | New balance: %.2f%n", amount, balance);
            return true;
        }
        System.out.println("[Server] Insufficient balance for withdrawal: " + amount);
        return false;
    }
}
