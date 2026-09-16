package tcp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import number.NumberConverter;

/**
 * Bai tap de xuat 2 - May chu chuyen so thanh chu (cong 5001)
 *
 * Giao thuc:
 *   CONVERT <so>  -> OK <chuoi-chu>
 *   QUIT          -> OK BYE
 *   <khac>        -> ERR UNKNOWN_COMMAND
 *
 * Vi du:
 *   CONVERT 123   -> OK mot tram hai muoi ba
 *   CONVERT -45   -> OK am bon muoi lam
 *   CONVERT abc   -> ERR INVALID_NUMBER
 *
 * Bien dich va chay:
 *   javac -d out src/number/NumberConverter.java src/tcp/NumberToWordServer.java src/tcp/NumberToWordClient.java
 *   java -cp out tcp.NumberToWordServer
 */
public class NumberToWordServer {

    private static final int PORT = 5001;

    public static void main(String[] args) {
        try (ServerSocket server = new ServerSocket(PORT)) {
            System.out.println("NumberToWord TCP server lang nghe cong " + PORT);
            System.out.println("Giao thuc: CONVERT <so> | QUIT");
            while (true) {
                try (Socket socket = server.accept()) {
                    System.out.println("Client ket noi: "
                            + socket.getInetAddress().getHostAddress());
                    serve(socket);
                    System.out.println("Client ngat ket noi.");
                } catch (IOException e) {
                    System.err.println("Loi phien: " + e.getMessage());
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

            String line;
            while ((line = in.readLine()) != null) {
                System.out.println("Nhan: " + line);
                String response = process(line.trim());
                out.println(response);
                System.out.println("Gui : " + response);
                if (line.trim().equalsIgnoreCase("QUIT"))
                    break;
            }
        }
    }

    public static String process(String request) {
        if (request.equalsIgnoreCase("QUIT"))
            return "OK BYE";

        if (request.toUpperCase().startsWith("CONVERT ")) {
            String numStr = request.substring(8).trim();
            if (numStr.isEmpty())
                return "ERR MISSING_NUMBER";
            try {
                long number = Long.parseLong(numStr);
                if (number < -999_999_999 || number > 999_999_999)
                    return "ERR OUT_OF_RANGE (-999999999 den 999999999)";
                return "OK " + NumberConverter.convert(number);
            } catch (NumberFormatException e) {
                return "ERR INVALID_NUMBER (phai la so nguyen)";
            }
        }

        return "ERR UNKNOWN_COMMAND (dung: CONVERT <so> | QUIT)";
    }
}