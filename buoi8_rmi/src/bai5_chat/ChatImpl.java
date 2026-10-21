import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class ChatImpl extends UnicastRemoteObject implements ChatInterface {

    public ChatImpl() throws RemoteException {
        super();
    }

    @Override
    public String sendMessage(String message) throws RemoteException {
        System.out.println("[Server] Received: " + message);
        // Server auto-reply
        String reply = "[Server Reply] Got your message: \"" + message + "\". How can I help you?";
        return reply;
    }
}
