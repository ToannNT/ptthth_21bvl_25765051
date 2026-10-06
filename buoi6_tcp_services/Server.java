import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Buoi 6 - Cau 1 (CLO1):
 * Server tao socket tai port 7000 va cho yeu cau ket noi tu client.
 * Server su dung luong (thread) de quan ly ket noi va cac yeu cau tu client(s).
 */
public class Server {
    public static final int PORT = 7000;

    // Bo dem STT client - atomic dam bao thread-safe
    private static final AtomicInteger clientCount = new AtomicInteger(0);

    public static void main(String[] args) {
        System.out.println("╔══════════════════════════════════════════╗");
        System.out.println("║     SERVER DANG KHOI DONG - PORT 7000    ║");
        System.out.println("╚══════════════════════════════════════════╝");

        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("[Server] Dang lang nghe ket noi...\n");

            while (true) {
                // Cho va chap nhan ket noi tu client
                Socket clientSocket = serverSocket.accept();
                int stt = clientCount.incrementAndGet();

                System.out.println("[Server] >>> Client #" + stt + " ket noi tu: "
                        + clientSocket.getInetAddress().getHostAddress()
                        + ":" + clientSocket.getPort());

                // Tao thread xu ly rieng cho tung client
                ClientHandler handler = new ClientHandler(clientSocket, stt);
                handler.start();
            }
        } catch (Exception e) {
            System.out.println("[Server] Loi: " + e.getMessage());
        }
    }
}
