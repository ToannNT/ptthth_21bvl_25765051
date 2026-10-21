import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;

public class ContactImpl extends UnicastRemoteObject implements ContactInterface {

    private HashMap<String, String> contacts = new HashMap<>();

    public ContactImpl() throws RemoteException {
        super();
    }

    @Override
    public void addContact(String name, String phone) throws RemoteException {
        contacts.put(name, phone);
        System.out.println("[Server] Added contact: " + name + " - " + phone);
    }

    @Override
    public String findContact(String name) throws RemoteException {
        String phone = contacts.get(name);
        if (phone != null) {
            return "Name: " + name + ", Phone: " + phone;
        }
        return "Contact not found: " + name;
    }

    @Override
    public boolean deleteContact(String name) throws RemoteException {
        if (contacts.containsKey(name)) {
            contacts.remove(name);
            System.out.println("[Server] Deleted contact: " + name);
            return true;
        }
        return false;
    }
}
