package tcp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

/**
 * Bai 4.2 - TCP Client theo giao thuc dong
 *
 * Su dung:
 * java -cp out tcp.TcpCommandClient [host] [port]
 * (mac dinh: localhost 5000)
 *
 * Cac lenh co the gui:
 * PING -> nhan "OK PONG"
 * TIME -> nhan thoi gian hien tai tu server
 * UPPER <text> -> nhan text viet hoa
 * QUIT -> dong ket noi
 * <bat ky> -> nhan "ERR UNKNOWN_COMMAND"
 */
public class TcpCommandClient {

    public static void main(String[] args) {
        String host = args.length > 0 ? args[0] : "localhost";
        int port = args.length > 1 ? Integer.parseInt(args[1]) : 5000;

        try (Socket socket = new Socket(host, port);
                BufferedReader console = new BufferedReader(
                        new InputStreamReader(System.in, StandardCharsets.UTF_8));
                BufferedReader in = new BufferedReader(new InputStreamReader(
                        socket.getInputStream(), StandardCharsets.UTF_8));
                PrintWriter out = new PrintWriter(new OutputStreamWriter(
                        socket.getOutputStream(), StandardCharsets.UTF_8), true)) {

            System.out.println("Connected to " + host + ":" + port);
            System.out.println("Enter PING, TIME, UPPER <text>, QUIT");
            System.out.println("----------------------------------------");

            String request;
            while ((request = console.readLine()) != null) {
                out.println(request);
                String response = in.readLine();
                if (response == null) {
                    System.out.println("Server closed the connection");
                    break;
                }
                System.out.println("Server: " + response);
                if (request.equalsIgnoreCase("QUIT"))
                    break;
            }

        } catch (NumberFormatException e) {
            System.err.println("Port phai la so nguyen");
        } catch (IOException e) {
            System.err.println("Loi ket noi: " + e.getMessage());
        }
    }
}
