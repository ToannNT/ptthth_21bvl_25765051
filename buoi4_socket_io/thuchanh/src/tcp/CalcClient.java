package tcp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

/**
 * Bai tap de xuat 4 - Client may tinh tu xa
 *
 * Su dung:
 * java -cp out tcp.CalcClient [host] [port]
 * (mac dinh: localhost 5002)
 *
 * Vi du cac lenh co the gui:
 * CALC 10 + 5 -> OK 15
 * CALC 7.5 * 4 -> OK 30.0
 * CALC 10 / 3 -> OK 3.3333...
 * CALC 10 / 0 -> ERR DIVISION_BY_ZERO
 * CALC abc + 1 -> ERR INVALID_NUMBER
 * HELP -> Hien thi huong dan
 * QUIT -> Dong ket noi
 */
public class CalcClient {

    public static void main(String[] args) {
        String host = args.length > 0 ? args[0] : "localhost";
        int port = args.length > 1 ? Integer.parseInt(args[1]) : 5002;

        try (Socket socket = new Socket(host, port);
                BufferedReader console = new BufferedReader(
                        new InputStreamReader(System.in, StandardCharsets.UTF_8));
                BufferedReader in = new BufferedReader(new InputStreamReader(
                        socket.getInputStream(), StandardCharsets.UTF_8));
                PrintWriter out = new PrintWriter(new OutputStreamWriter(
                        socket.getOutputStream(), StandardCharsets.UTF_8), true)) {

            System.out.println("May tinh tu xa - Ket noi den " + host + ":" + port);
            System.out.println("Cu phap: CALC <so> <toan-tu> <so>  (toan tu: + - * /)");
            System.out.println("Vi du  : CALC 10 + 5   |   CALC 7.5 * 4   |   HELP   |   QUIT");
            System.out.println("----------------------------------------");

            String line;
            while ((line = console.readLine()) != null) {
                out.println(line);
                String response = in.readLine();
                if (response == null) {
                    System.out.println("Server dong ket noi.");
                    break;
                }
                System.out.println("Server: " + response);
                if (line.trim().equalsIgnoreCase("QUIT"))
                    break;
            }

        } catch (NumberFormatException e) {
            System.err.println("Port phai la so nguyen.");
        } catch (IOException e) {
            System.err.println("Loi ket noi: " + e.getMessage());
        }
    }
}