package tcp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

/**
 * Bai tap de xuat 2 - Client chuyen so thanh chu
 *
 * Su dung:
 *   java -cp out tcp.NumberToWordClient [host] [port]
 *   (mac dinh: localhost 5001)
 *
 * Nhap lenh truc tiep:
 *   CONVERT 1234   -> nhan chu tieng Viet
 *   CONVERT -50    -> nhan "am nam muoi"
 *   QUIT           -> dong ket noi
 */
public class NumberToWordClient {

    public static void main(String[] args) {
        String host = args.length > 0 ? args[0] : "localhost";
        int port = args.length > 1 ? Integer.parseInt(args[1]) : 5001;

        try (Socket socket = new Socket(host, port);
                BufferedReader console = new BufferedReader(
                        new InputStreamReader(System.in, StandardCharsets.UTF_8));
                BufferedReader in = new BufferedReader(new InputStreamReader(
                        socket.getInputStream(), StandardCharsets.UTF_8));
                PrintWriter out = new PrintWriter(new OutputStreamWriter(
                        socket.getOutputStream(), StandardCharsets.UTF_8), true)) {

            System.out.println("Ket noi den " + host + ":" + port);
            System.out.println("Cac lenh: CONVERT <so>  |  QUIT");
            System.out.println("Vi du  : CONVERT 12345");
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