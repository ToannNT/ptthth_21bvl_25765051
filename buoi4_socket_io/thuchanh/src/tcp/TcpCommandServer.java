package tcp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.Locale;

/**
 * Bai 4.2 - TCP Server theo giao thuc dong
 *
 * Cac lenh ho tro:
 * PING -> OK PONG
 * TIME -> OK <thoi-gian-hien-tai>
 * UPPER <text> -> OK <TEXT-VIET-HOA>
 * QUIT -> OK BYE (dong ket noi)
 * <khac> -> ERR UNKNOWN_COMMAND
 *
 * Han che: server phuc vu tuan tu (mot client tai mot thoi diem).
 * Khi co nhieu client ket noi dong thoi, client thu hai phai cho den
 * khi client thu nhat gui QUIT hoac dong ket noi.
 *
 * Bien dich va chay:
 * javac -d out src/tcp/TcpCommandServer.java src/tcp/TcpCommandClient.java
 * java -cp out tcp.TcpCommandServer
 */
public class TcpCommandServer {

    private static final int PORT = 5000;

    public static void main(String[] args) {
        try (ServerSocket server = new ServerSocket(PORT)) {
            System.out.println("TCP server listening on port " + PORT);
            while (true) {
                try (Socket socket = server.accept()) {
                    System.out.println("Client ket noi: "
                            + socket.getInetAddress().getHostAddress()
                            + ":" + socket.getPort());
                    serve(socket);
                    System.out.println("Client ngat ket noi.");
                } catch (IOException e) {
                    System.err.println("Loi phien client: " + e.getMessage());
                }
            }
        } catch (IOException e) {
            System.err.println("Khong mo duoc server: " + e.getMessage());
        }
    }

    static void serve(Socket socket) throws IOException {
        try (BufferedReader in = new BufferedReader(new InputStreamReader(
                socket.getInputStream(), StandardCharsets.UTF_8));
                PrintWriter out = new PrintWriter(new OutputStreamWriter(
                        socket.getOutputStream(), StandardCharsets.UTF_8), true)) {

            String request;
            while ((request = in.readLine()) != null) {
                System.out.println("Nhan: " + request);
                String response = process(request);
                out.println(response);
                System.out.println("Gui : " + response);
                if (request.equalsIgnoreCase("QUIT"))
                    break;
            }
        }
    }

    static String process(String request) {
        String trimmed = request.trim();
        if (trimmed.equalsIgnoreCase("PING"))
            return "OK PONG";
        if (trimmed.equalsIgnoreCase("TIME")) {
            return "OK " + LocalDateTime.now();
        }
        if (trimmed.equalsIgnoreCase("QUIT"))
            return "OK BYE";
        if (trimmed.regionMatches(true, 0, "UPPER ", 0, 6)) {
            return "OK " + trimmed.substring(6).toUpperCase(Locale.ROOT);
        }
        return "ERR UNKNOWN_COMMAND";
    }
}
