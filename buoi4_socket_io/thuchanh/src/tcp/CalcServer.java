package tcp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

/**
 * Bai tap de xuat 4 - May tinh tu xa (cong 5002)
 *
 * Giao thuc tu thiet ke:
 * CALC <toan-hang-1> <toan-tu> <toan-hang-2>
 * Toan tu hop le: + - * /
 * HELP -> Hien thi huong dan su dung
 * QUIT -> Dong phien
 *
 * Phan hoi:
 * OK <ket-qua> Thanh cong
 * ERR DIVISION_BY_ZERO Chia cho khong
 * ERR INVALID_OPERATOR Toan tu khong hop le
 * ERR INVALID_FORMAT Cu phap lenh sai
 * ERR INVALID_NUMBER Toan hang khong phai so
 * ERR UNKNOWN_COMMAND Lenh khong biet
 *
 * Vi du phien lam viec:
 * CALC 10 + 5 -> OK 15.0
 * CALC 7.5 * 4 -> OK 30.0
 * CALC 10 / 0 -> ERR DIVISION_BY_ZERO
 * CALC abc + 1 -> ERR INVALID_NUMBER
 *
 * Bien dich va chay:
 * javac -d out src/tcp/CalcServer.java src/tcp/CalcClient.java
 * java -cp out tcp.CalcServer
 */
public class CalcServer {

    private static final int PORT = 5002;

    public static void main(String[] args) {
        try (ServerSocket server = new ServerSocket(PORT)) {
            System.out.println("May tinh tu xa lang nghe cong " + PORT);
            System.out.println("Giao thuc: CALC <a> <op> <b>  |  HELP  |  QUIT");
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

        if (request.equalsIgnoreCase("HELP"))
            return "OK Cu phap: CALC <so> <toan-tu> <so>  |  Toan tu: + - * /  |  QUIT";

        if (request.toUpperCase().startsWith("CALC ")) {
            String[] parts = request.substring(5).trim().split("\\s+");
            if (parts.length != 3)
                return "ERR INVALID_FORMAT (dung: CALC <a> <op> <b>)";

            double a, b;
            try {
                a = Double.parseDouble(parts[0]);
            } catch (NumberFormatException e) {
                return "ERR INVALID_NUMBER ('" + parts[0] + "' khong phai so)";
            }
            try {
                b = Double.parseDouble(parts[2]);
            } catch (NumberFormatException e) {
                return "ERR INVALID_NUMBER ('" + parts[2] + "' khong phai so)";
            }

            String op = parts[1];
            double result;
            switch (op) {
                case "+":
                    result = a + b;
                    break;
                case "-":
                    result = a - b;
                    break;
                case "*":
                    result = a * b;
                    break;
                case "/":
                    if (b == 0.0)
                        return "ERR DIVISION_BY_ZERO";
                    result = a / b;
                    break;
                default:
                    return "ERR INVALID_OPERATOR (chi chap nhan + - * /)";
            }

            // Hien thi so nguyen neu khong co phan thap phan
            if (result == Math.floor(result) && !Double.isInfinite(result)) {
                return "OK " + (long) result;
            }
            return "OK " + result;
        }

        return "ERR UNKNOWN_COMMAND (lenh hop le: CALC, HELP, QUIT)";
    }
}